package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.PythonVariableAccessExpression;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.croco.DotFrog;
import com.yoursway.sadr.python_v2.croco.Frog;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.ResolveModuleImportGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.goals.internal.CallResolver;
import com.yoursway.sadr.python_v2.model.builtins.FunctionObject;
import com.yoursway.sadr.python_v2.model.builtins.ModuleType;
import com.yoursway.sadr.python_v2.model.builtins.ModuleValue;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;

public class FieldAccessC extends PythonConstructImpl<PythonVariableAccessExpression> {
    
    private final PythonConstruct receiver;
    private final VariableReferenceC variable;
    private final int RECEIVER = 0, VARIABLE = 1;
    
    FieldAccessC(Scope sc, PythonVariableAccessExpression node) {
        super(sc, node);
        this.receiver = getPostChildren().get(RECEIVER);
        this.variable = (VariableReferenceC) getPostChildren().get(VARIABLE);
        if (receiver == null)
            throw new NullPointerException("receiver is null");
        if (variable == null)
            throw new NullPointerException("variable is null");
    }
    
    public boolean match(Frog frog) {
        if (frog instanceof DotFrog) {
            DotFrog dotFrog = (DotFrog) frog;
            return dotFrog.match(this.variable.name());
        }
        return false;
    }
    
    @Override
    public PythonValueSet evaluate(final Krocodile crocodile) {
        PythonValueSet results = new PythonValueSet();
        PythonValueSet receiverResolved = receiver.evaluate(crocodile);
        for (RuntimeObject object : receiverResolved) {
            PythonValueSet method;
            if (object instanceof FunctionObject) {
                method = CallResolver.findMethod(object, ((FunctionObject) object).name(), crocodile);
            } else if (object instanceof PythonObject) {
                if (object.getType() == ModuleType.instance()) {
                    PythonValue<ModuleValue> value = (PythonValue<ModuleValue>) object;
                    method = new ResolveModuleImportGoal(value, Frog.searchFrog(variable.name()), crocodile)
                            .evaluate();
                } else {
                    method = CallResolver.findMethod(object, variable.name(), crocodile);
                }
                results.addResults(method);
            }
        }
        return results;
    }
    
    public PythonConstruct receiver() {
        return receiver;
    }
    
    public VariableReferenceC variable() {
        return variable;
    }
}
