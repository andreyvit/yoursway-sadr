package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import kilim.pausable;

import org.eclipse.dltk.python.parser.ast.expressions.Assignment;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.index.IndexAffector;
import com.yoursway.sadr.python.analysis.index.IndexRequest;
import com.yoursway.sadr.python.analysis.index.wrapping.IndexNameWrappingStrategy;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonDeclaration;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class AssignmentC extends PythonConstructImpl<Assignment> implements PythonDeclaration, IndexAffector {
    
    private final PythonConstruct lhs, rhs;
    
    AssignmentC(PythonStaticContext sc, Assignment node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        if (node.getLeft() == null)
            throw new IllegalArgumentException("node.getLeft() should be not null");
        if (node.getRight() == null)
            throw new IllegalArgumentException("node.getRight() should be not null");
        lhs = wrap(node.getLeft(), sc);
        rhs = wrap(node.getRight(), sc);
    }
    
    public PythonConstruct lhs() {
        return lhs;
    }
    
    public PythonConstruct rhs() {
        return rhs;
    }
    
    public void index(PythonDynamicContext crocodile) {
        //        if (leftPart instanceof VariableReferenceC) {
        //            String name = ((VariableReferenceC) leftPart).name();
        //            Index.newRecord(name, crocodile, this);
        //        } else if (leftPart instanceof FieldAccessC) {
        //            FieldAccessC varRead = (FieldAccessC) leftPart;
        //            String tail = varRead.variable().name();
        //            if (searchFrog.match(tail)) {
        //                PythonRecord head = lookup(dotFrog.head(), varRead.receiver());
        //                if (head == null) {
        //                    return null;
        //                }
        //                DotFrog resultFrog = new DotFrog(dotFrog.head(), tail, Frog.UNKNOWN);
        //                PythonRecord whole = Index.lookup(resultFrog);
        //            }
        //        }
    }
    
    public String name() {
        if (lhs instanceof VariableReferenceC)
            return ((VariableReferenceC) lhs).name();
        return null;
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
        return rhs.evaluateValue(dc, infoKind);
    }
    
    public void actOnIndex(IndexRequest indexRequest) {
        Unode lhsUnode = lhs.toUnode();
        if (lhsUnode != null)
            indexRequest.addAssignment(lhsUnode, staticContext(), rhs);
    }
    
    public IndexNameWrappingStrategy createWrappingStrategy() {
        return null;
    }
    
}