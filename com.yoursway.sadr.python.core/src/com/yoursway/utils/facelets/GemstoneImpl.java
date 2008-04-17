package com.yoursway.utils.facelets;

import java.util.Map;

public class GemstoneImpl<G extends Gemstone<G>> implements Gemstone<G> {
    
    private final Map<Class<? extends Facelet<G>>, Facelet<G>> facelets;
    
    @SuppressWarnings("unchecked")
    public GemstoneImpl(GemstoneDefinition<G> definition) {
        facelets = definition.create((G) this);
    }
    
    @SuppressWarnings("unchecked")
    public final <F extends Facelet<G>> F get(Class<F> faceletInterface) {
        return (F) facelets.get(faceletInterface);
    }
    
}
