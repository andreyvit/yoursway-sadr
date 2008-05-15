package com.yoursway.sadr.python.core.typeinferencing.constructs;

import org.eclipse.dltk.ast.references.SimpleReference;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

/**
 * @deprecated
 */
@Deprecated
public class NilLiteralC extends PythonConstructImpl<SimpleReference> {
    
    NilLiteralC(Scope sc, SimpleReference node) {
        super(sc, node);
    }
}
