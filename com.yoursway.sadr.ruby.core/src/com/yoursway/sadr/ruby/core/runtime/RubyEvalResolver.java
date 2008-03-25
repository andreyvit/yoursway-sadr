package com.yoursway.sadr.ruby.core.runtime;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.compiler.problem.IProblem;
import org.eclipse.dltk.compiler.problem.IProblemReporter;
import org.eclipse.dltk.ruby.internal.parser.JRubySourceParser;

import com.yoursway.sadr.engine.AnalysisEngine;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.SimpleContinuation;
import com.yoursway.sadr.ruby.core.runtime.contributions.Context;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.EmptyDynamicContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.dtl.EvalRootC;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests.EvalRequest;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.requests.EvalRequest.EvalInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ExpressionValueInfoGoal;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfoGoal;
import com.yoursway.sadr.ruby.core.typeinferencing.values.StringValue;
import com.yoursway.sadr.ruby.core.typeinferencing.values.Value;
import com.yoursway.sadr.ruby.core.typeinferencing.valuesets.ValueSet;

public class RubyEvalResolver {
    
    private final AnalysisEngine engine;
    
    public RubyEvalResolver(AnalysisEngine engine) {
        this.engine = engine;
    }
    
    public void process(final CodeGatherer codeGatherer, final Context context, RubyConstruct root) {
        final EvalRequest request = new EvalRequest();
        root.staticContext().propagationTracker().traverseStatically(root, request, null,
                new SimpleContinuation() {
                    
                    public void run(ContinuationRequestor requestor) {
                        for (EvalInfo info : request.evalArgs()) {
                            RubyConstruct arg = info.getArgument();
                            ValueInfoGoal goal = new ExpressionValueInfoGoal(arg, new EmptyDynamicContext(),
                                    InfoKind.VALUE);
                            engine.evaluate(goal);
                            ValueSet valueSet = goal.roughResult().getValueSet();
                            Collection<String> values = new ArrayList<String>();
                            for (Value value : valueSet.containedValues())
                                if (value instanceof StringValue) {
                                    StringValue sv = (StringValue) value;
                                    String string = sv.coherseToString();
                                    values.add(string);
                                }
                            for (String value : values) {
                                JRubySourceParser parser = new JRubySourceParser();
                                ModuleDeclaration rootNode = parser.parse(null, value.toCharArray(),
                                        new IProblemReporter() {
                                            
                                            public void clearMarkers() {
                                            }
                                            
                                            public IMarker reportProblem(IProblem problem)
                                                    throws CoreException {
                                                throw new RuntimeException("OMFG! " + problem.getMessage());
                                            }
                                            
                                        });
                                codeGatherer.add(new EvalRootC(arg.staticContext(), rootNode), info.getEval()
                                        .node());
                            }
                        }
                        
                    }
                    
                });
        
    }
    
}
