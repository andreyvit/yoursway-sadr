package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.constructs.AssignmentC;
import com.yoursway.sadr.python_v2.constructs.BinaryC;
import com.yoursway.sadr.python_v2.constructs.CallC;
import com.yoursway.sadr.python_v2.constructs.FieldAccessC;
import com.yoursway.sadr.python_v2.constructs.IfC;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.constructs.UnaryC;
import com.yoursway.sadr.python_v2.constructs.VariableReferenceC;
import com.yoursway.sadr.python_v2.goals.acceptors.InstanceTracker;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;

public class ForwardDataFlowTraverseGoal extends ContextSensitiveGoal {
    
    private final InstanceTracker instTracker;
    
    public ForwardDataFlowTraverseGoal(InstanceTracker isntTracker, Context context) {
        super(context);
        this.instTracker = isntTracker;
        if (((PythonObject) isntTracker.blankInstance()).getDecl() == null)
            throw new IllegalStateException("Instance source construct must have a declaration.");
    }
    
    private void traverseScope(VariableReferenceC var) {
        Scope scope = var.parentScope();
        for (PythonConstruct c : scope.getEnclosedConstructs()) {
            // Matching construct tree pattern: var.attr = expression
            if (!(c instanceof AssignmentC))
                continue;
            AssignmentC ass = (AssignmentC) c;
            if (!(ass.lhs() instanceof FieldAccessC))
                continue;
            FieldAccessC attrAccessC = (FieldAccessC) c;
            if (!(attrAccessC.receiver() instanceof VariableReferenceC))
                continue;
            VariableReferenceC ref = (VariableReferenceC) attrAccessC.receiver();
            if (!(ref.name().equals(var.name())))
                continue;
            // Coming here means that construct 'c' matches the pattern.
            VariableReferenceC attr = attrAccessC.variable();
            instTracker.addAttribute(attr.name(), ass.rhs(), getContext());
        }
    }
    
    private void processFlowPosition(PythonConstruct flowPos) {
        if (flowPos instanceof VariableReferenceC) {
            VariableReferenceC var = (VariableReferenceC) flowPos;
            traverseScope(var);
        }
    }
    
    public void preRun() {
        PythonConstruct decl = ((PythonObject) instTracker.blankInstance()).getDecl();
        //TODO what if declaration is a class declaration or method declaration
        if (decl.parent() instanceof AssignmentC) {
            processFlowPosition(((AssignmentC) decl.parent()).rhs());
        } else if (decl.parent() instanceof CallC) {
            //TODO schedule responders goal
        } else if (decl.parent() instanceof BinaryC) {
            //TODO schedule special method responders goal
        } else if (decl.parent() instanceof IfC) {
            //TOOD schedule special method responders goal
        } else if (decl.parent() instanceof UnaryC) {
            //TOOD schedule special method responders goal
        }
        //TODO report finished.
        throw new IllegalStateException();
    }
}