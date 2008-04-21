package com.yoursway.sadr.python.core.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.parser.ISourceParser;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.python.internal.core.parser.PythonSourceParser;

import com.yoursway.sadr.engine.AnalysisEngine;
import com.yoursway.sadr.engine.CallDoneContinuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.Continuations;
import com.yoursway.sadr.engine.IterationContinuation;
import com.yoursway.sadr.engine.SimpleContinuation;
import com.yoursway.sadr.python.core.runtime.contributions.FileContributionsManager;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonFileC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.RootPythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.scopes.FileScope;
import com.yoursway.sadr.python.core.typeinferencing.scopes.RootScope;
import com.yoursway.sadr.python.core.typeinferencing.services.SearchService;

public class WholeProjectRuntime {
    
    private final class CodeGathererImpl implements CodeGatherer {
        private final PythonRuntimeModelCreator creator;
        
        private final PythonEvalResolver evalResolver;
        
        private CodeGathererImpl(PythonRuntimeModelCreator creator, PythonEvalResolver evalResolver) {
            this.creator = creator;
            this.evalResolver = evalResolver;
        }
        
        public ContinuationRequestorCalledToken add(final RootPythonConstruct root, ASTNode fakeParent,
                ContinuationScheduler scheduler) {
            final FileScope fileScope = root.innerStaticContext().nearestScope().fileScope();
            return creator.process(contributionsManager.createContext(fileScope), root, scheduler,
                    new SimpleContinuation() {
                        
                        public ContinuationRequestorCalledToken run(ContinuationScheduler requestor) {
                            return contributionsManager.addToIndex(root, requestor, new SimpleContinuation() {
                                
                                public ContinuationRequestorCalledToken run(ContinuationScheduler requestor) {
                                    return requestor.schedule(new SimpleContinuation() {
                                        
                                        public ContinuationRequestorCalledToken run(
                                                ContinuationScheduler requestor) {
                                            return evalResolver.process(CodeGathererImpl.this,
                                                    contributionsManager.createContext(fileScope), root,
                                                    requestor);
                                        }
                                        
                                    });
                                }
                                
                            });
                        }
                        
                    });
        }
    }
    
    protected PythonRuntimeModel runtimeModel;
    
    protected AnalysisEngine engine;
    
    protected HashMap<ISourceModule, PythonFileC> constructs = new HashMap<ISourceModule, PythonFileC>();
    
    private final Map<ASTNode, ModuleDeclaration> evalsToContents = new HashMap<ASTNode, ModuleDeclaration>();
    
    private FileContributionsManager contributionsManager;
    
    private List<ISourceModule> modules;
    
    private RootScope rootScope;
    
    private void init(final Collection<ISourceModule> modules) {
        engine = new AnalysisEngine();
        final ISourceParser parser = createSourceParser();
        
        PythonAnalysisSchema schema = new PythonAnalysisSchema();
        runtimeModel = schema.newRuntimeModel();
        
        contributionsManager = new FileContributionsManager(runtimeModel);
        rootScope = new RootScope(runtimeModel, contributionsManager, contributionsManager);
        final PythonRuntimeModelCreator creator = new PythonRuntimeModelCreator();
        final PythonEvalResolver evalResolver = new PythonEvalResolver(engine);
        final CodeGatherer codeGatherer = new CodeGathererImpl(creator, evalResolver);
        try {
            SimpleContinuation doEverything = new SimpleContinuation() {
                
                public ContinuationRequestorCalledToken run(ContinuationScheduler requestor) {
                    return Continuations.iterate(modules, new IterationContinuation<ISourceModule>() {
                        
                        public ContinuationRequestorCalledToken iteration(ISourceModule m,
                                ContinuationScheduler requestor, SimpleContinuation continuation) {
                            try {
                                ModuleDeclaration rootNode = parser.parse(m.getElementName().toCharArray(), m
                                        .getSourceAsCharArray(), null);
                                PythonModule module = new PythonModule(runtimeModel, m.getElementName());
                                PythonFileC fileC = new PythonFileC(rootScope, m, module, rootNode);
                                constructs.put(m, fileC);
                                codeGatherer.add(fileC, null, requestor);
                                //FIXME: Add sequencer
                                return requestor.schedule(continuation);
                            } catch (ModelException e) {
                                throw new ModelExceptionRuntimeWrapper(e); // TODO
                            }
                        }
                        
                    }, requestor, new CallDoneContinuation());
                }
                
            };
            engine.execute(doEverything);
        } catch (ModelExceptionRuntimeWrapper e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("restriction")
    private ISourceParser createSourceParser() {
        return new PythonSourceParser();
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
        PythonUtils.findSourceModules(project, modules);
        return modules;
    }
    
    public List<ISourceModule> getSourceModules() {
        return modules;
    }
    
    public WholeProjectRuntime(Collection<ISourceModule> modules) {
        init(modules);
    }
    
    public PythonFileC getConstructFor(ISourceModule m) {
        return constructs.get(m);
    }
    
    public PythonRuntimeModel getModel() {
        return runtimeModel;
    }
    
    public AnalysisEngine getEngine() {
        return engine;
    }
    
    public SearchService getSearchService() {
        return contributionsManager;
    }
    
}
