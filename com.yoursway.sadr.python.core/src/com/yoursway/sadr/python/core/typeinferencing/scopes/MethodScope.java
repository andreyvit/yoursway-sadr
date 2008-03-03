package com.yoursway.sadr.python.core.typeinferencing.scopes;

import static com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo.createResult;
import static com.yoursway.sadr.python.core.typeinferencing.typesets.TypeSetFactory.typeSetWith;

import org.eclipse.dltk.ast.declarations.MethodDeclaration;

import com.yoursway.sadr.python.core.runtime.PythonCallableArgument;
import com.yoursway.sadr.python.core.runtime.PythonBasicClass;
import com.yoursway.sadr.python.core.runtime.PythonClass;
import com.yoursway.sadr.python.core.runtime.PythonLocalVariable;
import com.yoursway.sadr.python.core.runtime.PythonMetaClass;
import com.yoursway.sadr.python.core.runtime.PythonMethod;
import com.yoursway.sadr.python.core.runtime.PythonSourceMethod;
import com.yoursway.sadr.python.core.runtime.PythonUtils;
import com.yoursway.sadr.python.core.runtime.PythonVariable;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.python.core.typeinferencing.values.InstanceValue;
import com.yoursway.sadr.python.core.typeinferencing.values.MetaClassValue;
import com.yoursway.sadr.python.core.typeinferencing.values.Value;
import com.yoursway.sadr.python.core.typeinferencing.valuesets.ValueSetFactory;

public class MethodScope extends LocalScope {
    
    private final PythonSourceMethod method;
    private final DtlArgumentVariable[] argumentVariables;
    
    public MethodScope(Scope parent, PythonSourceMethod method, MethodDeclaration node) {
        super(parent, node);
        if (method == null)
            throw new NullPointerException();
        if (node == null)
            throw new NullPointerException();
        this.method = method;
        argumentVariables = createArguments(method);
    }
    
    private DtlArgumentVariable[] createArguments(PythonMethod method) {
        PythonCallableArgument[] args = method.arguments();
        DtlArgumentVariable[] argVars = new DtlArgumentVariable[args.length];
        for (int i = 0; i < args.length; i++)
            argVars[i] = new DtlArgumentVariable(method, args[i], (null));
        return argVars;
    }
    
    @Override
    public MethodDeclaration node() {
        return (MethodDeclaration) super.node();
    }
    
    public PythonMethod getMethod() {
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
    public PythonVariable findOwnVariable(String name) {
        for (DtlArgumentVariable var : argumentVariables)
            if (var.name().equalsIgnoreCase(name))
                return var;
        PythonVariable var = method.findLocalVariable(name);
        if (var != null)
            return var;
        PythonBasicClass klass = method.klass();
        var = klass.findField(name);
        if (var != null)
            return var;
        if (klass instanceof PythonClass)
            return ((PythonClass) klass).metaClass().findField(name);
        return null;
    }
    
    @Override
    public PythonVariable lookupVariable(String name) {
        PythonVariable variable = findVariable(name);
        if (variable == null)
            variable = new PythonLocalVariable(method, null, method.scope(), name);
        return variable;
    }
    
    public ValueInfo selfType() {
        PythonBasicClass klass = method.klass();
        Value value;
        if (klass instanceof PythonMetaClass)
            value = new MetaClassValue((PythonMetaClass) klass);
        else
            value = new InstanceValue((PythonClass) klass, instanceRegistrar());
        return createResult(typeSetWith(PythonUtils.createType(klass)), ValueSetFactory.valueSetWith(value));
    }
    
}
