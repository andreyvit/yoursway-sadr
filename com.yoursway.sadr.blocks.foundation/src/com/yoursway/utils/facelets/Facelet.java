package com.yoursway.utils.facelets;

public interface Facelet<G extends Gemstone<G>> {
    
    G gemstone();
    
    void initializeFacelet();
    
}
