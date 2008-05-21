package com.yoursway.sadr.python_v2.goals.sideeffects;

import java.util.Collection;

import com.yoursway.sadr.python.core.typeinferencing.constructs.Effect;
import com.yoursway.sadr.python.core.typeinferencing.constructs.Frog;

public class CompoundEffect extends Effect {
    
    private final Collection<Effect> effects;
    
    public CompoundEffect(Collection<Effect> effects) {
        if (effects == null)
            throw new NullPointerException("effects is null");
        this.effects = effects;
    }
    
    @Override
    public Frog apply(Frog frog) {
        for (Effect effect : effects)
            frog = effect.apply(frog);
        return frog;
    }
    
    @Override
    public String toString() {
        return effects.toString();
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((effects == null) ? 0 : effects.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final CompoundEffect other = (CompoundEffect) obj;
        if (effects == null) {
            if (other.effects != null)
                return false;
        } else if (!effects.equals(other.effects))
            return false;
        return true;
    }
    
}
