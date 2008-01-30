package com.yoursway.sadr.ruby;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ruby.typeinference.MethodContext;
import org.eclipse.dltk.ti.BasicContext;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.ITypeInferencer;
import org.eclipse.dltk.ti.goals.AbstractTypeGoal;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;

import com.yoursway.sadr.core.AnalysisEngine;
import com.yoursway.sadr.ruby.constructs.ModuleDeclarationC;
import com.yoursway.sadr.ruby.goals.TypeG;
import com.yoursway.sadr.ruby.scopes.TopLevelS;

public class DltkTypeInferencingAdapter implements ITypeInferencer {
    
    public IEvaluatedType evaluateType(AbstractTypeGoal goal, int timeLimit) {
        if (goal instanceof ExpressionTypeGoal)
            return calculate((ExpressionTypeGoal) goal);
        return null;
    }
    
    private IEvaluatedType calculate(ExpressionTypeGoal goal) {
        //        ASTNode node = goal.getExpression();
        ModuleDeclaration rootNode = getModuleDeclaration(goal.getContext());
        
        //        World world = new World();
        AnalysisEngine engine = new AnalysisEngine();
        TopLevelS topLevelS = new TopLevelS();
        ModuleDeclarationC rootC = new ModuleDeclarationC(topLevelS, rootNode);
        engine.evaluate(new TypeG(), rootC);
        
        return null;
    }
    
    private ModuleDeclaration getModuleDeclaration(IContext context) {
        if (context instanceof BasicContext) {
            BasicContext basicContext = (BasicContext) context;
            return basicContext.getRootNode();
        } else if (context instanceof MethodContext) {
            MethodContext methodContext = (MethodContext) context;
            return methodContext.getRootNode();
        }
        return null;
    }
    
}
