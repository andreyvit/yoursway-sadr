package com.yoursway.sadr.python_v2.goals.internal;

import java.util.List;

import com.yoursway.sadr.python.core.typeinferencing.constructs.ClassDeclarationC;
import com.yoursway.sadr.python_v2.model.builtins.PythonClassType;

public class PythonUserClassType extends PythonClassType {
    private final ClassDeclarationC decl;
    
    public PythonUserClassType(ClassDeclarationC decl) {
        this.decl = decl;
    }
    
    public PythonUserClassType(ClassDeclarationC decl, List<PythonClassType> supers) {
        super(supers);
        this.decl = decl;
    }
    
    public ClassDeclarationC getDecl() {
        return decl;
    }
    
    String getName() {
        return decl.getName();
    }
    
    @Override
    public String describe() {
        return getName();
    }
    
    //    IGoal findMethod(String name, PythonValueSetAcceptor acceptor, final Context context) {
    //        return new FindClassMethodGoal(decl, name, new PythonValueSetAcceptor() {
    //            
    //            public <T> void checkpoint(IGrade<T> grade) {
    //                RuntimeObject result = getResultByContext(context);
    //                if (result != null && result instanceof FunctionObject) {
    //                    ((FunctionObject) result).bind(PythonUserClassType.this);
    //                    //                    callFunction();
    //                    FindClassMethodGoal.this.updateGrade(acceptor, grade);
    //                }
    //            }
    //            
    //        }, context);
    //    }
}
