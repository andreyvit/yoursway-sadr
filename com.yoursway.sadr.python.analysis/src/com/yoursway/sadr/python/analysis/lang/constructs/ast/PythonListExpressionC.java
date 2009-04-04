package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.python.parser.ast.expressions.PythonListExpression;

import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.analysis.lang.unodes.Bnode;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.lang.unodes.literals.ListLiteralUnode;

public class PythonListExpressionC extends PythonConstructImpl<PythonListExpression> implements
        PythonConstruct {
    
    private final List<PythonConstruct> items;
    
    //    private final List<Unode> itemUnodes;
    
    public PythonListExpressionC(PythonLexicalContext sc, PythonListExpression node,
            PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        items = wrap(node.getChilds(), sc);
        //        this.itemUnodes = createItemUnodes();
    }
    
    private List<Bnode> createItemUnodes() {
        List<Bnode> itemUnodes = new ArrayList<Bnode>(items.size());
        for (PythonConstruct construct : items)
            itemUnodes.add(construct.toBnode());
        return itemUnodes;
    }
    
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
    
    @Override
    public Unode toUnode() {
        return new ListLiteralUnode(createItemUnodes());
    }
    
}
