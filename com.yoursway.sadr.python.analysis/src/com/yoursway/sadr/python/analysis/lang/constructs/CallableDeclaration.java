package com.yoursway.sadr.python.analysis.lang.constructs;

import java.util.Collection;

import kilim.pausable;

import com.yoursway.sadr.python.analysis.context.dynamic.arguments.DeclaredArguments;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.lang.unodes.Bnode;

public interface CallableDeclaration extends PythonConstruct {
    
    String name();
    
    @pausable
    Collection<Bnode> findReturnedValues();
    
    DeclaredArguments getDeclaredArguments();
    
    PythonLexicalContext getInnerLC();
    
}
