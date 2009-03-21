package com.yoursway.sadr.python.constructs;

import java.util.Collections;
import java.util.List;

import kilim.pausable;

import org.eclipse.dltk.ast.ASTListNode;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.python.parser.ast.PythonArgument;
import org.eclipse.dltk.python.parser.ast.PythonClassDeclaration;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python.index.unodes.VariableUnode;
import com.yoursway.sadr.python.model.IndexAffector;
import com.yoursway.sadr.python.model.IndexAttributeWrappingStrategy;
import com.yoursway.sadr.python.model.IndexNameWrappingStrategy;
import com.yoursway.sadr.python.model.IndexRequest;
import com.yoursway.sadr.python.model.types.InstanceType;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class ClassDeclarationC extends PythonScopeImpl<PythonClassDeclaration> implements PythonDeclaration,
        IndexAffector {
    
    private final List<PythonConstruct> supers;
    private final List<PythonConstruct> body;
    private final InstanceType instanceType;
    
    @SuppressWarnings("unchecked")
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
        throw new NotImplementedException();
    }
    
    public PythonConstruct getArgInit(String name) {
        throw new NotImplementedException();
    }
    
    public List<PythonArgument> getArguments() {
        throw new NotImplementedException();
    }
    
    @pausable
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
        throw new VoidConstructException(this);
    }
    
    public void actOnIndex(IndexRequest req) {
        req.addAssignment(new VariableUnode(name()), new SpecialValueC(staticContext(), new PythonValueSet(
                instanceType)));
    }
    
    @Override
    public MethodDeclarationC getParentMethodDeclarationC() {
        return null;
    }
    
    public IndexNameWrappingStrategy createWrappingStrategy() {
        return new IndexAttributeWrappingStrategy(new VariableUnode(name()));
    }
    
}
