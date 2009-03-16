package com.yoursway.sadr.python.index.unodes;

import static java.lang.String.format;

import com.yoursway.sadr.python.index.punodes.AttributePunode;
import com.yoursway.sadr.python.index.punodes.HeadPunode;
import com.yoursway.sadr.python.index.punodes.Punode;

public class AttributeUnode extends Unode {
    
    private final Unode receiver;
    private final String name;
    
    public AttributeUnode(Unode receiver, String name) {
        if (receiver == null)
            throw new NullPointerException("receiver is null");
        if (name == null)
            throw new NullPointerException("name is null");
        this.receiver = receiver;
        this.name = name;
        this.hashCode = computeHashCode();
    }
    
    @Override
    public String toString() {
        return format("Field(%s, %s)", receiver.toString(), name);
    }
    
    @Override
    public int computeHashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((receiver == null) ? 0 : receiver.hashCode());
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
        AttributeUnode other = (AttributeUnode) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (receiver == null) {
            if (other.receiver != null)
                return false;
        } else if (!receiver.equals(other.receiver))
            return false;
        return true;
    }
    
    @Override
    public Punode punodize() {
        return new AttributePunode(new HeadPunode(receiver), name);
    }
}
