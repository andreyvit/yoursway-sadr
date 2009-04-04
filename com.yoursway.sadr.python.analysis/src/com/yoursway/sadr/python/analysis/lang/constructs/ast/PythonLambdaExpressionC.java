package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import kilim.pausable;

import org.eclipse.dltk.python.parser.ast.PythonArgument;
import org.eclipse.dltk.python.parser.ast.expressions.PythonLambdaExpression;

import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.dynamic.arguments.DeclaredArguments;
import com.yoursway.sadr.python.analysis.context.dynamic.arguments.DeclaredArgumentsBuilder;
import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.lang.constructs.CallableDeclaration;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonScopeImpl;
import com.yoursway.sadr.python.analysis.objectmodel.values.FunctionObject;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class PythonLambdaExpressionC extends PythonScopeImpl<PythonLambdaExpression> implements
        CallableDeclaration {
    
    private final PythonConstruct body;
    private final List<ArgumentC> arguments;
    private final DeclaredArguments declaredArguments;
    
    public PythonLambdaExpressionC(PythonStaticContext sc, PythonLambdaExpression node,
            PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        body = wrap(node.getBodyExpression(), sc);
        this.arguments = wrapArguments(node);
        this.declaredArguments = createDeclaredArguments();
    }
    
    private List<ArgumentC> wrapArguments(PythonLambdaExpression node) {
        List<PythonArgument> args = node.getArguments();
        List<ArgumentC> arguments = new ArrayList<ArgumentC>(args.size());
        for (PythonArgument arg : args)
            arguments.add((ArgumentC) wrap(arg, this));
        return arguments;
    }
    
    private DeclaredArguments createDeclaredArguments() {
        DeclaredArgumentsBuilder builder = new DeclaredArgumentsBuilder();
        for (ArgumentC arg : arguments)
            arg.addTo(builder);
        return builder.build();
    }
    
    public DeclaredArguments getDeclaredArguments() {
        return declaredArguments;
    }
    
    @Override
    public String toString() {
        return "Lambda";
    }
    
    @Override
    public String name() {
        return "<lambda>";
    }
    
    public List<PythonArgument> getArguments() {
        return node.getArguments();
    }
    
    public PythonConstruct getArgInit(String name) {
        return null;
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc) {
        return new PythonValueSet(new FunctionObject(this));
    }
    
    @pausable
    public Collection<PythonConstruct> findReturnedValues() {
        return Collections.singleton(body);
    }
    
}
