package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import kilim.pausable;

import org.eclipse.dltk.python.parser.ast.expressions.PythonArrayAccessExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonSubscriptExpression;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.analysis.lang.unodes.LiteralIntegerIndexUnode;
import com.yoursway.sadr.python.analysis.lang.unodes.UnknownIndexUnode;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class ArrayAccessC extends PythonConstructImpl<PythonArrayAccessExpression> {
    
    private final PythonConstruct array;
    private final PythonConstruct index;
    private final PythonConstruct sliceEnd;
    private final PythonConstruct sliceStep;
    
    ArrayAccessC(PythonStaticContext sc, PythonArrayAccessExpression node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        array = wrap(node.getArray(), sc);
        PythonSubscriptExpression subscript = (PythonSubscriptExpression) node.getIndex();
        index = wrapOrNull(subscript.getIndexOrSliceStart(), sc);
        sliceEnd = wrapOrNull(subscript.getSliceEnd(), sc);
        sliceStep = wrapOrNull(subscript.getSliceStep(), sc);
    }
    
    public boolean isSubscription() {
        return index != null && sliceEnd == null && sliceStep == null;
    }
    
    public PythonConstruct getIndex() {
        return index;
    }
    
    public PythonConstruct array() {
        return array;
    }
    
    @Override
    public Unode toUnode() {
        if (!isSubscription())
            return null;
        Unode receiverUnode = array.toUnode();
        if (receiverUnode == null)
            return null;
        if (index instanceof IntegerLiteralC)
            return new LiteralIntegerIndexUnode(receiverUnode, (int) ((IntegerLiteralC) index).getValue());
        else
            return new UnknownIndexUnode(receiverUnode);
    }
    
    //    @Override
    //    public PythonValueSet evaluate(final PythonDynamicContext context) {
    //        PythonValueSet results = new PythonValueSet();
    //        
    //        PythonValueSet values = array.evaluate(context);
    //        PythonValueSet indexes = index.evaluate(context);
    //        
    //        for (PythonValue arrayObject : values) {
    //            if (arrayObject != null) {
    //                for (PythonValue arrayIndex : indexes) {
    //                    RuntimeArguments args = new RuntimeArguments(newArrayList(arrayIndex));
    //                    PythonValueSet r = CallResolver.callMethod(arrayObject, "__getitem__", args, context,
    //                            this);
    //                    results.addResults(r);
    //                }
    //            }
    //        }
    //        return results;
    //    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
        Unode unode = toUnode();
        if (unode == null)
            return PythonValueSet.EMPTY;
        return unode.calculateValue(staticContext(), dc);
    }
    
}