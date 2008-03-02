package com.yoursway.sadr.python.core.typeinferencing.scopes;

import static com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo.createResult;
import static com.yoursway.sadr.python.core.typeinferencing.typesets.TypeSetFactory.typeSetWith;

import org.eclipse.dltk.python.parser.ast.PythonClassDeclaration;

import com.yoursway.sadr.python.core.runtime.RubyClass;
import com.yoursway.sadr.python.core.runtime.RubyUtils;
import com.yoursway.sadr.python.core.runtime.RubyVariable;
import com.yoursway.sadr.python.core.typeinferencing.constructs.dtl.ClassDeclarationC;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.python.core.typeinferencing.values.MetaClassValue;
import com.yoursway.sadr.python.core.typeinferencing.values.Value;
import com.yoursway.sadr.python.core.typeinferencing.valuesets.ValueSetFactory;

public class ClassScope extends LocalScope {
    private final ClassDeclarationC construct;
    private final RubyClass klass;
    
    public ClassScope(Scope parent, ClassDeclarationC construct, RubyClass klass) {
        super(parent, construct.node());
        this.construct = construct;
        this.klass = klass;
    }
    
    public RubyClass klass() {
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
    public RubyVariable findOwnVariable(String name) {
        RubyVariable var = klass.findField(name);
        if (var != null)
            return var;
        return klass.metaClass().findField(name);
    }
    
    public ValueInfo selfType() {
        Value value = new MetaClassValue(klass.metaClass());
        return createResult(typeSetWith(RubyUtils.createType(klass)), ValueSetFactory.valueSetWith(value));
    }
    
    @Override
    public RubyClass currentClass() {
        return klass;
    }
    
}
