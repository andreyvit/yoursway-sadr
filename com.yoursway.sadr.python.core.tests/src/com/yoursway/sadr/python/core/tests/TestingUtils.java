package com.yoursway.sadr.python.core.tests;

public class TestingUtils {

    public static String callerOutside(Class<?> klass) {
        boolean thisClassMet = false;
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            boolean inThisClass = klass.getName().equals(element.getClassName());
            if (!thisClassMet) {
                if (inThisClass)
                    thisClassMet = true;
            } else {
                if (!inThisClass)
                    return element.getMethodName();
            }
        }
        throw new AssertionError("Unreachable");
    }
    
}
