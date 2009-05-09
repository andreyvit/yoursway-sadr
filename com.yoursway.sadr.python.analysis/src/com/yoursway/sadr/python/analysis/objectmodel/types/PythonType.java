package com.yoursway.sadr.python.analysis.objectmodel.types;

import static com.google.common.collect.Lists.immutableList;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kilim.State;
import kilim.pausable;
import kilim.fibers.Fiber;
import kilim.fibers.Task;

import com.yoursway.sadr.blocks.foundation.types.Type;
import com.yoursway.sadr.python.analysis.context.dynamic.Arguments;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.dynamic.arguments.Starness;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.objectmodel.values.BuiltinFunctionObject;
import com.yoursway.sadr.python.analysis.objectmodel.values.PythonValue;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSetBuilder;

/**
 * Represents a class type object. Supports class attributes name resolution.
 */
public abstract class PythonType extends PythonValue implements Type {
    
    private abstract static class ReflectedBuiltinFunctionObject extends BuiltinFunctionObject {
        protected final Method method;
        protected final PythonType self;
        
        private ReflectedBuiltinFunctionObject(Method method, PythonType self) {
            super(method.getName());
            this.method = method;
            this.self = self;
        }
        
        @Override
        @pausable
        protected PythonValueSet calculate(PythonDynamicContext dc) {
            try {
                return run(dc);
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            } catch (InvocationTargetException e) {
                Throwable target = e.getTargetException();
                if (target instanceof RuntimeException)
                    throw (RuntimeException) target;
                else if (target instanceof Error)
                    throw (Error) target;
                else
                    throw new RuntimeException(target);
            }
        }
        
        @pausable
        protected abstract PythonValueSet run(PythonDynamicContext dc) throws IllegalAccessException,
                InvocationTargetException;
    }
    
    private final Map<String, PythonValue> builtinAttributes;
    
    PythonType() {
        this.builtinAttributes = collectBuiltinAttributes();
    }
    
    private Map<String, PythonValue> collectBuiltinAttributes() {
        Map<String, PythonValue> attributes = new HashMap<String, PythonValue>();
        try {
            addClassAttributesViaReflection(attributes);
        } catch (Throwable e) {
            e.printStackTrace(System.err);
            System.exit(13);
        }
        return attributes;
    }
    
    private void addClassAttributesViaReflection(Map<String, PythonValue> attributes) {
        Method[] methods = this.getClass().getMethods();
        for (final Method method : methods)
            if (method.isAnnotationPresent(Builtin.class)) {
                String name = method.getName();
                if (!method.isAnnotationPresent(pausable.class))
                    throw new AssertionError("Must be pausable: method " + name + " in class "
                            + getClass().getName());
                final Class<?>[] params = method.getParameterTypes();
                if (params[params.length - 1] != Fiber.class)
                    continue; // not yet instrumented
                BuiltinFunctionObject func;
                if (method.getReturnType() == PythonValueSet.class && params.length == 2
                        && params[0] == PythonDynamicContext.class && params[1] == Fiber.class) {
                    func = new ReflectedBuiltinFunctionObject(method, this) {
                        @Override
                        @pausable
                        protected PythonValueSet run(PythonDynamicContext dc) throws IllegalAccessException,
                                InvocationTargetException {
                            Task.errNotWoven();
                            throw new UnsupportedOperationException();
                        }
                        
                        @SuppressWarnings("unused")
                        protected PythonValueSet run(PythonDynamicContext dc, Fiber fiber)
                                throws IllegalAccessException, InvocationTargetException {
                            fiber.down();
                            PythonValueSet result = (PythonValueSet) method.invoke(self, dc, fiber);
                            switch (fiber.up()) {
                            case 2: //save
                                State state = new State();
                                state.pc = 0;
                                state.self = this;
                                fiber.setState(state);
                                return null;
                            case 3: // unwind
                                return null;
                            case 1: // restore
                                // do nothing, we're returning anyway
                            }
                            return result;
                        }
                    };
                } else if (method.getReturnType() == PythonValue.class) {
                    func = new ReflectedBuiltinFunctionObject(method, this) {
                        @Override
                        @pausable
                        protected PythonValueSet run(PythonDynamicContext dc) throws IllegalAccessException,
                                InvocationTargetException {
                            Arguments arguments = dc.argumentsOfTopCall();
                            int paramCount = params.length - 1;
                            List<PythonValueSet> untypedArgs = new ArrayList<PythonValueSet>(paramCount);
                            for (int arg = 0; arg < paramCount; arg++)
                                untypedArgs.add(arguments.computeArgument(dc, arg, null, null,
                                    Starness.REGULAR));
                            List<Collection<?>> typedArgs = new ArrayList<Collection<?>>(paramCount);
                            int index = 0;
                            for (PythonValueSet valueSet : untypedArgs)
                                typedArgs.add(valueSet.obtainTypedValues(params[index++]));
                            
                            Object[] params = new Object[paramCount + 1];
                            PythonValueSetBuilder builder = PythonValueSet.newBuilder();
                            iterate(builder, params, typedArgs, method.getParameterAnnotations(), 0);
                            return builder.build();
                        }
                        
                        @pausable
                        private void iterate(PythonValueSetBuilder builder, Object[] params,
                                List<Collection<?>> args, Annotation[][] annotations, int index)
                                throws IllegalArgumentException, IllegalAccessException,
                                InvocationTargetException {
                            if (index == args.size()) {
                                builder.addResult(invoke(params));
                            } else {
                                boolean optional = false;
                                for (Annotation ann : annotations[index])
                                    if (ann instanceof Optional)
                                        optional = true;
                                Collection<?> items = args.get(index);
                                if (items.isEmpty() && optional) {
                                    params[index] = null; // unknown
                                    iterate(builder, params, args, annotations, index + 1);
                                } else {
                                    for (Object item : items) {
                                        params[index] = item;
                                        iterate(builder, params, args, annotations, index + 1);
                                    }
                                }
                            }
                        }
                        
                        @pausable
                        private PythonValue invoke(Object[] params) throws IllegalAccessException,
                                InvocationTargetException {
                            throw new UnsupportedOperationException();
                        }
                        
                        @SuppressWarnings("unused")
                        private PythonValue invoke(Object[] params, Fiber fiber)
                                throws IllegalAccessException, InvocationTargetException {
                            if (params != null)
                                params[params.length - 1] = fiber;
                            fiber.down();
                            PythonValue result = (PythonValue) method.invoke(self, params);
                            switch (fiber.up()) {
                            case 2: //save
                                State state = new State();
                                state.pc = 0;
                                state.self = this;
                                fiber.setState(state);
                                return null;
                            case 3: // unwind
                                return null;
                            case 1: // restore
                                // do nothing, we're returning anyway
                            }
                            return result;
                        }
                    };
                } else {
                    throw new AssertionError("Invalid signature of method " + name + " in class "
                            + getClass().getName());
                }
                attributes.put(name, func);
            }
    }
    
    public List<PythonType> getSuperClasses() {
        return immutableList((PythonType) ObjectType.instance);
    }
    
    @Override
    public PythonType getType() {
        return TypeType.instance;
    }
    
    @Override
    public String describe() {
        return "type";
    }
    
    @Override
    public boolean isInstance(PythonType type) {
        return this.equals(type) || getSuperClasses().contains(type);
    }
    
    public PythonValue coerce(PythonValue value) throws PythonException {
        throw new CoersionFailed();
    }
    
    protected boolean hasType(List<PythonValue> objects) {
        for (PythonValue object : objects) {
            if (object.isInstance(this))
                return true;
        }
        return false;
    }
    
    @pausable
    public PythonValueSet getAttr(String name, PythonLexicalContext sc, PythonDynamicContext dc) {
        PythonValue value = builtinAttributes.get(name);
        if (value != null)
            return new PythonValueSet(value);
        return PythonValueSet.EMPTY;
    }
    
}
