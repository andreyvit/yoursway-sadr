package com.yoursway.sadr.python.analysis.lang.unodes.indexable;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import kilim.pausable;

import com.yoursway.sadr.engine.Analysis;
import com.yoursway.sadr.python.analysis.PythonAnalHelpers;
import com.yoursway.sadr.python.analysis.aliasing.AliasConsumer;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.index.data.AssignmentInfo;
import com.yoursway.sadr.python.analysis.index.queries.AttributeAssignmentsIndexQuery;
import com.yoursway.sadr.python.analysis.index.queries.AttributeAssignmentsRequestor;
import com.yoursway.sadr.python.analysis.lang.unodes.Bnode;
import com.yoursway.sadr.python.analysis.lang.unodes.ChainableSuffix;
import com.yoursway.sadr.python.analysis.lang.unodes.Suffix;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;

public class AttributeUnode extends AbstractIndexableUnode {
    
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
    
    public String getName() {
        return name;
    }
    
    public Unode getReceiver() {
        return receiver;
    }
    
    @Override
    public String toString() {
        return format("%s.%s", receiver.toString(), name);
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
    @pausable
    public void computeAliases(Suffix suffix, PythonLexicalContext sc, PythonDynamicContext dc,
            AliasConsumer aliases) {
        super.computeAliases(suffix, sc, dc, aliases);
        receiver.computeAliases(new AttributeSuffix(suffix, name), sc, dc, aliases);
    }
    
    @Override
    @pausable
    public PythonValueSet calculateValue(PythonLexicalContext sc, PythonDynamicContext dc) {
        // foo.bar
        // a) foo.bar = x
        //    boz.bar = x
        //    ...
        // b) 1) foo -> Instance(Foo) 
        //    2) Foo.bar = y 
        //    3) <-- bind(y, foo)
        // foo.bar.boz
        // 1) *.boz -> xx.boz = x
        // 2) check(xx == foo.bar)
        // 2.1) foo.bar
        // 2.1.1) *.bar -> zz.bar = z, pp.bar = p
        // 2.1.2) check(zz == foo)
        // 2.1.2.1) foo -> <Foo>
        // 2.1.2.2) zz -> <Foo>
        //     <- true
        // 2.1.3) check(pp == foo)
        // 2.1.3.1) foo -> <Foo>
        // 2.1.3.2) pp -> <Oof>
        //     <- false
        // 2.1.4) z -> <Bar>
        //    <-- foo.bar -> <Bar>
        // 2.2) xx -> <Bar>
        //  <- true: xx == foo.bar
        // 3) x -> 42
        // <- foo.bar.boz == 42
        System.out.println("AttributeUnode.calculateValue(" + this + ")");
        return PythonValueSet.merge(readFromTypeAndBind(sc, dc), trackAssignmentsAndRenames(sc, dc),
            findAllAssignmentsToAttributesOfSameNameAndCheckReceivers(sc, dc));
    }
    
    @pausable
    private PythonValueSet readFromTypeAndBind(PythonLexicalContext sc, PythonDynamicContext dc) {
        PythonValueSet foo = receiver.calculateValue(sc, dc);
        PythonValueSet result = foo.getAttrFromType(name, sc, dc);
        return result;
    }
    
    @pausable
    private PythonValueSet findAllAssignmentsToAttributesOfSameNameAndCheckReceivers(PythonLexicalContext sc,
            PythonDynamicContext dc) {
        Collection<Bnode> assignedValues = new ArrayList<Bnode>();
        final List<AssignmentInfo> assignments = new ArrayList<AssignmentInfo>();
        Analysis.queryIndex(new AttributeAssignmentsIndexQuery(name), new AttributeAssignmentsRequestor() {
            public void assignment(AssignmentInfo info) {
                assignments.add(info);
            }
        });
        PythonValueSet actualReceiverValue = receiver.calculateValue(sc, dc);
        for (AssignmentInfo info : assignments) {
            Bnode rhs = info.getRhs();
            PythonValueSet candidateReceiverValue = info.getReceiver().calculateValue(rhs.lc(), dc);
            if (candidateReceiverValue.canAlias(actualReceiverValue))
                assignedValues.add(rhs);
        }
        return PythonAnalHelpers.evaluateConstructs(assignedValues, dc);
    }
    
    @Override
    public VariableUnode leadingVariableUnode() {
        return receiver.leadingVariableUnode();
    }
    
    @Override
    public boolean isIndexable() {
        return receiver.isIndexable();
    }
    
    @Override
    public void addGenericVariationsTo(Collection<Unode> alternatives, Suffix suffix, boolean reading) {
        receiver.addGenericVariationsTo(alternatives, new AttributeSuffix(suffix, name), reading);
    }
    
    static class AttributeSuffix extends ChainableSuffix {
        
        private final String attrName;
        
        public AttributeSuffix(Suffix suffix, String attrName) {
            super(suffix);
            if (attrName == null)
                throw new NullPointerException("suffix is null");
            this.attrName = attrName;
        }
        
        @Override
        public String thisSuffixToString() {
            return "." + attrName;
        }
        
        @Override
        protected Unode appendThisSuffixTo(Unode replacementUnode) {
            return new AttributeUnode(replacementUnode, attrName);
        }
        
    }
    
}
