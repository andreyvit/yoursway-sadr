package com.yoursway.sadr.python.model;

import kilim.pausable;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.constructs.ConstructVisitor;
import com.yoursway.sadr.core.constructs.Request;
import com.yoursway.sadr.engine.util.AbstractMultiMap;
import com.yoursway.sadr.python.constructs.PythonConstruct;
import com.yoursway.sadr.python.constructs.PythonStaticContext;
import com.yoursway.sadr.python.index.unodes.Unode;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.utils.bugs.Bugs;

public class IndexRequest implements
        Request<PythonConstruct, PythonStaticContext, PythonDynamicContext, ASTNode> {
    
    private final AbstractMultiMap<Unode, PythonConstruct> assignments;
    
    public IndexRequest(AbstractMultiMap<Unode, PythonConstruct> assignments) {
        if (assignments == null)
            throw new NullPointerException("assignments is null");
        this.assignments = assignments;
    }
    
    public void accept(PythonConstruct construct) {
        if (construct == null)
            throw new NullPointerException("construct is null");
        if (construct instanceof IndexAffector)
            try {
                ((IndexAffector) construct).actOnIndex(this);
            } catch (Throwable e) {
                Bugs.bug(e);
            }
    }
    
    public void addAssignment(Unode lhs, PythonConstruct rhs) {
        if (lhs == null)
            throw new NullPointerException("lhs is null");
        if (rhs == null)
            throw new NullPointerException("rhs is null");
        assignments.put(lhs, rhs);
    }
    
    public void leave() {
    }
    
    @pausable
    public ConstructVisitor<PythonConstruct, PythonStaticContext, PythonDynamicContext, ASTNode> enter(
            PythonConstruct construct) {
        accept(construct);
        return this;
    }
    
}