package com.yoursway.utils.facelets;

public interface FaceletFactory<F extends Facelet<G>, G extends Gemstone<G>> {
    
    F create(G model);
    
}
