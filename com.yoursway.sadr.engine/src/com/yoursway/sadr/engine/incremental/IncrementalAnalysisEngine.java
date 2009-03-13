package com.yoursway.sadr.engine.incremental;

import static com.google.common.base.Join.join;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.yoursway.sadr.engine.AnalysisEngine;
import com.yoursway.sadr.engine.Goal;
import com.yoursway.sadr.engine.GoalResultCacheCleaner;
import com.yoursway.sadr.engine.Result;
import com.yoursway.sadr.engine.incremental.index.DependencyContributor;
import com.yoursway.sadr.engine.internal.CachedGoalHasNullResult;
import com.yoursway.utils.YsDebugging;
import com.yoursway.utils.broadcaster.Broadcaster;
import com.yoursway.utils.broadcaster.BroadcasterFactory;
import com.yoursway.utils.bugs.Bugs;

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
        
        private static final DependencyContributor[] NO_DEPENDENCY_CONTRIBUTORS = new DependencyContributor[0];
        
        private static final SourceUnit[] NO_SOURCE_UNITS = new SourceUnit[0];
        
        public static final int NON_RECURSIVE = -1;
        
        final Goal<?> goal;
        Result result;
        SourceUnit[] sourceUnits = NO_SOURCE_UNITS;
        DependencyContributor[] dependencyContributors = NO_DEPENDENCY_CONTRIBUTORS;
        int recursiveAttemps;
        final Collection<Goal<?>> recursiveDependencies = newArrayList();
        
        public CachedGoalData(Goal<?> goal) {
            if (goal == null)
                throw new NullPointerException("goal is null");
            this.goal = goal;
        }
        
        public void update(Result result, SourceUnit[] sourceUnits,
                DependencyContributor[] dependencyContributors, int recursiveAttemps) {
            if (result == null)
                throw new NullPointerException("result is null");
            if (sourceUnits == null)
                throw new NullPointerException("sourceUnits is null");
            if (dependencyContributors == null)
                throw new NullPointerException("dependencyContributors is null");
            this.result = result;
            this.sourceUnits = sourceUnits;
            this.dependencyContributors = dependencyContributors;
            this.recursiveAttemps = recursiveAttemps;
        }
        
        public void addRecursiveDependency(Goal<?> goal) {
            if (goal == null)
                throw new NullPointerException("goal is null");
            recursiveDependencies.add(goal);
        }
        
        public boolean isRecursiveResult() {
            return recursiveAttemps != NON_RECURSIVE;
        }
        
        public boolean resultChanged(Result result) {
            return this.result == null || !this.result.equals(result);
        }
        
        public Collection<Goal<?>> recursiveDependencies() {
            return newArrayList(recursiveDependencies);
        }
        
        public int incrementRecursiveAttempts() {
            if (recursiveAttemps == NON_RECURSIVE)
                throw new IllegalStateException(
                        "Attemp to increment recursive attemps for a non-recursively-dependent goal " + goal);
            return ++recursiveAttemps;
        }
    }
    
    private static final int MAX_RECURSIVE_ATTEMPS = 5;
    
    final Map<SourceUnit, SourceUnitData> sourceUnitDataMap = newHashMap();
    
    final Map<Goal<?>, CachedGoalData> cache = newHashMap();
    
    final Broadcaster<EngineListener> broadcaster = BroadcasterFactory.newBroadcaster(EngineListener.class);
    
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
    
    public class IncrementalGoalState extends GoalState implements IncrementalAnalysisTask {
        
        private final Set<SourceUnit> sourceUnitDependencies = newHashSet();
        
        private final Set<DependencyContributor> dependencyContributors = newHashSet();
        
        private boolean dependentOnRecursiveResult = false;
        
        private boolean needsToBeCalculatedAgainUponFinishingBecauseRecursiveDepsHaveChanged;
        
        public <R extends Result> IncrementalGoalState(Goal<R> goal) {
            super(goal);
            if (goal instanceof IncrementalGoal) {
                IncrementalGoal<?> ig = (IncrementalGoal<?>) goal;
                SourceUnit unit = ig.getInherentSourceUnitDependency();
                if (unit != null)
                    sourceUnitDependencies.add(unit);
            }
            broadcaster.fire().goalScheduled(goal);
        }
        
        @Override
        protected void subgoalFinished(GoalState state) {
            super.subgoalFinished(state);
            IncrementalGoalState igs = (IncrementalGoalState) state;
            sourceUnitDependencies.addAll(igs.sourceUnitDependencies);
            dependencyContributors.addAll(igs.dependencyContributors);
        }
        
        @Override
        public void addParent(GoalState parentState) {
            super.addParent(parentState);
            broadcaster.fire().goalParentAdded(goal, ((IncrementalGoalState) parentState).goal);
        }
        
        @Override
        public void run() {
            //            broadcaster.fire().goalExecutionStarting(goal);
            try {
                super.run();
            } finally {
                //
            }
        }
        
        @Override
        protected void goalFinished() {
            super.goalFinished();
            broadcaster.fire().goalFinished(goal, dependentOnRecursiveResult, slot.result());
            for (SourceUnit sourceUnit : sourceUnitDependencies)
                lookupSourceUnitData(sourceUnit).addDependentGoal(goal);
            for (DependencyContributor contributor : dependencyContributors)
                contributor.contributeDependenciesTo(goal);
            CachedGoalData data = storeIntoCache();
            if (dependentOnRecursiveResult && data != null)
                for (GoalState parent : parentStates) {
                    ((IncrementalGoalState) parent).setDependentOnRecursiveResult(data);
                }
        }
        
        CachedGoalData storeIntoCache() {
            if (!goal.cachable() || pruned)
                return null;
            SourceUnit[] sourceDeps = sourceUnitDependencies.toArray(new SourceUnit[sourceUnitDependencies
                    .size()]);
            DependencyContributor[] deps = dependencyContributors
                    .toArray(new DependencyContributor[dependencyContributors.size()]);
            CachedGoalData data = lookupCachedData(goal);
            Result result = slot.result();
            Collection<Goal<?>> goalsToRecalculate = emptyList();
            if (data.resultChanged(result)) {
                goalsToRecalculate = data.recursiveDependencies();
                //                if (!goalsToRecalculate.isEmpty())
                //                    System.out.println("IncrementalGoalState.storeIntoCache()");
            }
            int recursive;
            recursive = dependentOnRecursiveResult
                    || needsToBeCalculatedAgainUponFinishingBecauseRecursiveDepsHaveChanged ? 1
                    : CachedGoalData.NON_RECURSIVE;
            if (!data.isRecursiveResult())
                throw new IllegalStateException(
                        "Improving a non-recursive result is forbidden (should never happen)");
            if (data.recursiveAttemps != 0)
                recursive = data.recursiveAttemps;
            else
                recursive = dependentOnRecursiveResult
                        || needsToBeCalculatedAgainUponFinishingBecauseRecursiveDepsHaveChanged ? 1
                        : CachedGoalData.NON_RECURSIVE;
            data.update(result, sourceDeps, deps, recursive);
            if (needsToBeCalculatedAgainUponFinishingBecauseRecursiveDepsHaveChanged)
                // TODO: ideally we want to delay parent notification in this case 
                enqueueRecursiveRecalculation(goal, data);
            else {
                // TODO: ideally we want to delay parent notification in this case 
                for (Goal<?> goalToRecalculate : goalsToRecalculate) {
                    enqueueRecursiveRecalculation(goalToRecalculate, lookupCachedData(goalToRecalculate));
                }
            }
            return data;
        }
        
        public void contributeDependecyContributor(DependencyContributor contributor) {
            dependencyContributors.add(contributor);
        }
        
        public void setDependentOnRecursiveResult(CachedGoalData data) {
            if (data == null)
                throw new NullPointerException("data is null");
            data.addRecursiveDependency(goal);
            broadcaster.fire().goalRecursiveDependencyAdded(goal, data.goal);
            dependentOnRecursiveResult = true;
        }
        
        public void setNeedsToBeCalculatedAgainUponFinishingBecauseRecursiveDepsHaveChanged() {
            needsToBeCalculatedAgainUponFinishingBecauseRecursiveDepsHaveChanged = true;
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
            if (data.isRecursiveResult())
                ((IncrementalGoalState) parentState).setDependentOnRecursiveResult(data);
            IncrementalGoalState igs = (IncrementalGoalState) parentState;
            igs.sourceUnitDependencies.addAll(asList(data.sourceUnits));
            igs.dependencyContributors.addAll(asList(data.dependencyContributors));
        }
        return data.result;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected <R extends Result> R createRecursiveResult(GoalState parentState, Goal<R> goal) {
        CachedGoalData data = cache.get(goal);
        if (data == null) {
            data = new CachedGoalData(goal);
            data.update(super.createRecursiveResult(parentState, goal), CachedGoalData.NO_SOURCE_UNITS,
                    CachedGoalData.NO_DEPENDENCY_CONTRIBUTORS, 1);
            cache.put(goal, data);
        } else {
            if (data.result == null) {
                Bugs.bug(new CachedGoalHasNullResult(goal).add("parent_goal",
                        YsDebugging.simpleNameOf(parentState.goal)).add("recursive_attemps",
                        data.recursiveAttemps).add("recursive_dependencies",
                        join(",", data.recursiveDependencies)));
                // this is a stupid recovery, but anyway better than just failing
                data.result = goal.createRecursiveResult();
            }
        }
        ((IncrementalGoalState) parentState).setDependentOnRecursiveResult(data);
        return (R) data.result;
    }
    
    CachedGoalData lookupCachedData(Goal<?> goal) {
        CachedGoalData data = cache.get(goal);
        if (data == null) {
            data = new CachedGoalData(goal);
            cache.put(goal, data);
        }
        return data;
    }
    
    void enqueueRecursiveRecalculation(Goal<?> goal, CachedGoalData data) {
        if (goal == null)
            throw new NullPointerException("goal is null");
        if (data == null)
            throw new NullPointerException("data is null");
        if (TRACING_GOALS)
            trace("IncrementalAnalysisEngine.enqueueRecursiveRecalculation(" + goal + ")");
        
        if (data.incrementRecursiveAttempts() > MAX_RECURSIVE_ATTEMPS)
            return;
        GoalState state = activeGoalStates.get(goal);
        if (state == null)
            state = createAndPutGoalState(goal);
        else {
            ((IncrementalGoalState) state)
                    .setNeedsToBeCalculatedAgainUponFinishingBecauseRecursiveDepsHaveChanged();
        }
    }
    
    @Override
    public <R extends Result> R evaluate(Goal<R> goal) {
        R result = super.evaluate(goal);
        broadcaster.fire().rootGoalFinished(goal);
        return result;
    }
    
    public void addListener(EngineListener listener) {
        if (listener == null)
            throw new NullPointerException("listener is null");
        broadcaster.addListener(listener);
    }
    
    @Override
    protected void prune(GoalState current) {
        ((IncrementalGoalState) current).setPruned();
    }
    
}
