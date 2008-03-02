package com.yoursway.sadr.python.core.runtime;

import com.yoursway.sadr.engine.AnalysisEngine;
import com.yoursway.sadr.python.core.runtime.contributions.Context;
import com.yoursway.sadr.python.core.typeinferencing.constructs.dtl.PythonConstruct;

public class DtlEvalResolver {
    
    //    private final AnalysisEngine engine;
    //    private CodeGatherer codeGatherer;
    
    public DtlEvalResolver(AnalysisEngine engine) {
        //        this.engine = engine;
    }
    
    public void process(CodeGatherer codeGatherer, Context context, PythonConstruct root) {
        //        this.codeGatherer = codeGatherer;
        //        FileScope fileScope = context.fileScope();
        //        PythonFileC fileC = new PythonFileC(fileScope, fileScope.node());
        
        //        context.fileScope().propagationTracker().traverseStatically(fileC, request, requestor, continuation);
        //        PythonAstTraverser traverser = new PythonAstTraverser();
        //        traverser.traverse(root, new RootState(context));
    }
    
    //    abstract class State<T extends ASTNode> extends RubyAstVisitor<T> {
    //        
    //        protected final Context context;
    //        
    //        public State(Context context) {
    //            super(null);
    //            this.context = context;
    //        }
    //        
    //        public State(State<?> parentVisitor) {
    //            super(parentVisitor);
    //            context = parentVisitor.context();
    //        }
    //        
    //        public Context context() {
    //            return context;
    //        }
    //        
    //        @Override
    //        protected RubyAstVisitor<?> enterCallExpression(CallExpression node) {
    //            String name = node.getName();
    //            if (node.getReceiver() == null && "eval".equalsIgnoreCase(name)) {
    //                ASTNode[] args = RubyUtils.argumentsOf(node);
    //                if (args.length >= 1) {
    //                    ASTNode expr = args[0];
    //                    Scope scope = RubyUtils.restoreScope(context.fileScope(), expr);
    //                    ValueInfoGoal goal = new ExpressionValueInfoGoal(scope, expr, InfoKind.VALUE);
    //                    engine.evaluate(goal);
    //                    ValueSet valueSet = goal.resultWithoutKarma().getValueSet();
    //                    Collection<String> values = new ArrayList<String>();
    //                    for (Value value : valueSet.containedValues())
    //                        if (value instanceof StringValue) {
    //                            StringValue sv = (StringValue) value;
    //                            String string = sv.coherseToString();
    //                            values.add(string);
    //                        }
    //                    for (String value : values) {
    //                        ISourceParser parser = createSourceParser();
    //                        ModuleDeclaration rootNode = parser.parse(null, value.toCharArray(),
    //                                new IProblemReporter() {
    //                                    
    //                                    public void clearMarkers() {
    //                                    }
    //                                    
    //                                    public IMarker reportProblem(IProblem problem) throws CoreException {
    //                                        throw new RuntimeException("OMFG! " + problem.getMessage());
    //                                    }
    //                                    
    //                                });
    //                        codeGatherer.add(context.fileScope(), rootNode, node);
    //                    }
    //                }
    //            }
    //            return this;
    //        }
    //        
    //    }
    
    //    @SuppressWarnings("restriction")
    //    private ISourceParser createSourceParser() {
    //        return new PythonSourceParser();
    //    }
    //    
    //    class RootState extends State<ASTNode> {
    //        
    //        public RootState(Context context) {
    //            super(context);
    //        }
    //        
    //    }
    //    
}
