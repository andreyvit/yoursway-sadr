package com.yoursway.sadr.python.core.runtime;

import static com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.python.core.typeinferencing.engine.Construct;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class RubyBuiltinProcedure extends RubyProcedure {
    
    public RubyBuiltinProcedure(RubyRuntimeModel model, String name, RubyArgument... arguments) {
        super(model, name, arguments);
    }
    
    public Construct<Scope, ASTNode> construct() {
        throw new UnsupportedOperationException();
    }
    
    public ValueInfo evaluateBuiltin(ValueInfo[] arguments) {
        return emptyValueInfo();
    }
    
    public boolean isBuiltin() {
        return true;
    }
    
}
