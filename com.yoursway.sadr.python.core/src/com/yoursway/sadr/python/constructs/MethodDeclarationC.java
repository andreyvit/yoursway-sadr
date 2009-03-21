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
import com.yoursway.sadr.python.model.IndexRequest;
import com.yoursway.sadr.python.model.values.FunctionObject;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class MethodDeclarationC extends PythonScopeImpl<MethodDeclaration> implements PythonDeclaration,
        CallableDeclaration, IndexAffector {
    
    private final Map<String, PythonConstruct> inits;
    private final List<PythonConstruct> body;
    
    @SuppressWarnings("unchecked")
    MethodDeclarationC(PythonStaticContext sc, MethodDeclaration node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        body = wrap(node.getBody().getChilds(), this);
        inits = computeInitializers();
        body.isEmpty();
    }
    
    @SuppressWarnings("unchecked")
    public List<PythonArgument> getArguments() {
        return node.getArguments();
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
        r.addAssignment(new VariableUnode(node.getName()), new SpecialValueC(staticContext(),
                new PythonValueSet(new FunctionObject(this))));
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
    
}
