/**
 * 
 */
package com.yoursway.sadr.python.model;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.yoursway.sadr.engine.incremental.index.IndexMemento;
import com.yoursway.sadr.engine.incremental.index.IndexQuery;
import com.yoursway.sadr.engine.util.AbstractMultiMap;
import com.yoursway.sadr.engine.util.ArrayListHashMultiMap;
import com.yoursway.sadr.python.constructs.MethodDeclarationC;
import com.yoursway.sadr.python.constructs.PythonConstruct;
import com.yoursway.sadr.python.constructs.PythonFileC;
import com.yoursway.sadr.python.index.AssignmentsIndexQuery;
import com.yoursway.sadr.python.index.AssignmentsRequestor;
import com.yoursway.sadr.python.index.AttributeAssignmentsIndexQuery;
import com.yoursway.sadr.python.index.AttributeAssignmentsRequestor;
import com.yoursway.sadr.python.index.ReturnsRequestor;
import com.yoursway.sadr.python.index.unodes.Unode;

class PythonFileIndexMemento implements IndexMemento {
    
    final AbstractMultiMap<Unode, PythonConstruct> assignments = new ArrayListHashMultiMap<Unode, PythonConstruct>();
    
    final AbstractMultiMap<MethodDeclarationC, PythonConstruct> returns = new ArrayListHashMultiMap<MethodDeclarationC, PythonConstruct>();
    
    final AbstractMultiMap<String, AssignmentInfo> attributeAssignments = new ArrayListHashMultiMap<String, AssignmentInfo>();
    
    private boolean immutable = false;
    
    public Collection<IndexQuery<?>> diff(IndexMemento previous) {
        PythonFileIndexMemento prev = (PythonFileIndexMemento) previous;
        Collection<IndexQuery<?>> result = newArrayList();
        diffAssignments(result, this.assignments, prev.assignments);
        diffAttributeAssignments(result, this.attributeAssignments, prev.attributeAssignments);
        return result;
    }
    
    private void diffAssignments(Collection<IndexQuery<?>> result,
            AbstractMultiMap<Unode, PythonConstruct> curData,
            AbstractMultiMap<Unode, PythonConstruct> prevData) {
        Set<Unode> curKeys = newHashSet(curData.keySet());
        Set<Unode> prevKeys = newHashSet(prevData.keySet());
        Set<Unode> removedKeys = newHashSet(curKeys);
        removedKeys.removeAll(prevKeys);
        for (Unode name : removedKeys)
            result.add(new AssignmentsIndexQuery(name));
        for (Map.Entry<Unode, Collection<PythonConstruct>> entry : curData.entrySet()) {
            Unode name = entry.getKey();
            Set<PythonConstruct> curValues = newHashSet(entry.getValue());
            Set<PythonConstruct> oldValues = newHashSet(prevData.get(name));
            if (!curValues.equals(oldValues))
                result.add(new AssignmentsIndexQuery(name));
        }
    }
    
    private void diffAttributeAssignments(Collection<IndexQuery<?>> result,
            AbstractMultiMap<String, AssignmentInfo> curData,
            AbstractMultiMap<String, AssignmentInfo> prevData) {
        Set<String> curKeys = newHashSet(curData.keySet());
        Set<String> prevKeys = newHashSet(prevData.keySet());
        Set<String> removedKeys = newHashSet(curKeys);
        removedKeys.removeAll(prevKeys);
        for (String name : removedKeys)
            result.add(new AttributeAssignmentsIndexQuery(name));
        for (Map.Entry<String, Collection<AssignmentInfo>> entry : curData.entrySet()) {
            String name = entry.getKey();
            Set<AssignmentInfo> curValues = newHashSet(entry.getValue());
            Set<AssignmentInfo> oldValues = newHashSet(prevData.get(name));
            if (!curValues.equals(oldValues))
                result.add(new AttributeAssignmentsIndexQuery(name));
        }
    }
    
    public IndexRequest createIndexRequest() {
        if (immutable)
            throw new IllegalStateException("Index memento is immutable");
        return new IndexRequest(this, IndexNameWrappingStrategy.NULL);
    }
    
    public void makeImmutable() {
        immutable = true;
    }
    
    public void findAssignments(Unode name, AssignmentsRequestor requestor, PythonFileC scope) {
        for (PythonConstruct info : assignments.get(name))
            requestor.assignment(info, scope);
    }
    
    public void findReturns(MethodDeclarationC methodC, ReturnsRequestor requestor) {
        for (PythonConstruct returnedValue : returns.get(methodC))
            requestor.returnedValue(returnedValue);
    }
    
    public void findAttributeAssignments(String attributeName, AttributeAssignmentsRequestor requestor,
            PythonFileC fileC) {
        for (AssignmentInfo info : attributeAssignments.get(attributeName))
            requestor.assignment(info, fileC);
    }
    
}