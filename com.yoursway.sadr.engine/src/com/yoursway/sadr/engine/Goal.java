package com.yoursway.sadr.engine;

public interface Goal extends Sinner {
    
    void evaluate(ContinuationRequestor requestor);
    
    void done();
    
    void causesRecursion();
    
    void copyAnswerFrom(Result result);
    
    void copyAnswerFrom(Goal goal);
    
    boolean cachable();
    
    int debugSlot();
    
    void blame(Goal sin);
    
    Karma karma();
    
    boolean isSaint();
    
    Result resultWithoutKarma();
    
    Goal cloneGoal();
    
    boolean hasComplexUnnaturalRelationshipWithRecursion();
    
}
