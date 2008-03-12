package com.yoursway.sadr.ruby;

import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.evaluation.types.AmbiguousType;
import org.eclipse.dltk.evaluation.types.UnknownType;
import org.eclipse.dltk.ruby.internal.parsers.jruby.ASTUtils;
import org.eclipse.dltk.ruby.typeinference.RubyClassType;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.ISourceModuleContext;
import org.eclipse.dltk.ti.ITypeInferencer;
import org.eclipse.dltk.ti.goals.AbstractTypeGoal;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;

import com.google.common.collect.Lists;
import com.yoursway.sadr.engine.AnalysisEngine;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.ruby.core.runtime.RubyUtils;
import com.yoursway.sadr.ruby.core.runtime.WholeProjectRuntime;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ExpressionValueInfoGoal;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.FileScope;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.ruby.core.typeinferencing.types.ClassType;
import com.yoursway.sadr.ruby.core.typeinferencing.types.MetaClassType;
import com.yoursway.sadr.ruby.core.typeinferencing.types.Type;

public class DltkTypeInferencingAdapter implements ITypeInferencer {
    
    public IEvaluatedType evaluateType(AbstractTypeGoal goal, int timeLimit) {
        if (goal instanceof ExpressionTypeGoal)
            return calculate((ExpressionTypeGoal) goal);
        return null;
    }
    
    private IEvaluatedType calculate(ExpressionTypeGoal goal) {
        ASTNode node = goal.getExpression();
        IContext context = goal.getContext();
        if (context instanceof ISourceModuleContext) {
            ISourceModuleContext smc = (ISourceModuleContext) context;
            ISourceModule sm = smc.getSourceModule();
            IScriptProject project = sm.getScriptProject();
            WholeProjectRuntime runtime = new WholeProjectRuntime(project);
            
            ModuleDeclaration rootNode = runtime.getASTFor(sm);
            ASTNode newNode = ASTUtils.findMinimalNode(rootNode, node.sourceStart(), node.sourceEnd());
            
            AnalysisEngine engine = runtime.getEngine();
            FileScope fileScope = runtime.getScopeFor(sm);
            Scope scope = RubyUtils.restoreSubscope(fileScope, newNode);
            ExpressionValueInfoGoal g = new ExpressionValueInfoGoal(scope, newNode, InfoKind.TYPE);
            engine.evaluate(g);
            ValueInfo result = g.weakResult();
            
            List<IEvaluatedType> types = Lists.newArrayList();
            for (Type type : result.getTypeSet().containedTypes()) {
                if (type instanceof MetaClassType) {
                    MetaClassType mct = (MetaClassType) type;
                    String className = mct.runtimeMetaClass().instanceClass().name();
                    RubyClassType rt = new RubyClassType(className + "%");
                    types.add(rt);
                } else if (type instanceof ClassType) {
                    ClassType mct = (ClassType) type;
                    String className = mct.runtimeClass().name();
                    RubyClassType rt = new RubyClassType(className + "%");
                    types.add(rt);
                }
            }
            switch (types.size()) {
            case 0:
                return UnknownType.INSTANCE;
            case 1:
                return types.iterator().next();
            default:
                return new AmbiguousType(types.toArray(new IEvaluatedType[types.size()]));
            }
        }
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
