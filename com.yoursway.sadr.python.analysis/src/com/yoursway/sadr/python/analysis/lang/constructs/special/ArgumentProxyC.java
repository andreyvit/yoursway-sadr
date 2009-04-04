package com.yoursway.sadr.python.analysis.lang.constructs.special;

import java.util.Set;

import kilim.pausable;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.analysis.Alias;
import com.yoursway.sadr.python.analysis.context.dynamic.Arguments;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.analysis.lang.constructs.ast.ArgumentC;
import com.yoursway.sadr.python.analysis.lang.constructs.ast.MethodDeclarationC;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.lang.unodes.proxies.ArgumentProxyUnode;
import com.yoursway.sadr.python.analysis.lang.unodes.punodes.Punode;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

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
    
    @pausable
    public void findRenames(Punode punode, PythonStaticContext sc, PythonDynamicContext dc, Set<Alias> aliases) {
        Arguments arguments = dc.argumentsOfTopCall();
        arguments.findRenames(punode, sc, dc, aliases, index, argument.getName(), argument.getInit());
    }
    
    @Override
    public Unode toUnode() {
        return new ArgumentProxyUnode(this);
    }
    
}
