package com.yoursway.sadr.python.core.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.python.internal.core.parser.PythonSourceParser;

import com.yoursway.sadr.python_v2.constructs.PythonFileC;
import com.yoursway.sadr.succeeder.DefaultSchedulingStrategy;
import com.yoursway.sadr.succeeder.Engine;

public class ProjectRuntime {
    
    private final Collection<ISourceModule> modules;
    private final Map<String, PythonFileC> nameToModule = new HashMap<String, PythonFileC>();
    private final Engine engine;
    
    public ProjectRuntime(IScriptProject project) {
        this.engine = new Engine(new DefaultSchedulingStrategy());
        
        this.modules = findSourceModules(project);
        for (ISourceModule module : this.modules)
            contributeModule(module);
    }
    
    public PythonFileC getModule(String name) {
        return nameToModule.get(name);
    }
    
    public Collection<String> getModuleNames() {
        return nameToModule.keySet();
    }
    
    public Engine getEngine() {
        return this.engine;
    }
    
    public Collection<ISourceModule> getModules() {
        return modules;
    }
    
    private List<ISourceModule> findSourceModules(IScriptProject project) {
        List<ISourceModule> modules = new ArrayList<ISourceModule>();
        PythonUtils.findSourceModules(project, modules);
        return modules;
    }
    
    private void contributeModule(ISourceModule module) {
        try {
            PythonSourceParser parser = new PythonSourceParser();
            String path = module.getParent().getElementName();
            if (path.length() != 0)
                path = path + "/";
            String moduleName = path + module.getElementName();
            ModuleDeclaration moduleDecl = parser.parse(moduleName.toCharArray(), module
                    .getSourceAsCharArray(), null);
            
            PythonFileC moduleObject = new PythonFileC(null, moduleDecl, moduleName, this);
            //FIXME convert module names to python code form (e.g. "package.module").
            nameToModule.put(moduleName, moduleObject);
        } catch (ModelException e) {
            e.printStackTrace();
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
