package com.yoursway.sadr.python.core.runtime;

import static com.google.common.collect.Maps.newHashMap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.python.internal.core.parser.PythonSourceParser;

import com.yoursway.sadr.python_v2.constructs.PythonFileC;
import com.yoursway.sadr.succeeder.DefaultSchedulingStrategy;
import com.yoursway.sadr.succeeder.Engine;

public class ProjectRuntime {
    
    private final Collection<FileSourceUnit> modules;
    private final Map<File, PythonFileC> nameToModule = newHashMap();
    private final Engine engine;
    
    public ProjectRuntime(ProjectUnit project) {
        this.engine = new Engine(new DefaultSchedulingStrategy());
        
        this.modules = project.getModules();
        for (FileSourceUnit module : this.modules)
            contributeModule(module);
    }
    
    public ProjectRuntime(IScriptProject project) {
        this(new ProjectUnit(findSourceModules(project)));
    }
    
    private static Collection<FileSourceUnit> findSourceModules(IScriptProject project) {
        List<FileSourceUnit> modules = new ArrayList<FileSourceUnit>();
        PythonUtils.findSourceModules(project, modules);
        return modules;
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
    
    public Engine getEngine() {
        return this.engine;
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
            
            PythonFileC moduleObject = new PythonFileC(null, moduleDecl, moduleName, this);
            //FIXME convert module names to python code form (e.g. "package.module").
            nameToModule.put(module.getFile().getCanonicalFile(), moduleObject);
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
