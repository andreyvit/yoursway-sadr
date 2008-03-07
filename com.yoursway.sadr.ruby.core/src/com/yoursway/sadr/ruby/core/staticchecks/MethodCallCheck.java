package com.yoursway.sadr.ruby.core.staticchecks;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.osgi.util.NLS;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.util.Strings;
import com.yoursway.sadr.ruby.core.runtime.RubyArgument;
import com.yoursway.sadr.ruby.core.runtime.RubyBasicClass;
import com.yoursway.sadr.ruby.core.runtime.RubyClass;
import com.yoursway.sadr.ruby.core.runtime.RubyMetaClass;
import com.yoursway.sadr.ruby.core.runtime.RubyMethod;
import com.yoursway.sadr.ruby.core.runtime.RubyProcedure;
import com.yoursway.sadr.ruby.core.runtime.RubyUtils;
import com.yoursway.sadr.ruby.core.runtime.requestors.methods.CollectingMethodRequestor;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ExpressionValueInfoGoal;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.MethodScope;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.Scope;

public class MethodCallCheck extends OneModuleRuntimeBasedCheck {
    
    protected IRubyProblemReporter reporter;
    
    public MethodCallCheck() {
        
    }
    
    private String checkArguments(CallExpression call, RubyArgument[] arguments, boolean report) {
        DtlSymbol name = call.getName(); //? SimpleReference?
        int callArgsCount = call.getArgs().getChilds().size();
        int min = 0;
        int max = arguments.length;
        for (RubyArgument a : arguments)
            if (a.usage() == RubyArgument.Usage.REQUIRED)
                min++;
        //?
        //else if (a.usage() == RubyArgument.Usage.ELLIPSIS)
        //    max = -1;
        if (callArgsCount < min || (max >= 0 && callArgsCount > max)) {
            String forStr;
            if (min == max)
                forStr = "" + min;
            else if (max < 0)
                forStr = min + "+";
            else
                forStr = min + ".." + max;
            String errorMessage = "Wrong number of arguments (" + callArgsCount + " for " + forStr + ")";
            if (report) {
                reporter.warning(errorMessage, name.sourceStart(), name.sourceEnd() + 1);
            } else {
                return errorMessage;
            }
        }
        return null;
    }
    
    private void checkArguments(CallExpression call, RubyMethod[] methods) {
        DtlSymbol name = call.getName();
        int callArgsCount = call.getArgs().getChilds().size();
        boolean found = false;
        String errorMessage = null;
        for (RubyMethod m : methods) {
            errorMessage = checkArguments(call, m.arguments(), false);
            found = (null == errorMessage);
            if (found)
                break;
        }
        if (!found) {
            if (methods.length > 1) {
                errorMessage = "Could not find method named \"" + name.getName() + "\" which accepts "
                        + callArgsCount + " argument.";
            }
            reporter.warning(errorMessage, name.sourceStart(), name.sourceEnd() + 1);
        }
    }
    
    private void checkCall(CallExpression call) {
        ASTNode receiver = call.getReceiver();
        String name = call.getName();
        boolean found = false;
        boolean warn = true;
        String errorMessage = null;
        if (receiver == null) {
            RubyProcedure p = runtime.getModel().findProcedure(name);
            if (p != null) {
                found = true;
                checkArguments(call, p.arguments(), true);
            }
            errorMessage = NLS.bind("Global procedure named \"{0}\" does not exist.", name);
        } else {
            Scope scope = RubyUtils.restoreScope(currentFileScope, call);
            ExpressionValueInfoGoal goal = new ExpressionValueInfoGoal(scope, receiver, InfoKind.TYPE);
            runtime.getEngine().evaluate(goal);
            ValueInfo types = goal.weakResult();
            CollectingMethodRequestor rq = new CollectingMethodRequestor();
            types.findMethod(name, rq);
            found = rq.anythingFound();
            if (!found) {
                for (RubyBasicClass t1 : types.possibleClasses()) {
                    RubyClass klass = (t1 instanceof RubyClass ? (RubyClass) t1 : ((RubyMetaClass) t1)
                            .instanceClass());
                    if (klass.isUndefined())
                        return;
                }
                if (types.isEmpty()) {
                    String msg = "Cannot check a call to \"{0}\" because could not inference the type of the receiver.";
                    errorMessage = NLS.bind(msg, name);
                    warn = false;
                } else {
                    //? ignoreCase???
                    if (name.equalsIgnoreCase("init") && (call.getReceiver() instanceof DtlSymbol)
                            && ((DtlSymbol) call.getReceiver()).getName().equalsIgnoreCase("super"))
                        return;
                    String[] possibleTypes = types.describePossibleTypes();
                    String msg = "Method named \"{0}\" does not exist in any of the following types: {1}.";
                    errorMessage = NLS.bind(msg, name, Strings.join(possibleTypes, ", "));
                }
            } else {
                checkArguments(call, rq.asArray());
            }
        }
        if (!found) {
            if (warn)
                reporter.warning(errorMessage, name.sourceStart(), name.sourceEnd() + 1);
            else
                reporter.info(errorMessage, name.sourceStart(), name.sourceEnd() + 1);
        }
    }
    
    protected class CallsVisitor extends ASTVisitor {
        
        public CallsVisitor() {
        }
        
        @Override
        public boolean visitGeneral(ASTNode node) throws Exception {
            if (node instanceof CallExpression) {
                CallExpression call = (CallExpression) node;
                checkCall(call);
            } else if (node instanceof MethodDeclaration) {
                MethodDeclaration decl = (MethodDeclaration) node;
                if (decl.getReceiver() != null) {
                    Scope scope = RubyUtils.restoreScope(currentFileScope, decl.getNameNode());
                    if (scope instanceof MethodScope) {
                        MethodScope methodScope = (MethodScope) scope;
                        RubyBasicClass t1 = methodScope.getMethod().klass();
                        RubyClass klass = (t1 instanceof RubyClass ? (RubyClass) t1 : ((RubyMetaClass) t1)
                                .instanceClass());
                        if (klass.isUndefined())
                            reporter.warning(NLS.bind("Defining method {0} in an undefined class {1}", decl
                                    .getName(), klass.name()), decl.getNameNode().sourceStart(), decl
                                    .getNameNode().sourceEnd());
                    }
                }
            }
            return true;
        }
    }
    
    public void check(ISourceModule module, IRubyProblemReporter reporter) {
        this.reporter = reporter;
        init(module);
        try {
            currentModuleDeclaration.traverse(new CallsVisitor());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
