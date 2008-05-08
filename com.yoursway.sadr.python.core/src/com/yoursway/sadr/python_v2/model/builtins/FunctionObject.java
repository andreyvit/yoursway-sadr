package com.yoursway.sadr.python_v2.model.builtins;

import java.util.List;

import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.python.parser.ast.expressions.PythonLambdaExpression;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.yoursway.sadr.python_v2.model.LexicalScope;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class FunctionObject extends PythonObject {
    
    private final Statement decl; //either MethodDeclaration or PythonLambdaExpression
    
    public FunctionObject(LexicalScope enclosingScope, MethodDeclaration decl) {
        super(Builtins.FUNCTION);
        //        scope = new LexicalScopeImpl(enclosingScope);
        this.decl = decl;
    }
    
    public FunctionObject(LexicalScope enclosingScope, PythonLambdaExpression decl) {
        super(Builtins.FUNCTION);
        //        scope = enclosingScope;
        this.decl = decl;
    }
    
    public FunctionObject() {
        super(Builtins.FUNCTION);
        //        scope = null;
        this.decl = null;
    }
    
    /**
     * @return either MethodDeclaration or PythonLambdaExpression object
     */
    public Statement getDecl() {
        return decl;
    }
    
    public RuntimeObject evaluate(List<RuntimeObject> args) {
        throw new NotImplementedException();
    }
}
