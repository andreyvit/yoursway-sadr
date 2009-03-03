/**
 * 
 */
package com.yoursway.sadr.python_v2.goals;

import static com.yoursway.sadr.python_v2.constructs.PythonConstructFactory.NULL_CONSTRUCT;

import java.util.List;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.goals.internal.CallResolver;
import com.yoursway.sadr.python_v2.model.PythonArguments;

public final class CallRedirectGoal extends ContextSensitiveGoal {
    private final PythonArguments args;
    private final String methodName;
    
    public CallRedirectGoal(Krocodile context, PythonArguments args, String methodName, PythonConstruct caller) {
        super(context);
        this.args = args;
        this.methodName = methodName;
    }
    
    @Override
    public PythonValueSet evaluate() {
        List<RuntimeObject> posArgs = args.readPositionalArgs();
        if (posArgs.size() == 1) {
            return CallResolver.callMethod(posArgs.get(0), methodName, new PythonArguments(), getKrocodile(),
                    NULL_CONSTRUCT);
        } else if (posArgs.size() == 2) {
            return CallResolver.callMethod(posArgs.get(1), methodName, new PythonArguments(), getKrocodile(),
                    NULL_CONSTRUCT);
        } else
            throw new IllegalStateException("Only one or two arguments allowed!");
    }
    
    @Override
    public String describe() {
        return "ContextSensitiveGoal for function " + methodName;
    }
}