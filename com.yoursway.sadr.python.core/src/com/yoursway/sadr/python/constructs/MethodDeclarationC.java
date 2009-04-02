package com.yoursway.sadr.python.constructs;

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
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.index.ReturnsIndexQuery;
import com.yoursway.sadr.python.index.ReturnsRequestor;
import com.yoursway.sadr.python.index.unodes.VariableUnode;
import com.yoursway.sadr.python.model.IndexAffector;
import com.yoursway.sadr.python.model.IndexNameWrappingStrategy;
import com.yoursway.sadr.python.model.IndexRequest;
import com.yoursway.sadr.python.model.values.FunctionObject;
import com.yoursway.sadr.python.model.values.InstanceValue;
import com.yoursway.sadr.python_v2.croco.DeclaredArguments;
import com.yoursway.sadr.python_v2.croco.DeclaredArgumentsBuilder;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class MethodDeclarationC extends PythonScopeImpl<MethodDeclaration> implements PythonDeclaration,
        CallableDeclaration, IndexAffector {
    
    private final Map<String, PythonConstruct> inits;
    private final List<PythonConstruct> body;
    private final List<ArgumentC> arguments;
    private final DeclaredArguments declaredArguments;
    
    @SuppressWarnings("unchecked")
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
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
        return null;
    }
    
    public void actOnIndex(IndexRequest r) {
        r.addAssignment(new VariableUnode(node.getName()), staticContext(), new SpecialValueC(
                staticContext(), new PythonValueSet(new FunctionObject(this))));
        Collection<ArgumentProxyC> argumentProxies = new ArrayList<ArgumentProxyC>(arguments.size());
        boolean isFirst = true;
        int index = 0;
        for (ArgumentC argument : arguments) {
            //            ASTNode initialization = argument.getInitialization();
            //            String key = argument.getName();
            argumentProxies.add(new ArgumentProxyC(this, this, argument, index++));
            isFirst = false;
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
