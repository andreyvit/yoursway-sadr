package com.yoursway.sadr.ruby.core.typeinferencing.scopes;

import static com.yoursway.sadr.ruby.core.staticchecks.Nullability.CannotBeNull;
import static com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo.createResult;
import static com.yoursway.sadr.ruby.core.typeinferencing.typesets.TypeSetFactory.typeSetWith;

import org.eclipse.dltk.ruby.ast.RubyClassDeclaration;

import com.yoursway.sadr.ruby.core.runtime.RubySourceClassDefinition;
import com.yoursway.sadr.ruby.core.runtime.RubyUtils;
import com.yoursway.sadr.ruby.core.runtime.RubyVariable;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.values.MetaClassValue;
import com.yoursway.sadr.ruby.core.typeinferencing.values.Value;
import com.yoursway.sadr.ruby.core.typeinferencing.valuesets.ValueSetFactory;

public class ClassScope extends LocalScope {
    private final RubySourceClassDefinition klass;
    
    public ClassScope(Scope parent, RubySourceClassDefinition klass, RubyClassDeclaration node) {
        super(parent, node);
        if (klass == null)
            throw new NullPointerException();
        this.klass = klass;
    }
    
    @Override
    public RubyClassDeclaration node() {
        return (RubyClassDeclaration) super.node();
    }
    
    public RubySourceClassDefinition getKlass() {
        return klass;
    }
    
    @Override
    protected String leafToString() {
        if (klass == null)
            return "unkClass:" + node().getName();
        else
            return "m:" + klass;
    }
    
    @Override
    public RubyVariable findOwnVariable(String name) {
        
        RubyVariable var = klass.klass().findField(name);
        if (var != null)
            return var;
        return (klass.klass()).metaClass().findField(name);
    }
    
    public ValueInfo selfType() {
        Value value = new MetaClassValue(klass.klass().metaClass());
        return createResult(typeSetWith(RubyUtils.createType(klass.klass())), ValueSetFactory
                .valueSetWith(value), CannotBeNull);
    }
}
