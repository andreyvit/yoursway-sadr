package com.yoursway.sadr.python.analysis.objectmodel.values;

import static com.yoursway.sadr.python.analysis.objectmodel.values.InstanceRegistrar.BUILTIN_INSTANCE_ID;

import java.util.Set;

import kilim.pausable;

import com.yoursway.sadr.blocks.foundation.values.AbstractValue;
import com.yoursway.sadr.python.analysis.aliasing.AliasConsumer;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.index.data.PassedArgumentInfo;
import com.yoursway.sadr.python.analysis.lang.unodes.Suffix;
import com.yoursway.sadr.python.analysis.objectmodel.types.PythonException;
import com.yoursway.sadr.python.analysis.objectmodel.types.PythonType;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSetBuilder;

public abstract class PythonValue extends AbstractValue {
    private final int id;
    
    public PythonValue() {
        this.id = BUILTIN_INSTANCE_ID;
    }
    
    public abstract PythonType getType();
    
    @Override
    public String describe() {
        return getType().describe() + " " + " instance #" + id;
    }
    
    public String name() {
        return null;
    }
    
    public boolean isInstance(PythonType type) {
        return getType().isInstance(type);
    }
    
    public PythonValue cast(PythonType type) throws PythonException {
        return type.coerce(this);
    }
    
    @pausable
    public void call(PythonDynamicContext dc, PythonValueSetBuilder builder) {
    }
    
    @pausable
    public void getAttrFromType(String name, PythonLexicalContext sc, PythonDynamicContext dc,
            PythonValueSetBuilder builder) {
        getType().getAttr(name, sc, dc).bind(this, builder);
    }
    
    public void bind(PythonValue self, PythonValueSetBuilder builder) {
        builder.addResult(this);
    }
    
    public void obtainIntegerValue(Set<Long> result) {
    }
    
    public void computeArgumentAliases(PassedArgumentInfo info, PythonDynamicContext dc, Suffix suffix,
            AliasConsumer aliases) {
    }
    
    @pausable
    public void findRenames(Suffix suffix, PythonLexicalContext sc, PythonDynamicContext dc,
            AliasConsumer aliases) {
    }
    
}
