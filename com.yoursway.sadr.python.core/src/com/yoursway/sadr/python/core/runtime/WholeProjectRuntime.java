package com.yoursway.sadr.python.core.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.ruby.internal.parser.JRubySourceParser;

import com.yoursway.sadr.engine.AnalysisEngine;
import com.yoursway.sadr.python.core.runtime.contributions.FileContributionsManager;
import com.yoursway.sadr.python.core.typeinferencing.scopes.FileScope;
import com.yoursway.sadr.python.core.typeinferencing.scopes.RootScope;
import com.yoursway.sadr.python.core.typeinferencing.services.SearchService;

public class WholeProjectRuntime {
    
    private final class CodeGathererImpl implements CodeGatherer {
        private final RubyRuntimeModelCreator creator;
        private final LinkedList<Runnable> postProcessingQueue;
        private final DtlEvalResolver evalResolver;
        
        private CodeGathererImpl(RubyRuntimeModelCreator creator, LinkedList<Runnable> postProcessingQueue,
                DtlEvalResolver evalResolver) {
            this.creator = creator;
            this.postProcessingQueue = postProcessingQueue;
            this.evalResolver = evalResolver;
        }
        
        public void add(final FileScope fileScope, final ModuleDeclaration rootNode, ASTNode fakeParent) {
            fileScope.calculateParents(rootNode, fakeParent);
            creator.process(contributionsManager.createContext(fileScope), rootNode);
            contributionsManager.addToIndex(fileScope, rootNode);
            postProcessingQueue.add(new Runnable() {
                
                public void run() {
                    evalResolver.process(CodeGathererImpl.this,
                            contributionsManager.createContext(fileScope), rootNode);
                }
                
            });
        }
        
    }
    
    protected RubyRuntimeModel runtimeModel;
    
    protected AnalysisEngine engine;
    
    protected HashMap<ISourceModule, ModuleDeclaration> asts = new HashMap<ISourceModule, ModuleDeclaration>();
    protected HashMap<ISourceModule, FileScope> scopes = new HashMap<ISourceModule, FileScope>();
    
    private final Map<ASTNode, ModuleDeclaration> evalsToContents = new HashMap<ASTNode, ModuleDeclaration>();
    
    private FileContributionsManager contributionsManager;
    
    private List<ISourceModule> modules;
    
    private RootScope rootScope;
    
    private void init(Collection<ISourceModule> modules) {
        engine = new AnalysisEngine();
        asts.clear();
        JRubySourceParser parser = new JRubySourceParser();
        runtimeModel = new RubyRuntimeModel();
        contributionsManager = new FileContributionsManager(runtimeModel);
        rootScope = new RootScope(runtimeModel, contributionsManager, contributionsManager);
        final RubyRuntimeModelCreator creator = new RubyRuntimeModelCreator();
        final DtlEvalResolver evalResolver = new DtlEvalResolver(engine);
        final LinkedList<Runnable> postProcessingQueue = new LinkedList<Runnable>();
        final CodeGatherer codeGatherer = new CodeGathererImpl(creator, postProcessingQueue, evalResolver);
        try {
            for (ISourceModule m : modules) {
                ModuleDeclaration rootNode = parser.parse(m.getElementName().toCharArray(), m
                        .getSourceAsCharArray(), null);
                FileScope fileScope = new FileScope(rootScope, m, rootNode);
                asts.put(m, rootNode);
                scopes.put(m, fileScope);
                codeGatherer.add(fileScope, rootNode, null);
            }
            while (!postProcessingQueue.isEmpty()) {
                Runnable item = postProcessingQueue.remove();
                item.run();
            }
        } catch (ModelException e) {
            e.printStackTrace();
        }
    }
    
    public ModuleDeclaration mapEval(ASTNode eval) {
        return evalsToContents.get(eval);
    }
    
    public WholeProjectRuntime(IScriptProject project) {
        modules = findSourceModules(project);
        init(modules);
    }
    
    private List<ISourceModule> findSourceModules(IScriptProject project) {
        List<ISourceModule> modules = new ArrayList<ISourceModule>();
        RubyUtils.findSourceModules(project, modules);
        return modules;
    }
    
    public List<ISourceModule> getSourceModules() {
        return modules;
    }
    
    public WholeProjectRuntime(Collection<ISourceModule> modules) {
        init(modules);
    }
    
    public ModuleDeclaration getASTFor(ISourceModule m) {
        return asts.get(m);
    }
    
    public FileScope getScopeFor(ISourceModule module) {
        return scopes.get(module);
    }
    
    public RubyRuntimeModel getModel() {
        return runtimeModel;
    }
    
    public AnalysisEngine getEngine() {
        return engine;
    }
    
    public SearchService getSearchService() {
        return contributionsManager;
    }
    
}
