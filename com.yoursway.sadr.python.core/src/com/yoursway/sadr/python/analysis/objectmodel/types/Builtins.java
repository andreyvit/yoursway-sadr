package com.yoursway.sadr.python.analysis.objectmodel.types;

import com.yoursway.sadr.python.analysis.objectmodel.values.BooleanValue;
import com.yoursway.sadr.python.analysis.objectmodel.values.NoneValue;

/**
 * Utility and declaration class.
 */
public class Builtins extends BuiltinType {
    
    private static Builtins module = null;
    
    //---------Singleton infrastructure---------
    private Builtins() {
    }
    
    public static Builtins instance() {
        if (module == null) {
            module = new Builtins();
            module.setAttribute("type", TypeType.instance);
            module.setAttribute("object", ObjectType.instance);
            module.setAttribute("int", IntegerType.instance);
            module.setAttribute("long", LongType.instance);
            module.setAttribute("float", FloatType.instance);
            module.setAttribute("complex", ComplexType.instance);
            module.setAttribute("str", StringType.instance);
            module.setAttribute("unicode", UnicodeType.instance);
            module.setAttribute("bool", BooleanType.instance);
            module.setAttribute("list", ListType.instance);
            module.setAttribute("tuple", TupleType.instance);
            module.setAttribute("dict", DictType.instance);
            module.setAttribute("None", NoneValue.instance);
            module.setAttribute("True", BooleanValue.instance_true);
            module.setAttribute("False", BooleanValue.instance_false);
            //            module.setAttribute(new RedirectFunctionObject("len", "__len__"));
            //            module.setAttribute(new RedirectFunctionObject("str", "__str__"));
            //            module.setAttribute(new RedirectFunctionObject("repr", "__repr__"));
            //            module.setAttribute(new RedirectFunctionObject("unicode", "__unicode__"));
            //            module.setAttribute(new BuiltinFunctionObject("chr") {
            //                @Override
            //                public PythonValue evaluate(RuntimeArguments args) throws PythonException {
            //                    NumericValue chr = IntegerType.instance.coerce(args.readSingle());
            //                    if (!chr.coercibleToInt())
            //                        throw new CoersionFailed();
            //                    long code = chr.coerceToInt();
            //                    if (code > 255 || code < 0)
            //                        return null; //ValueError
            //                    return StringType.wrap(String.valueOf((char) code));
            //                    
            //                }
            //            });
            //            module.setAttribute(new BuiltinFunctionObject("unichr") {
            //                @Override
            //                public PythonValue evaluate(RuntimeArguments args) throws PythonException {
            //                    NumericValue chr = IntegerType.instance.cast(args.readSingle());
            //                    if (!chr.coercibleToInt())
            //                        throw new CoersionFailed();
            //                    long code = chr.coerceToInt();
            //                    if (code > 65535 || code < 0)
            //                        return null; //ValueError
            //                    return StringType.wrap(String.valueOf((char) code), true);
            //                    
            //                }
            //            });
            //            
        }
        return module;
    }
}
