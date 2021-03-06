package com.yoursway.sadr.ruby.core.typeinferencing.scopes;

import static com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import com.yoursway.sadr.ruby.core.runtime.RubyArgument;
import com.yoursway.sadr.ruby.core.runtime.RubyMethod;
import com.yoursway.sadr.ruby.core.runtime.RubyVariable;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.services.VariableLookup;

public class DynamicMethodScope extends ChildScope implements VariableLookup {
    
    private final ValueInfo receiver;
    private final DtlArgumentVariable[] argumentVariables;
    
    public DynamicMethodScope(MethodScope parent, ValueInfo receiver, ValueInfo[] arguments) {
        super(parent);
        
        RubyMethod method = parent.getMethod();
        this.receiver = receiver;
        //        this.receiver = calculateReceiver(method, receiver);
        argumentVariables = createArguments(method, arguments);
    }
    
    private DtlArgumentVariable[] createArguments(RubyMethod method, ValueInfo[] arguments) {
        RubyArgument[] args = method.arguments();
        DtlArgumentVariable[] argVars = new DtlArgumentVariable[args.length];
        for (int i = 0; i < args.length; i++)
            argVars[i] = new DtlArgumentVariable(method, args[i], (arguments.length > i ? arguments[i]
                    : emptyValueInfo()));
        return argVars;
    }
    
    //    private TypeSet calculateReceiver(DtlMethod method, ValueInfo possibleRecievers) {
    //        TypeSetBuilder builder = new TypeSetBuilder();
    //        DtlBasicClass declaredClass = method.klass();
    //        collectSuitableReceivers(possibleRecievers, method, builder);
    //        if (builder.isEmpty())
    //            // fallback - but this is unlikely to be hit, because one of possibleRecievers
    //            // should be the reason we found this method in the first place
    //            builder.add(DtlUtils.createType(declaredClass));
    //        TypeSet receiver = builder.build();
    //        return receiver;
    //    }
    
    //    private static void collectSuitableReceivers(ValueInfo possibleRecievers, DtlMethod method,
    //            TypeSetBuilder requestor) {
    //        boolean isStatic = method.isStatic();
    //        DtlClass klass = method.runtimeClass();
    //        for (DtlBasicClass receiver : possibleRecievers.possibleClasses()) {
    //            if (method.canBeCalledFrom(receiver)) {
    //                if (method.isStatic() && receiver instanceof DtlClass)
    //                    receiver = ((DtlClass) receiver).metaClass();
    //                requestor.add(DtlUtils.createType(receiver));
    //            }
    //        }
    //    }
    
    @Override
    protected String leafToString() {
        return null;
    }
    
    public ValueInfo selfType() {
        return receiver;
    }
    
    public VariableLookup variableLookup() {
        return this;
    }
    
    public RubyVariable findVariable(String name) {
        for (DtlArgumentVariable var : argumentVariables)
            if (var.name().equalsIgnoreCase(name))
                return var;
        return parent.variableLookup().findVariable(name);
    }
    
    public RubyConstruct createConstruct() {
        return parent.createConstruct();
    }
    
    public RubyVariable lookupVariable(String name) {
        return findVariable(name);
    }
    
}
