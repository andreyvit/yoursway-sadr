package com.yoursway.sadr.blocks.swamp.formulas;

import com.yoursway.utils.DebugOutputHelper;

public class AbstractFormula implements Formula {

    @Override
    public String toString() {
        return DebugOutputHelper.reflectionBasedToString(this);
    }
    
}
