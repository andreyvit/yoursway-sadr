package com.yoursway.sadr.python.analysis.context.dynamic;


import kilim.pausable;

import com.yoursway.sadr.python.analysis.aliasing.AliasConsumer;
import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.unodes.Suffix;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class BoundFunctionDynamicContextDecorator extends ChildDynamicContext {
    
    private final ArgumentsDecorator arguments;
    private final int hashCode;
    private final int callStackSize;
    
    public BoundFunctionDynamicContextDecorator(PythonDynamicContext dc, PythonValueSet self) {
        super(dc.unwind());
        this.arguments = new ArgumentsDecorator(dc.argumentsOfTopCall(), self);
        this.hashCode = computeHashCode();
        this.callStackSize = parent.callStackSize() + 1;
    }
    
    static class ArgumentsDecorator implements Arguments {
        
        private final Arguments delegate;
        private final PythonValueSet self;
        private final int hashCode;
        
        public ArgumentsDecorator(Arguments delegate, PythonValueSet self) {
            if (delegate == null)
                throw new NullPointerException("delegate is null");
            if (self == null)
                throw new NullPointerException("self is null");
            this.delegate = delegate;
            this.self = self;
            this.hashCode = computeHashCode();
        }
        
        @pausable
        public PythonValueSet computeArgument(PythonDynamicContext dc, int index, String name,
                PythonConstruct init) {
            if (index == 0)
                return self;
            return delegate.computeArgument(dc, index - 1, name, init);
        }
        
        @pausable
        public void findRenames(Suffix suffix, PythonStaticContext sc, PythonDynamicContext dc,
                AliasConsumer aliases, int index, String name, PythonConstruct init) {
            if (index == 0)
                return;
            delegate.findRenames(suffix, sc, dc, aliases, index - 1, name, init);
        }
        
        @Override
        public String toString() {
            return "(" + self + ") + " + delegate.toString();
        }
        
        @Override
        public int hashCode() {
            return hashCode;
        }
        
        private int computeHashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((delegate == null) ? 0 : delegate.hashCode());
            result = prime * result + ((self == null) ? 0 : self.hashCode());
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
            ArgumentsDecorator other = (ArgumentsDecorator) obj;
            if (delegate == null) {
                if (other.delegate != null)
                    return false;
            } else if (!delegate.equals(other.delegate))
                return false;
            if (self == null) {
                if (other.self != null)
                    return false;
            } else if (!self.equals(other.self))
                return false;
            return true;
        }
        
    }
    
    @Override
    public Arguments argumentsOfTopCall() {
        return arguments;
    }
    
    @Override
    protected String shallowToString() {
        return arguments.toString();
    }
    
    @Override
    public int hashCode() {
        return hashCode;
    }
    
    private int computeHashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((arguments == null) ? 0 : arguments.hashCode());
        result = prime * result + ((parent == null) ? 0 : parent.hashCode());
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
        BoundFunctionDynamicContextDecorator other = (BoundFunctionDynamicContextDecorator) obj;
        if (arguments == null) {
            if (other.arguments != null)
                return false;
        } else if (!arguments.equals(other.arguments))
            return false;
        if (parent == null) {
            if (other.parent != null)
                return false;
        } else if (!parent.equals(other.parent))
            return false;
        return true;
    }
    
    @Override
    public int callStackSize() {
        return callStackSize;
    }
    
}
