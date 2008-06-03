package com.yoursway.sadr.python_v2.constructs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.python.parser.ast.PythonImportStatement;
import org.eclipse.dltk.python.parser.ast.expressions.PythonImportExpression;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class ImportModuleC extends ImportC<PythonImportStatement> {
    
    public ImportModuleC(Scope sc, PythonImportStatement node) {
        super(sc, node);
        List<?> pairs = node.getImports();
        for (Object object : pairs) {
            if (object instanceof PythonImportExpression) {
                PythonImportExpression expr = (PythonImportExpression) object;
                String name = expr.getName();
                addModule(name, name, null);
            }
            
        }
    }
    
    @Override
    protected void wrapEnclosedChildren() {
        setPostChildren(new ArrayList<PythonConstruct>());
    }
}
