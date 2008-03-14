package com.yoursway.sadr.ruby.core.staticchecks;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.core.ISourceModule;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.ruby.core.runtime.RubyUtils;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ExpressionValueInfoGoal;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.Scope;

public class NullPointerCheck extends OneModuleRuntimeBasedCheck {
    
    protected IRubyProblemReporter reporter;
    
    private void checkCall(CallExpression call) {
        ASTNode receiver = call.getReceiver();
        
        String name = call.getName();
        if (isNilClassMethodName(name))
            return;
        
        if (receiver != null) {
            Scope scope = RubyUtils.restoreScope(currentFileScope, call);
            ExpressionValueInfoGoal goal = new ExpressionValueInfoGoal(scope, receiver, InfoKind.TYPE);
            runtime.getEngine().evaluate(goal);
            ValueInfo types = goal.roughResult();
            if (types.isEmpty()) {
                //?
                //String msg = "Cannot check a call to \"{0}\" because could not inference the type of the receiver.";
                //errorMessage = NLS.bind(msg, name);
                //warn = false;
            } else {
                String[] possibleTypes = types.describePossibleTypes();
                
                for (String each : possibleTypes) {
                    if (each == "NilClass") {
                        reporter.warning("Method can be invoked for nil", receiver.sourceStart(), receiver
                                .sourceEnd());
                        //? change message to "nil pointer dereferencing" or "method not exist"?
                        break;
                    }
                    
                }
                
                //String msg = "Method named \"{0}\" does not exist in any of the following types: {1}.";
                //errorMessage = NLS.bind(msg, name, Strings.join(possibleTypes, ", "));
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
