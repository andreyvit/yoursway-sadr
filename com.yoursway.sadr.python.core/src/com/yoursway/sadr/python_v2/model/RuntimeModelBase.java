package com.yoursway.sadr.python_v2.model;

import java.util.Collection;

public interface RuntimeModelBase {
    Collection<String> getModules();
    
    LexicalScope getModule(String name);
}
