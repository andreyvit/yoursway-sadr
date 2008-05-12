package com.yoursway.sadr.python_v2.model.builtins;

import java.util.List;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.yoursway.sadr.python.core.typeinferencing.constructs.MethodDeclarationC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class FunctionObject extends PythonObject {
    private final PythonConstruct decl; //either MethodDeclarationC or PythonLambdaExpressionC
    private final String name;
    
    public FunctionObject(MethodDeclarationC decl) {
        super(Builtins.FUNCTION);
        this.decl = decl;
        this.name = decl.node().getName();
        setAttribute("__name__", StringType.newStringObject(name));
    }
    
    public FunctionObject(PythonLambdaExpressionC decl) {
        super(Builtins.FUNCTION);
        this.decl = decl;
        this.name = "<lambda>";
        setAttribute("__name__", StringType.newStringObject(name));
    }
    
    public FunctionObject(String name) {
        super(Builtins.FUNCTION);
        this.decl = null;
        this.name = name;
        setAttribute("__name__", StringType.newStringObject(name));
    }
    
    /**
     * @return either MethodDeclaration or PythonLambdaExpression object
     */
    public PythonConstruct getDecl() {
        return decl;
    }
    
    public RuntimeObject evaluate(List<RuntimeObject> args) {
        throw new NotImplementedException();
    }
    
    @Override
    public String describe() {
        return "function " + name();
    }
    
    public String name() {
        return this.name;
    }
}
