package com.yoursway.sadr.python.analysis.index;

import java.util.HashMap;
import java.util.Map;

import kilim.pausable;

import com.yoursway.sadr.engine.incremental.SourceUnit;
import com.yoursway.sadr.engine.incremental.index.IndexMemento;
import com.yoursway.sadr.engine.incremental.index.IndexQuery;
import com.yoursway.sadr.python.analysis.context.lexical.areas.MethodArea;
import com.yoursway.sadr.python.analysis.index.queries.AssignmentsIndexQuery;
import com.yoursway.sadr.python.analysis.index.queries.AssignmentsRequestor;
import com.yoursway.sadr.python.analysis.index.queries.AttributeAssignmentsIndexQuery;
import com.yoursway.sadr.python.analysis.index.queries.AttributeAssignmentsRequestor;
import com.yoursway.sadr.python.analysis.index.queries.DtlIndexQuery;
import com.yoursway.sadr.python.analysis.index.queries.DtlIndexQueryVisitor;
import com.yoursway.sadr.python.analysis.index.queries.PassedArgumentsIndexQuery;
import com.yoursway.sadr.python.analysis.index.queries.PassedArgumentsRequestor;
import com.yoursway.sadr.python.analysis.index.queries.ReturnsIndexQuery;
import com.yoursway.sadr.python.analysis.index.queries.ReturnsRequestor;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;

public class IndexManager {
    
    private final Map<SourceUnit, PythonFileIndexManager> files = new HashMap<SourceUnit, PythonFileIndexManager>();
    
    private PythonFileIndexManager lookup(SourceUnit sourceUnit) {
        if (sourceUnit == null)
            throw new NullPointerException("sourceUnit is null");
        PythonFileIndexManager result = files.get(sourceUnit);
        if (result == null) {
            result = new PythonFileIndexManager();
            files.put(sourceUnit, result);
        }
        return result;
    }
    
    public IndexRequest createIndexRequest(SourceUnit sourceUnit) {
        return lookup(sourceUnit).createIndexRequest();
    }
    
    class PythonFileIndexManager {
        
        private PythonFileIndex index;
        
        public PythonFileIndexManager() {
            this.index = new PythonFileIndex();
        }
        
        public IndexRequest createIndexRequest() {
            return index.createIndexRequest();
        }
        
        public PythonFileIndex index() {
            return index;
        }
        
        public void removeAllContributions() {
            index = new PythonFileIndex();
        }
        
        public void makeImmutable() {
            index.makeImmutable();
        }
        
    }
    
    static class PythonFileIndex {
        
        private final PythonFileIndexMemento memento = new PythonFileIndexMemento();
        
        public void makeImmutable() {
            memento.makeImmutable();
        }
        
        public void clear() {
        }
        
        public IndexRequest createIndexRequest() {
            return memento.createIndexRequest();
        }
        
        public PythonFileIndexMemento getMemento() {
            return memento;
        }
        
        public void findAssignments(Unode name, AssignmentsRequestor requestor) {
            memento.findAssignments(name, requestor);
        }
        
        public void findReturns(MethodArea area, ReturnsRequestor requestor) {
            memento.findReturns(area, requestor);
        }
        
        public void findAttributeAssignments(String attributeName, AttributeAssignmentsRequestor requestor) {
            memento.findAttributeAssignments(attributeName, requestor);
        }
        
        public void findPassedArguments(Unode arg, PassedArgumentsRequestor requestor) {
            memento.findPassedArguments(arg, requestor);
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
                    PythonFileIndexManager fileIndex = files.get(query.localSourceUnit());
                    if (fileIndex != null)
                        fileIndex.index().findReturns(query.getArea(), requestor);
                }
                
                public void acceptAttributeAssignmentsQuery(AttributeAssignmentsIndexQuery query,
                        AttributeAssignmentsRequestor requestor) {
                    for (PythonFileIndexManager file : files.values())
                        file.index().findAttributeAssignments(query.getAttributeName(), requestor);
                }
                
                public void acceptPassedArgumentsQuery(PassedArgumentsIndexQuery query,
                        PassedArgumentsRequestor requestor) {
                    for (PythonFileIndexManager file : files.values())
                        file.index().findPassedArguments(query.getUnode(), requestor);
                }
                
            }, requestor);
        }
        
        public IndexMemento createMemento(SourceUnit sourceUnit) {
            PythonFileIndexManager file = files.get(sourceUnit);
            if (file == null)
                return new PythonFileIndexMemento();
            else
                return file.index.getMemento();
        }
        
    }
    
    public void pleaseAbstainFromContinuingToKeepContributionsOf(SourceUnit file) {
        PythonFileIndexManager fileObj = files.get(file);
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
