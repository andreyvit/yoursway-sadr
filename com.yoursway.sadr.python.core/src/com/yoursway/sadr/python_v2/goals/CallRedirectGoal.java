/**
 * 
 */
package com.yoursway.sadr.python_v2.goals;

import static com.yoursway.sadr.python_v2.constructs.PythonConstructFactory.NULL_CONSTRUCT;

import java.util.List;

import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.RuntimeArguments;
import com.yoursway.sadr.python_v2.model.TypeError;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;

public final class CallRedirectGoal extends ContextSensitiveGoal {
    private final RuntimeArguments args;
    private final String methodName;
    
    public CallRedirectGoal(Krocodile context, RuntimeArguments args, String methodName,
            PythonConstruct caller) {
        super(context);
        this.args = args;
        this.methodName = methodName;
    }
    
    @Override
    public PythonValueSet evaluate() {
        List<PythonObject> posArgs;
        try {
            posArgs = args.readPositionalArgs();
        } catch (TypeError e) {
            throw new IllegalStateException(e);
        }
        if (posArgs.size() == 1) {
            return CallResolver.callMethod(posArgs.get(0), methodName, new RuntimeArguments(posArgs.get(0)),
                    getKrocodile(), NULL_CONSTRUCT);
        } else if (posArgs.size() == 2) {
            return CallResolver.callMethod(posArgs.get(1), methodName, new RuntimeArguments(),
                    getKrocodile(), NULL_CONSTRUCT);
        } else
            throw new IllegalStateException("Only one or two arguments allowed!");
    }
    
    @Override
    public String describe() {
        return "ContextSensitiveGoal for function " + methodName;
    }
}