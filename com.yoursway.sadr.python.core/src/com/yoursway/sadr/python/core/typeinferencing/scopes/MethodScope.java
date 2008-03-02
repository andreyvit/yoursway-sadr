package com.yoursway.sadr.python.core.typeinferencing.scopes;

import static com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo.createResult;
import static com.yoursway.sadr.python.core.typeinferencing.typesets.TypeSetFactory.typeSetWith;

import org.eclipse.dltk.ast.declarations.MethodDeclaration;

import com.yoursway.sadr.python.core.runtime.RubyArgument;
import com.yoursway.sadr.python.core.runtime.RubyBasicClass;
import com.yoursway.sadr.python.core.runtime.RubyClass;
import com.yoursway.sadr.python.core.runtime.RubyLocalVariable;
import com.yoursway.sadr.python.core.runtime.RubyMetaClass;
import com.yoursway.sadr.python.core.runtime.RubyMethod;
import com.yoursway.sadr.python.core.runtime.RubySourceMethod;
import com.yoursway.sadr.python.core.runtime.RubyUtils;
import com.yoursway.sadr.python.core.runtime.RubyVariable;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.python.core.typeinferencing.values.InstanceValue;
import com.yoursway.sadr.python.core.typeinferencing.values.MetaClassValue;
import com.yoursway.sadr.python.core.typeinferencing.values.Value;
import com.yoursway.sadr.python.core.typeinferencing.valuesets.ValueSetFactory;

public class MethodScope extends LocalScope {
    
    private final RubySourceMethod method;
    private final DtlArgumentVariable[] argumentVariables;
    
    public MethodScope(Scope parent, RubySourceMethod method, MethodDeclaration node) {
        super(parent, node);
        if (method == null)
            throw new NullPointerException();
        if (node == null)
            throw new NullPointerException();
        this.method = method;
        argumentVariables = createArguments(method);
    }
    
    private DtlArgumentVariable[] createArguments(RubyMethod method) {
        RubyArgument[] args = method.arguments();
        DtlArgumentVariable[] argVars = new DtlArgumentVariable[args.length];
        for (int i = 0; i < args.length; i++)
            argVars[i] = new DtlArgumentVariable(method, args[i], (null));
        return argVars;
    }
    
    @Override
    public MethodDeclaration node() {
        return (MethodDeclaration) super.node();
    }
    
    public RubyMethod getMethod() {
        return method;
    }
    
    @Override
    protected String leafToString() {
        if (method == null)
            return "unkMethod:" + node().getName();
        else
            return "m:" + method;
    }
    
    @Override
    public RubyVariable findOwnVariable(String name) {
        for (DtlArgumentVariable var : argumentVariables)
            if (var.name().equalsIgnoreCase(name))
                return var;
        RubyVariable var = method.findLocalVariable(name);
        if (var != null)
            return var;
        RubyBasicClass klass = method.klass();
        var = klass.findField(name);
        if (var != null)
            return var;
        if (klass instanceof RubyClass)
            return ((RubyClass) klass).metaClass().findField(name);
        return null;
    }
    
    @Override
    public RubyVariable lookupVariable(String name) {
        RubyVariable variable = findVariable(name);
        if (variable == null)
            variable = new RubyLocalVariable(method, null, method.scope(), null);
        return variable;
    }
    
    public ValueInfo selfType() {
        RubyBasicClass klass = method.klass();
        Value value;
        if (klass instanceof RubyMetaClass)
            value = new MetaClassValue((RubyMetaClass) klass);
        else
            value = new InstanceValue((RubyClass) klass, instanceRegistrar());
        return createResult(typeSetWith(RubyUtils.createType(klass)), ValueSetFactory.valueSetWith(value));
    }
    
}
