package com.yoursway.sadr.python_v2.model.builtins;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.python.parser.ast.PythonClassDeclaration;

import com.yoursway.sadr.python.core.runtime.PythonMethod;
import com.yoursway.sadr.python.core.runtime.requestors.methods.MethodRequestor;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

/**
 * Represents a class type object. Supports class attributes name resolution.
 */
public class PythonClassType extends PythonObject {
    
    private final class ReflectedFunctionObject extends SyncFunctionObject {
        private final Method method;
        
        private ReflectedFunctionObject(String name, Method method) {
            super(name);
            this.method = method;
        }
        
        @Override
        public RuntimeObject evaluate(PythonArguments args) {
            try {
                return (RuntimeObject) method.invoke(PythonClassType.this, args);
            } catch (RuntimeException e) {
                e.printStackTrace();
                throw e;
            } catch (Exception e) {
                if (e.getCause() != null && e.getCause() instanceof RuntimeException)
                    throw (RuntimeException) e.getCause();
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
    
    private List<PythonClassType> supers;
    
    public PythonClassType() {
        super(null);
        supers = new ArrayList<PythonClassType>(1);
        supers.add(ObjectType.instance());
        setType(this);
        addClassFunctions();
    }
    
    private void addClassFunctions() {
        Method[] methods = this.getClass().getMethods();
        for (Method method : methods) {
            String name = method.getName();
            if (name.startsWith("__") && name.endsWith("__")) {
                setAttribute(new ReflectedFunctionObject(name, method));
            }
        }
    }
    
    public PythonClassType(PythonClassType superClass) {
        super(Builtins.getTypeType());
        supers = new ArrayList<PythonClassType>(1);
        supers.add(superClass);
    }
    
    public PythonClassType(List<PythonClassType> supers) {
        super(Builtins.getTypeType());
        setSuperClasses(supers);
    }
    
    public List<PythonClassType> getSuperClasses() {
        return supers;
    }
    
    public void setSuperClasses(List<PythonClassType> supers) {
        if (supers == null)
            this.supers = new ArrayList<PythonClassType>(0);
        else
            this.supers = supers;
    }
    
    @Override
    public RuntimeObject getAttribute(String name) {
        if (attributes.containsKey(name))
            return attributes.get(name);
        else
            return lookupInSuperclasses(name);
        
    }
    
    protected RuntimeObject lookupInSuperclasses(String name) {
        for (PythonClassType cls : supers) {
            RuntimeObject object = cls.getAttributes().get(name);
            if (object != null)
                return object;
        }
        return null;
    }
    
    public void setAttribute(FunctionObject object) {
        setAttribute(object.name(), object);
    }
    
    @Override
    public String describe() {
        PythonConstruct decl = instanceHistory().sourceDeclaration();
        if (decl == null) {
            return "type";
        }
        ASTNode node = decl.node();
        if (node instanceof PythonClassDeclaration) {
            return ((PythonClassDeclaration) node).getName();
        } else
            return "(unknown)";
    }
    
    public void findMethodsByPrefix(String prefix, Collection<PythonMethod> methods) {
        // TODO Auto-generated method stub
        
    }
    
    public void findMethod(String name, MethodRequestor requestor) {
        // TODO Auto-generated method stub
        
    }
}