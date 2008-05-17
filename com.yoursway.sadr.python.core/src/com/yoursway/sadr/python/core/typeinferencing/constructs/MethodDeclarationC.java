package com.yoursway.sadr.python.core.typeinferencing.constructs;

import static com.google.common.collect.Lists.newArrayList;
import static com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo.emptyValueInfo;
import static com.yoursway.sadr.python.core.typeinferencing.constructs.Effects.NO_FROGS;
import static java.util.Collections.singleton;

import java.util.List;

import org.eclipse.dltk.ast.declarations.Argument;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.goals.ExpressionValueGoal;
import com.yoursway.sadr.python_v2.goals.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.goals.VariableReadF;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.succeeder.IGoal;

public class MethodDeclarationC extends PythonScopeImpl<MethodDeclaration> {
    
    MethodDeclarationC(Scope sc, MethodDeclaration node) {
        super(sc, node);
        //        String name = node.getName();
        //        PythonClassImpl klass = staticContext().currentClass();
        //        if (klass != null) {
        //            PythonMethod method = klass.findMethod(name);
        //            if (method instanceof PythonSourceMethod)
        //                innerScope = ((PythonSourceMethod) method).scope();
        //        } else {
        //            PythonProcedure procedure = staticContext().procedureLookup().findProcedure(name);
        //            if (procedure instanceof PythonSourceProcedure)
        //                innerScope = ((PythonSourceProcedure) procedure).scope();
        //        }
    }
    
    @Override
    protected void wrapEnclosedChildren() {
        List<PythonConstruct> children = PythonConstructFactory.wrap(this.node.getStatements(), this);
        setChildConstructs(children);
    }
    
    public ContinuationRequestorCalledToken evaluateValue(PythonDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
        return continuation.consume(emptyValueInfo(), requestor);
    }
    
    public String displayName() {
        return "Method " + this.node.getName();
    }
    
    @Override
    public IGoal evaluate(Context context, PythonValueSetAcceptor acceptor) {
        return new ExpressionValueGoal(context, acceptor) {
            
            public void preRun() {
                updateGrade(acceptor, Grade.DONE);
            }
            
            @Override
            public String describe() {
                return super.describe() + "\nfor expression " + MethodDeclarationC.this.toString();
            }
        };
    }
    
    //    public void actOnModel(ModelRequest request) {
    //        PythonClassImpl klass = staticContext().currentClass();
    //        if (klass != null) {
    //            PythonSourceMethod method = new PythonSourceMethod(klass, request.context(), this);
    //            innerScope = new MethodScope(nearestScope(), method, node);
    //        } else {
    //            PythonSourceProcedure procedure = new PythonSourceProcedure(request.context(), this);
    //            innerScope = new ProcedureScope(nearestScope(), procedure, node);
    //        }
    //    }
    
    @Override
    public String name() {
        return this.node.getName();
    }
    
    @Override
    public Effects getEffects() {
        return new Effects(singleton(new MethodDeclarationEffect(this)), NO_FROGS);
    }
    
    public List<Frog> getArgumentFrogs() {
        List<Frog> result = newArrayList();
        for (Argument arg : (List<Argument>) node.getArguments())
            result.add(new VariableReadF(arg.getName()));
        return result;
    }
}
