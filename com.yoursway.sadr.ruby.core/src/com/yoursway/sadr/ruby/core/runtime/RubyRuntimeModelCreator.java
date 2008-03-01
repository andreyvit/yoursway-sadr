package com.yoursway.sadr.ruby.core.runtime;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ruby.ast.RubyAssignment;
import org.eclipse.dltk.ruby.ast.RubyClassDeclaration;
import org.eclipse.dltk.ruby.ast.RubyForStatement2;
import org.eclipse.dltk.ruby.ast.RubySelfReference;
import org.eclipse.dltk.ruby.ast.RubySingletonMethodDeclaration;
import org.eclipse.dltk.ruby.ast.RubyVariableKind;

import com.yoursway.sadr.ruby.core.ast.visitor.RubyAstTraverser;
import com.yoursway.sadr.ruby.core.ast.visitor.RubyAstVisitor;
import com.yoursway.sadr.ruby.core.runtime.contributions.Context;
import com.yoursway.sadr.ruby.core.typeinferencing.engine.Construct;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.ClassScope;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.FileScope;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.MethodScope;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.Scope;

public class RubyRuntimeModelCreator {
    
    public void process(Context context, ModuleDeclaration rootNode) {
        RubyAstTraverser traverser = new RubyAstTraverser();
        traverser.traverse(rootNode, new RootState(context));
    }
    
    static abstract class State<T extends ASTNode> extends RubyAstVisitor<T> {
        
        protected final Context context;
        
        public State(Context context) {
            super(null);
            this.context = context;
        }
        
        public State(State<?> parentVisitor) {
            super(parentVisitor);
            context = parentVisitor.context();
        }
        
        public Context context() {
            return context;
        }
        
        @Override
        protected RubyAstVisitor<?> enterAssignment(RubyAssignment node) {
            if (node.getLeft() instanceof VariableReference) {
                VariableReference reference = (VariableReference) node.getLeft();
                if (reference.getVariableKind() == RubyVariableKind.GLOBAL) {
                    RubyGlobalVariable var = context.model().lookupGlobalVariable(reference.getName());
                    new RubyGlobalVariableDefinition(var, context, reference, node);
                }
            }
            return null;
        }
        
    }
    
    static class RootState extends State<ASTNode> {
        
        private final FileScope scope;
        
        public RootState(Context context) {
            super(context);
            this.scope = context.fileScope();
        }
        
        @Override
        protected RubyAstVisitor<?> enterClassDeclaration(RubyClassDeclaration node) {
            String className = node.getName();
            //            RubyMetaClass klass = context.model().lookupClass(className).metaClass();
            
            RubyClass newClass = context.model().lookupClass(className);
            Construct<Scope, RubyClassDeclaration> construct = new Construct<Scope, RubyClassDeclaration>(
                    scope, node);
            RubySourceClassDefinition def = new RubySourceClassDefinition(newClass, context(), construct,
                    scope.classLookup().standardTypes().objectClass());
            return new ClassState(this, def, (ClassScope) def.scope());
        }
        
        @Override
        protected RubyAstVisitor<?> enterMethodDeclaration(MethodDeclaration node) {
            LocalVariableContainer container;
            Scope subscope;
            Construct<Scope, MethodDeclaration> construct = new Construct<Scope, MethodDeclaration>(scope,
                    node);
            container = new RubySourceProcedure(context(), construct);
            subscope = ((RubySourceProcedure) container).scope();
            return new LocalVariableContainerState(this, container, subscope);
        }
        
    }
    
    static class LocalVariableContainerState extends State<ASTNode> {
        
        private final LocalVariableContainer container;
        private final Scope scope;
        
        public LocalVariableContainerState(State<?> parentVisitor, LocalVariableContainer container,
                Scope scope) {
            super(parentVisitor);
            this.container = container;
            this.scope = scope;
        }
        
        @Override
        protected RubyAstVisitor<?> enterAssignment(RubyAssignment node) {
            super.enterAssignment(node);
            if (node.getLeft() instanceof VariableReference) {
                VariableReference ref = (VariableReference) node.getLeft();
                if (ref.getVariableKind() == RubyVariableKind.LOCAL)
                    new RubyLocalVariable(container, context, scope, node);
                else if (ref.getVariableKind() == RubyVariableKind.INSTANCE && scope instanceof MethodScope) {
                    MethodScope methodScope = (MethodScope) scope;
                    RubyBasicClass klass = methodScope.getMethod().klass();
                    if (null == klass.findField(ref.getName()))
                        new RubySourceField(context, klass, ref.getName(), ref);
                }
            }
            return this;
        }
        
        @Override
        protected RubyAstVisitor<?> enterForStatement(RubyForStatement2 node) {
            new RubyLocalVariable(container, context, scope, node);
            return this;
        }
        
    }
    
    static class ClassState extends LocalVariableContainerState {
        
        private final ClassScope scope;
        
        public ClassState(State<?> parentVisitor, LocalVariableContainer container, ClassScope scope) {
            super(parentVisitor, container, scope);
            this.scope = scope;
        }
        
        @Override
        protected RubyAstVisitor<?> enterMethodDeclaration(MethodDeclaration node) {
            Construct<Scope, MethodDeclaration> construct = new Construct<Scope, MethodDeclaration>(scope,
                    node);
            RubyClass klass = scope.getKlass().klass();
            LocalVariableContainer container = new RubySourceMethod(klass, context(), construct);
            Scope subscope = ((RubySourceMethod) container).scope();
            return new LocalVariableContainerState(this, container, subscope);
        }
        
        @Override
        protected RubyAstVisitor<?> enterSingletonMethodDeclaration(RubySingletonMethodDeclaration node) {
            if (node.getReceiver() instanceof RubySelfReference) {
                Construct<Scope, MethodDeclaration> construct = new Construct<Scope, MethodDeclaration>(
                        scope, node);
                RubyClass klass = scope.getKlass().klass();
                LocalVariableContainer container = new RubySourceMethod(klass.metaClass(), context(),
                        construct);
                Scope subscope = ((RubySourceMethod) container).scope();
                return new LocalVariableContainerState(this, container, subscope);
            }
            return this;
        }
        
    }
    
}
