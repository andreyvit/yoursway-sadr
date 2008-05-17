package com.yoursway.sadr.python.core.typeinferencing.constructs;

import static java.util.Collections.emptyList;

import java.util.Collection;

public class Effects {
    
    public static final Collection<? extends Effect> NO_EFFECTS = emptyList();
    
    public static final Collection<? extends Frog> NO_FROGS = emptyList();
    
    public static final Effects NONE = new Effects(NO_EFFECTS, NO_FROGS);
    
    private final Collection<? extends Effect> effects;
    private final Collection<? extends Frog> frogs;
    
    public Effects(Collection<? extends Effect> effects, Collection<? extends Frog> frogs) {
        this.effects = effects;
        this.frogs = frogs;
    }
    
    public Collection<? extends Effect> getEffects() {
        return effects;
    }
    
    public Collection<? extends Frog> getFrogs() {
        return frogs;
    }
}
