/**
 * 
 */
package com.yoursway.sadr.python_v2.goals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python_v2.constructs.ClassDeclarationC;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.DictIterator;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.builtins.FunctionObject;
import com.yoursway.sadr.python_v2.model.builtins.PythonClassType;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;

public class CreateInstanceGoal extends ContextSensitiveGoal {
    private final ClassDeclarationC classDeclarationC;
    private final PythonConstruct instanceCreator;
    
    public CreateInstanceGoal(ClassDeclarationC classDecl, PythonConstruct instanceCreator,
            PythonArguments args, Krocodile context) {
        super(context);
        this.instanceCreator = instanceCreator;
        if (instanceCreator == null)
            throw new IllegalArgumentException();
        this.classDeclarationC = classDecl;
    }
    
    @Override
    public PythonValueSet evaluate() {
        acceptor = new PythonValueSet();
        
        List<PythonConstruct> superClasses = classDeclarationC.getSuperClasses();
        Map<PythonConstruct, PythonValueSet> map = new HashMap<PythonConstruct, PythonValueSet>();
        for (PythonConstruct superClass : superClasses) {
            map.put(superClass, superClass.evaluate(getKrocodile()));
        }
        
        for (Map<PythonConstruct, RuntimeObject> results : new DictIterator<PythonConstruct>(map)) {
            List<PythonClassType> supers = new ArrayList<PythonClassType>();
            for (RuntimeObject obj : results.values()) {
                if (obj instanceof FunctionObject) {
                    FunctionObject func = (FunctionObject) obj;
                    ClassDeclarationC decl = (ClassDeclarationC) func.getDecl();
                    PythonClassType superType = new PythonClassType(decl);
                    supers.add(superType);
                }
                
            }
            PythonClassType receiverType = new PythonClassType(classDeclarationC, supers);
            PythonObject receiver = new PythonObject(receiverType, instanceCreator);
            acceptor.addResult(receiver, getKrocodile());
        }
        return acceptor;
    }
    
    @Override
    public String describe() {
        return super.describe() + "\nfor " + classDeclarationC.displayName();
    }
}