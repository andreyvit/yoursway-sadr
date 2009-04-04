package com.yoursway.sadr.python.analysis.index;

import java.util.ArrayList;
import java.util.Collection;

import kilim.pausable;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.constructs.ConstructVisitor;
import com.yoursway.sadr.core.constructs.Request;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonScope;
import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.index.data.AssignmentInfo;
import com.yoursway.sadr.python.analysis.index.data.PassedArgumentInfo;
import com.yoursway.sadr.python.analysis.index.wrapping.IndexNameWrappingStrategy;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.constructs.ast.MethodDeclarationC;
import com.yoursway.sadr.python.analysis.lang.unodes.AttributeUnode;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.lang.unodes.VariableUnode;
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
    
    public void addAssignmentWithoutWrapping(Unode lhs, PythonScope lhsScope, PythonConstruct rhs) {
        if (lhs == null)
            throw new NullPointerException("lhs is null");
        if (rhs == null)
            throw new NullPointerException("rhs is null");
        Collection<Unode> alternatives = new ArrayList<Unode>();
        lhs.addGenericVariationsTo(alternatives, false);
        for (Unode unode : alternatives) {
            if (!unode.isIndexable())
                continue;
            memento.assignments.put(unode, rhs);
            if (unode instanceof AttributeUnode) {
                AttributeUnode au = (AttributeUnode) unode;
                memento.attributeAssignments.put(au.getName(), new AssignmentInfo(au.getReceiver(), rhs));
            }
            addLocalVarToScope(lhsScope, unode);
        }
    }
    
    private void addLocalVarToScope(PythonScope lhsScope, Unode lhs) {
        VariableUnode variable = lhs.leadingVariableUnode();
        if (variable.equals(lhs))
            if (!lhsScope.isGlobalVariable(variable.getName()))
                lhsScope.addLocalVariable(variable.getName());
    }
    
    public void addAssignment(Unode lhs, PythonScope lhsScope, PythonConstruct rhs) {
        if (lhs == null)
            throw new NullPointerException("lhs is null");
        if (rhs == null)
            throw new NullPointerException("rhs is null");
        Collection<Unode> alternatives = new ArrayList<Unode>();
        lhs.addGenericVariationsTo(alternatives, false);
        for (Unode unode : alternatives) {
            if (!unode.isIndexable())
                continue;
            unode = wrappingStrategy.wrap(unode);
            if (unode == null)
                throw new NullPointerException("lhs is null");
            memento.assignments.put(unode, rhs);
            if (unode instanceof AttributeUnode) {
                AttributeUnode au = (AttributeUnode) unode;
                memento.attributeAssignments.put(au.getName(), new AssignmentInfo(au.getReceiver(), rhs));
            }
            addLocalVarToScope(lhsScope, unode);
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
    
    public void addPassedArgument(Unode arg, PassedArgumentInfo info) {
        if (arg == null)
            throw new NullPointerException("arg is null");
        if (info == null)
            throw new NullPointerException("callC is null");
        memento.passedArguments.put(arg, info);
    }
    
}
