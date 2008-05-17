package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python.core.typeinferencing.constructs.Frog;

public class FieldReadF extends Frog {
    
    private final Frog receiver;
    private final String field;
    
    public FieldReadF(Frog receiver, String field) {
        if (receiver == null)
            throw new NullPointerException("receiver is null");
        if (field == null)
            throw new NullPointerException("field is null");
        this.receiver = receiver;
        this.field = field;
    }
    
    public Frog getReceiver() {
        return receiver;
    }
    
    public String getField() {
        return field;
    }
    
    @Override
    public String toString() {
        return receiver + "." + getClass().getSimpleName() + "(" + field + ")";
    }
    
    @Override
    public Frog replace(Frog lhs, Frog rhs) {
        if (lhs.equals(this))
            return rhs;
        return new FieldReadF(receiver.replace(lhs, rhs), field);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((field == null) ? 0 : field.hashCode());
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
        final FieldReadF other = (FieldReadF) obj;
        if (field == null) {
            if (other.field != null)
                return false;
        } else if (!field.equals(other.field))
            return false;
        if (receiver == null) {
            if (other.receiver != null)
                return false;
        } else if (!receiver.equals(other.receiver))
            return false;
        return true;
    }
    
}
