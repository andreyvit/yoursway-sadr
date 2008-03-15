package com.yoursway.sadr.python.core.runtime;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.ASTListNode;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IParent;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.python.parser.ast.PythonArgument;
import org.eclipse.dltk.python.parser.ast.PythonClassDeclaration;
import org.eclipse.dltk.python.parser.ast.expressions.ExtendedVariableReference;
import org.eclipse.dltk.python.parser.ast.expressions.PythonArrayAccessExpression;
import org.eclipse.dltk.python.parser.ast.expressions.PythonVariableAccessExpression;

import com.yoursway.sadr.python.core.typeinferencing.keys.wildcards.ArrayWildcard;
import com.yoursway.sadr.python.core.typeinferencing.keys.wildcards.StarWildcard;
import com.yoursway.sadr.python.core.typeinferencing.keys.wildcards.Wildcard;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;
import com.yoursway.sadr.python.core.typeinferencing.services.ClassLookup;
import com.yoursway.sadr.python.core.typeinferencing.types.AbstractType;
import com.yoursway.sadr.python.core.typeinferencing.types.ArrayType;
import com.yoursway.sadr.python.core.typeinferencing.types.ClassType;
import com.yoursway.sadr.python.core.typeinferencing.types.MetaClassType;
import com.yoursway.sadr.python.core.typeinferencing.types.StubType;
import com.yoursway.sadr.python.core.typeinferencing.types.Type;
import com.yoursway.sadr.python.core.typeinferencing.typesets.TypeSet;
import com.yoursway.sadr.python.core.typeinferencing.typesets.TypeSetBuilder;

public class PythonUtils {
    
    public static String superclassName(PythonClassDeclaration decl) {
        ASTListNode superClasses = decl.getSuperClasses();
        if (superClasses == null)
            return null;
        List<ASTNode> children = childrenOf(superClasses);
        if (children.isEmpty())
            return null;
        ASTNode child = children.get(0);
        return ((SimpleReference) child).getName();
    }
    
    @SuppressWarnings("unchecked")
    public static List<ASTNode> childrenOf(ASTNode node) {
        return node.getChilds();
    }
    
    public static Wildcard assignmentWildcardExpression(ASTNode node) {
        if (node instanceof VariableReference)
            return StarWildcard.INSTANCE;
        if (node instanceof PythonArrayAccessExpression) {
            PythonArrayAccessExpression expr = (PythonArrayAccessExpression) node;
            return new ArrayWildcard(assignmentWildcardExpression(expr.getArray()));
        }
        if (node instanceof PythonVariableAccessExpression) {
            PythonVariableAccessExpression expr = (PythonVariableAccessExpression) node;
            if (expr.getReceiver() instanceof VariableReference)
                if (((VariableReference) expr.getReceiver()).getName().equals("self"))
                    return StarWildcard.INSTANCE;
            // TODO: field access wildcard
            return assignmentWildcardExpression(expr.getReceiver());
        }
        throw new AssertionError("assignmentWildcardExpression: not symbol, dot or array");
    }
    
    public static ASTNode assignmentTerminalNode(ASTNode node) {
        if (node instanceof VariableReference)
            return node;
        if (node instanceof PythonArrayAccessExpression) {
            PythonArrayAccessExpression expr = (PythonArrayAccessExpression) node;
            return assignmentTerminalNode(expr.getArray());
        }
        if (node instanceof PythonVariableAccessExpression) {
            PythonVariableAccessExpression expr = (PythonVariableAccessExpression) node;
            if (expr.getReceiver() instanceof VariableReference)
                if (((VariableReference) expr.getReceiver()).getName().equals("self"))
                    return expr.variable();
            return assignmentTerminalNode(expr.getReceiver());
        }
        throw new AssertionError("assignmentTerminalNode: not symbol, dot or array: "
                + node.getClass().getSimpleName());
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
    public static PythonMetaClass resolveStaticClassReference(ClassLookup lookup, ASTNode receiver) {
        if (receiver instanceof SimpleReference)
            return resolveStaticClassReference(lookup, (SimpleReference) receiver);
        else
            System.out.println("resolveStaticClassReference: " + receiver);
        return null;
    }
    
    public static PythonMetaClass resolveStaticClassReference(ClassLookup lookup, SimpleReference receiver) {
        PythonClass klass = lookup.findClass(receiver.getName());
        return klass == null ? null : klass.metaClass();
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
    
    public static ASTNode[] argumentsOf(CallExpression n) {
        List<ASTNode> children0 = childrenOf(n.getArgs());
        List<ASTNode> children = new ArrayList<ASTNode>();
        for (ASTNode nd : children0) {
            children.add(nd);
        }
        return children.toArray(new ASTNode[children.size()]);
    }
    
    @SuppressWarnings("unchecked")
    public static List<PythonArgument> argumentsOf(MethodDeclaration node) {
        return node.getArguments();
    }
    
    //    public static StringLiteral trackField(ASTNode node, String fieldName) {
    //        return trackStaticString(node, fieldTrackingPattern(fieldName));
    //    }
    
    //    private static Pattern fieldTrackingPattern(String fieldName) {
    //        return Pattern.compile("\\b" + Pattern.quote(fieldName) + "\\b", Pattern.CASE_INSENSITIVE);
    //    }
    
    public static AbstractType createType(PythonBasicClass klass) {
        return klass instanceof PythonClass ? new ClassType((PythonClass) klass) : new MetaClassType(
                (PythonMetaClass) klass);
    }
    
    public static PythonBasicClass unwrapType(Type type) {
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
    
    @SuppressWarnings("unchecked")
    public static List<ASTNode> expressionsOf(ExtendedVariableReference evr) {
        return evr.getExpressions();
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
