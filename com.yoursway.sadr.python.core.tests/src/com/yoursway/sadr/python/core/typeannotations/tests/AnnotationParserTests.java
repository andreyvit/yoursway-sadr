package com.yoursway.sadr.python.core.typeannotations.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.yoursway.sadr.python.core.typeannotations.Annotation;
import com.yoursway.sadr.python.core.typeannotations.AnnotationParser;
import com.yoursway.sadr.python.core.typeannotations.AnnotationParsingException;
import com.yoursway.sadr.python.core.typeannotations.FunctionAnnotation;
import com.yoursway.sadr.python.core.typeannotations.TypeAnnotation;

public class AnnotationParserTests {
    @Test
    public void typeAnnotationTest() throws AnnotationParsingException {
        final String test = "type  Name from  Module  ";
        Annotation a = AnnotationParser.parseAnnotation(test);
        assertTrue(a instanceof TypeAnnotation);
        TypeAnnotation ta = (TypeAnnotation) a;
        assertTrue("Name".equals(ta.getName()));
        assertTrue("Module".equals(ta.getModuleName()));
    }
    
    @Test
    public void callableAnnotationSimpleTest() throws AnnotationParsingException {
        final String test = "callable () -> ReturnType";
        Annotation a = AnnotationParser.parseAnnotation(test);
        assertTrue(a instanceof FunctionAnnotation);
        FunctionAnnotation fa = (FunctionAnnotation) a;
        assertTrue(fa.getArgumentsTypes().size() == 0);
        assertTrue("ReturnType".equals(fa.getReturnType().getName()));
    }
    
    @Test
    public void callableAnnotationComplexTest() throws AnnotationParsingException {
        final String test = "callable (Type1,Type2|Type3 from module,Type4) -> ReturnType";
        Annotation a = AnnotationParser.parseAnnotation(test);
        assertTrue(a instanceof FunctionAnnotation);
        FunctionAnnotation fa = (FunctionAnnotation) a;
        
        List<Set<TypeAnnotation>> argTypeSets = fa.getArgumentsTypes();
        assertEquals(argTypeSets.size(), 3);
        assertEquals(argTypeSets.get(0).size(), 1);
        assertEquals(argTypeSets.get(1).size(), 2);
        assertEquals(argTypeSets.get(2).size(), 1);
        
        assertEquals(argTypeSets.get(0).iterator().next().getName(), "Type1");
        Iterator<TypeAnnotation> iter = argTypeSets.get(1).iterator();
        assertEquals(iter.next().getName(), "Type2");
        assertEquals(iter.next().getName(), "Type3");
        assertEquals(argTypeSets.get(2).iterator().next().getName(), "Type4");
        
        assertTrue("ReturnType".equals(fa.getReturnType().getName()));
    }
    
    @Test
    public void callableAnnotationSpecNamesTest() throws AnnotationParsingException {
        final String test = "callable (^,*,**) -> ReturnType";
        Annotation a = AnnotationParser.parseAnnotation(test);
        assertTrue(a instanceof FunctionAnnotation);
        FunctionAnnotation fa = (FunctionAnnotation) a;
        
        List<Set<TypeAnnotation>> argTypeSets = fa.getArgumentsTypes();
        
        assertEquals(argTypeSets.size(), 3);
        assertEquals(argTypeSets.get(0).size(), 1);
        assertEquals(argTypeSets.get(1).size(), 1);
        assertEquals(argTypeSets.get(2).size(), 1);
        
        assertEquals(argTypeSets.get(0).iterator().next().getName(), "^");
        assertEquals(argTypeSets.get(1).iterator().next().getName(), "*");
        assertEquals(argTypeSets.get(2).iterator().next().getName(), "**");
        
        assertTrue("ReturnType".equals(fa.getReturnType().getName()));
    }
}
