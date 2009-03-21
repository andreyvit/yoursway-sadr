package com.yoursway.sadr.engine.incremental;

import com.yoursway.sadr.engine.AnalysisTask;
import com.yoursway.sadr.engine.incremental.index.DependencyContributor;

public interface IncrementalAnalysisTask extends AnalysisTask {
    
    void contributeDependecyContributor(DependencyContributor dependencyContributor);
    
    void contributeSourceUnitDependency(SourceUnit sourceUnit);
    
}
