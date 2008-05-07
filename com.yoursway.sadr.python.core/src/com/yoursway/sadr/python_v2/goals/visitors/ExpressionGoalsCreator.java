package com.yoursway.sadr.python_v2.goals.visitors;

import java.util.HashMap;
import java.util.Map;

import com.yoursway.sadr.python.core.runtime.contributions.Context;
import com.yoursway.sadr.python.core.typeinferencing.constructs.AssignmentC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.IntegerLiteralC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstructVisitor;
import com.yoursway.sadr.python.core.typeinferencing.constructs.StringLiteralC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.VariableReferenceC;
import com.yoursway.sadr.python_v2.goals.ExpressionValueGoal;
import com.yoursway.sadr.python_v2.goals.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.goals.ResolveName;
import com.yoursway.sadr.python_v2.goals.ResolvedNameAcceptor;
import com.yoursway.sadr.python_v2.model.builtins.IntType;
import com.yoursway.sadr.python_v2.model.builtins.StringType;
import com.yoursway.sadr.succeeder.Goal;
import com.yoursway.sadr.succeeder.IGrade;
import com.yoursway.sadr.succeeder.IScheduler;

public class ExpressionGoalsCreator extends PythonConstructVisitor {
    private final Map<PythonConstruct, PythonValueSetAcceptor> resultStorage = new HashMap<PythonConstruct, PythonValueSetAcceptor>();
    private final Context context;
    private final IScheduler engine;
    private final Goal parentGoal;
    
    public ExpressionGoalsCreator(IScheduler engine, Goal parentGoal, Context context) {
        this.engine = engine;
        this.parentGoal = parentGoal;
        this.context = context;
    }
    
    private PythonValueSetAcceptor createAcceptor(PythonConstruct construct) {
        PythonValueSetAcceptor acc = new PythonValueSetAcceptor() {
            public void checkpoint(IGrade<?> grade) {
                // TODO Auto-generated method stub
            }
            
        };
        resultStorage.put(construct, acc);
        return acc;
    }
    
    @Override
    public boolean visit(IntegerLiteralC construct) {
        PythonValueSetAcceptor acceptor = createAcceptor(construct);
        acceptor.addResult(IntType.newIntObject(construct), context);
        return false;
    }
    
    @Override
    public boolean visit(StringLiteralC construct) {
        PythonValueSetAcceptor acceptor = createAcceptor(construct);
        acceptor.addResult(StringType.newStringObject(construct), context);
        return false;
    }
    
    @Override
    public boolean visit(VariableReferenceC construct) {
        ResolvedNameAcceptor acceptor = new ResolvedNameAcceptor() {
            
            private AssignmentC assignmentC = null;
            
            public void setResult(AssignmentC assignmentC) {
                this.assignmentC = assignmentC;
            }
            
            public void checkpoint(IGrade<?> grade) {
                if (null == assignmentC)
                    throw new NullPointerException("Analysis failed");//FIXME
                engine.schedule(, new ExpressionValueGoal(assignmentC.rhs(), context));
            }
        };
        resultStorage.put(construct, acceptor);
        engine.schedule(parentGoal, new ResolveName(construct, acceptor));
        return false;
    }
    
    public Map<PythonConstruct, PythonValueSetAcceptor> getResultStorage() {
        return resultStorage;
    }
    
}
