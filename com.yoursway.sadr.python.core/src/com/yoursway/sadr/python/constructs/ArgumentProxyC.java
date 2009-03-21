package com.yoursway.sadr.python.constructs;

import kilim.pausable;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.engine.Analysis;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.goals.ExpressionValueGoal;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetBuilder;

public class ArgumentProxyC extends PythonConstructImpl<ASTNode> {
    
    private final ArgumentC argument;
    private final boolean canBeSelf;
    private final MethodDeclarationC methodC;
    
    public ArgumentProxyC(PythonStaticContext sc, MethodDeclarationC methodC, ArgumentC argument,
            boolean canBeSelf) {
        super(sc, new DummyAstNode(), null);
        if (methodC == null)
            throw new NullPointerException("methodC is null");
        if (argument == null)
            throw new NullPointerException("argument is null");
        this.methodC = methodC;
        this.argument = argument;
        this.canBeSelf = canBeSelf;
    }
    
    public String getName() {
        return argument.getName();
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
        PythonValueSetBuilder builder = PythonValueSet.newBuilder();
        PythonConstruct init = argument.getInit();
        if (init != null)
            builder.addResults(Analysis.evaluate(new ExpressionValueGoal(init, dc)));
        if (canBeSelf)
            builder.addResults(methodC.calculateSelf());
        return builder.build();
    }
}
