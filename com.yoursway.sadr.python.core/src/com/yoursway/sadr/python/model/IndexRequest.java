package com.yoursway.sadr.python.model;

import kilim.pausable;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.constructs.ConstructVisitor;
import com.yoursway.sadr.core.constructs.Request;
import com.yoursway.sadr.python.constructs.MethodDeclarationC;
import com.yoursway.sadr.python.constructs.PythonConstruct;
import com.yoursway.sadr.python.constructs.PythonStaticContext;
import com.yoursway.sadr.python.index.unodes.AttributeUnode;
import com.yoursway.sadr.python.index.unodes.Unode;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.utils.bugs.Bugs;

public class IndexRequest implements
        Request<PythonConstruct, PythonStaticContext, PythonDynamicContext, ASTNode> {
    
    private final IndexNameWrappingStrategy wrappingStrategy;
    private final PythonFileIndexMemento memento;
    
    public IndexRequest(PythonFileIndexMemento memento, IndexNameWrappingStrategy wrappingStrategy) {
        if (memento == null)
            throw new NullPointerException("memento is null");
        if (wrappingStrategy == null)
            throw new NullPointerException("wrappingStrategy is null");
        this.memento = memento;
        this.wrappingStrategy = wrappingStrategy;
    }
    
    public void addAssignmentWithoutWrapping(Unode lhs, PythonConstruct rhs) {
        if (lhs == null)
            throw new NullPointerException("lhs is null");
        if (rhs == null)
            throw new NullPointerException("rhs is null");
        memento.assignments.put(lhs, rhs);
        if (lhs instanceof AttributeUnode) {
            AttributeUnode au = (AttributeUnode) lhs;
            memento.attributeAssignments.put(au.getName(), new AssignmentInfo(au.getReceiver(), rhs));
        }
    }
    
    public void addAssignment(Unode lhs, PythonConstruct rhs) {
        if (lhs == null)
            throw new NullPointerException("lhs is null");
        if (rhs == null)
            throw new NullPointerException("rhs is null");
        lhs = wrappingStrategy.wrap(lhs);
        if (lhs == null)
            throw new NullPointerException("lhs is null");
        memento.assignments.put(lhs, rhs);
        if (lhs instanceof AttributeUnode) {
            AttributeUnode au = (AttributeUnode) lhs;
            memento.attributeAssignments.put(au.getName(), new AssignmentInfo(au.getReceiver(), rhs));
        }
    }
    
    public void addReturnedValue(MethodDeclarationC methodC, PythonConstruct value) {
        if (methodC == null)
            throw new NullPointerException("methodC is null");
        if (value == null)
            throw new NullPointerException("value is null");
        memento.returns.put(methodC, value);
    }
    
    public void leave() {
    }
    
    @pausable
    public ConstructVisitor<PythonConstruct, PythonStaticContext, PythonDynamicContext, ASTNode> enter(
            PythonConstruct construct) {
        if (construct == null)
            throw new NullPointerException("construct is null");
        if (construct instanceof IndexAffector) {
            IndexAffector affector = (IndexAffector) construct;
            try {
                affector.actOnIndex(this);
            } catch (Throwable e) {
                Bugs.bug(e);
            }
            IndexNameWrappingStrategy wrappingStrategy = affector.createWrappingStrategy();
            if (wrappingStrategy != null)
                return new IndexRequest(memento, wrappingStrategy);
        }
        return this;
    }
    
}
