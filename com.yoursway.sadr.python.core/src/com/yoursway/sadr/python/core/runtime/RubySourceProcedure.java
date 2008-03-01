package com.yoursway.sadr.python.core.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ruby.ast.RubyMethodArgument;

import com.yoursway.sadr.python.core.runtime.RubyArgument.Usage;
import com.yoursway.sadr.python.core.runtime.contributions.Context;
import com.yoursway.sadr.python.core.runtime.contributions.NodeBoundItem;
import com.yoursway.sadr.python.core.typeinferencing.engine.Construct;
import com.yoursway.sadr.python.core.typeinferencing.scopes.LocalScope;
import com.yoursway.sadr.python.core.typeinferencing.scopes.ProcedureScope;
import com.yoursway.sadr.python.core.typeinferencing.scopes.Scope;

public class RubySourceProcedure extends RubyProcedure implements NodeBoundItem, LocalVariableContainer {
    
    private final MethodDeclaration node;
    
    private final Collection<RubyLocalVariable> localVariables = new ArrayList<RubyLocalVariable>();
    
    private final Map<String, RubyLocalVariable> namesToLocalVariables = new HashMap<String, RubyLocalVariable>();
    
    private final ProcedureScope scope;
    
    public RubySourceProcedure(Context context, Construct<Scope, MethodDeclaration> construct) {
        super(context.model(), construct.node().getName(), createArguments(construct.node()));
        this.node = construct.node();
        this.scope = new ProcedureScope(construct.scope(), this, node);
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
    
    public ASTNode node() {
        return node;
    }
    
    public void addLocalVariable(RubyLocalVariable localVariable) {
        localVariables.add(localVariable);
        namesToLocalVariables.put(localVariable.name().toLowerCase(), localVariable);
    }
    
    public RubyVariable findLocalVariable(String name) {
        return namesToLocalVariables.get(name.toLowerCase());
    }
    
    public LocalScope scope() {
        return scope;
    }
    
    public Construct<Scope, ASTNode> construct() {
        return new Construct<Scope, ASTNode>(scope, node);
    }
    
    public boolean isBuiltin() {
        return false;
    }
    
}
