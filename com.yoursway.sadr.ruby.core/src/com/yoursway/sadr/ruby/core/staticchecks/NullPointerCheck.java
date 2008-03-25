package com.yoursway.sadr.ruby.core.staticchecks;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.core.ISourceModule;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.EmptyDynamicContext;
import com.yoursway.sadr.ruby.core.typeinferencing.constructs.RubyConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ExpressionValueInfoGoal;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo;

public class NullPointerCheck extends OneModuleRuntimeBasedCheck {
    
    protected IRubyProblemReporter reporter;
    
    private void checkCall(CallExpression call) {
        ASTNode receiver = call.getReceiver();
        
        String name = call.getName();
        if (isNilClassMethodName(name))
            return;
        
        if (receiver != null) {
            RubyConstruct callC = currentFileScope.createConstruct().subconstructFor(call);
            ExpressionValueInfoGoal goal = new ExpressionValueInfoGoal(callC, new EmptyDynamicContext(),
                    InfoKind.NULLABILITY);
            runtime.getEngine().evaluate(goal);
            ValueInfo info = goal.roughResult();
            
            if (info.getNullability() == Nullability.CanBeNull) {
                String message = "Object might be nil in this call."; //? The object can be nil in this invocation
                reporter.warning(message, receiver.sourceStart(), receiver.sourceEnd());
            }
        }
        
    }
    
    private boolean isNilClassMethodName(String name) {
        return name == "class";
    }
    
    protected class PointersVisitor extends ASTVisitor {
        @Override
        public boolean visitGeneral(ASTNode node) throws Exception {
            if (node instanceof CallExpression) {
                CallExpression call = (CallExpression) node;
                checkCall(call);
            }
            return true;
        }
    }
    
    public void check(ISourceModule module, IRubyProblemReporter reporter) {
        this.reporter = reporter;
        init(module);
        try {
            currentModuleDeclaration.traverse(new PointersVisitor());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
