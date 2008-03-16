package com.yoursway.sadr.python.core.typeinferencing.goals;

import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.keys.wildcards.ArrayWildcard;
import com.yoursway.sadr.python.core.typeinferencing.keys.wildcards.FieldWildcard;
import com.yoursway.sadr.python.core.typeinferencing.keys.wildcards.Wildcard;

/**
 * @see AssignmentInfo
 */
public class MumblaWumblaThreesome {
    
    private final Wildcard wildcard;
    private final PythonConstruct receiver;
    private final String variableName;
    
    public MumblaWumblaThreesome(PythonConstruct receiver, String variableName, Wildcard wildcard) {
        this.receiver = receiver;
        this.variableName = variableName;
        this.wildcard = wildcard;
    }
    
    public PythonConstruct receiver() {
        return receiver;
    }
    
    public Wildcard wildcard() {
        return wildcard;
    }
    
    public String variableName() {
        return variableName;
    }
    
    public MumblaWumblaThreesome wrapIntoArray() {
        return new MumblaWumblaThreesome(receiver, variableName, new ArrayWildcard(wildcard));
    }
    
    public MumblaWumblaThreesome wrapIntoField(String name) {
        return new MumblaWumblaThreesome(receiver, variableName, new FieldWildcard(name, wildcard));
    }
    
}
