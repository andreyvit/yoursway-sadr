package com.yoursway.sadr.python.core.typeinferencing.services;

import org.eclipse.dltk.python.parser.ast.expressions.PythonCallExpression;

import com.yoursway.sadr.python_v2.constructs.PythonConstructImpl;

public interface CallsRequestor {
    
    void call(PythonConstructImpl<PythonCallExpression> call);
    
}
