package com.yoursway.sadr.python_v2.goals;

import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.yoursway.sadr.python.core.typeinferencing.constructs.AssignmentEffect;
import com.yoursway.sadr.python.core.typeinferencing.constructs.Effect;
import com.yoursway.sadr.python.core.typeinferencing.constructs.Frog;
import com.yoursway.sadr.python.core.typeinferencing.constructs.MethodDeclarationC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.values.InstanceValue;
import com.yoursway.sadr.python_v2.goals.internal.CreateInstanceGoal;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class CallF extends Frog {
    
    private final Frog expression;
    private final List<Frog> argFrogs;
    
    public CallF(Frog expression, List<Frog> argFrogs) {
        if (expression == null)
            throw new NullPointerException("expression is null");
        this.argFrogs = argFrogs;
        this.expression = expression;
    }
    
    @Override
    public TransferOfControl compactSideEffect() {
        if (expression instanceof UserMethodF) {
            UserMethodF methodF = (UserMethodF) expression;
            MethodDeclarationC construct = methodF.getConstruct();
            List<PythonConstruct> enclosedConstructs = construct.getEnclosedConstructs();
            
            Collection<Effect> effects = newArrayList();
            List<Frog> args = construct.getArgumentFrogs();
            for (int i = 0; i < Math.min(argFrogs.size(), args.size()); i++)
                effects.add(new AssignmentEffect(argFrogs.get(i), args.get(i)));
            return new TransferOfControl(enclosedConstructs.get(enclosedConstructs.size() - 1),
                    new CompoundEffect(effects));
        }
        return null;
    }
    
    @Override
    public Frog replace(Frog lhs, Frog rhs) {
        if (lhs.equals(this))
            return rhs;
        List<Frog> replacedFrogs = new ArrayList<Frog>();
        for (Frog frog : argFrogs) {
            replacedFrogs.add(frog.replace(lhs, rhs));
        }
        return new CallF(expression.replace(lhs, rhs), replacedFrogs);
    }
    
    @Override
    public RuntimeObject compactValue() {
        if (expression instanceof UserClassF) {
            UserClassF klass = (UserClassF) expression;
            return new InstanceValue(klass.createType(), CreateInstanceGoal.instanceRegistrar);
        }
        return null;
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + expression + ")";
    }
}
