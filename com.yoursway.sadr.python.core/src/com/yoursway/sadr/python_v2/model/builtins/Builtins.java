package com.yoursway.sadr.python_v2.model.builtins;

import com.yoursway.sadr.python.core.typeinferencing.values.NilValue;

/**
 * Utility and declaration class.
 */
public class Builtins extends PythonClassType {
    
    private static PythonClassType typeType = null;
    
    static PythonClassType getTypeType() {
        if (null == typeType) {
            typeType = new PythonClassType();
            typeType.setType(typeType);
        }
        return typeType;
    }
    
    public static PythonObject createNone() {
        return new PythonValue<NilValue>(NONE, NilValue.instance());
    }
    
    public static final PythonClassType FUNCTION = getTypeType(); //TODO
    public static final PythonClassType MODULE = getTypeType(); //TODO
    public static final PythonClassType NONE = getTypeType();
    
    private static Builtins module = null;
    
    //---------Singleton infrastructure---------
    private Builtins() {
    }
    
    public static Builtins instance() {
        if (module == null) {
            module = new Builtins();
            module.setAttribute("type", getTypeType());
            module.setAttribute("object", ObjectType.instance());
            module.setAttribute("int", IntType.instance());
            module.setAttribute("str", StringType.instance());
            module.setAttribute("bool", BoolType.instance());
            module.setAttribute("list", ListType.instance());
            module.setAttribute("tuple", TupleType.instance());
            module.setAttribute("dict", DictType.instance());
        }
        return module;
    }
}
