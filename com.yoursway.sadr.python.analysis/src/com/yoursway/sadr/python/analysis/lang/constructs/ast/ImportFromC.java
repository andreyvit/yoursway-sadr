package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.dltk.python.parser.ast.PythonImportFromStatement;

import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;

public class ImportFromC extends ImportC<PythonImportFromStatement> {
    
    @Override
    public boolean hasImport(String variable) {
        if (node.isAllImport())
            return true;
        else
            return super.hasImport(variable);
    }
    
    public ImportFromC(PythonLexicalContext sc, PythonImportFromStatement node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        Map<?, ?> importedAsNames = node.getImportedAsNames();
        String module = node.getImportModuleName();
        if (node.isAllImport()) {
            addModule(module, "*", "*");
        }
        for (Entry<?, ?> object : importedAsNames.entrySet()) {
            String var = (String) object.getKey();
            String alias = (String) object.getValue();
            addModule(module, alias, var);
        }
    }
    
}
