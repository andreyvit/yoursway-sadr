package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python_v2.constructs.ImportDeclarationC;
import com.yoursway.sadr.python_v2.constructs.PythonFileC;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.builtins.ModuleValue;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;
import com.yoursway.sadr.succeeder.Goal;

public class ResolveModuleImportGoal extends Goal {
    
    private final PythonValueSetAcceptor acceptor;
    private final Context context;
    private final PythonValue<ModuleValue> module;
    private final ImportDeclarationC moduleImport;
    private final PythonValue<ModuleValue> parentModule;
    private final String variable;
    
    // evaluates a.b where a is module and b is identifier
    public ResolveModuleImportGoal(PythonValue<ModuleValue> module, String variable,
            PythonValueSetAcceptor acceptor, Context context) {
        this.moduleImport = (ImportDeclarationC) module.getDecl();
        String name = module.getValue().getPath() + "." + variable;
        this.module = moduleImport.resolveAlias(name);
        if (this.module == null) {
            this.variable = variable;
        } else {
            this.variable = null;
        }
        this.parentModule = module;
        this.acceptor = acceptor;
        this.context = context;
    }
    
    // evaluates a where a is module or aliased variable
    public ResolveModuleImportGoal(ImportDeclarationC moduleImport, String name,
            PythonValueSetAcceptor acceptor, Context context) {
        this.moduleImport = moduleImport;
        this.module = moduleImport.resolveAlias(name);
        if (this.module == null) {
            this.variable = name;
        } else {
            this.variable = null;
        }
        this.parentModule = moduleImport.resolveAlias("*");
        this.acceptor = acceptor;
        this.context = context;
        //        if (this.module == null) {
        //            throw new IllegalArgumentException("Impossible, cause hasImport will return false");
        //        }
        
    }
    
    public void preRun() {
        if (this.module == null) { // not found, looking for var in parent module
            String path = parentModule.getValue().getPath();
            PythonFileC fileC = moduleImport.resolvePath(path);
            if (fileC == null) {
                updateGrade(acceptor, Grade.DONE);
            } else {
                schedule(new ResolveNameToObjectGoal(fileC, variable, acceptor, context));
            }
        } else if (module.getValue().getVar() != null) { // found explicit import of var
            String path = module.getValue().getPath();
            PythonFileC fileC = moduleImport.resolvePath(path);
            if (fileC == null) {
                updateGrade(acceptor, Grade.DONE);
            } else {
                schedule(new ResolveNameToObjectGoal(fileC, module.getValue().getVar(), acceptor, context));
            }
        } else { // found submodule
            acceptor.addResult(module, context);
            updateGrade(acceptor, Grade.DONE);
        }
    }
}
