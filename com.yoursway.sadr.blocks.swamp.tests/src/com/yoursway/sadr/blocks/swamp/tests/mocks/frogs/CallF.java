package com.yoursway.sadr.blocks.swamp.tests.mocks.frogs;

import com.yoursway.sadr.blocks.swamp.formulas.AbstractFormula;
import com.yoursway.sadr.blocks.swamp.formulas.Formula;
import com.yoursway.sadr.blocks.swamp.tests.mocks.CallKind;

public class CallF extends AbstractFormula {
    
    private final Formula receiver;
    private final CallKind kind;
    private final String methodName;
    
    public CallF(Formula receiver, CallKind kind, String methodName) {
        if (receiver == null)
            throw new NullPointerException("receiver is null");
        if (kind == null)
            throw new NullPointerException("kind is null");
        if (methodName == null)
            throw new NullPointerException("methodName is null");
        this.receiver = receiver;
        this.kind = kind;
        this.methodName = methodName;
    }
    
    public Formula getReceiver() {
        return receiver;
    }
    
    public CallKind getKind() {
        return kind;
    }
    
    public String getMethodName() {
        return methodName;
    }
    
}
