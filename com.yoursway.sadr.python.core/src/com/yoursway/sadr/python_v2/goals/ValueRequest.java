package com.yoursway.sadr.python_v2.goals;

import static com.yoursway.sadr.python_v2.model.Context.EMPTY_CONTEXT;

import com.yoursway.sadr.python.core.typeinferencing.constructs.Effect;
import com.yoursway.sadr.python.core.typeinferencing.constructs.Frog;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class ValueRequest implements Request {
    private final PythonValueSetAcceptor acceptor;
    
    public ValueRequest(PythonValueSetAcceptor acceptor) {
        this.acceptor = acceptor;
    }
    
    public Frog apply(Frog frog, Effect effect) {
        Frog result = effect.apply(frog);
        if (!result.equals(frog))
            System.out.println("FROG:  " + frog + " => " + result);
        else
            System.out.println("FROG:  " + frog);
        RuntimeObject value = result.compactValue();
        if (value == null)
            return result;
        else {
            acceptor.addResult(value, EMPTY_CONTEXT);
            return null;
        }
    }
    
    public Request clone(Effect effect) {
        return this;
    }
    
}
