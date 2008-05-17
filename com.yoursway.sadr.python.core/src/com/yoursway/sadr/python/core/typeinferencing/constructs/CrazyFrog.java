package com.yoursway.sadr.python.core.typeinferencing.constructs;

public class CrazyFrog {
    
    private final Frog frog;
    private final Effect effect;
    
    public CrazyFrog(Frog frog, Effect effect) {
        if (frog == null)
            throw new NullPointerException("frog is null");
        if (effect == null)
            throw new NullPointerException("effect is null");
        this.frog = frog;
        this.effect = effect;
    }
    
    public Frog getFrog() {
        return frog;
    }
    
    public Effect getEffect() {
        return effect;
    }
    
}
