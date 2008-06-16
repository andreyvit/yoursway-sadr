package com.yoursway.sadr.python_v2.constructs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.python.parser.ast.statements.IfStatement;

import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class IfC extends PythonConstructImpl<IfStatement> {
    
    private List<PythonConstruct> thenBlock;
    private List<PythonConstruct> elseBlock;
    private PythonConstruct condition;
    
    IfC(Scope sc, IfStatement node) {
        super(sc, node);
    }
    
    @Override
    protected void wrapEnclosedChildren() {
        thenBlock = PythonConstructFactory.wrap(node.getThen().getChilds(), innerScope());
        List<ASTNode> astElse = Collections.EMPTY_LIST;
        if (node.getElse() != null) {
            if (node.getElse() instanceof IfStatement) {
                astElse = new ArrayList<ASTNode>(1);
                astElse.add(node.getElse());
            } else if (node.getElse() instanceof Block) {
                astElse = node.getElse().getChilds();
            } else
                throw new IllegalStateException("Improper if statement handling.");
        }
        elseBlock = PythonConstructFactory.wrap(astElse, innerScope());
        List<PythonConstruct> postChildren = new LinkedList<PythonConstruct>();
        postChildren.addAll(thenBlock);
        postChildren.addAll(elseBlock);
        setPostChildren(postChildren);
        
        condition = PythonConstructFactory.wrap(node.getCondition(), innerScope());
        List<PythonConstruct> preChildren = new ArrayList<PythonConstruct>(1);
        preChildren.add(condition);
        setPreChildren(preChildren);
    }
    
    @Override
    protected void setupPrevConstructRelation(PythonConstruct prev) {
        prev = setPrevConstructRelation(prev, getPreChildren());
        this.setSyntacticallyPreviousConstruct(prev);
        setPrevConstructRelation(prev, thenBlock());
        setPrevConstructRelation(prev, elseBlock());
    }
    
    public List<PythonConstruct> thenBlock() {
        return thenBlock;
    }
    
    public List<PythonConstruct> elseBlock() {
        return elseBlock;
    }
    
    public PythonConstruct getCondition() {
        return condition;
    }
}
