package com.yoursway.sadr.python.core.runtime;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.compiler.problem.IProblem;
import org.eclipse.dltk.compiler.problem.IProblemReporter;
import org.eclipse.dltk.ruby.internal.parser.JRubySourceParser;

import com.yoursway.sadr.engine.AnalysisEngine;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.ast.visitor.RubyAstTraverser;
import com.yoursway.sadr.python.core.ast.visitor.RubyAstVisitor;
import com.yoursway.sadr.python.core.runtime.contributions.Context;
import com.yoursway.sadr.python.core.typeinferencing.goals.ExpressionValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python.core.typeinferencing.values.StringValue;
import com.yoursway.sadr.python.core.typeinferencing.values.Value;
import com.yoursway.sadr.python.core.typeinferencing.valuesets.ValueSet;

public class DtlEvalResolver {
    
    private final AnalysisEngine engine;
    private CodeGatherer codeGatherer;
    
    public DtlEvalResolver(AnalysisEngine engine) {
        this.engine = engine;
    }
    
    public void process(CodeGatherer codeGatherer, Context context, ModuleDeclaration rootNode) {
        this.codeGatherer = codeGatherer;
        RubyAstTraverser traverser = new RubyAstTraverser();
        traverser.traverse(rootNode, new RootState(context));
    }
    
    abstract class State<T extends ASTNode> extends RubyAstVisitor<T> {
        
        protected final Context context;
        
        public State(Context context) {
            super(null);
            this.context = context;
        }
        
        public State(State<?> parentVisitor) {
            super(parentVisitor);
            context = parentVisitor.context();
        }
        
        public Context context() {
            return context;
        }
        
        @Override
        protected RubyAstVisitor<?> enterCallExpression(CallExpression node) {
            String name = node.getName();
            if (node.getReceiver() == null && "eval".equalsIgnoreCase(name)) {
                ASTNode[] args = RubyUtils.argumentsOf(node);
                if (args.length >= 1) {
                    ASTNode expr = args[0];
                    Scope scope = RubyUtils.restoreScope(context.fileScope(), expr);
                    ValueInfoGoal goal = new ExpressionValueInfoGoal(scope, expr, InfoKind.VALUE);
                    engine.evaluate(goal);
                    ValueSet valueSet = goal.resultWithoutKarma().getValueSet();
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
                                    
                                    public IMarker reportProblem(IProblem problem) throws CoreException {
                                        throw new RuntimeException("OMFG! " + problem.getMessage());
                                    }
                                    
                                });
                        codeGatherer.add(context.fileScope(), rootNode, node);
                    }
                }
            }
            return this;
        }
        
    }
    
    class RootState extends State<ASTNode> {
        
        public RootState(Context context) {
            super(context);
        }
        
    }
    
}
