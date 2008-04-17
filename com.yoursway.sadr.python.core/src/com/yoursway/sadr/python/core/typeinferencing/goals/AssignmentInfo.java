package com.yoursway.sadr.python.core.typeinferencing.goals;

import com.yoursway.sadr.blocks.foundation.wildcards.Wildcard;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;

/**
 * Example. The following Python call:
 * 
 * <pre>
 * foo[2].bar[4].boz[7] = 42
 * </pre>
 * 
 * will be translated into three <code>AssignmentInfo</code>s:
 * 
 * <ol>
 * <li><code>receiver</code> is <code>null</code>, <code>name</code> is
 * foo, <code>wildcard</code> is
 * <code>Array(Field("bar", Array(Field("boz", Array(*)))))<code>,
 * <code>rhs</code> is <code>42</code>
 * <li><code>receiver</code> is <code>foo[2]</code>, <code>name</code> is
 * bar, <code>wildcard</code> is
 * <code>Array(Field("boz", Array(*)))<code>,
 * <code>rhs</code> is <code>42</code>
 * <li><code>receiver</code> is <code>foo[2].bar[4]</code>, <code>name</code> is
 * boz, <code>wildcard</code> is
 * <code>Array(*)<code>,
 * <code>rhs</code> is <code>42</code>
 * </ol>
 *
 */
public class AssignmentInfo {
    
    private final PythonConstruct rhs;
    private final MumblaWumblaThreesome threesome;
    
    public AssignmentInfo(MumblaWumblaThreesome threesome, PythonConstruct rhs) {
        this.threesome = threesome;
        this.rhs = rhs;
    }
    
    public MumblaWumblaThreesome threesome() {
        return threesome;
    }
    
    public PythonConstruct rhs() {
        return rhs;
    }
    
    public String variableName() {
        return threesome.variableName();
    }
    
    public Wildcard wildcard() {
        return threesome.wildcard();
    }
    
    public PythonConstruct receiver() {
        return threesome.receiver();
    }
    
}
