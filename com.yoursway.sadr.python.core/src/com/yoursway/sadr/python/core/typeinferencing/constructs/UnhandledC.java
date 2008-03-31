package com.yoursway.sadr.python.core.typeinferencing.constructs;

import static com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.InfoKind;

public class UnhandledC extends PythonConstructImpl<ASTNode> {
    
    UnhandledC(PythonStaticContext sc, ASTNode node) {
        super(sc, node);
    }
    
    public ContinuationRequestorCalledToken evaluateValue(PythonDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
        return continuation.consume(emptyValueInfo(), requestor);
    }
    
}
