package com.yoursway.sadr.python_v2.model.builtins;

import com.yoursway.sadr.python.core.typeinferencing.values.BooleanValue;
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
    
    public static PythonObject createTrue() {
        return new PythonValue<BooleanValue>(BooleanType.instance(), new BooleanValue(true));
    }
    
    public static PythonObject createFalse() {
        return new PythonValue<BooleanValue>(BooleanType.instance(), new BooleanValue(false));
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
            module.setAttribute("int", IntegerType.instance());
            module.setAttribute("str", StringType.instance());
            module.setAttribute("bool", BooleanType.instance());
            module.setAttribute("list", ListType.instance());
            module.setAttribute("tuple", TupleType.instance());
            module.setAttribute("dict", DictType.instance());
            module.setAttribute("None", createNone());
            module.setAttribute("True", createTrue());
            module.setAttribute("False", createFalse());
            module.setAttribute(new RedirectFunctionObject("len", "__len__"));
            module.setAttribute(new RedirectFunctionObject("repr", "__repr__"));
            module.setAttribute(new RedirectFunctionObject("unicode", "__unicode__"));
            module.setAttribute(new RedirectFunctionObject("__unicode__", "__str__"));
        }
        return module;
    }
}
