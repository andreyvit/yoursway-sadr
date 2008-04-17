package com.yoursway.utils.facelets;

public interface Gemstone<G extends Gemstone<G>> {
    
    <F extends Facelet<G>> F get(Class<F> faceletInterface);
    
}
