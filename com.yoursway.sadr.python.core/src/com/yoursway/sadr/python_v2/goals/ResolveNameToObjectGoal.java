package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.constructs.AssignmentC;
import com.yoursway.sadr.python_v2.constructs.ClassDeclarationC;
import com.yoursway.sadr.python_v2.constructs.IfC;
import com.yoursway.sadr.python_v2.constructs.ImportDeclarationC;
import com.yoursway.sadr.python_v2.constructs.MethodDeclarationC;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.constructs.VariableReferenceC;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.goals.internal.CallResolver;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.builtins.Builtins;
import com.yoursway.sadr.python_v2.model.builtins.FunctionObject;
import com.yoursway.sadr.succeeder.IGrade;

public class ResolveNameToObjectGoal extends IterationGoal {
    
    //    private final PythonConstruct var;
    private final String name;
    private final PythonConstruct from;
    
    public ResolveNameToObjectGoal(String name, PythonConstruct from, Context context,
            final PythonValueSetAcceptor acceptor) {
        super(acceptor, context);
        this.name = name;
        this.from = from;
        assert name != null && from != null;
    }
    
    public ResolveNameToObjectGoal(VariableReferenceC variable, Context context,
            final PythonValueSetAcceptor acceptor) {
        super(acceptor, context);
        this.name = variable.name();
        this.from = variable;
        assert name != null && from != null;
    }
    
    private boolean scopeLeft(PythonConstruct currentConstruct, PythonConstruct prevConstruct) {
        return currentConstruct == null && prevConstruct != null
                || currentConstruct.parentScope() != prevConstruct.parentScope();
    }
    
    private boolean match(PythonConstruct currentConstruct) {
        if (currentConstruct instanceof AssignmentC) {
            AssignmentC assignmentC = (AssignmentC) currentConstruct;
            PythonConstruct lhs = assignmentC.lhs();
            if (lhs instanceof VariableReferenceC) {
                VariableReferenceC reference = (VariableReferenceC) lhs;
                if (reference.node().getName().equals(this.name)) {
                    PythonConstruct subexpr = assignmentC.rhs();
                    schedule(subexpr.evaluate(getContext(), incSync.createAcceptor(getContext())));
                    return true;
                }
            }
        } else if (currentConstruct instanceof MethodDeclarationC) {
            //FIXME merge with previous if-statement.
            MethodDeclarationC declarationC = (MethodDeclarationC) currentConstruct;
            if (declarationC.node().getName().equals(this.name)) {
                FunctionObject obj = new FunctionObject(declarationC);
                PythonValueSetAcceptor resultAcceptor = incSync.createAcceptor(getContext());
                resultAcceptor.addResult(obj, getContext());
                updateGrade(resultAcceptor, Grade.DONE);
                return true;
            }
        } else if (currentConstruct instanceof ClassDeclarationC) {
            //FIXME merge with previous if-statement.
            ClassDeclarationC declarationC = (ClassDeclarationC) currentConstruct;
            if (declarationC.node().getName().equals(this.name)) {
                FunctionObject obj = new FunctionObject(declarationC);
                PythonValueSetAcceptor resultAcceptor = incSync.createAcceptor(getContext());
                resultAcceptor.addResult(obj, getContext());
                updateGrade(resultAcceptor, Grade.DONE);
                return true;
            }
        } else if (currentConstruct instanceof ImportDeclarationC) {
            ImportDeclarationC moduleImport = (ImportDeclarationC) currentConstruct;
            if (moduleImport.hasImport(this.name)) {
                schedule(new ResolveModuleImportGoal(moduleImport, this.name, incSync
                        .createAcceptor(getContext()), getContext()));
                return true;
            }
        } else if (currentConstruct instanceof IfC) {
            final IfC ifc = (IfC) currentConstruct;
            schedule(ifc.getCondition().evaluate(getContext(), new PythonValueSetAcceptor(getContext()) {
                
                @Override
                protected <T> void acceptIndividualResult(RuntimeObject result, IGrade<T> grade) {
                    if (null == result)
                        return;
                    schedule(CallResolver.callMethod(result, "__nonzero__", new PythonArguments(),
                            new PythonValueSetAcceptor(getContext()) {
                                
                                @Override
                                protected <K> void acceptIndividualResult(RuntimeObject result,
                                        IGrade<K> grade) {
                                    if (Builtins.getTrue().equals(result)) {
                                        schedule(new ResolveNameToObjectGoal(name, ifc.thenBlock().get(
                                                ifc.thenBlock().size() - 1), getContext(), incSync
                                                .createAcceptor(getContext())));
                                    } else if (Builtins.getFalse().equals(result)) {
                                        schedule(new ResolveNameToObjectGoal(name, ifc.elseBlock().get(
                                                ifc.elseBlock().size() - 1), getContext(), incSync
                                                .createAcceptor(getContext())));
                                    } else {
                                        //TODO schedule both
                                    }
                                }
                            }, getContext()));
                }
            }));
            return true;
            
        }
        return false;
    }
    
    @Override
    public String describe() {
        String scope = (from.innerScope()).toString();
        return super.describe() + "\nfor name " + this.name + " in " + scope;
    }
    
    @Override
    protected IterationGoal iteration() {
        PythonConstruct currentConstruct = this.from;
        Scope scope = currentConstruct.parentScope();
        boolean found = match(currentConstruct);
        PythonConstruct prevConstruct = currentConstruct;
        currentConstruct = currentConstruct.getSyntacticallyPreviousConstruct();
        if (found)
            return null;
        if (scopeLeft(currentConstruct, prevConstruct)) {
            if (getContext() != null && getContext().contains(this.name)) {
                RuntimeObject argument = getContext().getActualArgument(this.name);
                PythonValueSetAcceptor resultAcceptor = incSync.createAcceptor(getContext());
                resultAcceptor.addResult(argument, getContext());
                updateGrade(resultAcceptor, Grade.DONE);
                return null;
            }
            scope = scope.parentScope();
            if (scope == null) {
                //built-in name is checked
                RuntimeObject object = Builtins.instance().getAttribute(name);
                if (object != null) {
                    PythonValueSetAcceptor resultAcceptor = incSync.createAcceptor(getContext());
                    resultAcceptor.addResult(object, getContext());
                    updateGrade(resultAcceptor, Grade.DONE);
                }
                //TODO if result is empty return IMPOSSIBLE object
                return null;
            }
            currentConstruct = scope.getPostChildren().get(scope.getPostChildren().size() - 1);
        }
        return new ResolveNameToObjectGoal(name, currentConstruct, getContext(), resultsAcceptor());
    }
}
