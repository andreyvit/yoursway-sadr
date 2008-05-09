package com.yoursway.sadr.python.core.runtime;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.compiler.problem.IProblem;
import org.eclipse.dltk.compiler.problem.IProblemReporter;
import org.eclipse.dltk.python.internal.core.parser.PythonSourceParser;

import com.yoursway.sadr.blocks.foundation.values.Value;
import com.yoursway.sadr.engine.CallDoneContinuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.Continuations;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.IterationContinuation;
import com.yoursway.sadr.engine.SimpleContinuation;
import com.yoursway.sadr.python.core.typeinferencing.constructs.EmptyDynamicContext;
import com.yoursway.sadr.python.core.typeinferencing.constructs.EvalRootC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.EvalRequest;
import com.yoursway.sadr.python.core.typeinferencing.constructs.requests.EvalRequest.EvalInfo;
import com.yoursway.sadr.python.core.typeinferencing.goals.ExpressionValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.values.StringValue;
import com.yoursway.sadr.python.core.typeinferencing.valuesets.ValueSet;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.succeeder.Engine;

public class PythonEvalResolver {
    
    private final class SingleEvalValueHandler implements IterationContinuation<String> {
        private final PythonConstruct arg;
        private final EvalInfo info;
        private final CodeGatherer codeGatherer;
        
        private SingleEvalValueHandler(PythonConstruct arg, EvalInfo info, CodeGatherer codeGatherer) {
            this.arg = arg;
            this.info = info;
            this.codeGatherer = codeGatherer;
        }
        
        public ContinuationRequestorCalledToken iteration(String value, ContinuationScheduler scheduler,
                SimpleContinuation continuation) {
            PythonSourceParser parser = new PythonSourceParser();
            ModuleDeclaration rootNode = parser.parse(null, value.toCharArray(), new IProblemReporter() {
                
                public void clearMarkers() {
                }
                
                public IMarker reportProblem(IProblem problem) throws CoreException {
                    throw new RuntimeException("OMFG! " + problem.getMessage());
                }
                
            });
            return codeGatherer.add(new EvalRootC(arg.staticContext(), rootNode), info.getEval().node(),
                    scheduler);
        }
    }
    
    private final class EvalInfoContinuation implements IterationContinuation<EvalInfo> {
        
        private final CodeGatherer codeGatherer;
        
        private EvalInfoContinuation(CodeGatherer codeGatherer) {
            this.codeGatherer = codeGatherer;
        }
        
        public ContinuationRequestorCalledToken iteration(final EvalInfo info,
                ContinuationScheduler requestor, SimpleContinuation continuation) {
            final PythonConstruct arg = info.getArgument();
            ValueInfoGoal goal = new ExpressionValueInfoGoal(arg, new EmptyDynamicContext(), InfoKind.VALUE);
            engine.evaluate(goal);
            ValueSet valueSet = goal.roughResult().getValueSet();
            Collection<String> values = new ArrayList<String>();
            for (Value value : valueSet.containedValues())
                if (value instanceof StringValue) {
                    StringValue sv = (StringValue) value;
                    String string = sv.coherseToString();
                    values.add(string);
                }
            return Continuations.iterate(values, new SingleEvalValueHandler(arg, info, codeGatherer),
                    requestor, new CallDoneContinuation());
        }
    }
    
    private final Engine engine;
    
    public PythonEvalResolver(Engine engine) {
        this.engine = engine;
    }
    
    public ContinuationRequestorCalledToken process(final CodeGatherer codeGatherer, final Context context,
            PythonConstruct root, ContinuationScheduler scheduler) {
        final EvalRequest request = new EvalRequest();
        return root.staticContext().propagationTracker().traverseStatically(root, request, scheduler,
                new SimpleContinuation() {
                    
                    public ContinuationRequestorCalledToken run(ContinuationScheduler requestor) {
                        return Continuations.iterate(request.evalArgs(), new EvalInfoContinuation(
                                codeGatherer), requestor, new CallDoneContinuation());
                    }
                    
                });
        
    }
}
