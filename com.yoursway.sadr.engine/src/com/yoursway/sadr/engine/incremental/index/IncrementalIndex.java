package com.yoursway.sadr.engine.incremental.index;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.GoalResultCacheCleaner;
import com.yoursway.sadr.engine.incremental.IncrementalAnalysisEngine;
import com.yoursway.sadr.engine.incremental.SourceUnit;
import com.yoursway.sadr.engine.incremental.IncrementalAnalysisEngine.IncrementalGoalState;

public class IncrementalIndex implements Index {
    
    static class QueryData {
        
        private final IndexQuery<?> query;
        
        private final Set<Goal<?>> dependentGoals = newHashSet();
        
        public QueryData(IndexQuery<?> query) {
            if (query == null)
                throw new NullPointerException("query is null");
            this.query = query;
        }
        
        @Override
        public String toString() {
            return query.toString();
        }
        
        public void addDependent(Goal<?> goal) {
            dependentGoals.add(goal);
        }
        
        public void uncacheDependentGoals(GoalResultCacheCleaner cleaner) {
            cleaner.removeCachedResultsOf(dependentGoals);
        }
        
    }
    
    static class QueryDependencyContributor implements DependencyContributor {
        
        private final QueryData queryData;
        
        public QueryDependencyContributor(QueryData queryData) {
            if (queryData == null)
                throw new NullPointerException("queryData is null");
            this.queryData = queryData;
        }
        
        public void contributeDependenciesTo(Goal<?> goal) {
            queryData.addDependent(goal);
        }
        
    }
    
    private final Map<IndexQuery<?>, QueryData> queryDataMap = newHashMap();
    
    private final Map<SourceUnit, IndexMemento> transientMementos = newHashMap();
    
    private final Index underlyingIndex;
    
    QueryData lookupQueryData(IndexQuery<?> query) {
        if (query == null)
            throw new NullPointerException("query is null");
        QueryData result = queryDataMap.get(query);
        if (result == null) {
            result = new QueryData(query);
            queryDataMap.put(query, result);
        }
        return result;
    }
    
    public IncrementalIndex(Index underlyingIndex) {
        if (underlyingIndex == null)
            throw new NullPointerException("underlyingIndex is null");
        this.underlyingIndex = underlyingIndex;
    }
    
    public <R> void query(IndexQuery<R> query, ContinuationScheduler cs, R requestor) {
        IncrementalAnalysisEngine.IncrementalGoalState goalState = (IncrementalGoalState) cs.getGoalState();
        goalState.contributeDependecyContributor(new QueryDependencyContributor(lookupQueryData(query)));
        underlyingIndex.query(query, cs, requestor);
    }
    
    public void prepareToUpdate(SourceUnit sourceUnit) {
        if (sourceUnit == null)
            throw new NullPointerException("sourceUnit is null");
        IndexMemento memento = underlyingIndex.createMemento(sourceUnit);
        if (memento == null)
            throw new NullPointerException("memento is null");
        transientMementos.put(sourceUnit, memento);
    }
    
    public void finishUpdate(GoalResultCacheCleaner cleaner) {
        for (SourceUnit sourceUnit : newArrayList(transientMementos.keySet())) {
            IndexMemento oldMemento = transientMementos.remove(sourceUnit);
            IndexMemento newMemento = underlyingIndex.createMemento(sourceUnit);
            Collection<IndexQuery<?>> diff = newMemento.diff(oldMemento);
            for (IndexQuery<?> query : diff) {
                QueryData data = queryDataMap.remove(query);
                if (data != null) {
                    data.uncacheDependentGoals(cleaner);
                }
            }
        }
    }
    
    public IndexMemento createMemento(SourceUnit sourceUnit) {
        return underlyingIndex.createMemento(sourceUnit);
    }
    
}
