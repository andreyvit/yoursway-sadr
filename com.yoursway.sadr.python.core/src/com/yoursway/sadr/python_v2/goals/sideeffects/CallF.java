package com.yoursway.sadr.python_v2.goals.sideeffects;

import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.yoursway.sadr.python.core.typeinferencing.values.InstanceValue;
import com.yoursway.sadr.python_v2.constructs.AssignmentEffect;
import com.yoursway.sadr.python_v2.constructs.ClassDeclarationC;
import com.yoursway.sadr.python_v2.constructs.Effect;
import com.yoursway.sadr.python_v2.constructs.Frog;
import com.yoursway.sadr.python_v2.constructs.MethodDeclarationC;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.goals.CreateInstanceGoal;
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
    
    public Frog getExpression() {
        return expression;
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
        } else if (expression instanceof FieldReadF) {
            FieldReadF frf = (FieldReadF) expression;
            Frog receiver = frf.getReceiver();
            // XXX this is a hack for finding a method, should have created another sideeffect instead
            if (receiver instanceof CallF) {
                CallF callF = (CallF) receiver;
                Frog receiver2 = callF.getExpression();
                if (receiver2 instanceof UserClassF) {
                    UserClassF classF = (UserClassF) receiver2;
                    ClassDeclarationC classC = classF.getConstruct();
                    MethodDeclarationC methodC = classC.findDeclaredMethod(frf.getField());
                    if (methodC != null) {
                        List<PythonConstruct> enclosedConstructs = methodC.getEnclosedConstructs();
                        
                        Collection<Effect> effects = newArrayList();
                        List<Frog> args = methodC.getArgumentFrogs();
                        for (int i = 0; i < Math.min(argFrogs.size(), args.size() - 1); i++)
                            effects.add(new AssignmentEffect(argFrogs.get(i), args.get(i + 1)));
                        // FIXME handle constructs with arguments (not sure this is a problem)
                        effects.add(new AssignmentEffect(receiver, args.get(0)));
                        return new TransferOfControl(enclosedConstructs.get(enclosedConstructs.size() - 1),
                                new CompoundEffect(effects));
                    }
                }
            }
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
        return "{" + expression + "(" + ")" + "}";
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((argFrogs == null) ? 0 : argFrogs.hashCode());
        result = prime * result + ((expression == null) ? 0 : expression.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final CallF other = (CallF) obj;
        if (argFrogs == null) {
            if (other.argFrogs != null)
                return false;
        } else if (!argFrogs.equals(other.argFrogs))
            return false;
        if (expression == null) {
            if (other.expression != null)
                return false;
        } else if (!expression.equals(other.expression))
            return false;
        return true;
    }
    
}
