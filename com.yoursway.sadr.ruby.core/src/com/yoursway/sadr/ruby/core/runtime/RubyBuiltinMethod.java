package com.yoursway.sadr.ruby.core.runtime;

import static com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.ruby.core.typeinferencing.engine.Construct;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.Scope;

public class RubyBuiltinMethod extends RubyMethod {
    
    private final String name;
    
    public RubyBuiltinMethod(RubyBasicClass klass, String name, RubyArgument... arguments) {
        super(klass, name, arguments);
        this.name = name;
    }
    
    @Override
    public String name() {
        return name;
    }
    
    public Construct<Scope, ASTNode> construct() {
        throw new UnsupportedOperationException();
    }
    
    public ValueInfo evaluateBuiltin(ValueInfo receiver, ValueInfo[] arguments) {
        return emptyValueInfo();
    }
    
    public boolean isBuiltin() {
        return true;
    }
    
}
