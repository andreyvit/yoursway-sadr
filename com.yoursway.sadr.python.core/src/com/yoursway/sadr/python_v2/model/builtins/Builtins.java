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
    
    public static final PythonClass TYPE = createTypeType(); //TODO
    public static final ObjectType OBJECT = ObjectType.instance(); //TODO
    public static final PythonClass FUNCTION = createTypeType(); //TODO
    public static final PythonClass MODULE = createTypeType(); //TODO
    public static final IntType INT = IntType.instance();
    public static final BoolType BOOL = BoolType.instance();
    public static final StringType STRING = StringType.instance();
    public static final PythonClass NONE = createTypeType();
    
    private static void init(Builtins inst) {
        inst.setName("type", TYPE);
        inst.setName("object", OBJECT);
        inst.setName("int", INT);
        inst.setName("str", STRING);
        inst.setName("bool", BOOL);
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
