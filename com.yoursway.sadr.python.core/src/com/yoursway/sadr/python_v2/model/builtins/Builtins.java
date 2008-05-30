package com.yoursway.sadr.python_v2.model.builtins;

import com.yoursway.sadr.python.core.typeinferencing.values.BooleanValue;
import com.yoursway.sadr.python.core.typeinferencing.values.NilValue;

/**
 * Utility and declaration class.
 */
public class Builtins extends PythonClassType {
    
    private static PythonClassType typeType = null;
    private static PythonValue<NilValue> pythonNone;
    
    static PythonClassType getTypeType() {
        if (null == typeType) {
            typeType = new PythonClassType();
            typeType.setType(typeType);
        }
        return typeType;
    }
    
    public static PythonObject getNone() {
        if (pythonNone == null) {
            pythonNone = new PythonValue<NilValue>(getTypeType(), NilValue.instance());
        }
        return pythonNone;
    }
    
    public static PythonObject createTrue() {
        if (pythonTrue == null) {
            pythonTrue = new PythonValue<BooleanValue>(BooleanType.instance(), new BooleanValue(true));
        }
        return pythonTrue;
    }
    
    public static PythonObject createFalse() {
        if (pythonFalse == null) {
            pythonFalse = new PythonValue<BooleanValue>(BooleanType.instance(), new BooleanValue(false));
        }
        return pythonFalse;
    }
    
    public static final PythonClassType FUNCTION = getTypeType(); //TODO
    
    private static Builtins module = null;
    private static PythonValue<BooleanValue> pythonTrue;
    private static PythonValue<BooleanValue> pythonFalse;
    
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
            module.setAttribute("None", getNone());
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
