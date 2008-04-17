package com.yoursway.utils.facelets;

import java.util.Map;

public interface GemstoneDefinition<G extends Gemstone<G>> {
    
    <F extends Facelet<G>> void addFacelet(FaceletFactory<F, G> factory,
            Class<? super F>... faceletInterfaces);
    
    Map<Class<? extends Facelet<G>>, Facelet<G>> create(G gemstone);
    
}
