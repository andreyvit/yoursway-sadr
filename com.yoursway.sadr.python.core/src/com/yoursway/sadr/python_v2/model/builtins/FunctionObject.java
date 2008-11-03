package com.yoursway.sadr.python_v2.model.builtins;

import com.yoursway.sadr.python_v2.constructs.ClassDeclarationC;
import com.yoursway.sadr.python_v2.constructs.MethodDeclarationC;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.constructs.PythonLambdaExpressionC;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.succeeder.IGoal;

public class FunctionObject extends PythonObject {
    private final PythonConstruct decl; //either MethodDeclarationC or PythonLambdaExpressionC
    private final String name;
    private PythonClassType boundClass = null;
    
    public FunctionObject(MethodDeclarationC decl) {
        super(Builtins.FUNCTION, decl);
        this.decl = decl;
        this.name = decl.node().getName();
        setAttribute("__name__", StringType.wrap(name));
    }
    
    public FunctionObject(PythonLambdaExpressionC decl) {
        super(Builtins.FUNCTION, decl);
        this.decl = decl;
        this.name = "<lambda>";
        setAttribute("__name__", StringType.wrap(name));
    }
    
    public FunctionObject(String name) {
        super(Builtins.FUNCTION);
        this.decl = null;
        this.name = name;
        setAttribute("__name__", StringType.wrap(name));
    }
    
    public FunctionObject(ClassDeclarationC classDeclarationC) {//FIXME OMG! Shit! class name
        super(Builtins.FUNCTION);//XXX illegal constructor
        this.decl = classDeclarationC;
        this.name = classDeclarationC.node().getName();
        //find/run constructor
    }
    
    /**
     * @return either MethodDeclarationC or PythonLambdaExpressionC
     */
    @Override
    public PythonConstruct getDecl() {
        return decl;
    }
    
    public IGoal evaluateGoal(PythonValueSetAcceptor acceptor, Krocodile context, PythonArguments args) {
        return null;
    }
    
    public boolean isBound() {
        return boundClass != null;
    }
    
    public PythonClassType getBoundClass() {
        return boundClass;
    }
    
    @Override
    public String describe() {
        return "function " + name();
    }
    
    public String name() {
        return this.name;
    }
    
    public void bind(PythonClassType instanceType) {
        this.boundClass = instanceType;
    }
}
