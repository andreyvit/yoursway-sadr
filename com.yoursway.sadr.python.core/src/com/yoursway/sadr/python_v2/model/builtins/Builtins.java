package com.yoursway.sadr.python_v2.model.builtins;

import com.yoursway.sadr.python_v2.model.LexicalScope;
import com.yoursway.sadr.python_v2.model.LexicalScopeImpl;
import com.yoursway.sadr.python_v2.model.PythonClass;

public class Builtins extends LexicalScopeImpl implements LexicalScope {
    public static PythonClass TYPE = new PythonClass();
    public static PythonClass OBJECT = new PythonClass();
    
    static void init(Builtins inst) {
        inst.setName("type", TYPE);
        inst.setName("object", OBJECT);
    }
    
    //---------Singletone infrastructure---------
    public Builtins() {
        init(this);
    }
    
    private static Builtins module = new Builtins();
    
    public static Builtins instance() {
        return module;
    }
    
    public static Builtins createModule() {
        return new Builtins();
    }
}
