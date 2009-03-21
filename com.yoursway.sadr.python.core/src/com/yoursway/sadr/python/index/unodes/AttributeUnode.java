package com.yoursway.sadr.python.index.unodes;

import static com.yoursway.sadr.python.constructs.PythonAnalHelpers.queryIndexForValuesAssignedTo;
import static java.lang.String.format;

import java.util.List;
import java.util.Set;

import kilim.pausable;

import com.yoursway.sadr.python.constructs.PythonAnalHelpers;
import com.yoursway.sadr.python.constructs.PythonScope;
import com.yoursway.sadr.python.constructs.PythonStaticContext;
import com.yoursway.sadr.python.index.punodes.AttributePunode;
import com.yoursway.sadr.python.index.punodes.HeadPunode;
import com.yoursway.sadr.python.index.punodes.Punode;
import com.yoursway.sadr.python_v2.croco.PythonDynamicContext;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;

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
    
    @Override
    @pausable
    public PythonValueSet calculateValue(PythonStaticContext sc, PythonDynamicContext dc,
            List<PythonScope> scopes) {
        // foo.bar
        // a) foo.bar = x
        //    boz.bar = x
        //    ...
        // b) 1) foo -> Instance(Foo) 
        //    2) Foo.bar = y 
        //    3) <-- bind(y, foo)
        PythonValueSet result = calculateValueFromTypeAttribute(sc, dc, scopes);
        PythonValueSet r2 = calculateValueFromAssignments(sc, dc, scopes);
        return PythonValueSet.merge(result, r2);
    }
    
    @pausable
    private PythonValueSet calculateValueFromTypeAttribute(PythonStaticContext sc, PythonDynamicContext dc,
            List<PythonScope> scopes) {
        PythonValueSet foo = receiver.calculateValue(sc, dc, scopes);
        PythonValueSet result = foo.getAttrFromType(name, sc, dc, scopes);
        return result;
    }
    
    @pausable
    private PythonValueSet calculateValueFromAssignments(PythonStaticContext sc, PythonDynamicContext dc,
            List<PythonScope> scopes) {
        Set<Unode> aliases = PythonAnalHelpers.computeAliases(this, scopes, sc);
        return queryIndexForValuesAssignedTo(aliases, sc, dc, scopes);
    }
    
}
