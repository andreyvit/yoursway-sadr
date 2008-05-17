package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python.core.typeinferencing.constructs.Effect;
import com.yoursway.sadr.python.core.typeinferencing.constructs.Frog;

public class SideEffectRequest implements Request {
    
    private final Request request;
    private final Swamp swamp;
    private final Frog requestFrog;
    
    public SideEffectRequest(Request request, Swamp swamp, Frog requestFrog) {
        this.request = request;
        this.swamp = swamp;
        this.requestFrog = requestFrog;
    }
    
    public Frog apply(Frog frog, Effect effect) {
        Frog result = effect.apply(frog);
        if (!result.equals(frog))
            System.out.println("SFROG:  " + frog + " => " + result);
        else
            System.out.println("SFROG:  " + frog);
        TransferOfControl value = result.compactSideEffect();
        if (value == null)
            return result;
        else {
            swamp.parrotizeStartingWith(value.getConstruct(), value.getEffect().apply(requestFrog), request);
            return null;
        }
    }
    
    public Request clone(Effect effect) {
        return new SideEffectRequest(request, swamp, effect.apply(requestFrog));
    }
    
}
