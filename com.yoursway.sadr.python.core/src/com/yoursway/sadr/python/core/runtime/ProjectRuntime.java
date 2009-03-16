package com.yoursway.sadr.python.core.runtime;

import static com.google.common.collect.Maps.newHashMap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import kilim.pausable;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.python.internal.core.parser.PythonSourceParser;

import com.yoursway.sadr.engine.incremental.IncrementalAnalysisEngine;
import com.yoursway.sadr.python.constructs.PythonFileC;
import com.yoursway.sadr.python.model.IndexManager;

public class ProjectRuntime {
    
    private final Collection<FileSourceUnit> modules;
    private final Map<File, PythonFileC> nameToModule = newHashMap();
    private final IncrementalAnalysisEngine engine;
    private final IndexManager indexManager;
    
    public ProjectRuntime(ProjectUnit project) {
        this.engine = new IncrementalAnalysisEngine();
        this.modules = project.getModules();
        this.indexManager = new IndexManager();
        for (FileSourceUnit module : this.modules)
            contributeModule(module);
    }
    
    public IncrementalAnalysisEngine getEngine() {
        return engine;
    }
    
    public PythonFileC getModule(File file) {
        try {
            return nameToModule.get(file.getCanonicalFile());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public PythonFileC getModule(FileSourceUnit fsu) {
        try {
            return nameToModule.get(fsu.getFile().getCanonicalFile());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    List<VariableReference> findVariables(String prefix) {
        List<VariableReference> list = new ArrayList<VariableReference>();
        
        return list;
        
    }
    
    public Collection<FileSourceUnit> getModules() {
        return modules;
    }
    
    private void contributeModule(FileSourceUnit module) {
        try {
            PythonSourceParser parser = new PythonSourceParser();
            String moduleName = module.getPathName();
            ModuleDeclaration moduleDecl = parser.parse(moduleName.toCharArray(), module
                    .getSourceAsCharArray(), null);
            
            final PythonFileC moduleObject = new PythonFileC(moduleDecl, moduleName, module, this);
            //FIXME convert module names to python code form (e.g. "package.module").
            nameToModule.put(module.getFile().getCanonicalFile(), moduleObject);
            engine.evaluate(new AbstractGenericGoal() {
                
                @pausable
                public GenericResult evaluate() {
                    indexManager.addToIndex(moduleObject);
                    return new GenericResult();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getCause() != null) {
                throw new IllegalStateException("Python module parse failed. See error in log.", e.getCause());
            } else {
                throw new IllegalStateException("Python module parse failed. See error in log.", e);
            }
        }
    }
}
