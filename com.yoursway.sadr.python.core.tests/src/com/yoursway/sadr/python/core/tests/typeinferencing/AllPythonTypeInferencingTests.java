package com.yoursway.sadr.python.core.tests.typeinferencing;import org.junit.runner.RunWith;import org.junit.runners.Suite;@RunWith(Suite.class)@Suite.SuiteClasses( { MiscTypeInferencingTests.class, ScopingTests.class, ArgumentTests.class,        VariablesTests.class, ArrayHandlingTests.class, MiscValueInferencingTests.class, EvalTests.class,        ForLoopTests.class, ControlFlowTests.class, CacheTests.class, BackPropagationTests.class,        HardcoreTests.class, FlowTests.class, TruethValuesTests.class, InitTests.class,        BinaryOperators.class, UnaryOperators.class, AssignmentOperators.class, TypeConversion.class,        CallableObjectsTests.class, ImportTests.class, AttributeAccessTests.class, ContainerEmulation.class,        LambdaExpressionTests.class, DecoratorsTests.class, ParametersPassing.class, BuiltInTypes.class,        DynamicFeaturesTests.class, ContextManagerTests.class, DescriptorsTests.class,        MethodResolutionOrder.class, DocStringTests.class, FindVarTests.class })public class AllPythonTypeInferencingTests {}