package com.yoursway.sadr.ruby.core.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.yoursway.sadr.ruby.core.tests.rewriting.AllRewritingTests;
import com.yoursway.sadr.ruby.core.tests.staticchecks.AllStaticCheckingTests;
import com.yoursway.sadr.ruby.core.tests.typeinferencing.AllTypeInferencingTests;

@RunWith(Suite.class)
@Suite.SuiteClasses( { AllRewritingTests.class, AllTypeInferencingTests.class, AllStaticCheckingTests.class })
public class AllTests {
    
}
