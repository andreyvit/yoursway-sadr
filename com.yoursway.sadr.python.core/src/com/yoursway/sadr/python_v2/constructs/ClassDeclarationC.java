package com.yoursway.sadr.python_v2.constructs;

import java.util.Collections;
import java.util.List;

import org.eclipse.dltk.ast.ASTListNode;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.python.parser.ast.PythonClassDeclaration;

import com.yoursway.sadr.python.Grade;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python_v2.croco.Frog;
import com.yoursway.sadr.python_v2.croco.Index;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.croco.PythonRecord;
import com.yoursway.sadr.python_v2.goals.Acceptor;
import com.yoursway.sadr.python_v2.goals.PassResultGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.python_v2.model.builtins.FunctionObject;
import com.yoursway.sadr.succeeder.IGoal;

public class ClassDeclarationC extends PythonScopeImpl<PythonClassDeclaration> implements PythonDeclaration {
    
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
    //        PythonClassType superclass = null;
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
    public IGoal evaluate(Krocodile context, PythonValueSetAcceptor acceptor) {
        FunctionObject functionObject = new FunctionObject(this);
        return new PassResultGoal(context, acceptor, functionObject);
    }
    
    public void index(Krocodile crocodile, final Acceptor acceptor) {
        PythonRecord record = Index.newRecord(name());
        Index.add(crocodile, this, record);
        acceptor.subgoalDone(Grade.DONE);
    }
    
    public MethodDeclarationC findDeclaredMethod(String methodName) {
        for (PythonConstruct construct : getPostChildren())
            if (construct instanceof MethodDeclarationC) {
                MethodDeclarationC methodC = (MethodDeclarationC) construct;
                if (methodC.name().equals(methodName))
                    return methodC;
            }
        return null;
    }
    
}
