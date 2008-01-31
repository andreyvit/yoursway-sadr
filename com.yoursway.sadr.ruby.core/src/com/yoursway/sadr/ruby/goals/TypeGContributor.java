package com.yoursway.sadr.ruby.goals;

import com.yoursway.sadr.core.ContinuationRequestor;
import com.yoursway.sadr.core.Contributor;

public interface TypeGContributor extends Contributor {
    
    void contributeToTypeG(TypeG goal, ContinuationRequestor requestor);
    
}
