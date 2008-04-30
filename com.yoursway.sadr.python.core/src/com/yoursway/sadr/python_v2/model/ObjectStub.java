package com.yoursway.sadr.python_v2.model;

import org.eclipse.dltk.ast.statements.Statement;

import com.yoursway.sadr.python_v2.model.builtins.Builtins;

/**
 * Represents an unevaluated expression that must be evaluated before use.
 */
public class ObjectStub extends PythonObject {
    
    private final Statement statement;
    
    public ObjectStub(Statement statement) {
        super(Builtins.OBJECT);
        this.statement = statement;
    }
    
    public Statement getStatement() {
        return statement;
    }
    
}
