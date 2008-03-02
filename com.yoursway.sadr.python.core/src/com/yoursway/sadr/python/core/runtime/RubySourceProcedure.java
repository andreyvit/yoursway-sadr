package com.yoursway.sadr.python.core.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.python.parser.ast.PythonArgument;

import com.yoursway.sadr.python.core.runtime.RubyArgument.Usage;
import com.yoursway.sadr.python.core.runtime.contributions.Context;
import com.yoursway.sadr.python.core.runtime.contributions.NodeBoundItem;
import com.yoursway.sadr.python.core.typeinferencing.constructs.dtl.MethodDeclarationC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.dtl.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.scopes.LocalScope;

public class RubySourceProcedure extends RubyProcedure implements NodeBoundItem, LocalVariableContainer {
    
    private final Collection<RubyLocalVariable> localVariables = new ArrayList<RubyLocalVariable>();
    
    private final Map<String, RubyLocalVariable> namesToLocalVariables = new HashMap<String, RubyLocalVariable>();
    
    private final MethodDeclarationC construct;
    
    public RubySourceProcedure(Context context, MethodDeclarationC construct) {
        super(context.model(), construct.node().getName(), createArguments(construct.node()));
        this.construct = construct;
        context.add(this);
    }
    
    private static RubyArgument[] createArguments(MethodDeclaration node) {
        List<RubyArgument> args = new ArrayList<RubyArgument>();
        for (PythonArgument argument : RubyUtils.argumentsOf(node)) {
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
        return construct.node();
    }
    
    public void addLocalVariable(RubyLocalVariable localVariable) {
        localVariables.add(localVariable);
        namesToLocalVariables.put(localVariable.name().toLowerCase(), localVariable);
    }
    
    public RubyVariable findLocalVariable(String name) {
        return namesToLocalVariables.get(name.toLowerCase());
    }
    
    public LocalScope scope() {
        return construct.methodScope();
    }
    
    public boolean isBuiltin() {
        return false;
    }
    
    public PythonConstruct construct() {
        return construct;
    }
    
}
