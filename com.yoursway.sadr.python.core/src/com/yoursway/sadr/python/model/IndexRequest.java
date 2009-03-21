package com.yoursway.sadr.python.model;

import kilim.pausable;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.constructs.ConstructVisitor;
import com.yoursway.sadr.core.constructs.Request;
import com.yoursway.sadr.engine.util.AbstractMultiMap;
import com.yoursway.sadr.python.constructs.MethodDeclarationC;
import com.yoursway.sadr.python.constructs.PythonConstruct;
import com.yoursway.sadr.python.constructs.PythonStaticContext;
import com.yoursway.sadr.python.index.unodes.Unode;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.utils.bugs.Bugs;

public class IndexRequest implements
        Request<PythonConstruct, PythonStaticContext, PythonDynamicContext, ASTNode> {
    
    private final AbstractMultiMap<Unode, PythonConstruct> assignments;
    private final AbstractMultiMap<MethodDeclarationC, PythonConstruct> returns;
    private final IndexNameWrappingStrategy wrappingStrategy;
    
    public IndexRequest(AbstractMultiMap<Unode, PythonConstruct> assignments,
            AbstractMultiMap<MethodDeclarationC, PythonConstruct> returns,
            IndexNameWrappingStrategy wrappingStrategy) {
        if (assignments == null)
            throw new NullPointerException("assignments is null");
        if (returns == null)
            throw new NullPointerException("returns is null");
        this.assignments = assignments;
        this.returns = returns;
        this.wrappingStrategy = wrappingStrategy;
    }
    
    public void addAssignment(Unode lhs, PythonConstruct rhs) {
        if (lhs == null)
            throw new NullPointerException("lhs is null");
        if (rhs == null)
            throw new NullPointerException("rhs is null");
        lhs = wrappingStrategy.wrap(lhs);
        if (lhs == null)
            throw new NullPointerException("lhs is null");
        assignments.put(lhs, rhs);
    }
    
    public void addReturnedValue(MethodDeclarationC methodC, PythonConstruct value) {
        if (methodC == null)
            throw new NullPointerException("methodC is null");
        if (value == null)
            throw new NullPointerException("value is null");
        returns.put(methodC, value);
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
                return new IndexRequest(assignments, returns, wrappingStrategy);
        }
        return this;
    }
    
}
