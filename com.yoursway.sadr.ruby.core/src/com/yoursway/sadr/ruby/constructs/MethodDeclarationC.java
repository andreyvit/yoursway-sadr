package com.yoursway.sadr.ruby.constructs;

import org.eclipse.dltk.ast.declarations.MethodDeclaration;

import com.yoursway.sadr.core.AbstractConstruct;
import com.yoursway.sadr.core.ContinuationRequestor;
import com.yoursway.sadr.core.Scope;
import com.yoursway.sadr.ruby.goals.WorldG;
import com.yoursway.sadr.ruby.goals.WorldGContributor;
import com.yoursway.sadr.ruby.world.RMethod;

public class MethodDeclarationC extends AbstractConstruct implements WorldGContributor {
    
    public MethodDeclarationC(Scope scope, MethodDeclaration node) {
        super(scope, node);
    }
    
    @Override
    public MethodDeclaration node() {
        return (MethodDeclaration) super.node();
    }
    
    public void contributeToWorldG(WorldG goal, ContinuationRequestor requestor) {
        goal.contributor().defineMethod(new RMethod(node().getName()));
    }
    
}
