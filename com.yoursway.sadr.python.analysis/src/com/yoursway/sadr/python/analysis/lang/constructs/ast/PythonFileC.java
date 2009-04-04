package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import java.util.List;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;

import com.yoursway.sadr.engine.incremental.SourceUnit;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.context.lexical.areas.ModuleArea;
import com.yoursway.sadr.python.analysis.context.lexical.scopes.ModuleScope;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonRootConstruct;

public class PythonFileC extends PythonConstructImpl<ModuleDeclaration> implements PythonRootConstruct {
    
    private final String moduleName;
    private final SourceUnit module;
    private final List<PythonConstruct> body;
    
    public PythonFileC(ModuleDeclaration node, String name, SourceUnit module) {
        super(new PythonLexicalContext(new ModuleScope(), new ModuleArea()), node, null);
        this.moduleName = name;
        this.module = module;
        body = wrap(node.getStatements(), staticContext());
        body.isEmpty();
    }
    
    @Override
    public String toString() {
        return "Module " + this.moduleName;
    }
    
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
    
}
