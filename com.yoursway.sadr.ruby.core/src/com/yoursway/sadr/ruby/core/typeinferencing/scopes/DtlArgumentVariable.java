package com.yoursway.sadr.ruby.core.typeinferencing.scopes;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.ruby.core.runtime.Callable;
import com.yoursway.sadr.ruby.core.runtime.RubyArgument;
import com.yoursway.sadr.ruby.core.runtime.RubyVariable;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo;

public class DtlArgumentVariable extends RubyVariable {
    
    private final ValueInfo valueInfo;
    private final RubyArgument argument;
    private final Callable callable;
    
    public DtlArgumentVariable(Callable callable, RubyArgument argument, ValueInfo valueInfo) {
        this.callable = callable;
        this.argument = argument;
        this.valueInfo = valueInfo;
    }
    
    @Override
    public String name() {
        return argument.name();
    }
    
    public ValueInfo valueInfo() {
        return valueInfo;
    }
    
    public Callable callable() {
        return callable;
    }
    
    public int index() {
        return argument.index();
    }
    
    @Override
    public String toString() {
        return argument.toString();
    }
    
    @Override
    public ASTNode[] getNodesForSelection() {
        return new ASTNode[] { callable.construct().node() };
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((argument == null) ? 0 : argument.hashCode());
        result = prime * result + ((callable == null) ? 0 : callable.hashCode());
        result = prime * result + ((valueInfo == null) ? 0 : valueInfo.hashCode());
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
        final DtlArgumentVariable other = (DtlArgumentVariable) obj;
        if (argument == null) {
            if (other.argument != null)
                return false;
        } else if (!argument.equals(other.argument))
            return false;
        if (callable == null) {
            if (other.callable != null)
                return false;
        } else if (!callable.equals(other.callable))
            return false;
        if (valueInfo == null) {
            if (other.valueInfo != null)
                return false;
        } else if (!valueInfo.equals(other.valueInfo))
            return false;
        return true;
    }
    
}
