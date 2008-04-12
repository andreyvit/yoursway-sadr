package com.yoursway.sadr.python.core.typeinferencing.constructs;

import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoGoal;

public interface AnalysisProvider {
    BinaryOperationHandler getBinaryPercentHandler(ValueInfoGoal leftGoal, ValueInfoGoal rightGoal);
}
