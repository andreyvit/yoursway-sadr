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
import com.yoursway.sadr.python.analysis.context.dynamic.arguments.DeclaredArguments;
import com.yoursway.sadr.python.analysis.context.dynamic.arguments.DeclaredArgumentsBuilder;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.context.lexical.areas.MethodArea;
import com.yoursway.sadr.python.analysis.context.lexical.scopes.MethodScope;
import com.yoursway.sadr.python.analysis.index.IndexAffector;
import com.yoursway.sadr.python.analysis.index.IndexRequest;
import com.yoursway.sadr.python.analysis.index.queries.ReturnsIndexQuery;
import com.yoursway.sadr.python.analysis.index.queries.ReturnsRequestor;
import com.yoursway.sadr.python.analysis.index.wrapping.IndexNameWrappingStrategy;
import com.yoursway.sadr.python.analysis.lang.constructs.CallableDeclaration;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonDeclaration;
import com.yoursway.sadr.python.analysis.lang.unodes.Bnode;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.VariableUnode;
import com.yoursway.sadr.python.analysis.lang.unodes.literals.ScalarLiteralUnode;
import com.yoursway.sadr.python.analysis.lang.unodes.proxies.ArgumentUnode;
import com.yoursway.sadr.python.analysis.objectmodel.values.FunctionObject;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class MethodDeclarationC extends PythonConstructImpl<MethodDeclaration> implements PythonDeclaration,
        CallableDeclaration, IndexAffector {
    
    private final Map<String, PythonConstruct> inits;
    private final List<PythonConstruct> body;
    private final List<ArgumentC> arguments;
    private final DeclaredArguments declaredArguments;
    private final PythonLexicalContext innerLC;
    
    MethodDeclarationC(PythonLexicalContext sc, MethodDeclaration node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        innerLC = sc.with(new MethodScope(sc.getScope())).with(new MethodArea());
        body = wrap(node.getBody().getChilds(), innerLC);
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
            arguments.add((ArgumentC) wrap(arg, innerLC));
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
    
    public String name() {
        return this.node.getName();
    }
    
    public void actOnIndex(IndexRequest r) {
        r.addAssignment(new VariableUnode(node.getName()), staticContext(), new Bnode(new ScalarLiteralUnode(
                new PythonValueSet(new FunctionObject(this))), staticContext()));
        int index = 0;
        for (ArgumentC argument : arguments) {
            //            ASTNode initialization = argument.getInitialization();
            //            String key = argument.getName();
            r.addAssignmentWithoutWrapping(new VariableUnode(argument.getName()), innerLC, new Bnode(
                    new ArgumentUnode(index++, argument.getName(), argument.getInitBnode()), innerLC));
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
    }
    
    @pausable
    public Collection<Bnode> findReturnedValues() {
        final Collection<Bnode> result = new ArrayList<Bnode>();
        Analysis.queryIndex(new ReturnsIndexQuery(this), new ReturnsRequestor() {
            public void returnedValue(Bnode value) {
                result.add(value);
            }
        });
        return result;
    }
    
    public IndexNameWrappingStrategy createWrappingStrategy() {
        return IndexNameWrappingStrategy.NULL;
    }
    
    public PythonLexicalContext getInnerLC() {
        return innerLC;
    }
    
    //    public PythonValueSet calculateSelf() {
    //        PythonLexicalContext sc = staticContext();
    //        if (sc instanceof ClassDeclarationC) {
    //            ClassDeclarationC cdc = (ClassDeclarationC) sc;
    //            return new PythonValueSet(new InstanceValue(cdc.getInstanceType()));
    //        }
    //        return PythonValueSet.EMPTY;
    //    }
    
}
