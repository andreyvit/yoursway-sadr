package com.yoursway.sadr.python.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import kilim.pausable;

import org.eclipse.dltk.core.ISourceModule;

import com.yoursway.sadr.engine.incremental.SourceUnit;
import com.yoursway.sadr.engine.incremental.index.IndexMemento;
import com.yoursway.sadr.engine.incremental.index.IndexQuery;
import com.yoursway.sadr.engine.util.AbstractMultiMap;
import com.yoursway.sadr.engine.util.ArrayListHashMultiMap;
import com.yoursway.sadr.python.constructs.CallC;
import com.yoursway.sadr.python.constructs.PythonConstruct;
import com.yoursway.sadr.python.constructs.PythonFileC;
import com.yoursway.sadr.python.constructs.PythonRootConstruct;
import com.yoursway.sadr.python.core.runtime.FileSourceUnit;
import com.yoursway.sadr.python.core.runtime.contributions.ContributableItem;
import com.yoursway.sadr.python.core.runtime.contributions.NodeBoundItem;

public class FileContributionsManager {
    
    private final Map<PythonFileC, DtlFile> files = new HashMap<PythonFileC, DtlFile>();
    
    private DtlFile lookup(PythonFileC fileC) {
        if (fileC == null)
            throw new NullPointerException("fileC is null");
        DtlFile result = files.get(fileC);
        if (result == null) {
            result = new DtlFile(fileC);
            files.put(fileC, result);
        }
        return result;
    }
    
    @pausable
    public void addToIndex(PythonRootConstruct root) {
        lookup(root.fileC()).addToIndex(root);
    }
    
    class DtlFile {
        
        private final Collection<ContributableItem> items = new ArrayList<ContributableItem>();
        
        private Index index;
        
        private final PythonFileC fileC;
        
        public DtlFile(PythonFileC fileC) {
            this.fileC = fileC;
            this.index = new Index(fileC);
        }
        
        @pausable
        public void addToIndex(PythonConstruct root) {
            index.add(root);
        }
        
        public Index index() {
            return index;
        }
        
        public ISourceModule file() {
            return file;
        }
        
        public FileScope scope() {
            return scope;
        }
        
        public void add(ContributableItem item) {
            items.add(item);
            if (item instanceof NodeBoundItem)
                bind((NodeBoundItem) item);
        }
        
        private void bind(NodeBoundItem item) {
            nodesToItems.put(item.node(), item);
        }
        
        public NodeBoundItem lookup(AstNode node) {
            return nodesToItems.get(node);
        }
        
        public void removeAllContributions() {
            index = new Index(scope);
            for (ContributableItem item : items)
                item.dispose();
            items.clear();
        }
        
        public void makeImmutable() {
            index.makeImmutable();
        }
        
    }
    
    static class DtlIndexMemento implements IndexMemento {
        
        private final AbstractMultiMap<String, CallC> procedureCalls = new ArrayListHashMultiMap<String, CallC>();
        
        private final AbstractMultiMap<String, CallC> methodsCalls = new ArrayListHashMultiMap<String, CallC>();
        
        private final AbstractMultiMap<String, AssignmentInfo> assignments = new ArrayListHashMultiMap<String, AssignmentInfo>();
        
        private boolean immutable = false;
        
        public Collection<IndexQuery<?>> diff(IndexMemento previous) {
            DtlIndexMemento prev = (DtlIndexMemento) previous;
            Collection<IndexQuery<?>> result = newArrayList();
            diffProcedureCalls(result, this.procedureCalls, prev.procedureCalls);
            diffMethodCalls(result, this.methodsCalls, prev.methodsCalls);
            diffAssignments(result, this.assignments, prev.assignments);
            return result;
        }
        
        private void diffProcedureCalls(Collection<IndexQuery<?>> result,
                AbstractMultiMap<String, CallC> curData, AbstractMultiMap<String, CallC> prevData) {
            Set<String> curKeys = newHashSet(curData.keySet());
            Set<String> prevKeys = newHashSet(prevData.keySet());
            Set<String> removedKeys = newHashSet(curKeys);
            removedKeys.removeAll(prevKeys);
            for (String name : removedKeys)
                result.add(new ProcedureCallsIndexQuery(name));
            for (Map.Entry<String, Collection<CallC>> entry : curData.entrySet()) {
                String name = entry.getKey();
                Set<CallC> curValues = newHashSet(entry.getValue());
                Set<CallC> oldValues = newHashSet(prevData.get(name));
                if (!curValues.equals(oldValues))
                    result.add(new ProcedureCallsIndexQuery(name));
            }
        }
        
        private void diffMethodCalls(Collection<IndexQuery<?>> result,
                AbstractMultiMap<String, CallC> curData, AbstractMultiMap<String, CallC> prevData) {
            Set<String> curKeys = newHashSet(curData.keySet());
            Set<String> prevKeys = newHashSet(prevData.keySet());
            Set<String> removedKeys = newHashSet(curKeys);
            removedKeys.removeAll(prevKeys);
            for (String name : removedKeys)
                result.add(new MethodCallsIndexQuery(name));
            for (Map.Entry<String, Collection<CallC>> entry : curData.entrySet()) {
                String name = entry.getKey();
                Set<CallC> curValues = newHashSet(entry.getValue());
                Set<CallC> oldValues = newHashSet(prevData.get(name));
                if (!curValues.equals(oldValues))
                    result.add(new MethodCallsIndexQuery(name));
            }
        }
        
        private void diffAssignments(Collection<IndexQuery<?>> result,
                AbstractMultiMap<String, AssignmentInfo> curData,
                AbstractMultiMap<String, AssignmentInfo> prevData) {
            Set<String> curKeys = newHashSet(curData.keySet());
            Set<String> prevKeys = newHashSet(prevData.keySet());
            Set<String> removedKeys = newHashSet(curKeys);
            removedKeys.removeAll(prevKeys);
            for (String name : removedKeys)
                result.add(new AssignmentsIndexQuery(name));
            for (Map.Entry<String, Collection<AssignmentInfo>> entry : curData.entrySet()) {
                String name = entry.getKey();
                Set<AssignmentInfo> curValues = newHashSet(entry.getValue());
                Set<AssignmentInfo> oldValues = newHashSet(prevData.get(name));
                if (!curValues.equals(oldValues))
                    result.add(new AssignmentsIndexQuery(name));
            }
        }
        
        public IndexRequest createIndexRequest() {
            if (immutable)
                throw new IllegalStateException("Index memento is immutable");
            return new IndexRequest(methodsCalls, procedureCalls, assignments);
        }
        
        public void makeImmutable() {
            immutable = true;
        }
        
        public void findAssignments(String name, AssignmentsRequestor requestor, FileScope scope) {
            for (AssignmentInfo info : assignments.get(name.toLowerCase()))
                requestor.assignment(info, scope);
        }
        
        public void findMethodCalls(String name, CallsRequestor requestor) {
            for (CallC call : methodsCalls.get(name.toLowerCase()))
                requestor.call(call);
        }
        
        public void findProcedureCalls(String name, CallsRequestor requestor) {
            for (CallC call : procedureCalls.get(name.toLowerCase()))
                requestor.call(call);
        }
        
    }
    
    static class Index {
        
        private final DtlIndexMemento memento = new DtlIndexMemento();
        private final PythonFileC fileC;
        
        public Index(PythonFileC fileC) {
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
        
        public DtlIndexMemento getMemento() {
            return memento;
        }
        
        public void findAssignments(String name, AssignmentsRequestor requestor) {
            memento.findAssignments(name, requestor, scope);
        }
        
        public void findMethodCalls(String name, CallsRequestor requestor) {
            memento.findMethodCalls(name, requestor);
        }
        
        public void findProcedureCalls(String name, CallsRequestor requestor) {
            memento.findProcedureCalls(name, requestor);
        }
        
    }
    
    class GlobalIndex implements com.yoursway.sadr.engine.incremental.index.Index {
        
        @pausable
        public <R> void query(IndexQuery<R> query, R requestor) {
            ((DtlIndexQuery<R>) query).accept(new DtlIndexQueryVisitor() {
                
                public void acceptAssignmentsQuery(AssignmentsIndexQuery query, AssignmentsRequestor requestor) {
                    for (DtlFile file : files.values())
                        file.index().findAssignments(query.name(), requestor);
                }
                
                public void acceptMethodCallsQuery(MethodCallsIndexQuery query, CallsRequestor requestor) {
                    for (DtlFile file : files.values())
                        file.index().findMethodCalls(query.name(), requestor);
                }
                
                public void acceptProcedureCallsQuery(ProcedureCallsIndexQuery query, CallsRequestor requestor) {
                    for (DtlFile file : files.values())
                        file.index().findProcedureCalls(query.name(), requestor);
                }
                
            }, requestor);
        }
        
        public IndexMemento createMemento(SourceUnit sourceUnit) {
            DtlFile file = filesBySourceUnit.get(((FileSourceUnit) sourceUnit).getFile());
            if (file == null)
                return new DtlIndexMemento();
            else
                return file.index.getMemento();
        }
        
    }
    
    public void pleaseObstainFromContinuingToKeepContributionsOf(IFile file) {
        DtlFile fileObj = filesBySourceUnit.get(file);
        if (fileObj != null)
            fileObj.removeAllContributions();
    }
    
    public com.yoursway.sadr.engine.incremental.index.Index createGlobalIndex() {
        return new GlobalIndex();
    }
    
    public void updateFinished() {
        for (DtlFile file : files.values())
            file.makeImmutable();
    }
    
}
