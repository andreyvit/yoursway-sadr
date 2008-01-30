package com.yoursway.sadr.ruby.constructs;

import org.eclipse.dltk.ast.expressions.CallExpression;

import com.yoursway.sadr.core.AbstractConstruct;
import com.yoursway.sadr.core.Scope;

public class CallC extends AbstractConstruct {
    
    //    private List<ASTNode> argument;
    
    public CallC(Scope scope, CallExpression node) {
        super(scope, node);
        //        argument = AstUtils.argumentsOf(node);
    }
    
    @Override
    public CallExpression node() {
        return (CallExpression) super.node();
    }
    
}
