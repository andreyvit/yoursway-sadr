package com.yoursway.sadr.python.constructs;

import java.util.List;

import org.eclipse.dltk.python.parser.ast.PythonImportStatement;
import org.eclipse.dltk.python.parser.ast.expressions.PythonImportExpression;

public class ImportModuleC extends ImportC<PythonImportStatement> {
    
    public ImportModuleC(PythonStaticContext sc, PythonImportStatement node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        List<?> pairs = node.getImports();
        for (Object object : pairs) {
            if (object instanceof PythonImportExpression) {
                PythonImportExpression expr = (PythonImportExpression) object;
                String name = expr.getName();
                addModule(name, name, null);
            }
            
        }
    }
    
}
