/**
 * 
 */
package com.yoursway.sadr.python.analysis.index;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.yoursway.sadr.engine.incremental.index.IndexMemento;
import com.yoursway.sadr.engine.incremental.index.IndexQuery;
import com.yoursway.sadr.engine.util.AbstractMultiMap;
import com.yoursway.sadr.engine.util.ArrayListHashMultiMap;
import com.yoursway.sadr.python.analysis.context.lexical.areas.MethodArea;
import com.yoursway.sadr.python.analysis.index.data.AssignmentInfo;
import com.yoursway.sadr.python.analysis.index.data.PassedArgumentInfo;
import com.yoursway.sadr.python.analysis.index.queries.AssignmentsIndexQuery;
import com.yoursway.sadr.python.analysis.index.queries.AssignmentsRequestor;
import com.yoursway.sadr.python.analysis.index.queries.AttributeAssignmentsIndexQuery;
import com.yoursway.sadr.python.analysis.index.queries.AttributeAssignmentsRequestor;
import com.yoursway.sadr.python.analysis.index.queries.PassedArgumentsIndexQuery;
import com.yoursway.sadr.python.analysis.index.queries.PassedArgumentsRequestor;
import com.yoursway.sadr.python.analysis.index.queries.ReturnsRequestor;
import com.yoursway.sadr.python.analysis.index.wrapping.IndexNameWrappingStrategy;
import com.yoursway.sadr.python.analysis.lang.unodes.Bnode;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;

class PythonFileIndexMemento implements IndexMemento {
    
    final AbstractMultiMap<Unode, Bnode> assignments = new ArrayListHashMultiMap<Unode, Bnode>();
    
    final AbstractMultiMap<Unode, PassedArgumentInfo> passedArguments = new ArrayListHashMultiMap<Unode, PassedArgumentInfo>();
    
    final AbstractMultiMap<MethodArea, Bnode> returns = new ArrayListHashMultiMap<MethodArea, Bnode>();
    
    final AbstractMultiMap<String, AssignmentInfo> attributeAssignments = new ArrayListHashMultiMap<String, AssignmentInfo>();
    
    private boolean immutable = false;
    
    public Collection<IndexQuery<?>> diff(IndexMemento previous) {
        PythonFileIndexMemento prev = (PythonFileIndexMemento) previous;
        Collection<IndexQuery<?>> result = newArrayList();
        diffAssignments(result, this.assignments, prev.assignments);
        diffPassedArguments(result, this.passedArguments, prev.passedArguments);
        diffAttributeAssignments(result, this.attributeAssignments, prev.attributeAssignments);
        return result;
    }
    
    private void diffAssignments(Collection<IndexQuery<?>> result, AbstractMultiMap<Unode, Bnode> curData,
            AbstractMultiMap<Unode, Bnode> prevData) {
        Set<Unode> curKeys = newHashSet(curData.keySet());
        Set<Unode> prevKeys = newHashSet(prevData.keySet());
        Set<Unode> removedKeys = newHashSet(curKeys);
        removedKeys.removeAll(prevKeys);
        for (Unode name : removedKeys)
            result.add(new AssignmentsIndexQuery(name));
        for (Map.Entry<Unode, Collection<Bnode>> entry : curData.entrySet()) {
            Unode name = entry.getKey();
            Set<Bnode> curValues = newHashSet(entry.getValue());
            Set<Bnode> oldValues = newHashSet(prevData.get(name));
            if (!curValues.equals(oldValues))
                result.add(new AssignmentsIndexQuery(name));
        }
    }
    
    private void diffPassedArguments(Collection<IndexQuery<?>> result,
            AbstractMultiMap<Unode, PassedArgumentInfo> curData,
            AbstractMultiMap<Unode, PassedArgumentInfo> prevData) {
        Set<Unode> curKeys = newHashSet(curData.keySet());
        Set<Unode> prevKeys = newHashSet(prevData.keySet());
        Set<Unode> removedKeys = newHashSet(curKeys);
        removedKeys.removeAll(prevKeys);
        for (Unode name : removedKeys)
            result.add(new PassedArgumentsIndexQuery(name));
        for (Map.Entry<Unode, Collection<PassedArgumentInfo>> entry : curData.entrySet()) {
            Unode name = entry.getKey();
            Set<PassedArgumentInfo> curValues = newHashSet(entry.getValue());
            Set<PassedArgumentInfo> oldValues = newHashSet(prevData.get(name));
            if (!curValues.equals(oldValues))
                result.add(new PassedArgumentsIndexQuery(name));
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
    
    public void findAssignments(Unode name, AssignmentsRequestor requestor) {
        for (Bnode info : assignments.get(name))
            requestor.assignment(info);
    }
    
    public void findReturns(MethodArea area, ReturnsRequestor requestor) {
        for (Bnode returnedValue : returns.get(area))
            requestor.returnedValue(returnedValue);
    }
    
    public void findAttributeAssignments(String attributeName, AttributeAssignmentsRequestor requestor) {
        for (AssignmentInfo info : attributeAssignments.get(attributeName))
            requestor.assignment(info);
    }
    
    public void findPassedArguments(Unode arg, PassedArgumentsRequestor requestor) {
        for (PassedArgumentInfo info : passedArguments.get(arg))
            requestor.call(info);
    }
    
}