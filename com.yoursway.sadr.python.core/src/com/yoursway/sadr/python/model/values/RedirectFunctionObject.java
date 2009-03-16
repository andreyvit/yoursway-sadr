//package com.yoursway.sadr.python.model.values;
//
//import com.yoursway.sadr.python.objects.RuntimeArguments;
//import com.yoursway.sadr.python_v2.croco.Krocodile;
//import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
//
//public final class RedirectFunctionObject extends BuiltinFunctionObject {
//    private final String methodName;
//    
//    public RedirectFunctionObject(String name, String methodName) {
//        super(name);
//        this.methodName = methodName;
//    }
//    
//    @Override
//    public PythonValueSet call(final Krocodile context, final RuntimeArguments args) {
//        return new CallRedirectGoal(context, args, methodName, getDecl()).evaluate();
//    }
//}