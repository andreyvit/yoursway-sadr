package com.yoursway.sadr.python_v2.constructs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.python.parser.ast.PythonArgument;
import org.eclipse.dltk.python.parser.ast.expressions.PythonCallExpression;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.DictIterator;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.goals.internal.CallResolver;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.builtins.FunctionObject;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;

public abstract class CallC extends PythonConstructImpl<PythonCallExpression> {
    
    private static final Object CALLABLE = "func";
    protected static final int CALLABLE_INDEX = 0;
    private static final int ARGUMENTS_INDEX = 1;
    protected static final Object SELF = "self";
    private List<PythonConstruct> args;
    private PythonConstruct self;
    private PythonConstruct callable;
    
    @SuppressWarnings("unchecked")
    CallC(Scope sc, PythonCallExpression node) {
        super(sc, node);
    }
    
    abstract public PythonConstruct getReceiver();
    
    @Override
    protected void wrapEnclosedChildren() {
        List<PythonConstruct> wrappedChildren = new ArrayList<PythonConstruct>();
        if (node.getArgs() != null) {
            List<ASTNode> argNodes = ((ASTNode) node.getChilds().get(ARGUMENTS_INDEX)).getChilds();
            this.args = PythonConstructFactory.wrap(argNodes, scope());
            wrappedChildren.addAll(this.args);
        }
        callable = PythonConstructFactory.wrap((ASTNode) node.getChilds().get(CALLABLE_INDEX), scope());
        if (callable instanceof FieldAccessC) {
            FieldAccessC fieldAccess = (FieldAccessC) callable;
            self = fieldAccess.receiver();
        }
        wrappedChildren.add(callable);
        setPreChildren(wrappedChildren);
        setPostChildren(Collections.EMPTY_LIST);
    }
    
    private PythonValueSet findArguments(PythonValueSet container, Map<Object, RuntimeObject> results,
            Krocodile crocodile) {
        RuntimeObject method = results.get(CALLABLE);
        PythonArguments real = new PythonArguments();
        if (results.containsKey(SELF)) {
            real.getArgs().add(results.get(SELF));
        }
        for (PythonConstruct arg : args) {
            if (arg instanceof CallArgumentC) {
                CallArgumentC argC = (CallArgumentC) arg;
                PythonConstruct value = (((CallArgumentC) arg).getValue());
                if (value instanceof AssignmentC) {
                    AssignmentC assignmentC = (AssignmentC) value;
                    real.getKwargs().put(assignmentC.name(), results.get(arg));
                } else {
                    RuntimeObject result = results.get(arg);
                    if (argC.getStar() == PythonArgument.NOSTAR) {
                        real.getArgs().add(result);
                    } else if (argC.getStar() == PythonArgument.STAR) {
                        real.setLastArg(result);
                    } else if (argC.getStar() == PythonArgument.DOUBLESTAR) {
                        real.setLastKwarg(result);
                    }
                }
            }
        }
        
        if (method instanceof FunctionObject) {
            container.addResults(CallResolver.callFunction((FunctionObject) method, real, crocodile, this));
        } else if (method instanceof PythonObject) {
            container.addResults(CallResolver.callMethod(method, "__call__", real, crocodile, CallC.this));
        } else if (method == null) {
            return container;
            //                            throw new IllegalStateException("Unable to find callable " + callable
            //                                    + ", resolved to " + method);
        }
        return container;
    }
    
    @Override
    public PythonValueSet evaluate(final Krocodile context) {
        HashMap<Object, PythonValueSet> choices = new HashMap<Object, PythonValueSet>();
        choices.put(CALLABLE, callable.evaluate(context));
        
        if (self != null) {
            choices.put(SELF, self.evaluate(context));
        }
        
        for (PythonConstruct arg : args) {
            if (arg instanceof CallArgumentC) {
                choices.put(arg, arg.evaluate(context));
            }
        }
        
        PythonValueSet collection = new PythonValueSet();
        for (Map<Object, RuntimeObject> list : new DictIterator<Object>(choices)) {
            findArguments(collection, list, context);
        }
        return collection;
    }
    
    @Override
    protected void setupPrevConstructRelation(PythonConstruct prev) {
        for (PythonConstruct preChild : getPreChildren()) {
            PythonConstructImpl<ASTNode> pyPreChild = (PythonConstructImpl<ASTNode>) preChild;
            pyPreChild.setupPrevConstructRelation(prev);
            prev = preChild;
        }
        this.setSyntacticallyPreviousConstruct(prev);
        assert getPostChildren().size() == 0 : "Must be no post children in CallC.";
    }
}
