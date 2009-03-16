package com.yoursway.sadr.python_v2.goals.internal;

import java.util.Set;

import com.yoursway.sadr.python_v2.model.builtins.PythonObject;

public interface ArgumentsContext {
    //PythonObject getPosition(int index);
    //    
    //int positionalArgumnetsNumber();
    
    PythonObject getByKeyword(String keyword);
    
    Set<String> keywordArguments();
}
