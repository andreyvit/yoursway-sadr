package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kilim.pausable;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.python.parser.ast.PythonArgument;

import com.yoursway.sadr.engine.Analysis;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.dynamic.arguments.DeclaredArguments;
import com.yoursway.sadr.python.analysis.context.dynamic.arguments.DeclaredArgumentsBuilder;
import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.index.IndexAffector;
import com.yoursway.sadr.python.analysis.index.IndexRequest;
import com.yoursway.sadr.python.analysis.index.queries.ReturnsIndexQuery;
import com.yoursway.sadr.python.analysis.index.queries.ReturnsRequestor;
import com.yoursway.sadr.python.analysis.index.wrapping.IndexNameWrappingStrategy;
import com.yoursway.sadr.python.analysis.lang.constructs.CallableDeclaration;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonDeclaration;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonScopeImpl;
import com.yoursway.sadr.python.analysis.lang.constructs.special.ArgumentProxyC;
import com.yoursway.sadr.python.analysis.lang.constructs.special.SpecialValueC;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.VariableUnode;
import com.yoursway.sadr.python.analysis.objectmodel.values.FunctionObject;
import com.yoursway.sadr.python.analysis.objectmodel.values.InstanceValue;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class MethodDeclarationC extends PythonScopeImpl<MethodDeclaration> implements PythonDeclaration,
        CallableDeclaration, IndexAffector {
    
    private final Map<String, PythonConstruct> inits;
    private final List<PythonConstruct> body;
    private final List<ArgumentC> arguments;
    private final DeclaredArguments declaredArguments;
    
    MethodDeclarationC(PythonStaticContext sc, MethodDeclaration node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        body = wrap(node.getBody().getChilds(), this);
        inits = computeInitializers();
        body.isEmpty();
        this.arguments = wrapArguments(node);
        this.declaredArguments = createDeclaredArguments();
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
    
    private List<ArgumentC> wrapArguments(MethodDeclaration node) {
        List<PythonArgument> args = node.getArguments();
        List<ArgumentC> arguments = new ArrayList<ArgumentC>(args.size());
        for (PythonArgument arg : args)
            arguments.add((ArgumentC) wrap(arg, this));
        return arguments;
    }
    
    private Map<String, PythonConstruct> computeInitializers() {
        Map<String, PythonConstruct> result = new HashMap<String, PythonConstruct>();
        for (Object arg : this.node.getArguments()) {
            if (arg instanceof PythonArgument) {
                PythonArgument argument = (PythonArgument) arg;
                ASTNode init = argument.getInitialization();
                if (init != null) {
                    PythonConstruct wrappedInitializer = wrap(init, staticContext());
                    result.put(argument.getName(), wrappedInitializer);
                }
            }
        }
        return result;
    }
    
    public PythonConstruct getArgInit(String name) {
        return inits.get(name);
    }
    
    @Override
    public String toString() {
        return "Method " + this.name();
    }
    
    @Override
    public String name() {
        return this.node.getName();
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc) {
        return null;
    }
    
    public void actOnIndex(IndexRequest r) {
        r.addAssignment(new VariableUnode(node.getName()), staticContext(), new SpecialValueC(
                staticContext(), new PythonValueSet(new FunctionObject(this))));
        Collection<ArgumentProxyC> argumentProxies = new ArrayList<ArgumentProxyC>(arguments.size());
        int index = 0;
        for (ArgumentC argument : arguments) {
            //            ASTNode initialization = argument.getInitialization();
            //            String key = argument.getName();
            argumentProxies.add(new ArgumentProxyC(this, this, argument, index++));
            //            if (argument.getStar() == PythonArgument.NOSTAR) {
            //                boolean required = initialization == null;
            //                PythonValue value = reader.getKwarg(key, required);
            //                args.put(key, value);
            //            } else if (argument.getStar() == PythonArgument.STAR) {
            //                TupleValue value = TupleType.wrap(reader.lastArgs());
            //                args.put(key, value);
            //            } else if (argument.getStar() == PythonArgument.DOUBLESTAR) {
            //                DictValue value = DictType.wrapStrDict(reader.lastKwargs());
            //                args.put(key, value);
            //            }
            //            
        }
        for (ArgumentProxyC proxy : argumentProxies)
            r.addAssignmentWithoutWrapping(new VariableUnode(proxy.getName()), this, proxy);
    }
    
    @pausable
    public Collection<PythonConstruct> findReturnedValues() {
        final Collection<PythonConstruct> result = new ArrayList<PythonConstruct>();
        Analysis.queryIndex(new ReturnsIndexQuery(this), new ReturnsRequestor() {
            public void returnedValue(PythonConstruct value) {
                result.add(value);
            }
        });
        return result;
    }
    
    @Override
    public MethodDeclarationC getParentMethodDeclarationC() {
        return this;
    }
    
    public IndexNameWrappingStrategy createWrappingStrategy() {
        return IndexNameWrappingStrategy.NULL;
    }
    
    public PythonValueSet calculateSelf() {
        PythonStaticContext sc = staticContext();
        if (sc instanceof ClassDeclarationC) {
            ClassDeclarationC cdc = (ClassDeclarationC) sc;
            return new PythonValueSet(new InstanceValue(cdc.getInstanceType()));
        }
        return PythonValueSet.EMPTY;
    }
    
}
