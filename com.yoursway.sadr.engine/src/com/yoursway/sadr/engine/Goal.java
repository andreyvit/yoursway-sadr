package com.yoursway.sadr.engine;

public interface Goal extends ContextSensitiveThing {
    
    void evaluate(ContinuationRequestor requestor);
    
    void done();
    
    void causesRecursion();
    
    void copyAnswerFrom(Result result);
    
    void copyAnswerFrom(Goal goal);
    
    boolean cachable();
    
    int debugSlot();
    
    void addPropagatingGoal(Goal propagatingGoal);
    
    ContextRelation contextRelation();
    
    boolean isContextFree();
    
    Result weakResult(); //? contextIgnoringResult
    
    Goal cloneGoal();
    
    boolean hasComplexUnnaturalRelationshipWithRecursion();
    
}
