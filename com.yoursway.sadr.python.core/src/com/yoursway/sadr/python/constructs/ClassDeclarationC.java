package com.yoursway.sadr.python.constructs;

import java.util.Collections;
import java.util.List;

import org.eclipse.dltk.ast.ASTListNode;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.python.parser.ast.PythonArgument;
import org.eclipse.dltk.python.parser.ast.PythonClassDeclaration;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.python_v2.croco.Frog;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

public class ClassDeclarationC extends PythonScopeImpl<PythonClassDeclaration> implements PythonDeclaration,
        CallableDeclaration {
    
    private final List<PythonConstruct> supers;
    private final List<PythonConstruct> body;
    
    @SuppressWarnings("unchecked")
    ClassDeclarationC(PythonStaticContext sc, PythonClassDeclaration node, PythonConstructImpl<?> parent) {
        super(sc, node, parent);
        body = wrap(node.getBody().getChilds(), this);
        supers = wrapSuperclasses();
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
    
    public boolean match(Frog frog) {
        return frog.match(name());
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
    
    public PythonValueSet evaluateValue(PythonDynamicContext dc, InfoKind infoKind) {
        throw new VoidConstructException(this);
    }
    
}
