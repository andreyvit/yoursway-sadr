package com.yoursway.sadr.python.core.runtime.std;

import static com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo.emptyValueInfo;
import static com.yoursway.sadr.python.core.typeinferencing.valuesets.ValueSetFactory.valueSetWith;

import com.yoursway.sadr.python.core.runtime.PythonCallableArgument;
import com.yoursway.sadr.python.core.runtime.PythonBuiltinClassDefinition;
import com.yoursway.sadr.python.core.runtime.PythonBuiltinMethod;
import com.yoursway.sadr.python.core.runtime.PythonBuiltinProcedure;
import com.yoursway.sadr.python.core.runtime.PythonClass;
import com.yoursway.sadr.python.core.runtime.PythonRuntimeModel;
import com.yoursway.sadr.python.core.runtime.PythonSimpleType;
import com.yoursway.sadr.python.core.runtime.PythonUtils;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.python.core.typeinferencing.types.ArrayType;
import com.yoursway.sadr.python.core.typeinferencing.types.StubType;
import com.yoursway.sadr.python.core.typeinferencing.types.Type;
import com.yoursway.sadr.python.core.typeinferencing.types.UnknownType;
import com.yoursway.sadr.python.core.typeinferencing.typesets.TypeSet;
import com.yoursway.sadr.python.core.typeinferencing.typesets.TypeSetFactory;
import com.yoursway.sadr.python.core.typeinferencing.typesets.internal.SingleTypeSet;
import com.yoursway.sadr.python.core.typeinferencing.values.ArrayValue;

public class StandardTypesImpl implements StandardTypes {
    
    // ELLIPSIS -> OPTIONAL
    
    private final PythonClass objectClass;
    private final PythonSimpleType intType;
    private final PythonSimpleType stringType;
    private final PythonSimpleType nilType;
    
    public StandardTypesImpl(PythonRuntimeModel model) {
        objectClass = model.lookupClass("Object");
        new PythonBuiltinClassDefinition(objectClass, null);
        
        intType = new PythonSimpleType(model, "Fixnum");
        stringType = new PythonSimpleType(model, "String");
        nilType = new PythonSimpleType(model, "NilClass");
        
        new PythonBuiltinMethod(objectClass.metaClass(), "subclass", new PythonCallableArgument("subclass_name",
                PythonCallableArgument.Usage.REQUIRED),
                new PythonCallableArgument("instance_fields", PythonCallableArgument.Usage.OPTIONAL), new PythonCallableArgument(
                        "class_fields", PythonCallableArgument.Usage.OPTIONAL));
        new PythonBuiltinMethod(objectClass, "has_superclass", new PythonCallableArgument("name",
                PythonCallableArgument.Usage.REQUIRED));
        new PythonBuiltinMethod(objectClass.metaClass(), "has_superclass", new PythonCallableArgument("name",
                PythonCallableArgument.Usage.REQUIRED));
        new PythonBuiltinMethod(objectClass, "has_method",
                new PythonCallableArgument("name", PythonCallableArgument.Usage.REQUIRED));
        new PythonBuiltinMethod(objectClass, "classname");
        new PythonBuiltinMethod(objectClass, "error", new PythonCallableArgument("message", PythonCallableArgument.Usage.REQUIRED));
        new PythonBuiltinMethod(objectClass.metaClass(), "export", new PythonCallableArgument("attr...",
                PythonCallableArgument.Usage.OPTIONAL));
        new PythonBuiltinMethod(objectClass, "submustprovide", new PythonCallableArgument("message",
                PythonCallableArgument.Usage.OPTIONAL));
        new PythonBuiltinMethod(objectClass.metaClass(), "submustprovide", new PythonCallableArgument("message",
                PythonCallableArgument.Usage.OPTIONAL));
        new PythonBuiltinMethod(objectClass, "mesg", new PythonCallableArgument("method", PythonCallableArgument.Usage.REQUIRED),
                new PythonCallableArgument("expr...", PythonCallableArgument.Usage.OPTIONAL));
        new PythonBuiltinMethod(objectClass.metaClass(), "mesg", new PythonCallableArgument("method",
                PythonCallableArgument.Usage.REQUIRED), new PythonCallableArgument("expr...", PythonCallableArgument.Usage.OPTIONAL));
        
        new NewMethod(objectClass.metaClass(), model);
        
        new PythonBuiltinProcedure(model, "eval", new PythonCallableArgument("code", PythonCallableArgument.Usage.REQUIRED));
        
        new PythonBuiltinProcedure(model, "array", new PythonCallableArgument("dimensions...",
                PythonCallableArgument.Usage.OPTIONAL)) {
            
            @Override
            public ValueInfo evaluateBuiltin(ValueInfo[] arguments) {
                return ValueInfo.createResult(evaluateType(arguments), valueSetWith(new ArrayValue()));
            }
            
            private TypeSet evaluateType(ValueInfo[] arguments) {
                if (arguments.length <= 1)
                    return TypeSetFactory.typeSetWith(new ArrayType(UnknownType.INSTANCE));
                else
                    return PythonUtils.replaceWildcard(new ArrayType(StubType.WILDCARD), arguments[1]
                            .getTypeSet());
            }
            
        };
        
        new PythonBuiltinProcedure(model, "list",
                new PythonCallableArgument("dimensions...", PythonCallableArgument.Usage.OPTIONAL)) {
            
            @Override
            public ValueInfo evaluateBuiltin(ValueInfo[] arguments) {
                SingleTypeSet ts = TypeSetFactory.typeSetWith(new ArrayType(UnknownType.INSTANCE));
                return ValueInfo.createResult(ts, valueSetWith(new ArrayValue()));
            }
            
        };
        
        new PythonBuiltinProcedure(model, "dict",
                new PythonCallableArgument("dimensions...", PythonCallableArgument.Usage.OPTIONAL)) {
            
            @Override
            public ValueInfo evaluateBuiltin(ValueInfo[] arguments) {
                Type type = UnknownType.INSTANCE;
                for (int i = 0; i < arguments.length; i++)
                    type = new ArrayType(type);
                SingleTypeSet ts = TypeSetFactory.typeSetWith(type);
                return ValueInfo.createResult(ts, valueSetWith(new ArrayValue()));
            }
            
        };
        
        new PythonBuiltinProcedure(model, "sort", new PythonCallableArgument("collection", PythonCallableArgument.Usage.REQUIRED)) {
            
            @Override
            public ValueInfo evaluateBuiltin(ValueInfo[] arguments) {
                if (arguments.length > 0)
                    return arguments[0];
                else
                    return emptyValueInfo();
            }
            
        };
        
        new PythonBuiltinProcedure(model, "ty", new PythonCallableArgument("expr...", PythonCallableArgument.Usage.OPTIONAL));
        new PythonBuiltinProcedure(model, "type", new PythonCallableArgument("expr...", PythonCallableArgument.Usage.OPTIONAL));
        new PythonBuiltinProcedure(model, "typef", new PythonCallableArgument("expr...", PythonCallableArgument.Usage.OPTIONAL));
        
        new PythonBuiltinProcedure(model, "exif", new PythonCallableArgument("condition", PythonCallableArgument.Usage.REQUIRED));
        
        if (false)
            for (String name : new String[] { "setscroll", "upcase", "lowcase", "string", "fputstr",
                    "setvideo", "fmtstr", "isnil", "showfield", "fclose", "_prof_", "fopenw", "fparse",
                    "%getkey", "is_ident", "isnumber", "isstring", "isarray", "isinst", "sizeof", "isdict",
                    "symbol", "int", "chr", "mod", "repl", "load_bltin", "lookup", "compile", "MSG_Error",
                    "break", "exit", "continue", "dumpobj", "LOC_Translate", "MSG_Info", "msg_warning",
                    "MSG_Message", "extr", "%date", "%time", "collapse", "compress", "concat", "elem",
                    "ffspec", "fopenr", "fgetstr", "logtype", "ascii2unicode", "number", "posstr", "pow",
                    "slist", "stackdump", "tail", "head", "throw", "totwobyte", "trim", "typa", "isdefined",
                    "length", "lognam",
                    "logty",
                    "match",
                    "mirrodo",
                    "slist",
                    "str2dis",
                    //
                    "gra_align", "gra_box", "gra_cap", "gra_clip", "gra_color", "gra_image", "gra_coldef",
                    "gra_dash", "gra_info", "gra_inkget", "gra_inkdef", "gra_inkload", "gra_inktab",
                    "gra_name", "gra_origin", "gra_outlinebox", "gra_page", "gra_paintmode", "gra_rule",
                    "gra_select", "gra_size", "gra_textmeasure", "gra_text", "gra_width", "backgroundedtext",
                    "FNT_GetDefaultFont", "fnt_info", "FNT_Open", "fnt_set", "FontDB_FindFontBG",
                    "FontDB_FindFontComposed", "FontDB_FindFontOT", "FontDB_FindFontPS",
                    "FontDB_GetBGFontNr", "FontDB_GetFontInfo", "FontDB_GetGUIInfo" }) {
                new RubyBuiltinProcedure(model, name,
                        new RubyArgument("args...", RubyArgument.Usage.OPTIONAL));
            }
    }
    
    public PythonClass objectClass() {
        return objectClass;
    }
    
    public PythonSimpleType intType() {
        return intType;
    }
    
    public PythonSimpleType stringType() {
        return stringType;
    }
    
    public PythonSimpleType nilType() {
        return nilType;
    }
    
}
