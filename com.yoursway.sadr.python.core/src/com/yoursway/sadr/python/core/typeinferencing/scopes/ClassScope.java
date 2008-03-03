package com.yoursway.sadr.python.core.typeinferencing.scopes;

import static com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo.createResult;
import static com.yoursway.sadr.python.core.typeinferencing.typesets.TypeSetFactory.typeSetWith;

import org.eclipse.dltk.python.parser.ast.PythonClassDeclaration;

import com.yoursway.sadr.python.core.runtime.PythonClass;
import com.yoursway.sadr.python.core.runtime.PythonUtils;
import com.yoursway.sadr.python.core.runtime.PythonVariable;
import com.yoursway.sadr.python.core.typeinferencing.constructs.ClassDeclarationC;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.python.core.typeinferencing.values.MetaClassValue;
import com.yoursway.sadr.python.core.typeinferencing.values.Value;
import com.yoursway.sadr.python.core.typeinferencing.valuesets.ValueSetFactory;

public class ClassScope extends LocalScope {
    private final ClassDeclarationC construct;
    private final PythonClass klass;
    
    public ClassScope(Scope parent, ClassDeclarationC construct, PythonClass klass) {
        super(parent, construct.node());
        this.construct = construct;
        this.klass = klass;
    }
    
    public PythonClass klass() {
        return klass;
    }
    
    @Override
    public PythonClassDeclaration node() {
        return (PythonClassDeclaration) super.node();
    }
    
    @Override
    protected String leafToString() {
        return construct.displayName();
    }
    
    @Override
    public PythonVariable findOwnVariable(String name) {
        PythonVariable var = klass.findField(name);
        if (var != null)
            return var;
        return klass.metaClass().findField(name);
    }
    
    public ValueInfo selfType() {
        Value value = new MetaClassValue(klass.metaClass());
        return createResult(typeSetWith(PythonUtils.createType(klass)), ValueSetFactory.valueSetWith(value));
    }
    
    @Override
    public PythonClass currentClass() {
        return klass;
    }
    
}
