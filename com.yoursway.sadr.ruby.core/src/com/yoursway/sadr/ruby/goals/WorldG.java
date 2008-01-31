package com.yoursway.sadr.ruby.goals;

import com.yoursway.sadr.core.AbstractGoal;
import com.yoursway.sadr.core.ContinuationRequestor;
import com.yoursway.sadr.core.Contributor;
import com.yoursway.sadr.core.PropagationStyle;
import com.yoursway.sadr.ruby.world.WorldContributor;

public class WorldG extends AbstractGoal {
    
    private final WorldContributor contributor;
    
    public WorldG(WorldContributor contributor) {
        this.contributor = contributor;
    }
    
    public WorldContributor contributor() {
        return contributor;
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
