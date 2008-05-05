package com.yoursway.sadr.python_v2.model.builtins;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.model.LexicalScopeImpl;

/**
 * Utility and declaration class.
 */
public class Builtins extends LexicalScopeImpl {
    
    private static PythonClassType typeType = null;
    
    private static PythonClass createTypeType() {
        if (null == typeType) {
            typeType = new PythonClassType();
            typeType.setType(typeType);
        }
        return typeType;
    }
    
    public static PythonClass TYPE = createTypeType(); //TODO
    public static PythonClass OBJECT = createTypeType(); //TODO
    public static PythonClass FUNCTION = createTypeType(); //TODO
    public static PythonClass MODULE = createTypeType(); //TODO
    public static IntType INT = IntType.instance();
    public static StringType STRING = StringType.instance();
    
    private static void init(Builtins inst) {
        inst.setName("type", TYPE);
        inst.setName("object", OBJECT);
    }
    
    //---------Singleton infrastructure---------
    private Builtins() {
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
    
    public Scope parentScope() {
        return null;
    }
}
