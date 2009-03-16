package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python_v2.constructs.CallableDeclaration;
import com.yoursway.sadr.python_v2.constructs.ClassDeclarationC;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.RuntimeArguments;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.python_v2.model.builtins.types.PythonType;
import com.yoursway.sadr.python_v2.model.builtins.values.CallableObject;

public final class CallResolver {
    private static void assertCallable(PythonObject callable) {
        if (callable == null) {
            throw new IllegalArgumentException("callable is null");
        }
        if (!(callable instanceof CallableObject)) {
            throw new IllegalArgumentException("callable is not CallableObject but "
                    + callable.getClass().getSimpleName());
        }
    }
    
    public static PythonValueSet callMethod(final PythonObject receiver, final String methodName,
            final RuntimeArguments args, Krocodile crocodile, final PythonConstruct callingConstruct) {
        PythonValueSet results = new PythonValueSet();
        for (PythonObject callable : findMethod(receiver, methodName, crocodile)) {
            //            if (!(callable instanceof RedirectFunctionObject))
            //                args.getArgs().add(0, receiver);
            results.addResults(callFunction(callable, args, crocodile, callingConstruct));
        }
        return results;
    }
    
    public static PythonValueSet findMethod(PythonObject receiver, String methodName, Krocodile context) {
        if (receiver == null) {
            throw new IllegalStateException("Receiver is null!");
        }
        System.out.println("Looking for: " + receiver.toString() + "." + methodName);
        PythonObject callable = receiver.getBuiltinAttribute(methodName); // instance attributes
        // look into class definition
        if (callable == null) {
            PythonType klass = receiver.getType();
            if (klass == null)
                return PythonValueSet.EMPTY;
            if (!(klass.getDecl() instanceof ClassDeclarationC))
                return PythonValueSet.EMPTY;
            CallableDeclaration declaredMethod = ((ClassDeclarationC) klass.getDecl())
                    .findDeclaredMethod(methodName);
            if (null == declaredMethod) {
                return PythonValueSet.EMPTY;
            }
            return declaredMethod.evaluate(context);
        }
        return new PythonValueSet(callable, context);
    }
    
    public static PythonValueSet callFunction(PythonObject callable, RuntimeArguments names,
            Krocodile crocodile, PythonConstruct callingConstruct) {
        assertCallable(callable);
        return callFunction((CallableObject) callable, names, crocodile, callingConstruct);
    }
    
    public static PythonValueSet callFunction(CallableObject callable, RuntimeArguments names,
            Krocodile crocodile, PythonConstruct callingConstruct) {
        if (crocodile.size() > 20) {
            return PythonValueSet.EMPTY;
        }
        PythonValueSet valueSet = callable.call(crocodile, names);
        valueSet.setCallingCostruct(callingConstruct);
        return valueSet;
    }
}
