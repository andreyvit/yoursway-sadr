package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import java.util.ArrayList;
import java.util.List;

import kilim.pausable;

import org.eclipse.dltk.python.parser.ast.PythonCallArgument;
import org.eclipse.dltk.python.parser.ast.expressions.PythonCallExpression;

import com.yoursway.sadr.python.analysis.aliasing.AliasConsumer;
import com.yoursway.sadr.python.analysis.context.dynamic.Arguments;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.dynamic.arguments.ActualArgumentsBuilder;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.index.IndexAffector;
import com.yoursway.sadr.python.analysis.index.IndexRequest;
import com.yoursway.sadr.python.analysis.index.wrapping.IndexNameWrappingStrategy;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.analysis.lang.unodes.Bnode;
import com.yoursway.sadr.python.analysis.lang.unodes.Suffix;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.lang.unodes.proxies.CallUnode;

public class CallC extends PythonConstructImpl<PythonCallExpression> implements IndexAffector {
    
    protected static final Object SELF = "self";
    
    private final PythonConstruct callable;
    private final List<CallArgumentC> args;
    
    private final Arguments arguments;
    
    CallC(PythonLexicalContext sc, PythonCallExpression node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        this.callable = wrap(node.getReceiver(), sc);
        this.args = wrapArguments(sc, node);
        this.arguments = buildArguments();
    }
    
    private List<CallArgumentC> wrapArguments(PythonLexicalContext sc, PythonCallExpression node) {
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
    
    private Arguments buildArguments() {
        ActualArgumentsBuilder builder = new ActualArgumentsBuilder();
        for (CallArgumentC argument : args)
            argument.addTo(builder);
        return builder.build();
    }
    
    @Override
    public Unode toUnode() {
        Unode callableUnode = callable.toUnode();
        if (callableUnode == null)
            return null;
        else
            return new CallUnode(new Bnode(callableUnode, staticContext()), arguments);
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
    public void findRenames(Suffix suffix, PythonLexicalContext sc, PythonDynamicContext dc,
            AliasConsumer aliases) {
    }
    
}
