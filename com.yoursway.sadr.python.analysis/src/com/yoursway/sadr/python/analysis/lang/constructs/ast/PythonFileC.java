package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import java.util.List;

import kilim.pausable;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;

import com.yoursway.sadr.engine.incremental.SourceUnit;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonRootConstruct;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonScopeImpl;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class PythonFileC extends PythonScopeImpl<ModuleDeclaration> implements PythonRootConstruct {
    
    private final String moduleName;
    private final SourceUnit module;
    private final List<PythonConstruct> body;
    
    public PythonFileC(ModuleDeclaration node, String name, SourceUnit module) {
        super(null, node, null);
        this.moduleName = name;
        this.module = module;
        body = wrap(node.getStatements(), this);
        body.isEmpty();
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
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public PythonStaticContext parentScope() {
        return null;
    }
    
    @Override
    public MethodDeclarationC getParentMethodDeclarationC() {
        return null;
    }
    
    @Override
    public boolean isGlobalScope() {
        return true;
    }
    
}
