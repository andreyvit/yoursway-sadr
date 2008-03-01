/**
 * 
 */
package com.yoursway.sadr.python.core.typeinferencing.goals;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ruby.ast.RubyColonExpression;

import com.yoursway.sadr.python.core.ast.visitor.RubyAstVisitor;
import com.yoursway.sadr.python.core.runtime.RubyUtils;
import com.yoursway.sadr.python.core.runtime.RubyVariable;
import com.yoursway.sadr.python.core.typeinferencing.keys.wildcards.Wildcard;

class CallsVisitor extends RubyAstVisitor<ASTNode> {
    
    private final Collection<CallInfo> calls = new ArrayList<CallInfo>();
    
    private final RubyVariable variable;
    
    public CallsVisitor(RubyVariable variable) {
        super(null);
        this.variable = variable;
    }
    
    public CallInfo[] calls() {
        return calls.toArray(new CallInfo[calls.size()]);
    }
    
    @Override
    protected RubyAstVisitor<?> enterCallExpression(CallExpression node) {
        ASTNode receiver = node.getReceiver();
        if (receiver != null) {
            ASTNode terminal = RubyUtils.assignmentTerminalNode(receiver);
            if (matches(terminal)) {
                Wildcard wildcard = RubyUtils.assignmentWildcardExpression(receiver);
                calls.add(new CallInfo(wildcard, node));
            }
        }
        return this;
    }
    
    private boolean matches(ASTNode terminal) {
        if (terminal instanceof SimpleReference) {
            return (((SimpleReference) terminal).getName().equalsIgnoreCase(variable.name()));
        } else if (terminal instanceof RubyColonExpression) {
            ; // TODO
        }
        return false;
    }
    
}
