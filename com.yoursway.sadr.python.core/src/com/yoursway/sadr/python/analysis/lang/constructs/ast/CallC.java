package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import kilim.pausable;

import org.eclipse.dltk.python.parser.ast.PythonCallArgument;
import org.eclipse.dltk.python.parser.ast.expressions.PythonCallExpression;

import com.yoursway.sadr.engine.Analysis;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.analysis.Alias;
import com.yoursway.sadr.python.analysis.Constants;
import com.yoursway.sadr.python.analysis.context.dynamic.Arguments;
import com.yoursway.sadr.python.analysis.context.dynamic.CallSiteDynamicContext;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.dynamic.arguments.ActualArgumentsBuilder;
import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.goals.ExpressionValueGoal;
import com.yoursway.sadr.python.analysis.index.IndexAffector;
import com.yoursway.sadr.python.analysis.index.IndexRequest;
import com.yoursway.sadr.python.analysis.index.wrapping.IndexNameWrappingStrategy;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.analysis.lang.unodes.CallUnode;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.lang.unodes.punodes.Punode;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class CallC extends PythonConstructImpl<PythonCallExpression> implements IndexAffector {
    
    protected static final Object SELF = "self";
    
    private final PythonConstruct callable;
    private final List<CallArgumentC> args;
    
    private final Arguments arguments;
    
    CallC(PythonStaticContext sc, PythonCallExpression node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        this.callable = wrap(node.getReceiver(), sc);
        this.args = wrapArguments(sc, node);
        this.arguments = buildArguments();
    }
    
    private List<CallArgumentC> wrapArguments(PythonStaticContext sc, PythonCallExpression node) {
        List<CallArgumentC> args = new ArrayList<CallArgumentC>();
        for (PythonCallArgument arg : node.getArgumentsAsList())
            args.add(new CallArgumentC(sc, arg, this));
        return args;
    }
    
    //    private RuntimeArguments addArguments(Map<Object, PythonValue> results) {
    //        RuntimeArguments real = new RuntimeArguments();
    //        if (results.containsKey(SELF)) {
    //            real.getArgs().add(results.get(SELF));
    //        }
    //        for (PythonConstruct arg : args) {
    //            if (arg instanceof CallArgumentC) {
    //                CallArgumentC argC = (CallArgumentC) arg;
    //                PythonConstruct value = (((CallArgumentC) arg).getValue());
    //                if (value instanceof AssignmentC) {
    //                    AssignmentC assignmentC = (AssignmentC) value;
    //                    real.getKwargs().put(assignmentC.name(), results.get(arg));
    //                } else {
    //                    if (!results.containsKey(arg)) {
    //                        throw new IllegalStateException("Argument is missing!");
    //                    }
    //                    PythonValue result = results.get(arg);
    //                    if (argC.getStar() == PythonArgument.NOSTAR) {
    //                        real.getArgs().add(result);
    //                    } else if (argC.getStar() == PythonArgument.STAR) {
    //                        real.setLastArg(result);
    //                    } else if (argC.getStar() == PythonArgument.DOUBLESTAR) {
    //                        real.setLastKwarg(result);
    //                    }
    //                }
    //            }
    //        }
    //        return real;
    //    }
    
    @Override
    public String toString() {
        return node.getName() + "(" + node.getArgs().getChilds() + ")";
    }
    
    public PythonConstruct getCallable() {
        return callable;
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
        int size = dc.callStackSize();
        if (size >= Constants.MAXIMUM_CALL_STACK_DEPTH)
            return PythonValueSet.EMPTY;
        PythonValueSet callableValueSet = Analysis.evaluate(new ExpressionValueGoal(callable, dc));
        return callableValueSet.call(createDynamicContext(dc));
    }
    
    public CallSiteDynamicContext createDynamicContext(PythonDynamicContext dc) {
        return new CallSiteDynamicContext(dc, arguments);
    }
    
    private Arguments buildArguments() {
        ActualArgumentsBuilder builder = new ActualArgumentsBuilder();
        for (CallArgumentC argument : args)
            argument.addTo(builder);
        return builder.build();
    }
    
    @Override
    public Unode toUnode() {
        return new CallUnode(this);
    }
    
    public void actOnIndex(IndexRequest r) {
        int index = 0;
        for (CallArgumentC arg : args)
            arg.actOnIndex(r, this, index++);
    }
    
    public IndexNameWrappingStrategy createWrappingStrategy() {
        return null;
    }
    
    @pausable
    public void findRenames(Punode punode, PythonStaticContext sc, PythonDynamicContext dc, Set<Alias> aliases) {
        int size = dc.callStackSize();
        if (size >= Constants.MAXIMUM_CALL_STACK_DEPTH)
            return;
        
        PythonValueSet callableValueSet = Analysis.evaluate(new ExpressionValueGoal(callable, dc));
        dc = createDynamicContext(dc);
        callableValueSet.findRenames(punode, sc, dc, aliases);
    }
    
}