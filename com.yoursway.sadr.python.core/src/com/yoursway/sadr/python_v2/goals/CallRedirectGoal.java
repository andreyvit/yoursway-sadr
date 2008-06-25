/**
 * 
 */
package com.yoursway.sadr.python_v2.goals;

import static com.yoursway.sadr.python_v2.constructs.PythonConstructFactory.NULL_CONSTRUCT;

import java.util.List;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.goals.internal.CallResolver;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.PythonArguments;

public final class CallRedirectGoal extends ExpressionValueGoal {
    private final PythonArguments args;
    private final String methodName;
    
    public CallRedirectGoal(Context context, PythonValueSetAcceptor acceptor, PythonArguments args,
            String methodName, PythonConstruct caller) {
        super(context, acceptor);
        this.args = args;
        this.methodName = methodName;
    }
    
    public void preRun() {
        List<RuntimeObject> posArgs = args.readPositionalArgs();
        if (posArgs.size() == 1) {
            schedule(CallResolver.callMethod(posArgs.get(0), methodName, new PythonArguments(), acceptor,
                    getContext(), NULL_CONSTRUCT));
        } else if (posArgs.size() == 2) {
            schedule(CallResolver.callMethod(posArgs.get(1), methodName, new PythonArguments(), acceptor,
                    getContext(), NULL_CONSTRUCT));
        } else
            throw new IllegalStateException("Only one or two arguments allowed!");
    }
    
    @Override
    public String describe() {
        return "ExpressionValueGoal for function " + "len";
    }
}