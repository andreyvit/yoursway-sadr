package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import java.util.HashMap;

import kilim.pausable;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.analysis.lang.constructs.support.VoidConstructException;
import com.yoursway.sadr.python.analysis.objectmodel.types.ModuleType;
import com.yoursway.sadr.python.analysis.objectmodel.values.ModuleValue;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public abstract class ImportC<N extends ASTNode> extends PythonConstructImpl<N> implements ImportDeclarationC {
    
    private final HashMap<String, ModuleValue> imports;
    
    public ImportC(PythonStaticContext sc, N node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        imports = new HashMap<String, ModuleValue>();
    }
    
    public boolean hasImport(String variable) {
        return imports.containsKey(variable);
    }
    
    //    public PythonConstruct resolveImport(ModuleValue value, String var) {
    //        ModuleValue module = value.getValue();
    //        String realPath = module.getPath() + '.' + var;
    //        return resolveImport(value, realPath);
    //    }
    //    
    public PythonConstruct resolveImport(ModuleValue value, String path) throws Exception {
        ModuleValue module = value;
        if (module.getVar() != null) {
            throw new Exception("Looking for variable, but already got variable! You suck!");
        }
        return resolvePath(module.getPath());
    }
    
    public PythonFileC resolvePath(String path) {
        //        String realPath = path.replace(".", "/");
        //        ProjectRuntime runtime = innerScope().getFileScope().getProjectRuntime();
        //        PythonFileC module = runtime.getModule(realPath + "/__init__.py");
        //        if (module == null)
        //            module = runtime.getModule(realPath + ".py");
        //        return module;
        return null;
    }
    
    public ModuleValue resolveAlias(String alias) {
        ModuleValue value = imports.get(alias);
        if (value != null)
            return ModuleType.wrap(this, value);
        else
            return null;
    }
    
    protected void addModule(String path, String alias, String var) {
        ModuleValue value = new ModuleValue(path, alias, var);
        if (alias.lastIndexOf(".") != -1) {
            int pos = alias.lastIndexOf(".");
            String parentPath = alias.substring(0, pos);
            addModule(parentPath, parentPath, null);
        }
        imports.put(alias, value);
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc) {
        throw new VoidConstructException(this);
    }
    
}
