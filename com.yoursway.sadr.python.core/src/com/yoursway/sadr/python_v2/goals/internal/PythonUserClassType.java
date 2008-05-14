package com.yoursway.sadr.python_v2.goals.internal;

import java.util.List;

import com.yoursway.sadr.python.core.typeinferencing.constructs.ClassDeclarationC;
import com.yoursway.sadr.python_v2.goals.FindClassMethodGoal;
import com.yoursway.sadr.python_v2.goals.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.python_v2.model.RuntimeObject;
import com.yoursway.sadr.python_v2.model.builtins.FunctionObject;
import com.yoursway.sadr.python_v2.model.builtins.PythonClass;
import com.yoursway.sadr.python_v2.model.builtins.PythonClassType;
import com.yoursway.sadr.succeeder.IGoal;
import com.yoursway.sadr.succeeder.IGrade;

public class PythonUserClassType extends PythonClassType {
    private final ClassDeclarationC decl;
    
    public PythonUserClassType(ClassDeclarationC decl) {
        this.decl = decl;
    }
    
    public PythonUserClassType(ClassDeclarationC decl, List<PythonClass> supers) {
        super(supers);
        this.decl = decl;
    }
    
    public ClassDeclarationC getDecl() {
        return decl;
    }
    
    IGoal findMethod(String name, PythonValueSetAcceptor acceptor, final Context context) {
        return new FindClassMethodGoal(decl, name, new PythonValueSetAcceptor() {
            
            public <T> void checkpoint(IGrade<T> grade) {
                RuntimeObject result = getResultByContext(context);
                if (result != null && result instanceof FunctionObject) {
                    ((FunctionObject) result).bind(PythonUserClassType.this);
                    callFunction();
                }
            }
            
        }, context);
    }
}
