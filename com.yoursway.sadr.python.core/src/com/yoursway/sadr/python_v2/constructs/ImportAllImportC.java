package com.yoursway.sadr.python_v2.constructs;

import org.eclipse.dltk.python.parser.ast.expressions.PythonAllImportExpression;


public class ImportAllImportC extends ImportC<PythonAllImportExpression> {
    
    public ImportAllImportC(Scope sc, PythonAllImportExpression node) {
        super(sc, node);
    }
    
}
