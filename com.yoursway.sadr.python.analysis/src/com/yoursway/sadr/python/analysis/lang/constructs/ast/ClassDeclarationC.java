package com.yoursway.sadr.python.analysis.lang.constructs.ast;

import java.util.Collections;
import java.util.List;

import org.eclipse.dltk.ast.ASTListNode;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.python.parser.ast.PythonArgument;
import org.eclipse.dltk.python.parser.ast.PythonClassDeclaration;

import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.context.lexical.scopes.ClassScope;
import com.yoursway.sadr.python.analysis.index.IndexAffector;
import com.yoursway.sadr.python.analysis.index.IndexRequest;
import com.yoursway.sadr.python.analysis.index.wrapping.IndexAttributeWrappingStrategy;
import com.yoursway.sadr.python.analysis.index.wrapping.IndexNameWrappingStrategy;
import com.yoursway.sadr.python.analysis.lang.constructs.CallableDeclaration;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstructImpl;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonDeclaration;
import com.yoursway.sadr.python.analysis.lang.unodes.Bnode;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.VariableUnode;
import com.yoursway.sadr.python.analysis.lang.unodes.literals.ScalarLiteralUnode;
import com.yoursway.sadr.python.analysis.objectmodel.types.InstanceType;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class ClassDeclarationC extends PythonConstructImpl<PythonClassDeclaration> implements
        PythonDeclaration, IndexAffector {
    
    private final List<PythonConstruct> supers;
    private final List<PythonConstruct> body;
    private final InstanceType instanceType;
    private final PythonLexicalContext innerScope;
    
    ClassDeclarationC(PythonLexicalContext sc, PythonClassDeclaration node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        innerScope = sc.with(new ClassScope(sc.getScope()));
        body = wrap(node.getBody().getChilds(), innerScope);
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
            return wrap(childs, innerScope);
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
    
    public void actOnIndex(IndexRequest req) {
        req.addAssignment(new VariableUnode(name()), staticContext().getScope(), new Bnode(
                new ScalarLiteralUnode(new PythonValueSet(instanceType)), staticContext()));
    }
    
    public IndexNameWrappingStrategy createWrappingStrategy() {
        return new IndexAttributeWrappingStrategy(new VariableUnode(name()));
    }
    
}
