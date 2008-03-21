package com.yoursway.sadr.ruby.core.runtime;

import static com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.Scope;

public class RubyBuiltinProcedure extends RubyProcedure {
    
    public RubyBuiltinProcedure(RubyRuntimeModel model, String name, RubyArgument... arguments) {
        super(model, name, arguments);
    }
    
    public RubyConstruct construct() {
        throw new UnsupportedOperationException();
    }
    
    public ValueInfo evaluateBuiltin(ValueInfo[] arguments) {
        return emptyValueInfo();
    }
    
    public boolean isBuiltin() {
        return true;
    }
    
}
