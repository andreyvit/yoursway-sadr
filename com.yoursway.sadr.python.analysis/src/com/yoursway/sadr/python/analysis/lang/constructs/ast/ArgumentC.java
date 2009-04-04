package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import kilim.pausable;

import org.eclipse.dltk.python.parser.ast.PythonArgument;

import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.dynamic.arguments.DeclaredArgumentsBuilder;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.analysis.lang.unodes.Bnode;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class ArgumentC extends PythonConstructImpl<PythonArgument> {
    
    private final PythonConstruct init;
    
    ArgumentC(PythonLexicalContext sc, PythonArgument node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        if (node.getName() == null)
            throw new AssertionError("node.getName() should be != null");
        if (node.getInitialization() == null)
            init = null;
        else
            init = wrap(node.getInitialization(), sc);
    }
    
    int getStar() {
        return node.getStar();
    }
    
    public String getName() {
        return node.getName();
    }
    
    public PythonConstruct getInit() {
        return init;
    }
    
    public Bnode getInitBnode() {
        return init == null ? null : init.toBnode();
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc) {
        throw new UnsupportedOperationException();
    }
    
    public void addTo(DeclaredArgumentsBuilder builder) {
        int star = getStar();
        if (star == 0)
            builder.add(getName(), this);
        else if (star == 1)
            builder.addStar(this);
        else if (star == 2)
            builder.addStar(this);
        else
            throw new AssertionError("Unreachable");
    }
    
}
