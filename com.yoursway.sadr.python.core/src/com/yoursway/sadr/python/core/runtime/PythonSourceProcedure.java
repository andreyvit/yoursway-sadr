package com.yoursway.sadr.python.core.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.python.parser.ast.PythonArgument;

import com.yoursway.sadr.python.core.runtime.PythonCallableArgument.Usage;
import com.yoursway.sadr.python.core.runtime.contributions.NodeBoundItem;
import com.yoursway.sadr.python.core.typeinferencing.constructs.MethodDeclarationC;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.model.Context;

public class PythonSourceProcedure extends PythonProcedure implements NodeBoundItem, LocalVariableContainer {
    
    private final Collection<PythonScopedVariable> localVariables = new ArrayList<PythonScopedVariable>();
    
    private final Map<String, PythonScopedVariable> namesToLocalVariables = new HashMap<String, PythonScopedVariable>();
    
    private final MethodDeclarationC construct;
    
    public PythonSourceProcedure(Context context, MethodDeclarationC construct) {
        super(context.model(), construct.node().getName(), createArguments(construct.node()));
        this.construct = construct;
        context.add(this);
    }
    
    private static PythonCallableArgument[] createArguments(MethodDeclaration node) {
        List<PythonCallableArgument> args = new ArrayList<PythonCallableArgument>();
        for (PythonArgument argument : PythonUtils.argumentsOf(node)) {
            String name = argument.getName();
            Usage usage;
            //            if ("...".equals(name))
            //                usage = RubyArgument.Usage.ELLIPSIS;
            if (argument.getInitialization() != null)
                usage = PythonCallableArgument.Usage.OPTIONAL;
            else
                usage = PythonCallableArgument.Usage.REQUIRED;
            args.add(new PythonCallableArgument(name, usage));
        }
        PythonCallableArgument[] a = args.toArray(new PythonCallableArgument[args.size()]);
        return a;
    }
    
    public ASTNode node() {
        return construct.node();
    }
    
    public void addLocalVariable(PythonScopedVariable localVariable) {
        localVariables.add(localVariable);
        namesToLocalVariables.put(localVariable.name().toLowerCase(), localVariable);
    }
    
    public PythonVariable findLocalVariable(String name) {
        return namesToLocalVariables.get(name.toLowerCase());
    }
    
    public LocalScope parentScope() {
        return construct.methodScope();
    }
    
    public boolean isBuiltin() {
        return false;
    }
    
    public PythonConstruct construct() {
        return construct;
    }
    
}
