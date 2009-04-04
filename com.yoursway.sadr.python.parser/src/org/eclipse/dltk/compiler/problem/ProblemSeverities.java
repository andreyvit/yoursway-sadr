package org.eclipse.dltk.compiler.problem;

public class ProblemSeverities {
    
    private ProblemSeverities() {
    }
    
    public static final int Ignore = -1; // during handling only
    public static final int Warning = 0; // during handling only
    
    public static final int Error = 1; // when bit is set: problem is error, if not it is a warning
    public static final int AbortCompilation = 2;
    public static final int AbortSourceModule = 4;
    public static final int AbortType = 8;
    public static final int AbortMethod = 16;
    public static final int Abort = 30; // 2r11110
    public static final int Optional = 32; // when bit is set: problem was configurable
    public static final int SecondaryError = 64;
    public static final int Fatal = 128; // when bit is set: problem was either a mandatory error, or an optional+treatOptionalErrorAsFatal 
    
}
