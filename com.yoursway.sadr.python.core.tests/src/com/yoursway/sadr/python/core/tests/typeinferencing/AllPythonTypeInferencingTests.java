package com.yoursway.sadr.python.core.tests.typeinferencing;import org.junit.runner.RunWith;import org.junit.runners.Suite;@RunWith(Suite.class)@Suite.SuiteClasses( { SameNamedVariableTests.class, FindVarTests.class, ScopingTests.class,        VariablesTests.class, ArrayHandlingTests.class, MiscValueInferencingTests.class,        MiscTypeInferencingTests.class, ContainerTests.class, AcceptanceTestsIteration2.class,        ForLoopTests.class, ControlFlowTests.class, FlowTests.class, CacheTests.class, InitTests.class,        ParametersPassing.class, BuiltInTypes.class, TruethValuesTests.class, UnaryOperators.class,        AssignmentOperators.class, BinaryOperators.class, CallableObjectsTests.class, ImportTests.class,        ContainerEmulation.class, LambdaExpressionTests.class, DecoratorsTests.class, TypeConversion.class,        ValueCalculationTests.class, ArgumentTests.class, AttributeAccessTests.class,        DynamicFeaturesTests.class, ContextManagerTests.class, DescriptorsTests.class, EvalTests.class,        MethodResolutionOrder.class, DocStringTests.class, HardcoreTests.class, BackPropagationTests.class, })public class AllPythonTypeInferencingTests {}