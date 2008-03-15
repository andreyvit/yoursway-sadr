package com.yoursway.sadr.python.core.typeinferencing.constructs;

import static com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import java.util.Collection;

import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.python.parser.ast.expressions.PythonVariableAccessExpression;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.runtime.PythonField;
import com.yoursway.sadr.python.core.typeinferencing.goals.MergeFieldsValueInfosContinuation;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo;

public class FieldAccessC extends PythonConstructImpl<PythonVariableAccessExpression> {
    
    FieldAccessC(PythonStaticContext sc, PythonVariableAccessExpression node) {
        super(sc, node);
    }
    
    public void evaluateValue(final PythonDynamicContext dc, final InfoKind infoKind,
            ContinuationRequestor requestor, final ValueInfoContinuation continuation) {
        if (node.getReceiver() instanceof VariableReference)
            if (((VariableReference) node.getReceiver()).getName().equals("self")) {
                VariableReference fieldName = node.variable();
                String name = fieldName.getName();
                
                ValueInfo selfType = dc.selfType();
                if (selfType == null)
                    selfType = staticContext().selfType();
                Collection<PythonField> fields = selfType.lookupField(name);
                requestor.subgoal(new MergeFieldsValueInfosContinuation(fields, infoKind, continuation));
                return;
            }
        continuation.consume(emptyValueInfo(), requestor);
    }
}
