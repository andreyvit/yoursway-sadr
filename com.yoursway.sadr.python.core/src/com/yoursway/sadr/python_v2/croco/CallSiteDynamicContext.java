package com.yoursway.sadr.python_v2.croco;

public class CallSiteDynamicContext extends ChildDynamicContext {
    
    private final Arguments arguments;
    private final int hashCode;
    
    public CallSiteDynamicContext(PythonDynamicContext parent, Arguments arguments) {
        super(parent);
        if (arguments == null)
            throw new NullPointerException("arguments is null");
        this.arguments = arguments;
        this.hashCode = computeHashCode();
    }
    
    @Override
    public String shallowToString() {
        return arguments.toString();
    }
    
    @Override
    public Arguments argumentsOfTopCall() {
        return arguments;
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
        CallSiteDynamicContext other = (CallSiteDynamicContext) obj;
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
    
}
