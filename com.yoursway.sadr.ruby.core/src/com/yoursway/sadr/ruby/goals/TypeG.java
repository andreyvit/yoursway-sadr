package com.yoursway.sadr.ruby.goals;

import com.yoursway.sadr.core.AbstractGoal;
import com.yoursway.sadr.core.ContinuationRequestor;
import com.yoursway.sadr.core.Contributor;
import com.yoursway.sadr.core.PropagationStyle;

public class TypeG extends AbstractGoal {
    
    public TypeG() {
    }
    
    @Override
    public PropagationStyle propagationStyle() {
        return PropagationStyle.VALUE;
    }
    
    @Override
    public void delegateContributionTo(Contributor contributor, ContinuationRequestor requestor) {
        if (contributor instanceof TypeGContributor)
            ((TypeGContributor) contributor).contributeToTypeG(this, requestor);
        else
            super.delegateContributionTo(contributor, requestor);
    }
    
}
