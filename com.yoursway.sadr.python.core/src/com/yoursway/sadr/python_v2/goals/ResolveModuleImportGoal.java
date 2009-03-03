package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python_v2.constructs.ImportDeclarationC;
import com.yoursway.sadr.python_v2.constructs.PythonFileC;
import com.yoursway.sadr.python_v2.constructs.PythonVariableAcceptor;
import com.yoursway.sadr.python_v2.croco.Frog;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.builtins.ModuleValue;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;
import com.yoursway.sadr.succeeder.Goal;

public class ResolveModuleImportGoal extends Goal<PythonValueSet> {
    
    private final Krocodile context;
    private final PythonValue<ModuleValue> module;
    private final ImportDeclarationC moduleImport;
    private final PythonValue<ModuleValue> parentModule;
    private final Frog variable;
    
    // evaluates a.b where a is module and b is identifier
    public ResolveModuleImportGoal(PythonValue<ModuleValue> module, Frog variable, Krocodile context) {
        this.moduleImport = (ImportDeclarationC) module.getDecl();
        String name = module.getValue().getPath() + "." + variable.toString();
        this.module = moduleImport.resolveAlias(name);
        if (this.module == null) {
            this.variable = variable;
        } else {
            this.variable = null;
        }
        this.parentModule = module;
        this.context = context;
    }
    
    // evaluates a where a is module or aliased variable
    public ResolveModuleImportGoal(ImportDeclarationC moduleImport, Frog name,
            PythonVariableAcceptor acceptor, Krocodile context) {
        this.moduleImport = moduleImport;
        this.module = moduleImport.resolveAlias(name.toString());
        if (this.module == null) {
            this.variable = name;
        } else {
            this.variable = null;
        }
        this.parentModule = moduleImport.resolveAlias("*");
        this.context = context;
        //        if (this.module == null) {
        //            throw new IllegalArgumentException("Impossible, cause hasImport will return false");
        //        }
        
    }
    
    public PythonValueSet evaluate() {
        if (this.module == null) { // not found, looking for var in parent module
            String path = parentModule.getValue().getPath();
            PythonFileC fileC = moduleImport.resolvePath(path);
            if (fileC == null) {
                return new PythonValueSet();
            } else {
                return new ResolveNameToObjectGoal(variable, fileC, context).evaluate();
            }
        } else if (module.getValue().getVar() != null) { // found explicit import of var
            String path = module.getValue().getPath();
            String alias = module.getValue().getAlias();
            PythonFileC fileC = moduleImport.resolvePath(path);
            if (fileC == null) {
                return PythonValueSet.EMPTY;
            } else {
                return new ResolveNameToObjectGoal(Frog.searchFrog(alias), fileC, context).evaluate();
            }
        } else { // found submodule
            return new PythonValueSet(module, context);
        }
    }
}
