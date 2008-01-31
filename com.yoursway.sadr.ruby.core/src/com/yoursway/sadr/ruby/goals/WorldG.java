package com.yoursway.sadr.ruby.goals;

import com.yoursway.sadr.core.AbstractGoal;
import com.yoursway.sadr.core.ContinuationRequestor;
import com.yoursway.sadr.core.Contributor;
import com.yoursway.sadr.core.PropagationStyle;

public class WorldG extends AbstractGoal {
    
    public WorldG() {
    }
    
    @Override
    public PropagationStyle propagationStyle() {
        return PropagationStyle.FLOW;
    }
    
    @Override
    public void delegateContributionTo(Contributor contributor, ContinuationRequestor requestor) {
        if (contributor instanceof WorldGContributor)
            ((WorldGContributor) contributor).contributeToWorldG(this, requestor);
        else
            super.delegateContributionTo(contributor, requestor);
    }
    
}
