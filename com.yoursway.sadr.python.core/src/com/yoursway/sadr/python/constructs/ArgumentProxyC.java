package com.yoursway.sadr.python.constructs;

import kilim.pausable;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python_v2.croco.Arguments;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class ArgumentProxyC extends PythonConstructImpl<ASTNode> {
    
    private final ArgumentC argument;
    private final int index;
    
    public ArgumentProxyC(PythonStaticContext sc, MethodDeclarationC methodC, ArgumentC argument, int index) {
        super(sc, new DummyAstNode(), null);
        if (methodC == null)
            throw new NullPointerException("methodC is null");
        if (argument == null)
            throw new NullPointerException("argument is null");
        this.argument = argument;
        this.index = index;
    }
    
    public String getName() {
        return argument.getName();
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
        Arguments arguments = dc.argumentsOfTopCall();
        return arguments.computeArgument(dc, index, argument.getName(), argument.getInit());
    }
}
