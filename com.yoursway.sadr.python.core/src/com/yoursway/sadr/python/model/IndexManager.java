package com.yoursway.sadr.python.model;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import kilim.pausable;

import com.yoursway.sadr.engine.incremental.SourceUnit;
import com.yoursway.sadr.engine.incremental.index.IndexMemento;
import com.yoursway.sadr.engine.incremental.index.IndexQuery;
import com.yoursway.sadr.engine.util.AbstractMultiMap;
import com.yoursway.sadr.engine.util.ArrayListHashMultiMap;
import com.yoursway.sadr.python.constructs.MethodDeclarationC;
import com.yoursway.sadr.python.constructs.PythonConstruct;
import com.yoursway.sadr.python.constructs.PythonFileC;
import com.yoursway.sadr.python.constructs.PythonRootConstruct;
import com.yoursway.sadr.python.index.AssignmentsIndexQuery;
import com.yoursway.sadr.python.index.AssignmentsRequestor;
import com.yoursway.sadr.python.index.DtlIndexQuery;
import com.yoursway.sadr.python.index.DtlIndexQueryVisitor;
import com.yoursway.sadr.python.index.ReturnsIndexQuery;
import com.yoursway.sadr.python.index.ReturnsRequestor;
import com.yoursway.sadr.python.index.unodes.Unode;

public class IndexManager {
    
    private final Map<PythonFileC, PythonFileIndexManager> files = new HashMap<PythonFileC, PythonFileIndexManager>();
    
    private final Map<SourceUnit, PythonFileIndexManager> sourceUnitsTofiles = new HashMap<SourceUnit, PythonFileIndexManager>();
    
    private PythonFileIndexManager lookup(PythonFileC fileC) {
        if (fileC == null)
            throw new NullPointerException("fileC is null");
        PythonFileIndexManager result = files.get(fileC);
        if (result == null) {
            result = new PythonFileIndexManager(fileC);
            files.put(fileC, result);
            sourceUnitsTofiles.put(fileC.sourceUnit(), result);
        }
        return result;
    }
    
    @pausable
    public void addToIndex(PythonRootConstruct root) {
        lookup(root.fileC()).addToIndex(root);
    }
    
    class PythonFileIndexManager {
        
        private PythonFileIndex index;
        
        private final PythonFileC fileC;
        
        public PythonFileIndexManager(PythonFileC fileC) {
            this.fileC = fileC;
            this.index = new PythonFileIndex(fileC);
        }
        
        @pausable
        public void addToIndex(PythonConstruct root) {
            index.add(root);
        }
        
        public PythonFileIndex index() {
            return index;
        }
        
        public void removeAllContributions() {
            index = new PythonFileIndex(fileC);
        }
        
        public void makeImmutable() {
            index.makeImmutable();
        }
        
    }
    
    static class PythonFileIndexMemento implements IndexMemento {
        
        private final AbstractMultiMap<Unode, PythonConstruct> assignments = new ArrayListHashMultiMap<Unode, PythonConstruct>();
        
        private final AbstractMultiMap<MethodDeclarationC, PythonConstruct> returns = new ArrayListHashMultiMap<MethodDeclarationC, PythonConstruct>();
        
        private boolean immutable = false;
        
        public Collection<IndexQuery<?>> diff(IndexMemento previous) {
            PythonFileIndexMemento prev = (PythonFileIndexMemento) previous;
            Collection<IndexQuery<?>> result = newArrayList();
            diffAssignments(result, this.assignments, prev.assignments);
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
        
        public IndexRequest createIndexRequest() {
            if (immutable)
                throw new IllegalStateException("Index memento is immutable");
            return new IndexRequest(assignments, returns);
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
        
    }
    
    static class PythonFileIndex {
        
        private final PythonFileIndexMemento memento = new PythonFileIndexMemento();
        private final PythonFileC fileC;
        
        public PythonFileIndex(PythonFileC fileC) {
            this.fileC = fileC;
        }
        
        public void makeImmutable() {
            memento.makeImmutable();
        }
        
        public void clear() {
        }
        
        @pausable
        public void add(PythonConstruct root) {
            IndexRequest request = memento.createIndexRequest();
            root.staticContext().propagationTracker().traverseStatically(root, request);
        }
        
        public PythonFileIndexMemento getMemento() {
            return memento;
        }
        
        public void findAssignments(Unode name, AssignmentsRequestor requestor) {
            memento.findAssignments(name, requestor, fileC);
        }
        
        public void findReturns(MethodDeclarationC methodC, ReturnsRequestor requestor) {
            memento.findReturns(methodC, requestor);
        }
        
    }
    
    class GlobalIndex implements com.yoursway.sadr.engine.incremental.index.Index {
        
        @pausable
        public <R> void query(IndexQuery<R> query, R requestor) {
            ((DtlIndexQuery<R>) query).accept(new DtlIndexQueryVisitor() {
                
                public void acceptAssignmentsQuery(AssignmentsIndexQuery query, AssignmentsRequestor requestor) {
                    for (PythonFileIndexManager file : files.values())
                        file.index().findAssignments(query.getUnode(), requestor);
                }
                
                public void acceptReturnsQuery(ReturnsIndexQuery query, ReturnsRequestor requestor) {
                    PythonFileIndexManager fileIndex = sourceUnitsTofiles.get(query.localSourceUnit());
                    if (fileIndex != null)
                        fileIndex.index().findReturns(query.getMethodC(), requestor);
                }
                
            }, requestor);
        }
        
        public IndexMemento createMemento(SourceUnit sourceUnit) {
            PythonFileIndexManager file = sourceUnitsTofiles.get(sourceUnit);
            if (file == null)
                return new PythonFileIndexMemento();
            else
                return file.index.getMemento();
        }
        
    }
    
    public void pleaseAbstainFromContinuingToKeepContributionsOf(SourceUnit file) {
        PythonFileIndexManager fileObj = sourceUnitsTofiles.get(file);
        if (fileObj != null)
            fileObj.removeAllContributions();
    }
    
    public com.yoursway.sadr.engine.incremental.index.Index createGlobalIndex() {
        return new GlobalIndex();
    }
    
    public void updateFinished() {
        for (PythonFileIndexManager file : files.values())
            file.makeImmutable();
    }
    
}
