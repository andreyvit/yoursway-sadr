package com.yoursway.sadr.ruby.core.staticchecks;

public interface IRubyProblemReporter {
    
    public static final String MARKER_TYPE = "com.esko.dtl.core.staticcheckproblem";
    
    void warning(String message, int startOffset, int endOffset);
    
    void error(String message, int startOffset, int endOffset);
    
    void info(String message, int startOffset, int endOffset);
    
    void reset();
    
}
