package com.yoursway.sadr.python_v2.constructs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.python.parser.ast.PythonArgument;

import com.yoursway.sadr.python_v2.croco.Frog;
import com.yoursway.sadr.python_v2.croco.Index;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.croco.PythonRecord;
import com.yoursway.sadr.python_v2.goals.CallReturnValueGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.builtins.values.FunctionObject;

public class MethodDeclarationC extends PythonScopeImpl<MethodDeclaration> implements PythonDeclaration,
        CallableDeclaration {
    
    Map<String, PythonConstruct> inits;
    private FunctionObject functionObject;
    
    @SuppressWarnings("unchecked")
    public List<PythonArgument> getArguments() {
        return node.getArguments();
    }
    
    MethodDeclarationC(Scope sc, MethodDeclaration node) {
        super(sc, node);
    }
    
    @Override
    protected void wrapEnclosedChildren() {
        List<PythonConstruct> children = PythonConstructFactory.wrap(this.node.getStatements(), this);
        setPostChildren(children);
        this.inits = new HashMap<String, PythonConstruct>();
        List<PythonConstruct> preChildren = new LinkedList<PythonConstruct>();
        for (Object arg : this.node.getArguments()) {
            if (arg instanceof PythonArgument) {
                PythonArgument argument = (PythonArgument) arg;
                ASTNode init = argument.getInitialization();
                if (init != null) {
                    PythonConstruct wrappedInitializer = PythonConstructFactory.wrap(init, scope());
                    inits.put(argument.getName(), wrappedInitializer);
                    preChildren.add(wrappedInitializer);
                }
            }
        }
        setPreChildren(preChildren);
    }
    
    public PythonConstruct getArgInit(String name) {
        return inits.get(name);
    }
    
    public String displayName() {
        return "Method " + this.name();
    }
    
    @Override
    public PythonValueSet evaluate(Krocodile context) {
        if (functionObject == null)
            functionObject = new FunctionObject(this);
        return new PythonValueSet(functionObject, context);
    }
    
    public PythonValueSet call(Krocodile crocodile) {
        CallReturnValueGoal subgoal = new CallReturnValueGoal(this, crocodile);
        return subgoal.evaluate();
    }
    
    @Override
    public String name() {
        return this.node.getName();
    }
    
    public void index(Krocodile crocodile) {
        PythonRecord record = Index.newRecord(name());
        Index.add(crocodile, this, record);
        for (Object arg : this.node.getArguments()) {
            if (arg instanceof PythonArgument) {
                PythonArgument argument = (PythonArgument) arg;
                record = Index.newRecord(argument.getName());
                Index.add(crocodile, this, record);
            }
        }
    }
    
    public boolean match(Frog frog) {
        return frog.match(name());
    }
}
