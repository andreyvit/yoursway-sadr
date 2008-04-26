package com.yoursway.sadr.python_v2.model;

import java.util.Set;

import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.python.parser.ast.expressions.PythonLambdaExpression;

import com.yoursway.sadr.python_v2.model.builtins.Builtins;

public class FunctionObject extends PythonObject implements LexicalScope {
    
    private final LexicalScope scope;
    
    private final Statement decl; //either MethodDeclaration or PythonLambdaExpression
    
    public FunctionObject(LexicalScope enclosingScope, MethodDeclaration decl) {
        super(Builtins.FUNCTION);
        scope = new LexicalScopeImpl(enclosingScope);
        this.decl = decl;
    }
    
    public FunctionObject(LexicalScope enclosingScope, PythonLambdaExpression decl) {
        super(Builtins.FUNCTION);
        scope = enclosingScope;
        this.decl = decl;
    }
    
    /**
     * @return either MethodDeclaration or PythonLambdaExpression object
     */
    public Statement getDecl() {
        return decl;
    }
    
    public LexicalScope enclosingScope() {
        return scope.enclosingScope();
    }
    
    public Set<String> getNames() {
        return scope.getNames();
    }
    
    public RuntimeObject lookup(String name) {
        return scope.lookup(name);
    }
    
    public void setName(String name, RuntimeObject object) {
        scope.setName(name, object);
    }
    
    public LexicalScope getScope() {
        return scope;
    }
    
}
