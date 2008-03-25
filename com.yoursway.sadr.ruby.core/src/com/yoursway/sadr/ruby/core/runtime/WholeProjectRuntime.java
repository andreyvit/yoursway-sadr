package com.yoursway.sadr.ruby.core.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.parser.ISourceParser;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.ruby.internal.parser.JRubySourceParser;

import com.yoursway.sadr.engine.AnalysisEngine;
import com.yoursway.sadr.engine.Continuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.Query;
import com.yoursway.sadr.engine.SimpleContinuation;
import com.yoursway.sadr.ruby.core.runtime.contributions.FileContributionsManager;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.dtl.DtlFileC;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.FileScope;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.RootScope;
import com.yoursway.sadr.ruby.core.typeinferencing.services.SearchService;

public class WholeProjectRuntime {
    
    private final class CodeGathererImpl implements CodeGatherer {
        private final RubyRuntimeModelCreator creator;
        private final LinkedList<Runnable> postProcessingQueue;
        
        private final RubyEvalResolver evalResolver;
        
        private CodeGathererImpl(RubyRuntimeModelCreator creator, LinkedList<Runnable> postProcessingQueue,
                RubyEvalResolver evalResolver) {
            this.creator = creator;
            this.postProcessingQueue = postProcessingQueue;
            this.evalResolver = evalResolver;
        }
        
        public void add(final RubyConstruct root, ASTNode fakeParent) {
            final FileScope fileScope = root.staticContext().nearestScope().fileScope();
            ContinuationRequestor tenderRequestor = new ContinuationRequestor() {
                
                public Query currentQuery() {
                    throw new UnsupportedOperationException();
                }
                
                public void done() {
                    throw new UnsupportedOperationException();
                }
                
                public void subgoal(Continuation cont) {
                    throw new UnsupportedOperationException();
                }
                
            };
            creator.process(contributionsManager.createContext(fileScope), root, tenderRequestor,
                    new SimpleContinuation() {
                        
                        public void run(ContinuationRequestor requestor) {
                            contributionsManager.addToIndex(root, requestor, new SimpleContinuation() {
                                
                                public void run(ContinuationRequestor requestor) {
                                    postProcessingQueue.add(new Runnable() {
                                        
                                        public void run() {
                                            evalResolver.process(CodeGathererImpl.this, contributionsManager
                                                    .createContext(fileScope), root);
                                        }
                                        
                                    });
                                }
                                
                            });
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
        ISourceParser parser = createSourceParser();
        runtimeModel = new RubyRuntimeModel();
        contributionsManager = new FileContributionsManager(runtimeModel);
        rootScope = new RootScope(runtimeModel, contributionsManager, contributionsManager);
        final RubyRuntimeModelCreator creator = new RubyRuntimeModelCreator();
        final RubyEvalResolver evalResolver = new RubyEvalResolver(engine);
        final LinkedList<Runnable> postProcessingQueue = new LinkedList<Runnable>();
        final CodeGatherer codeGatherer = new CodeGathererImpl(creator, postProcessingQueue, evalResolver);
        try {
            for (ISourceModule m : modules) {
                ModuleDeclaration rootNode = parser.parse(m.getElementName().toCharArray(), m
                        .getSourceAsCharArray(), null);
                RubyFile module = new RubyFile(runtimeModel, m.getElementName());
                FileScope fileScope = new FileScope(rootScope, module, m, rootNode);
                asts.put(m, rootNode);
                scopes.put(m, fileScope);
                codeGatherer.add(new DtlFileC(fileScope, rootNode), null);
            }
            while (!postProcessingQueue.isEmpty()) {
                Runnable item = postProcessingQueue.remove();
                item.run();
            }
        } catch (ModelException e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("restriction")
    private ISourceParser createSourceParser() {
        return new JRubySourceParser();
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
