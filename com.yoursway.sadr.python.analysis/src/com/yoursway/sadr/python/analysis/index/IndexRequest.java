package com.yoursway.sadr.python.analysis.index;

import java.util.ArrayList;
import java.util.Collection;

import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.context.lexical.areas.MethodArea;
import com.yoursway.sadr.python.analysis.context.lexical.scopes.PythonScope;
import com.yoursway.sadr.python.analysis.index.data.AssignmentInfo;
import com.yoursway.sadr.python.analysis.index.data.PassedArgumentInfo;
import com.yoursway.sadr.python.analysis.index.wrapping.IndexAttributeWrappingStrategy;
import com.yoursway.sadr.python.analysis.index.wrapping.IndexNameWrappingStrategy;
import com.yoursway.sadr.python.analysis.lang.unodes.Bnode;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.AttributeUnode;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.VariableUnode;

public class IndexRequest {
    
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
    
    public void addAssignmentWithoutWrapping(Unode lhs, PythonLexicalContext lhsLc, Bnode rhs) {
        addAssignment(lhs, lhsLc.getScope(), rhs);
    }
    
    public void addAssignmentWithoutWrapping(Unode lhs, PythonScope lhsScope, Bnode rhs) {
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
    
    public void addAssignment(Unode lhs, PythonLexicalContext lc, Bnode rhs) {
        addAssignment(lhs, lc.getScope(), rhs);
    }
    
    public void addAssignment(Unode lhs, PythonScope lhsScope, Bnode rhs) {
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
    
    public void addReturnedValue(MethodArea area, Bnode value) {
        if (area == null)
            throw new NullPointerException("methodC is null");
        if (value == null)
            throw new NullPointerException("value is null");
        memento.returns.put(area, value);
    }
    
    public void addPassedArgument(Unode arg, PassedArgumentInfo info) {
        if (arg == null)
            throw new NullPointerException("arg is null");
        if (info == null)
            throw new NullPointerException("callC is null");
        memento.passedArguments.put(arg, info);
    }
    
    public IndexRequest with(IndexAttributeWrappingStrategy ws) {
        return new IndexRequest(memento, ws);
    }
    
}
