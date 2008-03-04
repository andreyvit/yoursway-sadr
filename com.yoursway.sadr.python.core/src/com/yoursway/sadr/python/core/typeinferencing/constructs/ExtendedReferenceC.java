package com.yoursway.sadr.python.core.typeinferencing.constructs;

import static com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo.emptyValueInfo;

import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.python.parser.ast.expressions.ExtendedVariableReference;

import com.yoursway.sadr.core.ValueInfoContinuation;
import com.yoursway.sadr.engine.ContinuationRequestor;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.core.runtime.PythonField;
import com.yoursway.sadr.python.core.runtime.PythonUtils;
import com.yoursway.sadr.python.core.runtime.PythonVariable;
import com.yoursway.sadr.python.core.typeinferencing.goals.FieldValueInfoGoal;

public class ExtendedReferenceC extends PythonConstructImpl<ExtendedVariableReference> {
    
    ExtendedReferenceC(PythonStaticContext sc, ExtendedVariableReference node) {
        super(sc, node);
    }
    
    public void evaluateValue(final PythonDynamicContext dc, final InfoKind infoKind,
            ContinuationRequestor requestor, final ValueInfoContinuation continuation) {
        ExtendedVariableReference evr = node;
        List<ASTNode> cc = PythonUtils.expressionsOf(evr);
        if (cc.size() == 2 && cc.get(0) instanceof VariableReference
                && ((VariableReference) cc.get(0)).getName().equals("self")
                && cc.get(1) instanceof VariableReference) {
            VariableReference fieldName = (VariableReference) cc.get(1);
            String name = fieldName.getName();
            PythonVariable var = dc.variableLookup().findVariable(name);
            if (var == null)
                var = staticContext().variableLookup().findVariable(name);
            if (var instanceof PythonField) {
                PythonField field = (PythonField) var;
                requestor.subgoal(new SingleSubgoalContinuation(new FieldValueInfoGoal(field, infoKind),
                        continuation));
                return;
            }
        }
        continuation.consume(emptyValueInfo(), requestor);
        
        //        
        //        final Statement name = node.getName();
        //        requestor.subgoal(new Continuation() {
        //            
        //            private final ValueInfoGoal nameGoal = new ExpressionValueInfoGoal((Scope) dc, name, infoKind);
        //            
        //            public void provideSubgoals(SubgoalRequestor requestor) {
        //                requestor.subgoal(nameGoal);
        //            }
        //            
        //            public void done(ContinuationRequestor requestor) {
        //                continuation.consume(nameGoal.result(null).unwrapArray(), requestor);
        //            }
        //            
        //        });
    }
    
}
