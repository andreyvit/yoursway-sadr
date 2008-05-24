package com.yoursway.sadr.python_v2.goals.sideeffects;

import com.yoursway.sadr.python_v2.constructs.Effect;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;

public class TransferOfControl {
    
    private final PythonConstruct construct;
    private final Effect effect;
    
    public TransferOfControl(PythonConstruct construct, Effect effect) {
        if (construct == null)
            throw new NullPointerException("construct is null");
        if (effect == null)
            throw new NullPointerException("effect is null");
        this.construct = construct;
        this.effect = effect;
    }
    
    public PythonConstruct getConstruct() {
        return construct;
    }
    
    public Effect getEffect() {
        return effect;
    }
    
}
