package com.yoursway.sadr.python.model.types;

import com.yoursway.sadr.python.constructs.ImportDeclarationC;
import com.yoursway.sadr.python.model.values.ModuleValue;

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
