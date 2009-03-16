package com.yoursway.sadr.python.constructs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kilim.pausable;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.python.parser.ast.PythonArgument;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python_v2.croco.Frog;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class MethodDeclarationC extends PythonScopeImpl<MethodDeclaration> implements PythonDeclaration,
        CallableDeclaration {
    
    private final Map<String, PythonConstruct> inits;
    private final List<PythonConstruct> body;
    
    @SuppressWarnings("unchecked")
    MethodDeclarationC(PythonStaticContext sc, MethodDeclaration node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        body = wrap(node.getBody().getChilds(), this);
        inits = computeInitializers();
        body.isEmpty();
    }
    
    @SuppressWarnings("unchecked")
    public List<PythonArgument> getArguments() {
        return node.getArguments();
    }
    
    private Map<String, PythonConstruct> computeInitializers() {
        Map<String, PythonConstruct> result = new HashMap<String, PythonConstruct>();
        for (Object arg : this.node.getArguments()) {
            if (arg instanceof PythonArgument) {
                PythonArgument argument = (PythonArgument) arg;
                ASTNode init = argument.getInitialization();
                if (init != null) {
                    PythonConstruct wrappedInitializer = wrap(init, staticContext());
                    result.put(argument.getName(), wrappedInitializer);
                }
            }
        }
        return result;
    }
    
    public PythonConstruct getArgInit(String name) {
        return inits.get(name);
    }
    
    @Override
    public String toString() {
        return "Method " + this.name();
    }
    
    @Override
    public String name() {
        return this.node.getName();
    }
    
    public boolean match(Frog frog) {
        return frog.match(name());
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
        return null;
    }
    
    public PythonValueSet call(PythonDynamicContext crocodile) {
        return null;
    }
    
}
