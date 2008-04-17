package com.yoursway.utils.facelets;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

import java.util.Collection;
import java.util.Map;

public class GemstoneDefinitionImpl<G extends Gemstone<G>> implements GemstoneDefinition<G> {
    
    static class FaceletDefinition<F extends Facelet<G>, G extends Gemstone<G>> {
        
        private final Class<? super F>[] faceletInterfaces;
        private final FaceletFactory<F, G> factory;
        
        public FaceletDefinition(Class<? super F>[] faceletInterfaces, FaceletFactory<F, G> factory) {
            this.faceletInterfaces = faceletInterfaces;
            this.factory = factory;
        }
        
        @SuppressWarnings("unchecked")
        public void create(G gemstone, Map<Class<? extends Facelet<G>>, Facelet<G>> facelets,
                Collection<Facelet<G>> allCreatedFacelets) {
            F facelet = factory.create(gemstone);
            allCreatedFacelets.add(facelet);
            for (Class<? super F> klass : faceletInterfaces)
                facelets.put((Class<? extends Facelet<G>>) klass, facelet);
        }
        
    }
    
    private final Collection<FaceletDefinition<?, G>> facelets = newArrayList();
    
    public <F extends Facelet<G>> void addFacelet(FaceletFactory<F, G> factory,
            Class<? super F>... faceletInterfaces) {
        facelets.add(new FaceletDefinition<F, G>(faceletInterfaces, factory));
    }
    
    public Map<Class<? extends Facelet<G>>, Facelet<G>> create(G gemstone) {
        Map<Class<? extends Facelet<G>>, Facelet<G>> result = newHashMap();
        Collection<Facelet<G>> allCreatedFacelets = newArrayList();
        for (FaceletDefinition<?, G> facelet : facelets)
            facelet.create(gemstone, result, allCreatedFacelets);
        for (Facelet<G> facelet : allCreatedFacelets)
            facelet.initializeFacelet();
        return result;
    }
    
    public static <G extends Gemstone<G>> GemstoneDefinition<G> create() {
        return new GemstoneDefinitionImpl<G>();
    }
    
}
