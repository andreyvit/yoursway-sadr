package com.yoursway.sadr.ruby.core.typeinferencing.goals;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.Goal;

public class ForCounterVariableValueInfoGoal extends AbstractValueInfoGoal {
    
    @Override
    protected String describeParameters() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return 0;
    }
    
    public Goal cloneGoal() {
        // TODO Auto-generated method stub
        return null;
    }
    
    public int debugSlot() {
        // TODO Auto-generated method stub
        return 0;
    }
    
    public ContinuationRequestorCalledToken evaluate(ContinuationScheduler requestor) {
        throw new NotImplementedException();
        // TODO Auto-generated method stub
    }
    //    
    //    private final class ForCounterContinuation implements ValueInfoContinuation {
    //        //        private final RubyForStatement2 node;
    //        private final ForScope scope;
    //        private final ValueInfoContinuation continuation;
    //        
    //        private ForCounterContinuation(RubyForStatement2 node, ForScope scope,
    //                ValueInfoContinuation continuation) {
    //            //            this.node = node;
    //            this.scope = scope;
    //            this.continuation = continuation;
    //        }
    //        
    //        public void consume(final ValueInfo result, ContinuationRequestor requestor) {
    //            // TODO: here we've put hui over the effect assignments have
    //            // on the loop iterations
    //            ASTNode leftBound0 = null, rightBound0 = null;
    //            RubyForStatement2 st = scope.node();
    //            if (st.getList().getChilds().get(0) instanceof RubyDotExpression) {
    //                RubyDotExpression expr = (RubyDotExpression) st.getList().getChilds().get(0);
    //                leftBound0 = expr.getBegin();
    //                rightBound0 = expr.getEnd();
    //            }
    //            if (leftBound0 == null)
    //                return;
    //            final ASTNode leftBound = leftBound0;
    //            final ASTNode rightBound = rightBound0;
    //            requestor.subgoal(new Continuation() {
    //                
    //                ValueInfoGoal startGoal = new ExpressionValueInfoGoal(fromC, new EmptyDynamicContext(),
    //                        InfoKind.VALUE);
    //                ValueInfoGoal endGoal = new ExpressionValueInfoGoal(toC, new EmptyDynamicContext(),
    //                        InfoKind.VALUE);
    //                
    //                public void provideSubgoals(SubgoalRequestor requestor) {
    //                    requestor.subgoal(startGoal);
    //                    requestor.subgoal(endGoal);
    //                }
    //                
    //                public void done(ContinuationRequestor requestor) {
    //                    ValueInfoBuilder builder = new ValueInfoBuilder();
    //                    builder.add(result);
    //                    executeIterations(builder);
    //                    builder.add(new SimpleType(scope.classLookup().standardTypes().intType()));
    //                    continuation.consume(builder.build(), requestor);
    //                }
    //                
    //                private void executeIterations(ValueInfoBuilder builder) {
    //                    ValueInfo startInfo = startGoal.result(thing());
    //                    ValueInfo endInfo = endGoal.result(thing());
    //                    ValueSet startValues = startInfo.getValueSet();
    //                    ValueSet endValues = endInfo.getValueSet();
    //                    for (Value startV : startValues.containedValues()) {
    //                        ValueTraits startTraits = startV.traits();
    //                        if (!startTraits.isInteger())
    //                            continue;
    //                        long start = startTraits.integerValue();
    //                        for (Value endV : endValues.containedValues()) {
    //                            ValueTraits endTraits = endV.traits();
    //                            if (!endTraits.isInteger())
    //                                continue;
    //                            long end = endTraits.integerValue();
    //                            
    //                            long count = Math.abs(end - start) + 1;
    //                            if (start <= end && count < MAX_ITERATIONS) {
    //                                for (long i = start; i <= end; i++)
    //                                    builder.add(new IntegerValue(i));
    //                            }
    //                        }
    //                    }
    //                }
    //                
    //            });
    //        }
    //        
    //    }
    //    
    //    private static final int MAX_ITERATIONS = 1000;
    //    
    //    private final ForCounterVariable variable;
    //    private final InfoKind kind;
    //    
    //    public ForCounterVariableValueInfoGoal(ForCounterVariable variable, InfoKind kind) {
    //        this.variable = variable;
    //        this.kind = kind;
    //    }
    //    
    //    public void evaluate(ContinuationRequestor requestor) {
    //        ForScope scope = variable.scope();
    //        if (scope == null)
    //            throw new NullPointerException();
    //        RubyConstruct rubyConstruct = scope.createConstruct();
    //        PropagationTracker tracker = scope.propagationTracker();
    //        
    //        VariableRequest request = new VariableRequest(variable, kind);
    //        tracker.traverseEntirely(rubyConstruct, request, requestor, new DelayedAssignmentsContinuation(
    //                request, kind, new ForCounterContinuation((RubyForStatement2) rubyConstruct.node(), scope,
    //                        ForCounterVariableValueInfoGoal.this)));
    //    }
    //    
    //    @Override
    //    public String describeParameters() {
    //        return "" + variable;
    //    }
    //    
    //    @Override
    //    public int hashCode() {
    //        final int prime = 31;
    //        int result = 1;
    //        result = prime * result + ((kind == null) ? 0 : kind.hashCode());
    //        result = prime * result + ((variable == null) ? 0 : variable.hashCode());
    //        return result;
    //    }
    //    
    //    @Override
    //    public boolean equals(Object obj) {
    //        if (this == obj)
    //            return true;
    //        if (obj == null)
    //            return false;
    //        if (getClass() != obj.getClass())
    //            return false;
    //        final ForCounterVariableValueInfoGoal other = (ForCounterVariableValueInfoGoal) obj;
    //        if (kind == null) {
    //            if (other.kind != null)
    //                return false;
    //        } else if (!kind.equals(other.kind))
    //            return false;
    //        if (variable == null) {
    //            if (other.variable != null)
    //                return false;
    //        } else if (!variable.equals(other.variable))
    //            return false;
    //        return true;
    //    }
    //    
    //    public int debugSlot() {
    //        return 5;
    //    }
    //    
    //    public Goal cloneGoal() {
    //        return new ForCounterVariableValueInfoGoal(variable, kind);
    //    }
    //    
}
