/**
 * 
 */
package com.yoursway.sadr.blocks.swamp.tests;

import java.util.List;

public class Program {
    
    private final List<Statement> statements;

    public Program(List<Statement> statements) {
        if (statements == null)
            throw new NullPointerException("statements is null");
        this.statements = statements;
    }
    
}