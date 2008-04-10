package com.yoursway.sadr.python.core.typeinferencing.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.BinaryExpression;

import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo;

/**
 * Handler for ordinary binary operations
 */
public interface BinaryOperationHandler extends Continuation {
    
    void setContext(PythonConstructImpl<BinaryExpression> context, ValueInfo left, ValueInfo right);
    
    ValueInfo result();
}
