package com.yoursway.sadr.python.analysis.index.data;

import com.yoursway.sadr.python.analysis.aliasing.Alias;
import com.yoursway.sadr.python.analysis.aliasing.AliasConsumer;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.dynamic.arguments.Argument;
import com.yoursway.sadr.python.analysis.context.dynamic.arguments.DeclaredArguments;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.lang.unodes.Bnode;
import com.yoursway.sadr.python.analysis.lang.unodes.Suffix;
import com.yoursway.sadr.python.analysis.lang.unodes.indexable.VariableUnode;

public final class PassedKeywordArgumentInfo extends PassedCallArgumentInfo {
    
    private final String name;
    
    public PassedKeywordArgumentInfo(Bnode callable, String name) {
        super(callable);
        this.name = name;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((callable == null) ? 0 : callable.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PassedKeywordArgumentInfo other = (PassedKeywordArgumentInfo) obj;
        if (callable == null) {
            if (other.callable != null)
                return false;
        } else if (!callable.equals(other.callable))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
    
    @Override
    public void computeAliases(DeclaredArguments declaredArguments, PythonLexicalContext lc,
            PythonDynamicContext dc, Suffix suffix, AliasConsumer aliases) {
        Argument arg = declaredArguments.findKeyword(name);
        if (arg != null)
            aliases.add(new Alias(suffix.appendTo(new VariableUnode(arg.getName())), lc, dc));
    }
    
    @Override
    public PassedArgumentInfo translateToUnbound() {
        return this;
    }
    
    @Override
    public String toString() {
        return "arg '" + name + "' of " + callable;
    }
    
}
