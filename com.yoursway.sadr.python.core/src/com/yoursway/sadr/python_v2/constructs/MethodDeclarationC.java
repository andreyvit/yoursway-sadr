package com.yoursway.sadr.python_v2.constructs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.python.parser.ast.PythonArgument;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.builtins.FunctionObject;
import com.yoursway.sadr.succeeder.IGoal;

public class MethodDeclarationC extends PythonScopeImpl<MethodDeclaration> {
    
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
                    PythonConstruct wrappedInitializer = PythonConstructFactory.wrap(init, innerScope());
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
        return "Method " + this.node.getName();
    }
    
    @Override
    public IGoal evaluate(Context context, PythonValueSetAcceptor acceptor) {
        if (functionObject == null)
            functionObject = new FunctionObject(this);
        return new PassResultGoal(context, acceptor, functionObject);
    }
    
    //    public void actOnModel(ModelRequest request) {
    //        PythonClassImpl klass = staticContext().currentClass();
    //        if (klass != null) {
    //            PythonSourceMethod method = new PythonSourceMethod(klass, request.context(), this);
    //            innerScope = new MethodScope(nearestScope(), method, node);
    //        } else {
    //            PythonSourceProcedure procedure = new PythonSourceProcedure(request.context(), this);
    //            innerScope = new ProcedureScope(nearestScope(), procedure, node);
    //        }
    //    }
    
    @Override
    public String name() {
        return this.node.getName();
    }
    
    @Override
    public boolean match(Frog frog) {
        return node.equals(frog.getAccessor());
    }
}
