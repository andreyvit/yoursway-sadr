package com.yoursway.sadr.python.model.shit;
//package com.yoursway.sadr.python_v2.goals;
//
//import com.yoursway.sadr.python.model.values.ModuleValue;
//import com.yoursway.sadr.python_v2.constructs.ImportDeclarationC;
//import com.yoursway.sadr.python_v2.constructs.PythonFileC;
//import com.yoursway.sadr.python_v2.constructs.PythonVariableAcceptor;
//import com.yoursway.sadr.python_v2.croco.Frog;
//import com.yoursway.sadr.python_v2.croco.Krocodile;
//import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
//import com.yoursway.sadr.succeeder.Goal;
//
//public class ResolveModuleImportGoal extends Goal<PythonValueSet> {
//    
//    private final Krocodile context;
//    private final ModuleValue module;
//    private final ImportDeclarationC moduleImport;
//    private final ModuleValue parentModule;
//    private final Frog variable;
//    
//    // evaluates a.b where a is module and b is identifier
//    public ResolveModuleImportGoal(ModuleValue module, Frog variable, Krocodile context) {
//        this.moduleImport = (ImportDeclarationC) module.getDecl();
//        String name = module.getPath() + "." + variable.toString();
//        this.module = moduleImport.resolveAlias(name);
//        if (this.module == null) {
//            this.variable = variable;
//        } else {
//            this.variable = null;
//        }
//        this.parentModule = module;
//        this.context = context;
//    }
//    
//    // evaluates a where a is module or aliased variable
//    public ResolveModuleImportGoal(ImportDeclarationC moduleImport, Frog name,
//            PythonVariableAcceptor acceptor, Krocodile context) {
//        this.moduleImport = moduleImport;
//        this.module = moduleImport.resolveAlias(name.toString());
//        if (this.module == null) {
//            this.variable = name;
//        } else {
//            this.variable = null;
//        }
//        this.parentModule = moduleImport.resolveAlias("*");
//        this.context = context;
//        //        if (this.module == null) {
//        //            throw new IllegalArgumentException("Impossible, cause hasImport will return false");
//        //        }
//        
//    }
//    
//    public PythonValueSet evaluate() {
//        if (this.module == null) { // not found, looking for var in parent module
//            String path = parentModule.getPath();
//            PythonFileC fileC = moduleImport.resolvePath(path);
//            if (fileC == null) {
//                return new PythonValueSet();
//            } else {
//                return new ResolveNameToObjectGoal(variable, fileC, context).evaluate();
//            }
//        } else if (module.getVar() != null) { // found explicit import of var
//            String path = module.getPath();
//            String alias = module.getAlias();
//            PythonFileC fileC = moduleImport.resolvePath(path);
//            if (fileC == null) {
//                return PythonValueSet.EMPTY;
//            } else {
//                return new ResolveNameToObjectGoal(Frog.searchFrog(alias), fileC, context).evaluate();
//            }
//        } else { // found submodule
//            return new PythonValueSet(module, context);
//        }
//    }
//    
//}
