package com.yoursway.sadr.python_v2.model;

import java.util.Set;

public interface Arguments {
    RuntimeObject getPosition(int index);
    
    RuntimeObject getByKeyword(String keyword);
    
    int positionalArgumnetsNumber();
    
    Set<String> keywordArguments();
}
