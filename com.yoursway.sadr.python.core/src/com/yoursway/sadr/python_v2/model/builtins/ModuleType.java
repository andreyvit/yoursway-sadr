package com.yoursway.sadr.python_v2.model.builtins;

import com.yoursway.sadr.python_v2.constructs.ImportDeclarationC;

public class ModuleType extends PythonClassType {
    public ModuleType() {
    }
    
    private static ModuleType instance;
    
    public static ModuleType instance() {
        if (instance == null) {
            instance = new ModuleType();
        }
        return instance;
    }
    
    public static PythonValue<ModuleValue> wrap(ImportDeclarationC decl, ModuleValue value) {
        return new PythonValue<ModuleValue>(instance(), value, decl);
    }
    
    @Override
    public String describe() {
        return "module";
    }
    
}
