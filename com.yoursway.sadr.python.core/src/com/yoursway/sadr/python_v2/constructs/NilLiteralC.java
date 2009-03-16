package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.dltk.ast.references.SimpleReference;


/**
 * @deprecated
 */
@Deprecated
public class NilLiteralC extends PythonConstructImpl<SimpleReference> {
    
    NilLiteralC(Scope sc, SimpleReference node) {
        super(sc, node);
    }
}
