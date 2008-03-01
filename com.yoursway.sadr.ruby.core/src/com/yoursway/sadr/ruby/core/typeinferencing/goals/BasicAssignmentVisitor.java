package com.yoursway.sadr.ruby.core.typeinferencing.goals;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ruby.ast.RubyAssignment;

import com.yoursway.sadr.ruby.core.ast.visitor.RubyAstVisitor;
import com.yoursway.sadr.ruby.core.runtime.RubyUtils;
import com.yoursway.sadr.ruby.core.typeinferencing.engine.Construct;
import com.yoursway.sadr.ruby.core.typeinferencing.keys.wildcards.Wildcard;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.Scope;

public abstract class BasicAssignmentVisitor extends RubyAstVisitor<ASTNode> {
    
    public Scope scope;
    
    protected abstract boolean matches(ASTNode terminal);
    
    protected abstract void matched(ASTNode terminal, AssignmentInfo info);
    
    public BasicAssignmentVisitor(RubyAstVisitor<?> parentVisitor) {
        super(parentVisitor);
    }
    
    @Override
    protected RubyAstVisitor<?> enterAssignment(RubyAssignment node) {
        ASTNode lhs = node.getLeft();
        ASTNode rhs = node.getRight();
        if (rhs != null) {
            ASTNode terminal = RubyUtils.assignmentTerminalNode(lhs);
            Wildcard wildcard = RubyUtils.assignmentWildcardExpression(lhs);
            if (matches(terminal)) {
                Scope subscope = RubyUtils.restoreSubscope(scope, rhs);
                Construct<Scope, ASTNode> construct = new Construct<Scope, ASTNode>(subscope, rhs);
                AssignmentInfo info = new AssignmentInfo(wildcard, construct);
                matched(terminal, info);
            }
        }
        return this;
    }
    
}