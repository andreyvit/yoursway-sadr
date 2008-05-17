package com.yoursway.sadr.python_v2.goals;

import static com.yoursway.sadr.python_v2.model.Context.EMPTY_CONTEXT;

import java.util.List;

import com.yoursway.sadr.python.core.typeinferencing.constructs.Effect;
import com.yoursway.sadr.python.core.typeinferencing.constructs.Effects;
import com.yoursway.sadr.python.core.typeinferencing.constructs.Frog;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.VariableReferenceC;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.RuntimeObject;
import com.yoursway.sadr.succeeder.IGrade;

public class ReadFieldGoal extends ContextSensitiveGoal {
    
    private final PythonValueSetAcceptor acceptor;
    private final PythonConstruct receiver;
    private final String variable;
    
    public ReadFieldGoal(Context context, PythonConstruct receiver, VariableReferenceC variable,
            PythonValueSetAcceptor acceptor) {
        super(context);
        if (acceptor == null)
            throw new NullPointerException("acceptor is null");
        if (variable == null)
            throw new NullPointerException("variable is null");
        if (receiver == null)
            throw new NullPointerException("receiver is null");
        this.receiver = receiver;
        this.variable = variable.node().getName();
        this.acceptor = acceptor;
    }
    
    void parrotize(final PythonConstruct currentConstruct, final Frog frog) {
        schedule(new FindPrevConstructGoal(currentConstruct, new PythonConstructAcceptor() {
            @Override
            public <T> void checkpoint(IGrade<T> grade) {
                List<PythonConstruct> results = getResults();
                for (PythonConstruct previousConstruct : results) {
                    Effects effects = previousConstruct.getEffects();
                    for (Effect effect : effects.getEffects()) {
                        Frog newFrog = apply(frog, effect);
                        if (newFrog != null)
                            parrotize(previousConstruct, newFrog);
                    }
                    for (Frog crazyFrog : effects.getFrogs()) {
                        // TODO hui znaet
                        System.out.println("ReadFieldGoal.checkpoint(): " + crazyFrog);
                    }
                    if (effects.getEffects().isEmpty())
                        parrotize(previousConstruct, frog);
                }
            }
        }));
    }
    
    protected Frog apply(Frog frog, Effect effect) {
        Frog result = effect.apply(frog);
        RuntimeObject value = result.compact();
        if (value == null)
            return result;
        else {
            acceptor.addResult(value, EMPTY_CONTEXT);
            return null;
        }
    }
    
    public void preRun() {
        Frog frog = new FieldReadF(receiver.toFrog(), variable);
        parrotize(receiver, frog);
    }
    
}
