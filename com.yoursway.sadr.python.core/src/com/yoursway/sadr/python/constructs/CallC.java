package com.yoursway.sadr.python.constructs;

import java.util.List;

import kilim.pausable;

import org.eclipse.dltk.python.parser.ast.expressions.PythonCallExpression;

import com.yoursway.sadr.engine.Analysis;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.goals.ExpressionValueGoal;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class CallC extends PythonConstructImpl<PythonCallExpression> {
    
    protected static final Object SELF = "self";
    
    private final PythonConstruct callable;
    private final List<PythonConstruct> args;
    
    CallC(PythonStaticContext sc, PythonCallExpression node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        callable = wrap(node.getReceiver(), sc);
        args = wrap(node.getArgumentsAsList(), sc);
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
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
        PythonValueSet callableValueSet = Analysis.evaluate(new ExpressionValueGoal(callable, dc));
        args.isEmpty();
        //        for (PythonConstruct arg : args) {
        //            if (arg instanceof CallArgumentC) {
        //                choices.put(arg, arg.evaluate(context));
        //            }
        //        }
        //        
        //        PythonValueSet collection = new PythonValueSet();
        //        for (Map<Object, PythonValue> list : new DictIterator<Object>(choices)) {
        //            findArguments(collection, list, context);
        //        }
        return callableValueSet.call(dc);
    }
    
}
