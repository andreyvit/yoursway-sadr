package com.yoursway.sadr.python_v2.constructs;

import static com.google.common.collect.Lists.newArrayList;

import org.eclipse.dltk.python.parser.ast.expressions.PythonArrayAccessExpression;

import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.CallResolver;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.RuntimeArguments;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;

public class ArrayAccessC extends PythonConstructImpl<PythonArrayAccessExpression> {
    
    private final PythonConstruct array;
    private final PythonConstruct index;
    private final int ARRAY = 0, INDEX = 1;
    
    ArrayAccessC(Scope sc, PythonArrayAccessExpression node) {
        super(sc, node);
        array = getPostChildren().get(ARRAY);
        index = getPostChildren().get(INDEX).getPostChildren().get(0);
    }
    
    public PythonConstruct array() {
        return array;
    }
    
    @Override
    public PythonValueSet evaluate(final Krocodile context) {
        PythonValueSet results = new PythonValueSet();
        
        PythonValueSet values = array.evaluate(context);
        PythonValueSet indexes = index.evaluate(context);
        
        for (PythonObject arrayObject : values) {
            if (arrayObject != null) {
                for (PythonObject arrayIndex : indexes) {
                    RuntimeArguments args = new RuntimeArguments(newArrayList(arrayIndex));
                    PythonValueSet r = CallResolver.callMethod(arrayObject, "__getitem__", args, context,
                            this);
                    results.addResults(r);
                }
            }
        }
        return results;
    }
}
