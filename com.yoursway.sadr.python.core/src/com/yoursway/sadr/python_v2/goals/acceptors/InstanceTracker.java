package com.yoursway.sadr.python_v2.goals.acceptors;

import java.util.HashMap;
import java.util.Map;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.succeeder.IAcceptor;
import com.yoursway.sadr.succeeder.IGoal;

/**
 * Tracks instance changes through data-flow.
 * <p>
 * <b>Potential use</b>: this class may define <code>checkpoint()</code> and
 * declare abstract <code>processIindividualInstance()</code> in order to deal
 * with value sets.
 * </p>
 */
public abstract class InstanceTracker implements IAcceptor {
    private class EvaluationDescriptor {
        private final PythonConstruct valueSource;
        private final Context context;
        
        public EvaluationDescriptor(PythonConstruct valueSource, Context context) {
            if (valueSource == null || context == null)
                throw new IllegalArgumentException();
            this.valueSource = valueSource;
            this.context = context;
        }
        
        IGoal evaluate(PythonValueSetAcceptor acceptor) {
            return valueSource.evaluate(this.context, acceptor);
        }
    }
    
    private final RuntimeObject instance;
    private final Map<String, EvaluationDescriptor> attributes = new HashMap<String, EvaluationDescriptor>();
    
    public InstanceTracker(RuntimeObject instance) {
        this.instance = instance;
    }
    
    public void addAttribute(String name, PythonConstruct valueSource, Context context) {
        if (name == null || name.length() == 0)
            throw new IllegalArgumentException("incorrect name");
        attributes.put(name, new EvaluationDescriptor(valueSource, context));
    }
    
    /**
     * @return unprocessed tracked instance.
     */
    public RuntimeObject blankInstance() {
        return instance;
    }
}
