package com.yoursway.sadr.python.core.typeinferencing.constructs;

import static com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo.emptyValueInfo;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.python.parser.ast.PythonCallArgument;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.Context;
import com.yoursway.sadr.succeeder.IGoal;

public class CallArgumentC extends PythonConstructImpl<PythonCallArgument> {
    
    private final PythonConstruct value;
    
    CallArgumentC(Scope scope, PythonCallArgument node) {
        super(scope, node);
        Assert.isLegal(node.getValue() != null, "node.getValue() should be != null");
        value = PythonConstructFactory.wrap(node.getValue(), scope);
    }
    
    public ContinuationRequestorCalledToken evaluateValue(PythonDynamicContext dc, InfoKind infoKind,
            ContinuationScheduler requestor, ValueInfoContinuation continuation) {
        return continuation.consume(emptyValueInfo(), requestor);
    }
    
    int getStar() {
        return node.getStar();
    }
    
    public PythonConstruct getValue() {
        return value;
    }
    
    @Override
    public IGoal evaluate(Context context, PythonValueSetAcceptor acceptor) {
        return value.evaluate(context, acceptor);
    }
}
