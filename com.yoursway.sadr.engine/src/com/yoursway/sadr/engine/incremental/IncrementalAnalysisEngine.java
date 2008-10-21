package com.yoursway.sadr.engine.incremental;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.asList;

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
    
    static class CachedGoalData {
        
        final Goal<?> goal;
        final Result result;
        final SourceUnit[] sourceUnits;
        final DependencyContributor[] dependencyContributors;
        
        public CachedGoalData(Goal<?> goal, Result result, SourceUnit[] sourceUnits,
                DependencyContributor[] dependencyContributors) {
            if (goal == null)
                throw new NullPointerException("goal is null");
            if (result == null)
                throw new NullPointerException("result is null");
            if (sourceUnits == null)
                throw new NullPointerException("sourceUnits is null");
            if (dependencyContributors == null)
                throw new NullPointerException("dependencyContributors is null");
            this.goal = goal;
            this.result = result;
            this.sourceUnits = sourceUnits;
            this.dependencyContributors = dependencyContributors;
        }
        
    }
    
    final Map<SourceUnit, SourceUnitData> sourceUnitDataMap = newHashMap();
    
    final Map<Goal<?>, CachedGoalData> cache = newHashMap();
    
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
        }
        
        @Override
        protected void goalFinished() {
            super.goalFinished();
            for (SourceUnit sourceUnit : sourceUnitDependencies)
                lookupSourceUnitData(sourceUnit).addDependentGoal(goal);
            for (DependencyContributor contributor : dependencyContributors)
                contributor.contributeDependenciesTo(goal);
            storeIntoCache();
        }
        
        void storeIntoCache() {
            if (!goal.cachable())
                return;
            cache.put(goal, new CachedGoalData(goal, slot.result(), sourceUnitDependencies
                    .toArray(new SourceUnit[sourceUnitDependencies.size()]), dependencyContributors
                    .toArray(new DependencyContributor[dependencyContributors.size()])));
        }
        
        public void contributeDependecyContributor(DependencyContributor contributor) {
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
    
    public void removeCachedResultsOf(Collection<Goal<?>> goals) {
        for (Goal<?> goal : goals) {
            if (goal == null)
                throw new NullPointerException("goal is null");
            cache.remove(goal);
        }
    }
    
    public void clearCache() {
        cache.clear();
    }
    
    @Override
    public boolean isCached(Goal<?> goal) {
        return cache.containsKey(goal);
    }
    
    @Override
    protected Result lookupResultInCache(Goal<?> goal, GoalState parentState) {
        CachedGoalData data = cache.get(goal);
        if (data == null)
            return null;
        if (parentState != null) {
            IncrementalGoalState igs = (IncrementalGoalState) parentState;
            igs.sourceUnitDependencies.addAll(asList(data.sourceUnits));
            igs.dependencyContributors.addAll(asList(data.dependencyContributors));
        }
        return data.result;
    }
    
}
