package com.yoursway.sadr.blocks.swamp.tests;

import static com.google.common.collect.Lists.newArrayList;
import static com.yoursway.easymock.additions.Variable.anyObjectSavingInto;
import static com.yoursway.easymock.additions.Variable.create;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.easymock.IAnswer;
import org.junit.Before;
import org.junit.Test;

import com.yoursway.easymock.additions.Variable;
import com.yoursway.sadr.blocks.swamp.effects.Effect;
import com.yoursway.sadr.blocks.swamp.formulas.Formula;
import com.yoursway.sadr.blocks.swamp.tests.mocks.frogs.DotAccessF;
import com.yoursway.sadr.blocks.swamp.tests.mocks.frogs.LiteralF;
import com.yoursway.sadr.blocks.swamp.tests.mocks.frogs.VariableF;


public class FlowAnalysisSwampTests {
    
    private HypotheticalEquivalenceCalculator ec;

    interface HypotheticalEquivalenceCalculator {

        Collection<Alias> findAliasesAt(Formula subject, Crocodile position);
        
    }
    
    static class Alias {
        
        private final CrocodileRange range;
        private final Formula name;

        public Alias(CrocodileRange range, Formula name) {
            if (range == null)
                throw new NullPointerException("range is null");
            if (name == null)
                throw new NullPointerException("name is null");
            this.range = range;
            this.name = name;
        }

        public CrocodileRange getRange() {
            return range;
        }

        public Formula getName() {
            return name;
        }
        
    }
    
    static class MockEquivalenceCalculator implements HypotheticalEquivalenceCalculator {

        @Override
        public Collection<Alias> findAliasesAt(Formula subject, Crocodile position) {
            Collection<Alias> result = newArrayList();
            return result;
        }
        
    }
    
//    static class MockEffectsProvider implement
    
    interface CrocodileRange {
        
        void visitEffects(EffectVisitor visitor);
        
    }
    
    interface Crocodile {
        
    }
    
    static class MockCrocodile implements Crocodile {
        
    }
    
    @Before
    public void setup() throws Exception {
        ec = createMock(HypotheticalEquivalenceCalculator.class);
    }
    
    @Test
    public void foo() throws Exception {
        Crocodile position = createMock(Crocodile.class);
        Formula subject = new VariableF("x");
        
        CrocodileRange range = createMock(CrocodileRange.class);
        
        final Crocodile fakeCroco = createMock(Crocodile.class);
        
        final Variable<EffectVisitor> visitorVar = create(EffectVisitor.class);
        range.visitEffects(anyObjectSavingInto(visitorVar));
        expectLastCall().andAnswer(new IAnswer<Object>() {

            @Override
            public Object answer() throws Throwable {
                EffectVisitor visitor = visitorVar.extractValue();
                
                visitor.visit(new AssignmentEffect(new DotAccessF(new VariableF("y"), "foo"),
                        new LiteralF(42)), fakeCroco);
                return null;
            }
            
        });
        
        Alias alias = new Alias(range, new VariableF("y"));
        
        ec.findAliasesAt(subject, position);
        expectLastCall().andReturn(newArrayList(alias));
        
        replay(ec, position, range, fakeCroco);
        
        Collection<String> attributes = findAttributes(position, subject);
        verify(ec, position, range, fakeCroco);
        
        assertEquals("[foo]", attributes.toString());
    }
    
    interface EffectVisitor {
        
        void visit(Effect effect, Crocodile position);
        
    }
    
    public Collection<String> findAttributes(Crocodile position, Formula subject) throws Exception {
        Collection<String> result = newArrayList();
        Collection<Alias> aliases = ec.findAliasesAt(subject, position);
        for (Alias alias : aliases)
            findAttributesIn(alias.getRange(), alias.getName(), result);
        return result;
    }
    
    static class AttributeCollectingEffectVisitor implements EffectVisitor {
        
        private final Collection<String> result;
        private final Formula name;

        public AttributeCollectingEffectVisitor(Collection<String> result, Formula name) {
            if (result == null)
                throw new NullPointerException("result is null");
            if (name == null)
                throw new NullPointerException("name is null");
            this.result = result;
            this.name = name;
        }

        @Override
        public void visit(Effect effect, Crocodile position) {
            if (effect instanceof AssignmentEffect) {
                AssignmentEffect ae = (AssignmentEffect) effect;
                Formula lhs = ae.lhs();
                if (lhs instanceof DotAccessF) {
                    DotAccessF da = (DotAccessF) lhs;
                    Formula receiver = da.getReceiver();
                    if (receiver.equals(name))
                        resultFound(da.getFieldName());
                }
            }
        }

        private void resultFound(String fieldName) {
            result.add(fieldName);
        }
        
    }

    private void findAttributesIn(CrocodileRange range, Formula name, Collection<String> result) {
        range.visitEffects(new AttributeCollectingEffectVisitor(result, name));
    }
    
}
