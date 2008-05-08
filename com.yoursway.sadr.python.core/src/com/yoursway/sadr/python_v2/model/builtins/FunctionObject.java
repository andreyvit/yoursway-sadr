package com.yoursway.sadr.python_v2.model.builtins;

import java.util.List;

import org.eclipse.dltk.python.parser.ast.expressions.PythonLambdaExpression;

import com.yoursway.sadr.python.core.typeinferencing.constructs.MethodDeclarationC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class FunctionObject extends PythonObject {
    
    private final PythonConstruct decl; //either MethodDeclarationC or PythonLambdaExpressionC
    
    public FunctionObject(MethodDeclarationC decl) {
        super(Builtins.FUNCTION);
        this.decl = decl;
    }
    
    public FunctionObject(PythonLambdaExpression decl) {
        super(Builtins.FUNCTION);
        this.decl = decl;
    }
    
    /**
     * @return either MethodDeclaration or PythonLambdaExpression object
     */
    public PythonConstruct getDecl() {
        return decl;
    }
    
    /**
     * @return call result
     */
    public RuntimeObject evaluate(List<RuntimeObject> actualArguments) {
        return null;
    }
    
}
