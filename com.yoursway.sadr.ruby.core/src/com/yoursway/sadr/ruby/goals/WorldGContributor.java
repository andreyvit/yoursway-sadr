package com.yoursway.sadr.ruby.goals;

import com.yoursway.sadr.core.ContinuationRequestor;
import com.yoursway.sadr.core.Contributor;

public interface WorldGContributor extends Contributor {
    
    void contributeToWorldG(WorldG goal, ContinuationRequestor requestor);
    
}
