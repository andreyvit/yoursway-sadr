package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.python.parser.ast.PythonCallArgument;
import org.eclipse.dltk.python.parser.ast.expressions.Assignment;

import com.yoursway.sadr.python.analysis.context.dynamic.arguments.ActualArgumentsBuilder;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.index.IndexRequest;
import com.yoursway.sadr.python.analysis.index.data.PassedKeywordArgumentInfo;
import com.yoursway.sadr.python.analysis.index.data.PassedPositionalArgumentInfo;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.analysis.lang.unodes.Bnode;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;

public class CallArgumentC extends PythonConstructImpl<PythonCallArgument> {
    
    private final String name;
    
    private final PythonConstruct value;
    
    public CallArgumentC(PythonLexicalContext staticContext, PythonCallArgument node,
            PythonConstructImpl<?> parent) {
        super(staticContext, node, parent);
        if (node.getValue() == null)
            throw new IllegalArgumentException("node.getValue() should be != null");
        ASTNode value = node.getValue();
        if (value instanceof Assignment) {
            Assignment assignment = (Assignment) value;
            SimpleReference lhs = (SimpleReference) assignment.getLeft();
            this.name = lhs.getName();
            this.value = wrap(assignment.getRight(), staticContext);
        } else {
            this.name = null;
            this.value = wrap(value, staticContext);
        }
    }
    
    int getStar() {
        return node.getStar();
    }
    
    public PythonConstruct getValue() {
        return value;
    }
    
    public void addTo(ActualArgumentsBuilder builder) {
        Unode valueU = value.toUnode();
        if (valueU == null)
            return;
        Bnode bnode = new Bnode(valueU, staticContext());
        if (name != null)
            builder.addKeyword(name, bnode);
        else if (getStar() == 1)
            builder.addStar(bnode);
        else if (getStar() == 2)
            builder.addSuperstar(bnode);
        else
            builder.addPositional(bnode);
    }
    
    public boolean isStarOrSuperstar() {
        return getStar() > 0;
    }
    
    public void actOnIndex(IndexRequest r, CallC call, int index) {
        if (isStarOrSuperstar())
            return;
        Unode argUnode = value.toUnode();
        if (argUnode != null)
            if (name != null)
                r.addPassedArgument(argUnode, new PassedKeywordArgumentInfo(call, name));
            else
                r.addPassedArgument(argUnode, new PassedPositionalArgumentInfo(call, index));
    }
    
}
