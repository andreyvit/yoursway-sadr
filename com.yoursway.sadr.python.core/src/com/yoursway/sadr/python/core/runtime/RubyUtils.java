package com.yoursway.sadr.python.core.runtime;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.expressions.Literal;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IParent;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.ruby.ast.RubyArrayAccessExpression;
import org.eclipse.dltk.ruby.ast.RubyCallArgument;
import org.eclipse.dltk.ruby.ast.RubyColonExpression;
import org.eclipse.dltk.ruby.ast.RubyForStatement2;
import org.eclipse.dltk.ruby.ast.RubySelfReference;

import com.yoursway.sadr.python.core.ast.visitor.RubyAstTraverser;
import com.yoursway.sadr.python.core.ast.visitor.RubyAstVisitor;
import com.yoursway.sadr.python.core.ast.visitor.RubyExtendedAstTraverser;
import com.yoursway.sadr.python.core.ast.visitor.TargetedAstVisitor;
import com.yoursway.sadr.python.core.runtime.contributions.NodeBoundItem;
import com.yoursway.sadr.python.core.typeinferencing.keys.wildcards.ArrayWildcard;
import com.yoursway.sadr.python.core.typeinferencing.keys.wildcards.StarWildcard;
import com.yoursway.sadr.python.core.typeinferencing.keys.wildcards.Wildcard;
import com.yoursway.sadr.python.core.typeinferencing.scopes.FileScope;
import com.yoursway.sadr.python.core.typeinferencing.scopes.ForScope;
import com.yoursway.sadr.python.core.typeinferencing.scopes.LocalScope;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python.core.typeinferencing.services.ClassLookup;
import com.yoursway.sadr.python.core.typeinferencing.services.NodeLookup;
import com.yoursway.sadr.python.core.typeinferencing.types.AbstractType;
import com.yoursway.sadr.python.core.typeinferencing.types.ArrayType;
import com.yoursway.sadr.python.core.typeinferencing.types.ClassType;
import com.yoursway.sadr.python.core.typeinferencing.types.MetaClassType;
import com.yoursway.sadr.python.core.typeinferencing.types.StubType;
import com.yoursway.sadr.python.core.typeinferencing.types.Type;
import com.yoursway.sadr.python.core.typeinferencing.typesets.TypeSet;
import com.yoursway.sadr.python.core.typeinferencing.typesets.TypeSetBuilder;

public class RubyUtils {
    
    public static Wildcard assignmentWildcardExpression(ASTNode node) {
        if (node instanceof SimpleReference || node instanceof RubyColonExpression
                || node instanceof RubySelfReference || node instanceof CallExpression
                || node instanceof Literal)
            return StarWildcard.INSTANCE;
        else if (node instanceof RubyArrayAccessExpression) {
            return new ArrayWildcard(assignmentWildcardExpression(((RubyArrayAccessExpression) node)
                    .getArray()));
        } else
            throw new AssertionError("assignmentWildcardExpression: not symbol, dot or array");
    }
    
    public static ASTNode assignmentTerminalNode(ASTNode node) {
        if (node instanceof SimpleReference || node instanceof RubyColonExpression
                || node instanceof RubySelfReference || node instanceof CallExpression
                || node instanceof Literal)
            return node;
        else if (node instanceof RubyArrayAccessExpression)
            return assignmentTerminalNode(((RubyArrayAccessExpression) node).getArray());
        else
            throw new AssertionError("assignmentTerminalNode: not symbol, dot or array");
    }
    
    public static TypeSet replaceWildcard(Type wildcard, TypeSet replacement) {
        TypeSetBuilder builder = new TypeSetBuilder();
        for (Type type : replacement.containedTypes())
            builder.add(replaceWildcard(wildcard, type));
        return builder.build();
    }
    
    public static Type replaceWildcard(Type wildcard, Type replacement) {
        if (wildcard == StubType.WILDCARD)
            return replacement;
        else if (wildcard instanceof ArrayType) {
            ArrayType arr = (ArrayType) wildcard;
            Type replaced = replaceWildcard(arr.component(), replacement);
            if (replaced.equals(arr.component()))
                return wildcard;
            else
                return new ArrayType(replaced);
        } else {
            return wildcard;
        }
    }
    
    public static String[] splitByComma(String fieldList) {
        String[] res = fieldList.split(",");
        String[] r = new String[res.length];
        for (int i = 0; i < r.length; i++)
            r[i] = res[i].trim();
        return r;
    }
    
    public static boolean isDtlIdentifierPart(char c) {
        return Character.isLetter(c) || Character.isDigit(c) || c == '$' || c == '%';
    }
    
    //    public static String resolveStaticString(ASTNode receiver) {
    //        if (receiver instanceof StringLiteral)
    //            return ((StringLiteral) receiver).getValue();
    //        if (receiver instanceof BinaryExpression && "+".equals(((BinaryExpression) receiver).getExprKind())) {
    //            BinaryExpression b = (BinaryExpression) receiver;
    //            String left = resolveStaticString(b.getLeft());
    //            String right = resolveStaticString(b.getRight());
    //            if (left != null && right != null)
    //                return left + right;
    //            else
    //                return null;
    //        }
    //        return null;
    //    }
    
    //    public static StringLiteral trackStaticString(ASTNode receiver, Pattern pattern) {
    //        if (receiver instanceof StringLiteral) {
    //            StringLiteral lit = (StringLiteral) receiver;
    //            if (pattern.matcher(lit.getValue()).find())
    //                return lit;
    //        }
    //        if (receiver instanceof BinaryExpression && "+".equals(((BinaryExpression) receiver).getExprKind())) {
    //            BinaryExpression b = (BinaryExpression) receiver;
    //            StringLiteral res = trackStaticString(b.getLeft(), pattern);
    //            if (res != null)
    //                return res;
    //            return trackStaticString(b.getRight(), pattern);
    //        }
    //        return null;
    //    }
    //    
    public static RubyMetaClass resolveStaticClassReference(ClassLookup lookup, ASTNode receiver) {
        if (receiver instanceof SimpleReference)
            return resolveStaticClassReference(lookup, (SimpleReference) receiver);
        else
            System.out.println("resolveStaticClassReference: " + receiver);
        return null;
    }
    
    public static RubyMetaClass resolveStaticClassReference(ClassLookup lookup, SimpleReference receiver) {
        RubyClass klass = lookup.findClass(receiver.getName());
        return klass == null ? null : klass.metaClass();
    }
    
    public static Scope restoreScope(FileScope fileScope, ASTNode node) {
        System.out.println();
        LocalScope parentScope = findParentScope(fileScope, node);
        ScopeRequestor requestor = new ScopeRequestor(parentScope);
        RubyAstTraverser traverser = new RubyExtendedAstTraverser(fileScope.nodeLookup());
        traverser.traverse(parentScope.node(), new RootScoper(node, parentScope, requestor));
        return requestor.getInnerScope();
    }
    
    public static Scope restoreSubscope(Scope scope, ASTNode node) {
        return restoreScope(scope.fileScope(), node);
    }
    
    private static LocalScope findParentScope(FileScope fileScope, ASTNode node) {
        NodeLookup nodeLookup = fileScope.nodeLookup();
        for (ASTNode current = node; current != null; current = fileScope.parentOf(current)) {
            NodeBoundItem item = nodeLookup.lookup(current);
            if (item instanceof RubySourceMethod)
                return ((RubySourceMethod) item).scope();
            if (item instanceof RubySourceProcedure)
                return ((RubySourceProcedure) item).scope();
        }
        return fileScope;
    }
    
    static class ScopeRequestor {
        
        private final List<Scope> scopes = new ArrayList<Scope>();
        
        private Scope answer = null;
        
        public ScopeRequestor(Scope initialScope) {
            scopes.add(initialScope);
        }
        
        public void innerScope(Scope innerScope) {
            scopes.add(innerScope);
        }
        
        public void leave() {
            scopes.remove(scopes.size() - 1);
        }
        
        public void found() {
            this.answer = scopes.get(scopes.size() - 1);
        }
        
        public Scope getInnerScope() {
            return answer;
        }
        
    }
    
    abstract static class AbstractScoper<T extends ASTNode> extends TargetedAstVisitor<T> {
        
        protected final Scope scope;
        protected final ScopeRequestor requestor;
        
        public AbstractScoper(ASTNode target, Scope scope, ScopeRequestor requestor) {
            super(target);
            this.scope = scope;
            this.requestor = requestor;
            requestor.innerScope(scope);
        }
        
        public AbstractScoper(TargetedAstVisitor<?> parentVisitor, Scope scope, ScopeRequestor requestor) {
            super(parentVisitor);
            this.scope = scope;
            this.requestor = requestor;
            requestor.innerScope(scope);
        }
        
        @Override
        protected RubyAstVisitor<?> enterNode(ASTNode node) {
            checkAnswer(node);
            return this;
        }
        
        protected void checkAnswer(ASTNode node) {
            if (node == target())
                requestor.found();
        }
        
        @Override
        protected void leave() {
            requestor.leave();
        }
        
    }
    
    static class RootScoper extends AbstractScoper<ASTNode> {
        
        public RootScoper(ASTNode target, Scope scope, ScopeRequestor requestor) {
            super(target, scope, requestor);
        }
        
        public RootScoper(TargetedAstVisitor<?> parentVisitor, Scope scope, ScopeRequestor requestor) {
            super(parentVisitor, scope, requestor);
        }
        
        @Override
        protected RubyAstVisitor<?> enterForStatement(RubyForStatement2 node) {
            ForScope forScope = new ForScope(scope, node);
            return new RootScoper(this, forScope, requestor);
        }
        
    }
    
    @SuppressWarnings("unchecked")
    public static ASTNode[] argumentsOf(CallExpression n) {
        List<ASTNode> children0 = n.getArgs().getChilds();
        List<ASTNode> children = new ArrayList<ASTNode>();
        for (ASTNode nd : children0) {
            if (nd instanceof RubyCallArgument)
                children.add(((RubyCallArgument) nd).getValue());
        }
        return children.toArray(new ASTNode[children.size()]);
    }
    
    //    public static StringLiteral trackField(ASTNode node, String fieldName) {
    //        return trackStaticString(node, fieldTrackingPattern(fieldName));
    //    }
    
    //    private static Pattern fieldTrackingPattern(String fieldName) {
    //        return Pattern.compile("\\b" + Pattern.quote(fieldName) + "\\b", Pattern.CASE_INSENSITIVE);
    //    }
    
    public static AbstractType createType(RubyBasicClass klass) {
        return klass instanceof RubyClass ? new ClassType((RubyClass) klass) : new MetaClassType(
                (RubyMetaClass) klass);
    }
    
    public static RubyBasicClass unwrapType(Type type) {
        if (type instanceof ClassType)
            return ((ClassType) type).runtimeClass();
        if (type instanceof MetaClassType)
            return ((MetaClassType) type).runtimeMetaClass();
        return null;
    }
    
    public static boolean containsMetaclassWithName(TypeSet set, String name) {
        for (Type t : set.containedTypes()) {
            if (t instanceof MetaClassType) {
                MetaClassType metaClassType = (MetaClassType) t;
                if (metaClassType.describe().equalsIgnoreCase(name))
                    return true;
            }
            
        }
        return false;
    }
    
    public static void findSourceModules(IParent element, List<ISourceModule> list) {
        if (element instanceof ISourceModule) {
            list.add((ISourceModule) element);
            return;
        }
        IModelElement[] children;
        try {
            children = element.getChildren();
            for (IModelElement e : children)
                if (e instanceof IParent) {
                    findSourceModules((IParent) e, list);
                }
        } catch (ModelException e1) {
            e1.printStackTrace();
        }
    }
    
    //    public static IModelElement[] dtlClassToIType(RubyClass klass) {
    //        List<IModelElement> result = new ArrayList<IModelElement>();
    //        Collection<RubyClassDefinition> defs = klass.getDefinitions();
    //        for (RubyClassDefinition def : defs) {
    //            if (def instanceof RubySourceClassDefinition) {
    //                RubySourceClassDefinition def2 = (RubySourceClassDefinition) def;
    //                DtlProcedureCall callNode = def2.getCallNode();
    //                org.eclipse.dltk.core.ISourceModule file = def2.fileScope().file();
    //                int offset = callNode.sourceStart();
    //                int length = callNode.sourceEnd() - callNode.sourceStart() + 1;
    //                FakeClass fakeMethod = new FakeClass((ModelElement) file, klass.name(), offset, length);
    //                result.add(fakeMethod);
    //            }
    //        }
    //        return result.toArray(new IModelElement[result.size()]);
    //    }
    
}
