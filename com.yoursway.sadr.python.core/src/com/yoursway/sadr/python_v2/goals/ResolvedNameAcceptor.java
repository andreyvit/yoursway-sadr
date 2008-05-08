package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.succeeder.IAcceptor;

public interface ResolvedNameAcceptor extends IAcceptor {
    void addResult(PythonConstruct result);
}
