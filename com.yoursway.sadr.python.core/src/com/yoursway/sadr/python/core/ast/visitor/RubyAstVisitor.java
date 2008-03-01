package com.yoursway.sadr.python.core.ast.visitor;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ruby.ast.RubyAssignment;
import org.eclipse.dltk.ruby.ast.RubyBinaryExpression;
import org.eclipse.dltk.ruby.ast.RubyCallArgument;
import org.eclipse.dltk.ruby.ast.RubyClassDeclaration;
import org.eclipse.dltk.ruby.ast.RubyColonExpression;
import org.eclipse.dltk.ruby.ast.RubyConstantDeclaration;
import org.eclipse.dltk.ruby.ast.RubyDAssgnExpression;
import org.eclipse.dltk.ruby.ast.RubyForStatement2;
import org.eclipse.dltk.ruby.ast.RubyIfStatement;
import org.eclipse.dltk.ruby.ast.RubyModuleDeclaration;
import org.eclipse.dltk.ruby.ast.RubyReturnStatement;
import org.eclipse.dltk.ruby.ast.RubySelfReference;
import org.eclipse.dltk.ruby.ast.RubySingletonClassDeclaration;
import org.eclipse.dltk.ruby.ast.RubySingletonMethodDeclaration;
import org.eclipse.dltk.ruby.ast.RubySymbolReference;

/**
 * A Ruby AST visitor, differing from the DLTK's visitor in four ways:
 * <ol>
 * <li>Contains typed enter methods for every interesting node types (please
 * add additional methods when more node types are needed).</li>
 * <li>Enter methods return a visitor that will be used to visit the children
 * and will be called when traversing of the children has finished, or
 * <code>null</code> to skip visiting children.
 * <li>There is a single <code>leave</code> method that is called on the
 * children visitor (i.e. the one returned from the enter method) when
 * traversing leaves the first node it has visited; if the enter method returns
 * <code>null</code> or <code>this</code> (or any other existing visitor),
 * no leave method is called.</li>
 * <li>Enter/leave methods do not throw checked exceptions.</li>
 * </ol>
 * 
 * <p>
 * {@link RubyAstTraverser} should be used to traverse DLTK AST nodes using this
 * visitor.
 * </p>
 * 
 * <p>
 * If you think you need an ability to track leaving from interim nodes, please
 * think again. And if you still aren't convinced, feel free to implement that.
 * </p>
 * 
 * @author Andrey Tarantsov
 */
public class RubyAstVisitor<T extends ASTNode> {
    
    private final RubyAstVisitor<?> parentVisitor;
    
    private final T initialNode;
    
    private ASTNode currentNode;
    
    @SuppressWarnings("unchecked")
    public RubyAstVisitor(RubyAstVisitor<?> parentVisitor) {
        this.parentVisitor = parentVisitor;
        this.initialNode = (parentVisitor == null ? null : (T) parentVisitor.getCurrentNode());
    }
    
    void $verify(RubyAstVisitor<?> parentVisitor, ASTNode initialNode) {
        if (parentVisitor == this)
            return;
        Assert.isTrue(this.parentVisitor == parentVisitor);
        Assert.isTrue(this.initialNode == initialNode);
    }
    
    protected RubyAstVisitor<?> enterNode(ASTNode node) {
        return this;
    }
    
    // copy this method to support a new node
    protected RubyAstVisitor<?> enter(RubyForStatement2 node) {
        return enterNode(node);
    }
    
    public final RubyAstVisitor<?> switchEnter(ASTNode node) {
        currentNode = node;
        try {
            if (!shouldEnter(node))
                return null;
            //Methods
            if (node instanceof RubySingletonMethodDeclaration)
                return enterSingletonMethodDeclaration((RubySingletonMethodDeclaration) node);
            else if (node instanceof MethodDeclaration)
                return enterMethodDeclaration((MethodDeclaration) node);
            
            //Classes
            else if (node instanceof RubyClassDeclaration)
                return enterClassDeclaration((RubyClassDeclaration) node);
            else if (node instanceof RubyModuleDeclaration)
                return enterModuleDeclaration((RubyModuleDeclaration) node);
            else if (node instanceof RubySingletonClassDeclaration)
                return enterSingletonClassDeclaration((RubySingletonClassDeclaration) node);
            
            //Literals
            else if (node instanceof RubySymbolReference)
                return enterSymbolReference((RubySymbolReference) node);
            else if (node instanceof RubySelfReference)
                return enterSelfReference((RubySelfReference) node);
            else if (node instanceof RubyConstantDeclaration)
                return enterConstantDeclaration((RubyConstantDeclaration) node);
            else if (node instanceof SimpleReference)
                return enterSimpleReference((SimpleReference) node);
            //Statements
            else if (node instanceof RubyColonExpression)
                return enterColorExpression((RubyColonExpression) node);
            else if (node instanceof RubyReturnStatement)
                return enterReturnStatement((RubyReturnStatement) node);
            else if (node instanceof RubyIfStatement)
                return enterIfStatement((RubyIfStatement) node);
            else if (node instanceof RubyForStatement2)
                return enterForStatement((RubyForStatement2) node);
            else if (node instanceof CallExpression)
                return enterCallExpression((CallExpression) node);
            else if (node instanceof RubyCallArgument)
                return enterCallArgument((RubyCallArgument) node);
            else if (node instanceof RubyDAssgnExpression)
                return enterDAssgnExpression((RubyDAssgnExpression) node);
            else if (node instanceof RubyAssignment)
                return enterAssignment((RubyAssignment) node);
            else if (node instanceof RubyBinaryExpression)
                return enterBinaryExpression((RubyBinaryExpression) node);
            // copy the following two lines to support a new node
            else if (node instanceof RubyForStatement2)
                return enter((RubyForStatement2) node);
            else
                return enterUnknown(node);
        } finally {
            currentNode = null;
        }
    }
    
    protected RubyAstVisitor<?> enterUnknown(ASTNode node) {
        return enterNode(node);
    }
    
    protected RubyAstVisitor<?> enterBinaryExpression(RubyBinaryExpression node) {
        return enterNode(node);
    }
    
    protected RubyAstVisitor<?> enterAssignment(RubyAssignment node) {
        return enterNode(node);
    }
    
    protected RubyAstVisitor<?> enterDAssgnExpression(RubyDAssgnExpression node) {
        return enterNode(node);
    }
    
    protected RubyAstVisitor<?> enterCallArgument(RubyCallArgument node) {
        return enterNode(node);
    }
    
    protected RubyAstVisitor<?> enterCallExpression(CallExpression node) {
        return enterNode(node);
    }
    
    protected RubyAstVisitor<?> enterForStatement(RubyForStatement2 node) {
        return enterNode(node);
    }
    
    protected RubyAstVisitor<?> enterIfStatement(RubyIfStatement node) {
        return enterNode(node);
    }
    
    protected RubyAstVisitor<?> enterReturnStatement(RubyReturnStatement node) {
        return enterNode(node);
    }
    
    protected RubyAstVisitor<?> enterColorExpression(RubyColonExpression node) {
        return enterNode(node);
    }
    
    protected RubyAstVisitor<?> enterSimpleReference(SimpleReference node) {
        return enterNode(node);
    }
    
    protected RubyAstVisitor<?> enterConstantDeclaration(RubyConstantDeclaration node) {
        return enterNode(node);
    }
    
    protected RubyAstVisitor<?> enterSelfReference(RubySelfReference node) {
        return enterNode(node);
    }
    
    protected RubyAstVisitor<?> enterSymbolReference(RubySymbolReference node) {
        return enterNode(node);
    }
    
    protected RubyAstVisitor<?> enterSingletonClassDeclaration(RubySingletonClassDeclaration node) {
        return enterNode(node);
    }
    
    protected RubyAstVisitor<?> enterModuleDeclaration(RubyModuleDeclaration node) {
        return enterNode(node);
    }
    
    protected RubyAstVisitor<?> enterClassDeclaration(RubyClassDeclaration node) {
        return enterNode(node);
    }
    
    protected RubyAstVisitor<?> enterMethodDeclaration(MethodDeclaration node) {
        return enterNode(node);
    }
    
    protected RubyAstVisitor<?> enterSingletonMethodDeclaration(RubySingletonMethodDeclaration node) {
        return enterNode(node);
    }
    
    protected boolean shouldEnter(ASTNode node) {
        return true;
    }
    
    public final RubyAstVisitor<?> leaveNode(ASTNode node) {
        // assert: (initialNode != null) => (node != null) 
        Assert.isTrue(initialNode == null || node != null);
        if (node == initialNode) {
            leave();
            return parentVisitor;
        }
        return this;
    }
    
    protected void leave() {
    }
    
    public RubyAstVisitor<?> getParentVisitor() {
        return parentVisitor;
    }
    
    /**
     * Returns the node processing of which has resulted in the creation of this
     * visitor.
     */
    public T node() {
        return initialNode;
    }
    
    private ASTNode getCurrentNode() {
        if (currentNode == null)
            throw new IllegalStateException("getCurrentNode() can only be called from inside enter*()");
        return currentNode;
    }
    
}
