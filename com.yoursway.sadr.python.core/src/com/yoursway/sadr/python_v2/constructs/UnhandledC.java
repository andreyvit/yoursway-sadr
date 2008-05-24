package com.yoursway.sadr.python_v2.constructs;

import static com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo.emptyValueInfo;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class UnhandledC extends PythonConstructImpl<ASTNode> {
    
    UnhandledC(Scope sc, ASTNode node) {
        super(sc, node);
    }
    
    public ContinuationRequestorCalledToken evaluateValue(PythonDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
        return continuation.consume(emptyValueInfo(), requestor);
    }
    
}
