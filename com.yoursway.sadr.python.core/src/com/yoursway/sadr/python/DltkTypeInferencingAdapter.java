package com.yoursway.sadr.python;

import org.eclipse.dltk.ti.ITypeInferencer;
import org.eclipse.dltk.ti.goals.AbstractTypeGoal;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;

public class DltkTypeInferencingAdapter implements ITypeInferencer {
    
    public IEvaluatedType evaluateType(AbstractTypeGoal goal, int timeLimit) {
        if (goal instanceof ExpressionTypeGoal)
            return calculate((ExpressionTypeGoal) goal);
        return null;
    }
    
    private IEvaluatedType calculate(ExpressionTypeGoal goal) {
        //        //        ASTNode node = goal.getExpression();
        //        ModuleDeclaration rootNode = getModuleDeclaration(goal.getContext());
        //        
        //        World world = new World();
        //        AnalysisEngine engine = new AnalysisEngine();
        //        TopLevelS topLevelS = new TopLevelS();
        //        ModuleDeclarationC rootC = new ModuleDeclarationC(topLevelS, rootNode);
        //        engine.evaluate(new WorldG(world), rootC);
        //        engine.evaluate(new TypeG(), rootC);
        //        
        //        System.out.println(world);
        
        return null;
    }
    
    //    private ModuleDeclaration getModuleDeclaration(IContext context) {
    //        if (context instanceof BasicContext) {
    //            BasicContext basicContext = (BasicContext) context;
    //            return basicContext.getRootNode();
    //        } else if (context instanceof MethodContext) {
    //            MethodContext methodContext = (MethodContext) context;
    //            return methodContext.getRootNode();
    //        }
    //        return null;
    //    }
    
}
