package com.yoursway.sadr.python_v2.goals.internal;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python_v2.constructs.ClassDeclarationC;
import com.yoursway.sadr.python_v2.constructs.MethodDeclarationC;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.builtins.FunctionObject;
import com.yoursway.sadr.python_v2.model.builtins.PythonClassType;

public final class CallResolver {
    private static void assertCallable(RuntimeObject callable) {
        if (callable == null) {
            throw new IllegalArgumentException("callable is null");
        }
        if (!(callable instanceof FunctionObject)) {
            throw new IllegalArgumentException("callable is not FunctionObject but "
                    + callable.getClass().getSimpleName());
        }
    }
    
    public static PythonValueSet callMethod(final RuntimeObject receiver, final String methodName,
            final PythonArguments args, Krocodile crocodile, final PythonConstruct callingConstruct) {
        PythonValueSet results = new PythonValueSet();
        for (RuntimeObject callable : findMethod(receiver, methodName, crocodile)) {
            args.getArgs().add(0, receiver);
            results.addResults(callFunction(callable, args, crocodile, callingConstruct));
        }
        return results;
    }
    
    public static PythonValueSet findMethod(RuntimeObject receiver, String methodName, Krocodile context) {
        if (receiver == null) {
            throw new IllegalStateException("Receiver is null!");
        }
        System.out.println("Looking for: " + receiver.toString() + "." + methodName);
        RuntimeObject callable = receiver.getScopedAttribute(methodName); // instance attributes
        // look into class definition
        if (callable == null && receiver.getType() instanceof PythonClassType) {
            PythonClassType klass = (PythonClassType) receiver.getType();
            if (klass == null)
                return PythonValueSet.EMPTY;
            if (!(klass.getDecl() instanceof ClassDeclarationC))
                return PythonValueSet.EMPTY;
            MethodDeclarationC declaredMethod = ((ClassDeclarationC) klass.getDecl())
                    .findDeclaredMethod(methodName);
            if (null == declaredMethod) {
                return PythonValueSet.EMPTY;
            }
            return declaredMethod.evaluate(context);
        }
        return new PythonValueSet(callable, context);
    }
    
    public static PythonValueSet callFunction(RuntimeObject callable, PythonArguments names,
            Krocodile crocodile, PythonConstruct callingConstruct) {
        assertCallable(callable);
        return callFunction((FunctionObject) callable, names, crocodile, callingConstruct);
    }
    
    public static PythonValueSet callFunction(FunctionObject callable, PythonArguments names,
            Krocodile crocodile, PythonConstruct callingConstruct) {
        if (crocodile.size() > 20) {
            return PythonValueSet.EMPTY;
        }
        PythonValueSet valueSet = callable.call(crocodile, names);
        valueSet.setCallingCostruct(callingConstruct);
        return valueSet;
    }
}
