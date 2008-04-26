package com.yoursway.sadr.python_v2.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.python.internal.core.parser.PythonSourceParser;

public class RuntimeModelBaseImpl implements RuntimeModelBase {
    
    Map<String, LexicalScope> nameToModule = new HashMap<String, LexicalScope>();
    
    public LexicalScope getModule(String name) {
        return nameToModule.get(name);
    }
    
    public Collection<String> getModules() {
        return nameToModule.keySet();
    }
    
    public void contributeModule(ISourceModule module) {
        PythonSourceParser parser = new PythonSourceParser();
        try {
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
