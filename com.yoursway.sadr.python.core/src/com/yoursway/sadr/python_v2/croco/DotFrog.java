package com.yoursway.sadr.python_v2.croco;

public class DotFrog extends Frog {
    protected final Frog head;
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((head == null) ? 0 : head.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        DotFrog other = (DotFrog) obj;
        if (head == null) {
            if (other.head != null)
                return false;
        } else if (!head.equals(other.head))
            return false;
        return true;
    }
    
    protected int length;
    
    public DotFrog(Frog head, String var, int id) {
        super(var, id);
        this.head = head;
        this.length = super.getLength() + 1;
    }
    
    @Override
    public int getLength() {
        return this.length;
    }
    
    @Override
    public String toString() {
        return head.toString() + "." + name();
    }
    
    public Frog head() {
        return head;
    }
}
