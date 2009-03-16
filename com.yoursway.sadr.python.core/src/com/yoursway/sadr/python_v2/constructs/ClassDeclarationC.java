package com.yoursway.sadr.python_v2.constructs;

import java.util.Collections;
import java.util.List;

import org.eclipse.dltk.ast.ASTListNode;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.python.parser.ast.PythonArgument;
import org.eclipse.dltk.python.parser.ast.PythonClassDeclaration;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.yoursway.sadr.python_v2.croco.Frog;
import com.yoursway.sadr.python_v2.croco.Index;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.croco.PythonRecord;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.python_v2.model.builtins.types.InstanceType;

public class ClassDeclarationC extends PythonScopeImpl<PythonClassDeclaration> implements PythonDeclaration,
        CallableDeclaration {
    
    private List<PythonConstruct> supers;
    
    ClassDeclarationC(Scope sc, PythonClassDeclaration node) {
        super(sc, node);
    }
    
    @Override
    protected void wrapEnclosedChildren() {
        List<PythonConstruct> children = PythonConstructFactory.wrap(this.node.getStatements(), this);
        setPostChildren(children);
        wrapSuperclasses();
    }
    
    private void wrapSuperclasses() {
        ASTListNode superClasses = this.node.getSuperClasses();
        if (superClasses != null) {
            List<ASTNode> childs = superClasses.getChilds();
            supers = PythonConstructFactory.wrap(childs, this);
        } else {
            supers = Collections.EMPTY_LIST;
        }
        setPreChildren(supers);
    }
    
    //    public void actOnModel(ModelRequest request) {
    //        String superclassName = PythonUtils.superclassName(node);
    //        PythonType superclass = null;
    //        if (superclassName != null)
    //            superclass = staticContext().classLookup().lookupClass(superclassName);
    //        
    //        new PythonSourceClassDefinition(innerContext, request.context(), this, superclass);
    //    }
    
    public String displayName() {
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
    
    @Override
    public PythonValueSet evaluate(Krocodile context) {
        PythonObject callableObject = new InstanceType(this);
        return new PythonValueSet(callableObject, context);
    }
    
    public void index(Krocodile crocodile) {
        PythonRecord record = Index.newRecord(name());
        Index.add(crocodile, this, record);
    }
    
    public CallableDeclaration findDeclaredMethod(String methodName) {
        for (PythonConstruct construct : getPostChildren())
            if (construct instanceof MethodDeclarationC) {
                MethodDeclarationC methodC = (MethodDeclarationC) construct;
                if (methodC.name().equals(methodName))
                    return methodC;
            }
        return null;
    }
    
    public PythonValueSet call(Krocodile crocodile) {
        throw new NotImplementedException();
    }
    
    public PythonConstruct getArgInit(String name) {
        throw new NotImplementedException();
    }
    
    public List<PythonArgument> getArguments() {
        throw new NotImplementedException();
    }
}
