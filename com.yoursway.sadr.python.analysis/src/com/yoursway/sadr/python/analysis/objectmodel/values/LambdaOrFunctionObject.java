package com.yoursway.sadr.python.analysis.objectmodel.values;

import java.util.Collection;

import kilim.pausable;

import com.yoursway.sadr.python.analysis.PythonAnalHelpers;
import com.yoursway.sadr.python.analysis.aliasing.AliasConsumer;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.dynamic.arguments.DeclaredArguments;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.index.data.PassedArgumentInfo;
import com.yoursway.sadr.python.analysis.lang.unodes.Bnode;
import com.yoursway.sadr.python.analysis.lang.unodes.Suffix;
import com.yoursway.sadr.python.analysis.objectmodel.types.FunctionType;
import com.yoursway.sadr.python.analysis.objectmodel.types.PythonType;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSetBuilder;

public abstract class LambdaOrFunctionObject extends PythonValue implements CallableObject {
    
    private final DeclaredArguments arguments;
    protected final PythonLexicalContext lc;
    
    public LambdaOrFunctionObject(DeclaredArguments arguments, PythonLexicalContext lc) {
        if (arguments == null)
            throw new NullPointerException("arguments is null");
        if (lc == null)
            throw new NullPointerException("lc is null");
        this.arguments = arguments;
        this.lc = lc;
    }
    
    @Override
    @pausable
    public final void call(PythonDynamicContext dc, PythonValueSetBuilder builder) {
        PythonAnalHelpers.evaluateConstructs(findReturnedValues(), dc, builder);
    }
    
    @pausable
    protected abstract Collection<Bnode> findReturnedValues();
    
    @Override
    public final PythonType getType() {
        return FunctionType.instance;
    }
    
    @Override
    public final void bind(PythonValue self, PythonValueSetBuilder builder) {
        builder.addResult(new BoundFunctionObject(this, self));
    }
    
    @Override
    public final void computeArgumentAliases(PassedArgumentInfo info, PythonDynamicContext dc, Suffix suffix,
            AliasConsumer aliases) {
        info.computeAliases(arguments, lc, dc, suffix, aliases);
    }
    
    @Override
    @pausable
    public final void findRenames(Suffix suffix, PythonLexicalContext sc, PythonDynamicContext dc,
            AliasConsumer aliases) {
        Collection<Bnode> returnedValues = findReturnedValues();
        PythonAnalHelpers.addRenamesForConstructs(suffix, aliases, returnedValues, dc);
    }
}
