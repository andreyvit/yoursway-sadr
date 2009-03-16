package com.yoursway.sadr.python_v2.model.builtins.types;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.yoursway.sadr.python_v2.model.RuntimeArguments;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.python_v2.model.builtins.values.ReflectedFunctionObject;

public class BuiltinType extends PythonType {
    public BuiltinType() {
        addClassFunctions();
    }
    
    protected void addClassFunctions() {
        Method[] methods = this.getClass().getMethods();
        for (Method method : methods) {
            String name = method.getName();
            Class<?>[] params = method.getParameterTypes();
            boolean returnOk = PythonObject.class.isAssignableFrom(method.getReturnType());
            if (returnOk && params.length == 1 && params[0].equals(RuntimeArguments.class)) {
                setAttribute(new ReflectedFunctionObject(this, name, method));
            } else if (name.startsWith("__") && name.endsWith("__")) {
                String mesg = "Method " + name + " of class " + this.getClass() + " has wrong arguments";
                throw new IllegalStateException(mesg);
                
            }
        }
    }
    
    Map<String, PythonObject> attributes = new HashMap<String, PythonObject>();
    
    public Map<String, PythonObject> getAttributes() {
        return attributes;
    }
    
    public void setAttribute(String name, PythonObject object) {
        if (null == name || null == object) {
            throw new IllegalArgumentException("setAttribute failed for class" + getClass().getSimpleName());
        }
        attributes.put(name, object);
    }
    
    @Override
    public PythonObject getBuiltinAttribute(String name) {
        if (attributes.containsKey(name))
            return attributes.get(name);
        else {
            for (PythonType cls : getSuperClasses()) {
                PythonObject object = cls.getBuiltinAttribute(name);
                if (object != null)
                    return object;
            }
            return null;
        }
    }
    
    public void setAttribute(PythonObject object) {
        setAttribute(object.name(), object);
    }
    
}
