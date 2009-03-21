package com.yoursway.sadr.python.model.values;

import kilim.pausable;

import com.yoursway.sadr.python.constructs.CallableDeclaration;
import com.yoursway.sadr.python.constructs.PythonAnalHelpers;
import com.yoursway.sadr.python.model.types.FunctionType;
import com.yoursway.sadr.python.model.types.PythonType;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetBuilder;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;

public final class BoundFunctionObject extends PythonValue implements CallableObject {
    
    private final FunctionObject func;
    private final PythonValue self;
    
    public BoundFunctionObject(FunctionObject func, PythonValue self) {
        if (func == null)
            throw new NullPointerException("func is null");
        if (self == null)
            throw new NullPointerException("self is null");
        this.func = func;
        this.self = self;
    }
    
    @Override
    public String describe() {
        return "bound method " + name() + " of " + self.describe();
    }
    
    @Override
    public String name() {
        return func.name();
    }
    
    @Override
    public CallableDeclaration getDecl() {
        return func.getDecl();
    }
    
    @Override
    @pausable
    public void call(PythonDynamicContext dc, PythonValueSetBuilder builder) {
        PythonAnalHelpers.evaluateConstructs(getDecl().findReturnedValues(), dc, builder);
    }
    
    //    public PythonValueSet call(Krocodile crocodile, RuntimeArguments args) {
    //        List<PythonArgument> nodeArgs = getDecl().getArguments();
    //        ContextImpl context;
    //        try {
    //            context = new ContextImpl(nodeArgs, args);
    //        } catch (PythonException e) {
    //            return new PythonValueSet();
    //        }
    //        Map<String, PythonValueSet> defaults = new HashMap<String, PythonValueSet>();
    //        for (PythonArgument arg : nodeArgs) {
    //            String key = arg.getName();
    //            if (context.getActualArgument(key) == null) {
    //                PythonConstruct init = getDecl().getArgInit(key);
    //                if (init == null)
    //                    //XXX:can't find argument initialization. This is a true error.
    //                    return PythonValueSet.EMPTY;
    //                PythonValueSet argDefault = init.evaluate(crocodile);
    //                defaults.put(key, argDefault);
    //            }
    //        }
    //        PythonValueSet results = new PythonValueSet();
    //        for (Map<String, PythonValue> def : new DictIterator<String>(defaults)) {
    //            for (Entry<String, PythonValue> e : def.entrySet()) {
    //                context.put(e.getKey(), e.getValue());
    //            }
    //            Krocodile arguments = new Krocodile(crocodile, getDecl(), context);
    //            PythonValueSet result = getDecl().call(arguments);
    //            results.addResults(result);
    //        }
    //        return results;
    //    }
    
    @Override
    public PythonType getType() {
        return FunctionType.instance;
    }
    
}
