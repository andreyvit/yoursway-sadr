package com.yoursway.sadr.ruby.core.runtime.std;

import static com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo.createResult;
import static com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo.emptyValueInfo;
import static com.yoursway.sadr.ruby.core.typeinferencing.valuesets.ValueSetFactory.valueSetWith;

import com.yoursway.sadr.ruby.core.runtime.RubyArgument;
import com.yoursway.sadr.ruby.core.runtime.RubyBuiltinClassDefinition;
import com.yoursway.sadr.ruby.core.runtime.RubyBuiltinMethod;
import com.yoursway.sadr.ruby.core.runtime.RubyBuiltinProcedure;
import com.yoursway.sadr.ruby.core.runtime.RubyClass;
import com.yoursway.sadr.ruby.core.runtime.RubyRuntimeModel;
import com.yoursway.sadr.ruby.core.runtime.RubySimpleType;
import com.yoursway.sadr.ruby.core.runtime.RubyUtils;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.types.ArrayType;
import com.yoursway.sadr.ruby.core.typeinferencing.types.StubType;
import com.yoursway.sadr.ruby.core.typeinferencing.types.Type;
import com.yoursway.sadr.ruby.core.typeinferencing.types.UnknownType;
import com.yoursway.sadr.ruby.core.typeinferencing.typesets.TypeSet;
import com.yoursway.sadr.ruby.core.typeinferencing.typesets.TypeSetFactory;
import com.yoursway.sadr.ruby.core.typeinferencing.typesets.internal.SingleTypeSet;
import com.yoursway.sadr.ruby.core.typeinferencing.values.ArrayValue;

public class StandardTypesImpl implements StandardTypes {
    
    // ELLIPSIS -> OPTIONAL
    
    private final RubyClass objectClass;
    private final RubySimpleType intType;
    private final RubySimpleType stringType;
    private final RubySimpleType nilType;
    
    public StandardTypesImpl(RubyRuntimeModel model) {
        objectClass = model.lookupClass("Ruby$object");
        new RubyBuiltinClassDefinition(objectClass, null);
        
        intType = new RubySimpleType(model, "int");
        stringType = new RubySimpleType(model, "string");
        nilType = new RubySimpleType(model, "nil");
        
        new RubyBuiltinMethod(objectClass.metaClass(), "subclass", new RubyArgument("subclass_name",
                RubyArgument.Usage.REQUIRED),
                new RubyArgument("instance_fields", RubyArgument.Usage.OPTIONAL), new RubyArgument(
                        "class_fields", RubyArgument.Usage.OPTIONAL));
        new RubyBuiltinMethod(objectClass, "has_superclass", new RubyArgument("name",
                RubyArgument.Usage.REQUIRED));
        new RubyBuiltinMethod(objectClass.metaClass(), "has_superclass", new RubyArgument("name",
                RubyArgument.Usage.REQUIRED));
        new RubyBuiltinMethod(objectClass, "has_method",
                new RubyArgument("name", RubyArgument.Usage.REQUIRED));
        new RubyBuiltinMethod(objectClass, "classname");
        new RubyBuiltinMethod(objectClass, "error", new RubyArgument("message", RubyArgument.Usage.REQUIRED));
        new RubyBuiltinMethod(objectClass.metaClass(), "export", new RubyArgument("attr...",
                RubyArgument.Usage.OPTIONAL));
        new RubyBuiltinMethod(objectClass, "submustprovide", new RubyArgument("message",
                RubyArgument.Usage.OPTIONAL));
        new RubyBuiltinMethod(objectClass.metaClass(), "submustprovide", new RubyArgument("message",
                RubyArgument.Usage.OPTIONAL));
        new RubyBuiltinMethod(objectClass, "mesg", new RubyArgument("method", RubyArgument.Usage.REQUIRED),
                new RubyArgument("expr...", RubyArgument.Usage.OPTIONAL));
        new RubyBuiltinMethod(objectClass.metaClass(), "mesg", new RubyArgument("method",
                RubyArgument.Usage.REQUIRED), new RubyArgument("expr...", RubyArgument.Usage.OPTIONAL));
        
        new NewMethod(objectClass.metaClass(), model);
        
        new RubyBuiltinProcedure(model, "eval", new RubyArgument("code", RubyArgument.Usage.REQUIRED));
        
        new RubyBuiltinProcedure(model, "array", new RubyArgument("dimensions...",
                RubyArgument.Usage.OPTIONAL)) {
            
            @Override
            public ValueInfo evaluateBuiltin(ValueInfo[] arguments) {
                return createResult(evaluateType(arguments), valueSetWith(new ArrayValue()));
            }
            
            private TypeSet evaluateType(ValueInfo[] arguments) {
                if (arguments.length <= 1)
                    return TypeSetFactory.typeSetWith(new ArrayType(UnknownType.INSTANCE));
                else
                    return RubyUtils.replaceWildcard(new ArrayType(StubType.WILDCARD), arguments[1]
                            .getTypeSet());
            }
            
        };
        
        new RubyBuiltinProcedure(model, "list",
                new RubyArgument("dimensions...", RubyArgument.Usage.OPTIONAL)) {
            
            @Override
            public ValueInfo evaluateBuiltin(ValueInfo[] arguments) {
                SingleTypeSet ts = TypeSetFactory.typeSetWith(new ArrayType(UnknownType.INSTANCE));
                return createResult(ts, valueSetWith(new ArrayValue()));
            }
            
        };
        
        new RubyBuiltinProcedure(model, "dict",
                new RubyArgument("dimensions...", RubyArgument.Usage.OPTIONAL)) {
            
            @Override
            public ValueInfo evaluateBuiltin(ValueInfo[] arguments) {
                Type type = UnknownType.INSTANCE;
                for (int i = 0; i < arguments.length; i++)
                    type = new ArrayType(type);
                SingleTypeSet ts = TypeSetFactory.typeSetWith(type);
                return createResult(ts, valueSetWith(new ArrayValue()));
            }
            
        };
        
        new RubyBuiltinProcedure(model, "sort", new RubyArgument("collection", RubyArgument.Usage.REQUIRED)) {
            
            @Override
            public ValueInfo evaluateBuiltin(ValueInfo[] arguments) {
                if (arguments.length > 0)
                    return arguments[0];
                else
                    return emptyValueInfo();
            }
            
        };
        
        new RubyBuiltinProcedure(model, "ty", new RubyArgument("expr...", RubyArgument.Usage.OPTIONAL));
        new RubyBuiltinProcedure(model, "type", new RubyArgument("expr...", RubyArgument.Usage.OPTIONAL));
        new RubyBuiltinProcedure(model, "typef", new RubyArgument("expr...", RubyArgument.Usage.OPTIONAL));
        
        new RubyBuiltinProcedure(model, "exif", new RubyArgument("condition", RubyArgument.Usage.REQUIRED));
        
        for (String name : new String[] { "setscroll", "upcase", "lowcase", "string", "fputstr", "setvideo",
                "fmtstr", "isnil", "showfield", "fclose", "_prof_", "fopenw", "fparse", "%getkey",
                "is_ident", "isnumber", "isstring", "isarray", "isinst", "sizeof", "isdict", "symbol", "int",
                "chr", "mod", "repl", "load_bltin", "lookup", "compile", "MSG_Error", "break", "exit",
                "continue", "dumpobj", "LOC_Translate", "MSG_Info", "msg_warning", "MSG_Message", "extr",
                "%date", "%time", "collapse", "compress", "concat", "elem", "ffspec", "fopenr", "fgetstr",
                "logtype", "ascii2unicode", "number", "posstr", "pow", "slist", "stackdump", "tail", "head",
                "throw", "totwobyte", "trim", "typa", "isdefined", "length",
                "lognam",
                "logty",
                "match",
                "mirrodo",
                "slist",
                "str2dis",
                //
                "gra_align", "gra_box", "gra_cap", "gra_clip", "gra_color", "gra_image", "gra_coldef",
                "gra_dash", "gra_info", "gra_inkget", "gra_inkdef", "gra_inkload", "gra_inktab", "gra_name",
                "gra_origin", "gra_outlinebox", "gra_page", "gra_paintmode", "gra_rule", "gra_select",
                "gra_size", "gra_textmeasure", "gra_text", "gra_width", "backgroundedtext",
                "FNT_GetDefaultFont", "fnt_info", "FNT_Open", "fnt_set", "FontDB_FindFontBG",
                "FontDB_FindFontComposed", "FontDB_FindFontOT", "FontDB_FindFontPS", "FontDB_GetBGFontNr",
                "FontDB_GetFontInfo", "FontDB_GetGUIInfo" }) {
            new RubyBuiltinProcedure(model, name, new RubyArgument("args...", RubyArgument.Usage.OPTIONAL));
        }
        
        model.lookupGlobalVariable("Ruby$debug");
        model.lookupGlobalVariable("Ruby$globl");
        model.lookupGlobalVariable("Ruby$exception");
    }
    
    public RubyClass objectClass() {
        return objectClass;
    }
    
    public RubySimpleType intType() {
        return intType;
    }
    
    public RubySimpleType stringType() {
        return stringType;
    }
    
    public RubySimpleType nilType() {
        return nilType;
    }
    
}
