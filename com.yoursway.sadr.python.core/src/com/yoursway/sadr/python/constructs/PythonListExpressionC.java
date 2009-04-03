package com.yoursway.sadr.python.constructs;

import java.util.List;

import kilim.pausable;

import org.eclipse.dltk.python.parser.ast.expressions.PythonListExpression;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.index.unodes.ListLiteralUnode;
import com.yoursway.sadr.python.index.unodes.Unode;
import com.yoursway.sadr.python.model.values.ListValue;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class PythonListExpressionC extends PythonConstructImpl<PythonListExpression> implements
        PythonConstruct {
    
    private final List<PythonConstruct> items;
    
    //    private final List<Unode> itemUnodes;
    
    public PythonListExpressionC(PythonStaticContext sc, PythonListExpression node,
            PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        items = wrap(node.getChilds(), sc);
        //        this.itemUnodes = createItemUnodes();
    }
    
    //    private List<Unode> createItemUnodes() {
    //        List<Unode> itemUnodes = new ArrayList<Unode>(items.size());
    //        for (PythonConstruct construct : items)
    //            itemUnodes.add(construct.toUnode());
    //        return itemUnodes;
    //    }
    
    //    
    //    @Override
    //    public PythonValueSet evaluate(final PythonDynamicContext context) {
    //        List<PythonConstruct> args = getPostChildren();
    //        PythonValueSet results = new PythonValueSet();
    //        ArrayList<PythonValueSet> choices = new ArrayList<PythonValueSet>();
    //        for (PythonConstruct arg : args) {
    //            PythonValueSet evaluated = arg.evaluate(context);
    //            choices.add(evaluated);
    //        }
    //        for (List<PythonValue> actualArguments : new TupleIterator(choices)) {
    //            ListValue listObject = ListType.wrap(actualArguments);
    //            results.addResult(listObject, context);
    //        }
    //        return results;
    //    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
        return new PythonValueSet(new ListValue());
    }
    
    @Override
    public Unode toUnode() {
        return new ListLiteralUnode(items);
    }
    
}
