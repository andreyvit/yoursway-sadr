package com.yoursway.sadr.python.constructs;

import java.util.List;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.incremental.SourceUnit;
import com.yoursway.sadr.python.core.runtime.FileSourceUnit;
import com.yoursway.sadr.python.core.runtime.ProjectRuntime;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class PythonFileC extends PythonScopeImpl<ModuleDeclaration> implements PythonRootConstruct {
    
    private final String moduleName;
    private final ProjectRuntime projectRuntime;
    private final FileSourceUnit module;
    private final List<PythonConstruct> body;
    
    public PythonFileC(PythonStaticContext sc, ModuleDeclaration node, String name, FileSourceUnit module,
            ProjectRuntime projectRuntime) {
        super(sc, node, null);
        this.moduleName = name;
        this.module = module;
        this.projectRuntime = projectRuntime;
        body = wrap(node.getStatements(), this);
        body.isEmpty();
    }
    
    public ProjectRuntime getProjectRuntime() {
        return projectRuntime;
    }
    
    @Override
    public String toString() {
        return "Module " + this.moduleName;
    }
    
    @Override
    public PythonFileC getFileScope() {
        return this;
    }
    
    @Override
    public String name() {
        return this.moduleName;
    }
    
    public SourceUnit sourceUnit() {
        return module;
    }
    
    @Override
    public PythonFileC fileC() {
        return this;
    }
    
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
        throw new UnsupportedOperationException();
    }
    
}
