package com.yoursway.sadr.python_v2.model;

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

import com.yoursway.sadr.python.core.runtime.PythonUtils;

/**
 * Provides access to construct structures of all modules of a project.
 */
public class RuntimeModelBaseImpl implements RuntimeModelBase {
    
    Map<String, LexicalScope> nameToModule = new HashMap<String, LexicalScope>();
    
    public RuntimeModelBaseImpl(IScriptProject project) {
        List<ISourceModule> modules = findSourceModules(project);
        for (ISourceModule module : modules)
            contributeModule(module);
    }
    
    public LexicalScope getModule(String name) {
        return nameToModule.get(name);
    }
    
    public Collection<String> getModuleNames() {
        return nameToModule.keySet();
    }
    
    private List<ISourceModule> findSourceModules(IScriptProject project) {
        List<ISourceModule> modules = new ArrayList<ISourceModule>();
        PythonUtils.findSourceModules(project, modules);
        return modules;
    }
    
    private void contributeModule(ISourceModule module) {
        try {
            PythonSourceParser parser = new PythonSourceParser();
            ModuleDeclaration moduleDecl = parser.parse(module.getElementName().toCharArray(), module
                    .getSourceAsCharArray(), null);
            ModuleModelBuilder moduleModelBuilder = new ModuleModelBuilder();
            moduleDecl.traverse(moduleModelBuilder);
            LexicalScope moduleObject = moduleModelBuilder.getModel();
            //FIXME convert module names to python code form (e.g. "package.module").
            nameToModule.put(module.getElementName(), moduleObject);
        } catch (ModelException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
