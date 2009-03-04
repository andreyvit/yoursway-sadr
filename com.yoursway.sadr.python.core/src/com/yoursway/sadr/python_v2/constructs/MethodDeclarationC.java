package com.yoursway.sadr.python_v2.constructs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.python.parser.ast.PythonArgument;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.croco.Frog;
import com.yoursway.sadr.python_v2.croco.Index;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.croco.PythonRecord;
import com.yoursway.sadr.python_v2.goals.CallReturnValueGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.DictIterator;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.ContextImpl;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.builtins.FunctionObject;

public class MethodDeclarationC extends PythonScopeImpl<MethodDeclaration> implements PythonDeclaration,
        PythonCallable {
    
    private Map<String, PythonConstruct> inits;
    private FunctionObject functionObject;
    
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
    
    public PythonValueSet call(Krocodile crocodile, PythonArguments args) {
        List<PythonArgument> nodeArgs = this.node().getArguments();
        ContextImpl context = new ContextImpl(nodeArgs, args);
        Map<String, PythonValueSet> defaults = new HashMap<String, PythonValueSet>();
        for (Entry<String, PythonConstruct> init : inits.entrySet()) {
            String key = init.getKey();
            if (context.getActualArgument(key) == null) {
                PythonValueSet argDefault = init.getValue().evaluate(crocodile);
                defaults.put(key, argDefault);
            }
        }
        PythonValueSet results = new PythonValueSet();
        for (Map<String, RuntimeObject> def : new DictIterator<String>(defaults)) {
            for (Entry<String, RuntimeObject> e : def.entrySet()) {
                context.put(e.getKey(), e.getValue());
            }
            results.addResults(callWithArgs(crocodile, context));
        }
        return results;
    }
    
    private PythonValueSet callWithArgs(Krocodile crocodile, ContextImpl context) {
        Krocodile actualArguments = new Krocodile(crocodile, this, context);
        CallReturnValueGoal subgoal = new CallReturnValueGoal(this, actualArguments);
        return subgoal.evaluate();
    }
    
    @Override
    public PythonValueSet evaluate(Krocodile context) {
        if (functionObject == null)
            functionObject = new FunctionObject(this);
        return new PythonValueSet(functionObject, context);
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
