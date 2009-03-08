package com.yoursway.sadr.python_v2.model.builtins.types;

import com.yoursway.sadr.python_v2.constructs.ImportDeclarationC;
import com.yoursway.sadr.python_v2.model.builtins.values.ModuleValue;

public class ModuleType extends PythonType {
    private ModuleType() {
    }
    
    public static ModuleType instance = new ModuleType();
    
    public static ModuleValue wrap(ImportDeclarationC decl, ModuleValue value) {
        ModuleValue moduleValue = new ModuleValue(value.getPath(), value.getAlias(), value.getVar());
        moduleValue.setDecl(decl);
        return moduleValue;
    }
    
    @Override
    public String describe() {
        return "module";
    }
}
