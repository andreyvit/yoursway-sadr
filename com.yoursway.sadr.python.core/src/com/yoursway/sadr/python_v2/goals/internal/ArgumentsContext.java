package com.yoursway.sadr.python_v2.goals.internal;

import java.util.Set;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;

public interface ArgumentsContext {
    //RuntimeObject getPosition(int index);
    //    
    //int positionalArgumnetsNumber();
    
    RuntimeObject getByKeyword(String keyword);
    
    Set<String> keywordArguments();
}
