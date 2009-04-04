package com.yoursway.sadr.python.analysis.aliasing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class MultiPassAliasesCollector extends AliasConsumer {
    
    private final Set<Alias> aliases = new HashSet<Alias>();
    
    private Collection<Alias> newAliases = new ArrayList<Alias>();
    
    public Collection<Alias> retrieveNewAliases() {
        Collection<Alias> result = newAliases;
        newAliases = new ArrayList<Alias>();
        return result;
    }
    
    @Override
    public void add(Alias alias) {
        if (aliases.add(alias))
            newAliases.add(alias);
    }
    
    public Set<Alias> asSet() {
        return aliases;
    }
    
    public boolean hasNew() {
        return !newAliases.isEmpty();
    }
    
}
