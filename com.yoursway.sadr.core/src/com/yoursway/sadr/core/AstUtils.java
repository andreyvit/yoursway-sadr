package com.yoursway.sadr.core;

import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.expressions.CallArgumentsList;
import org.eclipse.dltk.ast.expressions.CallExpression;

public class AstUtils {
    
    @SuppressWarnings("unchecked")
    public static List<ASTNode> argumentsOf(CallExpression node) {
        CallArgumentsList args = node.getArgs();
        List<ASTNode> arguments = null;
        if (args != null)
            arguments = args.getChilds();
        return arguments;
    }
    
}
