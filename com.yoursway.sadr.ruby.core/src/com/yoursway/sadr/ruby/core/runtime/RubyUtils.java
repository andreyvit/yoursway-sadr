package com.yoursway.sadr.ruby.core.runtime;

import static java.util.Collections.emptyList;

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
import org.eclipse.dltk.ruby.ast.RubyClassDeclaration;
import org.eclipse.dltk.ruby.ast.RubyColonExpression;
import org.eclipse.dltk.ruby.ast.RubySelfReference;

import com.yoursway.sadr.ruby.core.typeinferencing.keys.wildcards.ArrayWildcard;
import com.yoursway.sadr.ruby.core.typeinferencing.keys.wildcards.StarWildcard;
import com.yoursway.sadr.ruby.core.typeinferencing.keys.wildcards.Wildcard;
import com.yoursway.sadr.ruby.core.typeinferencing.services.ClassLookup;
import com.yoursway.sadr.ruby.core.typeinferencing.types.AbstractType;
import com.yoursway.sadr.ruby.core.typeinferencing.types.ArrayType;
import com.yoursway.sadr.ruby.core.typeinferencing.types.ClassType;
import com.yoursway.sadr.ruby.core.typeinferencing.types.MetaClassType;
import com.yoursway.sadr.ruby.core.typeinferencing.types.StubType;
import com.yoursway.sadr.ruby.core.typeinferencing.types.Type;
import com.yoursway.sadr.ruby.core.typeinferencing.typesets.TypeSet;
import com.yoursway.sadr.ruby.core.typeinferencing.typesets.TypeSetBuilder;

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
    
    @SuppressWarnings("unchecked")
    public static List<ASTNode> childrenOf(ASTNode node) {
        if (node == null)
            return emptyList();
        return node.getChilds();
    }
    
    @SuppressWarnings("unchecked")
    public static String superclassName(RubyClassDeclaration node) {
        List superClassNames = node.getSuperClassNames();
        if (superClassNames.size() != 1)
            return null;
        return (String) superClassNames.iterator().next();
    }
    
}
