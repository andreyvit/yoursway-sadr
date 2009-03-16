package com.yoursway.sadr.python.constructs;

import kilim.pausable;

import org.eclipse.dltk.python.parser.ast.expressions.PythonArrayAccessExpression;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class ArrayAccessC extends PythonConstructImpl<PythonArrayAccessExpression> {
    
    private final PythonConstruct array;
    private final PythonConstruct index;
    
    ArrayAccessC(PythonStaticContext sc, PythonArrayAccessExpression node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        array = wrap(node.getArray(), sc);
        index = wrap(node.getIndex(), sc);
    }
    
    public PythonConstruct getIndex() {
        return index;
    }
    
    public PythonConstruct array() {
        return array;
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
        return null;
    }
    
}
