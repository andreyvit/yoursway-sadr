package com.yoursway.sadr.python_v2.model.builtins;

import com.yoursway.sadr.python_v2.model.LexicalScope;
import com.yoursway.sadr.python_v2.model.LexicalScopeImpl;
import com.yoursway.sadr.python_v2.model.PythonClass;

/**
 * Utility and declaration class.
 */
public class Builtins extends LexicalScopeImpl implements LexicalScope {
    public static PythonClass TYPE = new PythonClass(); //TODO
    public static PythonClass OBJECT = new PythonClass(); //TODO
    public static PythonClass FUNCTION = new PythonClass(); //TODO
    public static PythonClass MODULE = new PythonClass(); //TODO
    
    static void init(Builtins inst) {
        inst.setName("type", TYPE);
        inst.setName("object", OBJECT);
    }
    
    //---------Singletone infrastructure---------
    public Builtins() {
        super(null);
        init(this);
    }
    
    private static Builtins module = new Builtins();
    
    public static Builtins getBuiltinModule() {
        return module;
    }
    
    public static Builtins createModule() {
        return new Builtins();
    }
}
