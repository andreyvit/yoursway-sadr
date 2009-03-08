/**
 * 
 */
package com.yoursway.sadr.python_v2.goals;

import java.util.ArrayList;
import java.util.List;

import com.yoursway.sadr.python_v2.constructs.ClassDeclarationC;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.goals.acceptors.TupleIterator;
import com.yoursway.sadr.python_v2.model.RuntimeArguments;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.python_v2.model.builtins.types.InstanceType;
import com.yoursway.sadr.python_v2.model.builtins.types.PythonType;
import com.yoursway.sadr.python_v2.model.builtins.values.InstanceValue;

public class CreateInstanceGoal extends ContextSensitiveGoal {
    private final ClassDeclarationC classDeclarationC;
    
    public CreateInstanceGoal(ClassDeclarationC classDecl, RuntimeArguments args, Krocodile context) {
        super(context);
        this.classDeclarationC = classDecl;
    }
    
    @Override
    public PythonValueSet evaluate() {
        acceptor = new PythonValueSet();
        
        List<PythonConstruct> superClasses = classDeclarationC.getSuperClasses();
        ArrayList<PythonValueSet> supers = new ArrayList<PythonValueSet>();
        for (PythonConstruct superClass : superClasses) {
            PythonValueSet sup = superClass.evaluate(getKrocodile());
            if (!sup.isEmpty())
                supers.add(sup);
        }
        for (List<PythonObject> results : new TupleIterator(supers)) {
            ArrayList<PythonType> types = convertToTypes(results);
            if (types != null) {
                InstanceType receiverType = new InstanceType(classDeclarationC);
                receiverType.setSuperClasses(types);
                PythonObject receiver = new InstanceValue(receiverType);
                acceptor.addResult(receiver, getKrocodile());
            }
        }
        return acceptor;
    }
    
    private ArrayList<PythonType> convertToTypes(List<PythonObject> results) {
        ArrayList<PythonType> types = new ArrayList<PythonType>();
        for (PythonObject r : results) {
            if (r instanceof PythonType) {
                types.add((PythonType) r);
            } else {
                return null;
            }
        }
        return types;
    }
    
    @Override
    public String describe() {
        return super.describe() + "\nfor " + classDeclarationC.displayName();
    }
}