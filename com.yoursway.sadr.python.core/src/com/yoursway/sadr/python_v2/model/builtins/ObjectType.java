package com.yoursway.sadr.python_v2.model.builtins;

import com.yoursway.sadr.python.core.typeinferencing.values.InstanceRegistrarImpl;
import com.yoursway.sadr.python.core.typeinferencing.values.InstanceValue;
import com.yoursway.sadr.python_v2.goals.ExpressionValueGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.goals.internal.CallResolver;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.RuntimeObject;
import com.yoursway.sadr.succeeder.IGoal;

public class ObjectType extends PythonClassType {
    final InstanceRegistrarImpl registrar = new InstanceRegistrarImpl();
    
    public RuntimeObject __init__(PythonArguments args) {
        return Builtins.createNone();
    }
    
    public RuntimeObject __call__(PythonArguments args) {
        return new PythonValue<InstanceValue>(ObjectType.instance(), new InstanceValue(ObjectType.instance(),
                registrar));
    }
    
    public ObjectType() {
        setSuperClasses(null);
        setAttribute(new SyncFunctionObject("len") {
            @Override
            public IGoal evaluateGoal(PythonValueSetAcceptor acceptor, final Context context,
                    final PythonArguments args) {
                return new ExpressionValueGoal(context, acceptor) {
                    public void preRun() {
                        RuntimeObject receiver = args.getSingle();
                        schedule(CallResolver.callMethod(receiver, "__len__", new PythonArguments(),
                                acceptor, context));
                    }
                    
                    @Override
                    public String describe() {
                        return "ExpressionValueGoal for function " + "len";
                    }
                };
            }
        });
    }
    
    private static ObjectType instance = new ObjectType();
    
    static ObjectType instance() {
        return instance;
    }
    
    @Override
    public String describe() {
        return "object";
    }
}
