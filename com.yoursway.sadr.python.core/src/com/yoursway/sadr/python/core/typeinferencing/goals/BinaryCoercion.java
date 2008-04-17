package com.yoursway.sadr.python.core.typeinferencing.goals;

import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.blocks.foundation.values.Value;
import com.yoursway.sadr.blocks.foundation.values.ValueTraits;
import com.yoursway.sadr.python.core.typeinferencing.services.ClassLookup;

public class BinaryCoercion {
    
    private final ClassLookup classLookup;
    
    public BinaryCoercion(ClassLookup classLookup) {
        this.classLookup = classLookup;
    }
    
    public void coerce(ValueInfo a, ValueInfo b, BinaryCoercionRequestor requestor) {
        // type analysis
        TypeAnalyzer leftTA = new TypeAnalyzer(a.getTypeSet(), classLookup);
        TypeAnalyzer rightTA = new TypeAnalyzer(b.getTypeSet(), classLookup);
        
        if (leftTA.isInt() && rightTA.isInt())
            requestor.intType();
        else if (leftTA.cohersibleToString() && rightTA.cohersibleToString())
            requestor.stringType();
        
        // value analysis
        for (Value left : a.containedValues()) {
            ValueTraits lt = left.traits();
            for (Value right : b.containedValues()) {
                ValueTraits rt = right.traits();
                if (lt.isInteger() && rt.isInteger())
                    requestor.ints(lt.integerValue(), rt.integerValue());
                else if (lt.cohersibleToString() && rt.cohersibleToString())
                    requestor.strings(lt.coherseToString(), rt.coherseToString());
                else
                    requestor.unknowns();
            }
        }
        
    }
    
}
