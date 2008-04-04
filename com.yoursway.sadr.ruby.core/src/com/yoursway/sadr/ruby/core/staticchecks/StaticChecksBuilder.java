package com.yoursway.sadr.ruby.core.staticchecks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.builder.IScriptBuilder;

import com.yoursway.sadr.engine.AnalysisStats;
import com.yoursway.sadr.ruby.core.runtime.RubyUtils;
import com.yoursway.sadr.ruby.core.runtime.WholeProjectRuntime;

@SuppressWarnings("unchecked")
public class StaticChecksBuilder implements IScriptBuilder {
    
    private final IStaticCheck[] checks = new IStaticCheck[] { new MethodCallCheck(), new NullPointerCheck() };
    private WholeProjectRuntime runtime;
    
    public StaticChecksBuilder() {
    }
    
    public IStatus[] buildModelElements(IScriptProject project, List elements, IProgressMonitor monitor,
            int status) {
        if (elements.isEmpty())
            return null;
        
        SubMonitor progress = SubMonitor.convert(monitor, 100);
        progress.setTaskName("Static Analysis");
        long start = System.currentTimeMillis();
        
        List<ISourceModule> modules = findModules(project);
        progress.worked(1);
        progress.subTask("Building runtime model");
        initializeChecks(modules);
        System.out.println("runtime building, time = " + (System.currentTimeMillis() - start));
        progress.worked(10);
        
        runtime.getEngine().clearStats();
        
        progress.setWorkRemaining(checks.length * modules.size());
        for (ISourceModule module : modules) {
            progress.subTask("Checking " + module.getResource().getName());
            IResource resource = module.getResource();
            RubyProblemReporter reporter = new RubyProblemReporter(resource);
            reporter.reset();
            for (IStaticCheck c : checks) {
                c.check(module, reporter);
                progress.worked(1);
            }
            if (progress.isCanceled())
                return null;
        }
        
        System.out.println();
        System.out.println("------------------------------------------------");
        System.out.println("STATIC CHEKCING DONE in " + (System.currentTimeMillis() - start) + " ms");
        AnalysisStats stats = runtime.getEngine().clearStats();
        System.out.println(stats.toString());
        return null;
    }
    
    private void initializeChecks(List<ISourceModule> modules) {
        runtime = new WholeProjectRuntime(modules);
        for (IStaticCheck c : checks)
            c.init(runtime);
    }
    
    private List<ISourceModule> findModules(IScriptProject project) {
        List<ISourceModule> modules = new ArrayList<ISourceModule>();
        RubyUtils.findSourceModules(project, modules);
        return modules;
    }
    
    public IStatus[] buildResources(IScriptProject project, List resources, IProgressMonitor monitor,
            int status) {
        return null;
    }
    
    public List getDependencies(IScriptProject project, List resources) {
        return Collections.EMPTY_LIST;
    }
    
    public int estimateElementsToBuild(List elements) {
        // FIXME Auto-generated method stub
        return 0;
    }
    
}
