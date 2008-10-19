package com.yoursway.sadr.engine.incremental;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.yoursway.sadr.engine.AnalysisEngine;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.GoalResultCacheCleaner;
import com.yoursway.sadr.engine.Result;
import com.yoursway.sadr.engine.incremental.index.DependencyContributor;

/**
 * TODO Validate cached results instead of throwing them out.
 * 
 * Currently this class simply removes any cached results that were dependent on
 * the changed files. While this is a valid (and correct) implementation, it can
 * be greatly improved by keeping intergoal dependencies and validating the
 * cached results on demand instead of throwing them out.
 */
public class IncrementalAnalysisEngine extends AnalysisEngine {
    
    static class SourceUnitData {
        
        private final SourceUnit sourceUnit;
        
        private final Collection<Goal<?>> dependentGoals = newHashSet();
        
        public SourceUnitData(SourceUnit sourceUnit) {
            if (sourceUnit == null)
                throw new NullPointerException("sourceUnit is null");
            this.sourceUnit = sourceUnit;
        }
        
        public void addDependentGoal(Goal<?> goal) {
            if (goal == null)
                throw new NullPointerException("goal is null");
            dependentGoals.add(goal);
        }
        
        @Override
        public String toString() {
            return sourceUnit.toString();
        }
        
        public void removeAllDependentResultsFromCache(GoalResultCacheCleaner cleaner) {
            cleaner.removeCachedResultsOf(dependentGoals);
        }
        
    }
    
    Map<SourceUnit, SourceUnitData> sourceUnitDataMap = newHashMap();
    
    SourceUnitData lookupSourceUnitData(SourceUnit sourceUnit) {
        if (sourceUnit == null)
            throw new NullPointerException("sourceUnit is null");
        SourceUnitData result = sourceUnitDataMap.get(sourceUnit);
        if (result == null) {
            result = new SourceUnitData(sourceUnit);
            sourceUnitDataMap.put(sourceUnit, result);
        }
        return result;
    }
    
    public class IncrementalGoalState extends GoalState {
        
        private final Set<SourceUnit> sourceUnitDependencies = newHashSet();
        
        private final Set<DependencyContributor> dependencyContributors = newHashSet();
        
        public <R extends Result> IncrementalGoalState(Goal<R> goal) {
            super(goal);
            if (goal instanceof IncrementalGoal) {
                IncrementalGoal<?> ig = (IncrementalGoal<?>) goal;
                SourceUnit unit = ig.getInherentSourceUnitDependency();
                if (unit != null)
                    sourceUnitDependencies.add(unit);
            }
        }
        
        @Override
        protected void subgoalFinished(GoalState state) {
            super.subgoalFinished(state);
            IncrementalGoalState igs = (IncrementalGoalState) state;
            sourceUnitDependencies.addAll(igs.sourceUnitDependencies);
            dependencyContributors.addAll(igs.dependencyContributors);
            for (DependencyContributor contributor : dependencyContributors)
                contributor.contributeDependenciesTo(goal);
        }
        
        @Override
        protected void goalFinished() {
            super.goalFinished();
            for (SourceUnit sourceUnit : sourceUnitDependencies)
                lookupSourceUnitData(sourceUnit).addDependentGoal(goal);
        }
        
        public void contributeDependecyContributor(DependencyContributor contributor) {
            contributor.contributeDependenciesTo(goal);
            dependencyContributors.add(contributor);
        }
        
    }
    
    @Override
    protected <R extends Result> GoalState createGoalState(Goal<R> goal) {
        return new IncrementalGoalState(goal);
    }
    
    public void sourceUnitChanged(SourceUnit sourceUnit) {
        if (sourceUnit == null)
            throw new NullPointerException("sourceUnit is null");
        SourceUnitData data = sourceUnitDataMap.remove(sourceUnit);
        if (data != null)
            data.removeAllDependentResultsFromCache(this);
    }
    
}
