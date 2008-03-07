package com.yoursway.sadr.ruby.core.staticchecks;

import java.util.Stack;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ruby.ast.RubyAssignment;
import org.eclipse.dltk.ruby.ast.RubyForStatement2;
import org.eclipse.osgi.util.NLS;

import com.yoursway.sadr.ruby.core.runtime.RubyUtils;
import com.yoursway.sadr.ruby.core.runtime.RubyVariable;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.ruby.core.typeinferencing.services.VariableLookup;

public class VariableAccessCheck extends OneModuleRuntimeBasedCheck {
    
    protected IRubyProblemReporter reporter;
    
    public VariableAccessCheck() {
        
    }
    
    protected class VariableVisitor extends ASTVisitor {
        
        private final Stack<ASTNode> stack;
        
        public VariableVisitor() {
            stack = new Stack<ASTNode>();
        }
        
        private void magic(DtlSymbol node) {
            ASTNode parent = parent();
            String name = (node).getName();
            for (String notVar : new String[] { "self", "super", "%nil", "false", "true" })
                if (name.equalsIgnoreCase(notVar))
                    return;
            if (name.startsWith("%"))
                return;
            if (parent instanceof MethodDeclaration) {
                if (((MethodDeclaration) parent).getNameNode() == node)
                    return;
            }
            if (parent instanceof RubyForStatement2) {
                if (((RubyForStatement2) parent).getVar().equals(name))
                    return;
            }
            if (parent instanceof RubyAssignment) {
                if (stack.size() > 2) {
                    ASTNode preparent = stack.get(stack.size() - 2);
                    if (preparent instanceof LocalVariableDeclaration
                            && ((RubyAssignment) parent).getLeft() == node)
                        return;
                    if (preparent instanceof GlobalVariableDeclaration
                            && ((RubyAssignment) parent).getLeft() == node)
                        return;
                } else
                    return;
            }
            if (parent instanceof CallExpression) {
                if (((CallExpression) parent).getName() == node)
                    return;
            }
            Scope scope = RubyUtils.restoreScope(currentFileScope, node);
            if (scope.classLookup().findClass(name) == null) {
                VariableLookup variableLookup = scope.variableLookup();
                RubyVariable var = variableLookup.findVariable(name);
                if (var == null) {
                    reporter.warning(NLS.bind("Undeclared variable {0}", name), node.sourceStart(), node
                            .sourceEnd() + 1);
                }
            }
        }
        
        @Override
        public boolean visitGeneral(ASTNode node) throws Exception {
            
            if (node instanceof DtlSymbol) {
                try {
                    magic((DtlSymbol) node);
                } catch (Exception e) {
                    //Haha! Have fun :)
                    e.printStackTrace();
                }
            }
            this.stack.add(node);
            return true;
        }
        
        private ASTNode parent() {
            return (stack.size() > 0) ? stack.peek() : null;
        }
        
        @Override
        public void endvisitGeneral(ASTNode node) throws Exception {
            if (parent() == node)
                stack.pop();
            super.endvisitGeneral(node);
        }
        
    }
    
    public void check(ISourceModule module, IRubyProblemReporter reporter) {
        this.reporter = reporter;
        init(module);
        try {
            currentModuleDeclaration.traverse(new VariableVisitor());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
