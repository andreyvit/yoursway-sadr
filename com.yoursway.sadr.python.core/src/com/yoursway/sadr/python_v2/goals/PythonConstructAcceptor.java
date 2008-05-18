package com.yoursway.sadr.python_v2.goals;

import java.util.ArrayList;
import java.util.List;

import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.succeeder.IAcceptor;
import com.yoursway.sadr.succeeder.IGrade;

public class PythonConstructAcceptor implements IAcceptor {
    
    List<PythonConstruct> constructs = new ArrayList<PythonConstruct>();
    
    public <T> void checkpoint(IGrade<T> grade) {
    }
    
    void addResult(PythonConstruct construct) {
        constructs.add(construct);
    }
    
    public List<PythonConstruct> getResults() {
        return constructs;
    }
    
    public void addResults(List<PythonConstruct> results) {
        constructs.addAll(results);
    }
    
}
