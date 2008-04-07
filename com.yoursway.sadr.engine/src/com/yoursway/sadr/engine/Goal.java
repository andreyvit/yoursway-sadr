package com.yoursway.sadr.engine;

public interface Goal extends ContextSensitiveThing {
    
    ContinuationRequestorCalledToken evaluate(ContinuationScheduler requestor);
    
    void done();
    
    void causesRecursion();
    
    void copyAnswerFrom(Result result);
    
    void copyAnswerFrom(Goal goal);
    
    boolean cachable();
    
    int debugSlot();
    
    void addPropagatingGoal(Goal propagatingGoal);
    
    ContextRelation contextRelation();
    
    boolean isContextFree();
    
    Result roughResult();
    
    Goal cloneGoal();
    
    boolean hasComplexUnnaturalRelationshipWithRecursion();
    
}
