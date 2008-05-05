package com.yoursway.sadr.python_v2.model.builtins;

import org.eclipse.dltk.ast.statements.Statement;

/**
 * Fake class that must be resolved (evaluated) into a actual class.
 */
public class ClassStub extends PythonClassType {
    private final Statement statement;
    
    public ClassStub(Statement statement) {
        this.statement = statement;
    }
    
    public Statement getStatement() {
        return statement;
    }
    
}
