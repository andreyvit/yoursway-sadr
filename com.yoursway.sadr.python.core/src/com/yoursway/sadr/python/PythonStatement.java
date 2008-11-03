package com.yoursway.sadr.python;

import java.util.List;

import com.yoursway.sadr.python_v2.croco.Frog;

public interface PythonStatement {
    List<PythonElement> getElements();
    
    boolean match(Frog frog); //null if match failed
    
    List<PythonElement> matches(Frog frog);
}
