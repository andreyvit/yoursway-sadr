package com.yoursway.sadr.blocks.swamp.tests;

import static com.google.common.base.Join.join;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Multimaps.newHashMultimap;
import static com.yoursway.utils.DebugOutputHelper.reflectionBasedToString;
import static com.yoursway.utils.DebugOutputHelper.simpleNameOf;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.collect.Multimap;
import com.yoursway.sadr.blocks.swamp.Swamp;
import com.yoursway.sadr.blocks.swamp.formulas.Formula;
import com.yoursway.sadr.blocks.swamp.old.Resultstream;

public class EquivalenceSwampTests {
    
    public static abstract class AbstractConstruct {
        
        public Formula describeValue() {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public String toString() {
            return reflectionBasedToString(this);
        }
        
    }
    
    public static class Position {
        
        private int target = -1;
        private final String label;
        
        public Position(String label) {
            this.label = label;
        }
        
        public Position(String label, int target) {
            this(label);
            resolve(target);
        }
        
        public void resolve(int target) {
            if (target < 0)
                throw new IllegalArgumentException("target is < 0");
            if (this.target >= 0)
                throw new IllegalStateException("already resolved");
            this.target = target;
        }
        
        public int getTarget() {
            if (target < 0)
                throw new IllegalStateException("not yet resolved");
            return target;
        }
        
        @Override
        public String toString() {
            return simpleNameOf(getClass()) + "('" + label + "', " + (target >= 0 ? "" + target : "?") + ")";
        }
        
    }
    
    public static class AssignmentC extends AbstractConstruct {
        
        private final AbstractConstruct lhs;
        private final AbstractConstruct rhs;
        
        public AssignmentC(AbstractConstruct lhs, AbstractConstruct rhs) {
            if (lhs == null)
                throw new NullPointerException("lhs is null");
            if (rhs == null)
                throw new NullPointerException("rhs is null");
            this.lhs = lhs;
            this.rhs = rhs;
        }
        
        @Override
        public Formula describeValue() {
            return rhs.describeValue();
        }
        
    }
    
    public static class IntegerLiteralC extends AbstractConstruct implements Formula {
        
        private final int value;
        
        public IntegerLiteralC(int value) {
            this.value = value;
        }
        
        @Override
        public Formula describeValue() {
            return this;
        }
        
    }
    
    public static class IfZeroC extends AbstractConstruct {
        
        private final AbstractConstruct thenClause;
        private final AbstractConstruct expression;
        
        public IfZeroC(AbstractConstruct expression, AbstractConstruct thenClause) {
            if (expression == null)
                throw new NullPointerException("expression is null");
            if (thenClause == null)
                throw new NullPointerException("thenClause is null");
            this.expression = expression;
            this.thenClause = thenClause;
        }
        
    }
    
    public static class VariableReferenceC extends AbstractConstruct {
        
        private final String name;
        
        public VariableReferenceC(String name) {
            if (name == null)
                throw new NullPointerException("name is null");
            this.name = name;
        }
        
    }
    
    public static class GoToC extends AbstractConstruct {
        
        private final Position target;
        
        public GoToC(Position target) {
            if (target == null)
                throw new NullPointerException("target is null");
            this.target = target;
        }
        
    }
    
    public static class BinaryOperationC extends AbstractConstruct {
        
        private final AbstractConstruct firstArgument;
        private final AbstractConstruct secondArgument;
        
        public BinaryOperationC(AbstractConstruct firstArgument, AbstractConstruct secondArgument) {
            if (firstArgument == null)
                throw new NullPointerException("firstArgument is null");
            if (secondArgument == null)
                throw new NullPointerException("secondArgument is null");
            this.firstArgument = firstArgument;
            this.secondArgument = secondArgument;
        }
        
    }
    
    public static class SubtractC extends BinaryOperationC {
        
        public SubtractC(AbstractConstruct firstArgument, AbstractConstruct secondArgument) {
            super(firstArgument, secondArgument);
        }
        
    }
    
    public static class PrintC extends AbstractConstruct {
        
        private final AbstractConstruct argument;
        
        public PrintC(AbstractConstruct argument) {
            if (argument == null)
                throw new NullPointerException("argument is null");
            this.argument = argument;
        }
        
    }
    
    public static class CompoundC extends AbstractConstruct {
        
        private final List<AbstractConstruct> children;
        
        public CompoundC(List<AbstractConstruct> children) {
            if (children == null)
                throw new NullPointerException("children is null");
            this.children = children;
        }
        
    }
    
    public static class ProgramBuilder {
        
        private List<AbstractConstruct> constructs = newArrayList();
        
        private Map<String, Integer> labels = newHashMap();
        
        private Multimap<String, Position> forwardReferences = newHashMultimap();
        
        public ProgramBuilder add(AbstractConstruct construct) {
            constructs.add(construct);
            return this;
        }
        
        public ProgramBuilder add(String label, AbstractConstruct construct) {
            if (labels.containsKey(label))
                throw new IllegalArgumentException("Label " + label + " has already been defined");
            add(construct);
            int index = constructs.size() - 1;
            labels.put(label, index);
            Collection<Position> forwardRefsToThisLabel = forwardReferences.removeAll(label);
            if (forwardRefsToThisLabel != null)
                for (Position position : forwardRefsToThisLabel)
                    position.resolve(index);
            return this;
        }
        
        public CompoundC build() {
            if (!forwardReferences.isEmpty())
                throw new IllegalStateException("Undefined labels: " + join(", ", forwardReferences.keySet()));
            return new CompoundC(constructs);
        }
        
        public Position resolve(String label) {
            Integer index = labels.get(label);
            if (index == null)
                return addForwardReference(label, new Position(label));
            else
                return new Position(label, index);
        }
        
        private Position addForwardReference(String label, Position position) {
            forwardReferences.put(label, position);
            return position;
        }
        
    }
    
    public static class MyAnalysisConfiguration {
        
        private Swamp swamp;
        
        public MyAnalysisConfiguration() {
            swamp = new Swamp();
        }
        
        public Swamp swamp() {
            return swamp;
        }
        
    }
    
    @Test
    public void foo() throws Exception {
        CompoundC program = buildProgram();
        MyAnalysisConfiguration configuration = new MyAnalysisConfiguration();
        Swamp swamp = configuration.swamp();
        // a.b = c
        // 1) aliasing a.b == c, aliasing a.__dict__['b'] = c
        // 2) a.__setattr__('b', c)
//        0 -> (2, 3, 4) -> 5 -> (1 | -2)
        
//        a.c = foo
//        a.setc(foo)
        // Aliasing Info
        // print x
        // 1) print(x)

//        Resultstream aliases = swamp.calculateAliasing((AbstractConstruct) null, new VariableReferenceC("x").describeValue());
    }
    
    private CompoundC buildProgram() {
        ProgramBuilder builder = new ProgramBuilder();
        builder.add(new AssignmentC(i(), new IntegerLiteralC(4)));
        builder.add("loop", new IfZeroC(i(), new GoToC(builder.resolve("endloop"))));
        builder.add(new PrintC(i()));
        builder.add(new AssignmentC(i(), new SubtractC(i(), new IntegerLiteralC(1))));
        builder.add(new GoToC(builder.resolve("loop")));
        builder.add("endloop", new PrintC(i()));
        return builder.build();
    }
    
    public int foo(int a) {
        // 0 -> value -> (1 | -2) 
        return a * 40;
        // value(foo) = arg0 * 40
    }
    
    private VariableReferenceC i() {
        return new VariableReferenceC("i");
    }
    
}
