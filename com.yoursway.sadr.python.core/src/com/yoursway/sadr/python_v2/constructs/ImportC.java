package com.yoursway.sadr.python_v2.constructs;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.builtins.ModuleType;
import com.yoursway.sadr.python_v2.model.builtins.ModuleValue;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;
import com.yoursway.sadr.succeeder.IGoal;

public abstract class ImportC<N extends ASTNode> extends PythonConstructImpl<N> implements ImportDeclarationC {
    
    private final HashMap<String, ModuleValue> imports;
    
    public ImportC(Scope sc, N node) {
        super(sc, node);
        imports = new HashMap<String, ModuleValue>();
    }
    
    @Override
    protected void wrapEnclosedChildren() {
        setPostChildren(new ArrayList<PythonConstruct>());
    }
    
    public boolean hasImport(String variable) {
        return imports.containsKey(variable);
    }
    
    //    public PythonConstruct resolveImport(PythonValue<ModuleValue> value, String var) {
    //        ModuleValue module = value.getValue();
    //        String realPath = module.getPath() + '.' + var;
    //        return resolveImport(value, realPath);
    //    }
    //    
    public PythonConstruct resolveImport(PythonValue<ModuleValue> value, String path) throws Exception {
        ModuleValue module = value.getValue();
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
    
    public PythonValue<ModuleValue> resolveAlias(String alias) {
        ModuleValue value = imports.get(alias);
        if (value != null)
            return ModuleType.wrap(this, value);
        else
            return null;
    }
    
    @Override
    public IGoal evaluate(Context context, PythonValueSetAcceptor acceptor) {
        return new PassResultGoal(context, acceptor, null);
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
    
    @Override
    public boolean match(Frog frog) {
        return hasImport(frog.getAccessor());
    }
}
