package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import java.util.Collections;
import java.util.List;

import kilim.pausable;

import org.eclipse.dltk.ast.ASTListNode;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.python.parser.ast.PythonArgument;
import org.eclipse.dltk.python.parser.ast.PythonClassDeclaration;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.index.IndexAffector;
import com.yoursway.sadr.python.analysis.index.IndexRequest;
import com.yoursway.sadr.python.analysis.index.wrapping.IndexAttributeWrappingStrategy;
import com.yoursway.sadr.python.analysis.index.wrapping.IndexNameWrappingStrategy;
import com.yoursway.sadr.python.analysis.lang.constructs.CallableDeclaration;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonDeclaration;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonScopeImpl;
import com.yoursway.sadr.python.analysis.lang.constructs.special.SpecialValueC;
import com.yoursway.sadr.python.analysis.lang.constructs.support.VoidConstructException;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.VariableUnode;
import com.yoursway.sadr.python.analysis.objectmodel.types.InstanceType;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class ClassDeclarationC extends PythonScopeImpl<PythonClassDeclaration> implements PythonDeclaration,
        IndexAffector {
    
    private final List<PythonConstruct> supers;
    private final List<PythonConstruct> body;
    private final InstanceType instanceType;
    
    ClassDeclarationC(PythonStaticContext sc, PythonClassDeclaration node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        body = wrap(node.getBody().getChilds(), this);
        supers = wrapSuperclasses();
        instanceType = new InstanceType(this);
    }
    
    public InstanceType getInstanceType() {
        return instanceType;
    }
    
    private List<PythonConstruct> wrapSuperclasses() {
        ASTListNode superClasses = this.node.getSuperClasses();
        if (superClasses != null) {
            List<ASTNode> childs = superClasses.getChilds();
            return wrap(childs, this);
        } else {
            return Collections.emptyList();
        }
    }
    
    @Override
    public String toString() {
        return "Class " + node.getName();
    }
    
    public List<PythonConstruct> getSuperClasses() {
        return supers;
    }
    
    @Override
    public String name() {
        return node.getName();
    }
    
    public CallableDeclaration findDeclaredMethod(String methodName) {
        for (PythonConstruct construct : body)
            if (construct instanceof MethodDeclarationC) {
                MethodDeclarationC methodC = (MethodDeclarationC) construct;
                if (methodC.name().equals(methodName))
                    return methodC;
            }
        return null;
    }
    
    public PythonValueSet call(PythonDynamicContext crocodile) {
        throw new UnsupportedOperationException();
    }
    
    public PythonConstruct getArgInit(String name) {
        throw new UnsupportedOperationException();
    }
    
    public List<PythonArgument> getArguments() {
        throw new UnsupportedOperationException();
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
        throw new VoidConstructException(this);
    }
    
    public void actOnIndex(IndexRequest req) {
        req.addAssignment(new VariableUnode(name()), staticContext(), new SpecialValueC(staticContext(),
                new PythonValueSet(instanceType)));
    }
    
    @Override
    public MethodDeclarationC getParentMethodDeclarationC() {
        return null;
    }
    
    public IndexNameWrappingStrategy createWrappingStrategy() {
        return new IndexAttributeWrappingStrategy(new VariableUnode(name()));
    }
    
}
