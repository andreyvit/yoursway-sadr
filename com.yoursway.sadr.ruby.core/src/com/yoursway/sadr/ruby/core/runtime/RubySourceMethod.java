package com.yoursway.sadr.ruby.core.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ruby.ast.RubyMethodArgument;

import com.yoursway.sadr.ruby.core.runtime.RubyArgument.Usage;
import com.yoursway.sadr.ruby.core.runtime.contributions.Context;
import com.yoursway.sadr.ruby.core.runtime.contributions.NodeBoundItem;
import com.yoursway.sadr.ruby.core.typeinferencing.engine.Construct;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.MethodScope;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.Scope;

public class RubySourceMethod extends RubyMethod implements NodeBoundItem, LocalVariableContainer {
    
    private final MethodDeclaration node;
    
    private final MethodScope scope;
    
    private final Collection<RubyLocalVariable> localVariables = new ArrayList<RubyLocalVariable>();
    
    private final Map<String, RubyLocalVariable> namesToLocalVariables = new HashMap<String, RubyLocalVariable>();
    
    public RubySourceMethod(RubyBasicClass klass, Context context,
            Construct<Scope, MethodDeclaration> construct) {
        super(klass, construct.node().getName(), createArguments(construct.node()));
        this.node = construct.node();
        this.scope = new MethodScope(construct.scope(), this, node);
        context.add(this);
    }
    
    private static RubyArgument[] createArguments(MethodDeclaration node) {
        List<RubyArgument> args = new ArrayList<RubyArgument>();
        for (RubyMethodArgument argument : (List<RubyMethodArgument>) node.getArguments()) {
            String name = argument.getName();
            Usage usage;
            //            if ("...".equals(name))
            //                usage = RubyArgument.Usage.ELLIPSIS;
            if (argument.getInitialization() != null)
                usage = RubyArgument.Usage.OPTIONAL;
            else
                usage = RubyArgument.Usage.REQUIRED;
            args.add(new RubyArgument(name, usage));
        }
        RubyArgument[] a = args.toArray(new RubyArgument[args.size()]);
        return a;
    }
    
    public MethodDeclaration node() {
        return node;
    }
    
    public MethodScope scope() {
        return scope;
    }
    
    @Override
    public String[] parameterNames() {
        List<String> result = new ArrayList<String>();
        for (RubyMethodArgument argument : (List<RubyMethodArgument>) node.getArguments())
            result.add(argument.getName());
        return result.toArray(new String[result.size()]);
    }
    
    @Override
    public String name() {
        return node.getName();
    }
    
    public void addLocalVariable(RubyLocalVariable localVariable) {
        localVariables.add(localVariable);
        namesToLocalVariables.put(localVariable.name().toLowerCase(), localVariable);
    }
    
    public RubyVariable findLocalVariable(String name) {
        return namesToLocalVariables.get(name.toLowerCase());
    }
    
    public Construct<Scope, ASTNode> construct() {
        return new Construct<Scope, ASTNode>(scope, node);
    }
    
    public boolean isBuiltin() {
        return false;
    }
    
}
