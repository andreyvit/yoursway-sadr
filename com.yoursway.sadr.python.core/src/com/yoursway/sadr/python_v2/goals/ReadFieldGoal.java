package com.yoursway.sadr.python_v2.goals;

import java.util.List;

import com.yoursway.sadr.python.core.typeinferencing.constructs.Effect;
import com.yoursway.sadr.python.core.typeinferencing.constructs.Effects;
import com.yoursway.sadr.python.core.typeinferencing.constructs.Frog;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.VariableReferenceC;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.succeeder.IGrade;

public class ReadFieldGoal extends ContextSensitiveGoal implements Swamp {
    
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
    
    public void parrotizeStartingWithParentOf(final PythonConstruct currentConstruct, final Frog frog,
            final Request request) {
        schedule(new FindPrevConstructGoal(currentConstruct, new PythonConstructAcceptor() {
            @Override
            public <T> void checkpoint(IGrade<T> grade) {
                List<PythonConstruct> results = getResults();
                for (PythonConstruct previousConstruct : results) {
                    parrotizeStartingWith(previousConstruct, frog, request);
                }
            }
        }));
    }
    
    public void preRun() {
        Frog frog = new FieldReadF(receiver.toFrog(), variable);
        parrotizeStartingWithParentOf(receiver, frog, new ValueRequest(acceptor));
    }
    
    public void parrotizeStartingWith(PythonConstruct previousConstruct, final Frog frog,
            final Request request) {
        Effects effects = previousConstruct.getEffects();
        for (Effect effect : effects.getEffects()) {
            Request clone = request.clone(effect);
            Frog newFrog = clone.apply(frog, effect);
            if (newFrog != null)
                parrotizeStartingWithParentOf(previousConstruct, newFrog, clone);
        }
        for (Frog crazyFrog : effects.getFrogs()) {
            SideEffectRequest sideEffectRequest = new SideEffectRequest(request, ReadFieldGoal.this, frog);
            parrotizeStartingWithParentOf(previousConstruct, crazyFrog, sideEffectRequest);
        }
        if (effects.getEffects().isEmpty())
            //            if (!effects.getFrogs().isEmpty())
            //                System.out.println("ReadFieldGoal.parrotizeStartingWith()");
            //            else
            parrotizeStartingWithParentOf(previousConstruct, frog, request);
    }
    
}
