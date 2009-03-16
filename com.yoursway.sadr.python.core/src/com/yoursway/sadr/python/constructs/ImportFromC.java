package com.yoursway.sadr.python.constructs;

import java.util.Map;
import java.util.Map.Entry;

import kilim.pausable;

import org.eclipse.dltk.python.parser.ast.PythonImportFromStatement;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class ImportFromC extends ImportC<PythonImportFromStatement> {
    
    @Override
    public boolean hasImport(String variable) {
        if (node.isAllImport())
            return true;
        else
            return super.hasImport(variable);
    }
    
    public ImportFromC(PythonStaticContext sc, PythonImportFromStatement node, PythonConstructImpl<?> parent) {
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
    
    @Override
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
        throw new VoidConstructException(this);
    }
    
}
