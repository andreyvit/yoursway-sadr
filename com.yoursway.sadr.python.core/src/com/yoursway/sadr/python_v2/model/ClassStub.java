package com.yoursway.sadr.python_v2.model;

import org.eclipse.dltk.ast.statements.Statement;

public class ClassStub extends PythonClass {
    private final Statement statement;
    
    public ClassStub(Statement statement) {
        this.statement = statement;
    }
    
    public Statement getStatement() {
        return statement;
    }
    
}
