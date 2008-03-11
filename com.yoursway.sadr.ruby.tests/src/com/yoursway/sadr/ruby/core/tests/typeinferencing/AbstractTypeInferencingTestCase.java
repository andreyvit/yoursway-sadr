package com.yoursway.sadr.ruby.core.tests.typeinferencing;

import static com.yoursway.sadr.engine.util.Strings.join;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.ruby.internal.parsers.jruby.ASTUtils;

import com.yoursway.sadr.engine.AnalysisEngine;
import com.yoursway.sadr.engine.InfoKind;
import com.yoursway.sadr.engine.util.Strings;
import com.yoursway.sadr.ruby.core.runtime.RubyUtils;
import com.yoursway.sadr.ruby.core.runtime.RubyVariable;
import com.yoursway.sadr.ruby.core.runtime.WholeProjectRuntime;
import com.yoursway.sadr.ruby.core.runtime.requestors.methods.AnyMethodRequestor;
import com.yoursway.sadr.ruby.core.runtime.requestors.methods.MethodNamesRequestor;
import com.yoursway.sadr.ruby.core.tests.AbstractTestCase;
import com.yoursway.sadr.ruby.core.tests.TestUtils;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ExpressionValueInfoGoal;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.Goals;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ValueInfoGoal;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.FileScope;
import com.yoursway.sadr.ruby.core.typeinferencing.scopes.Scope;

public abstract class AbstractTypeInferencingTestCase extends AbstractTestCase {
    
    protected void runTest() throws Exception {
        WholeProjectRuntime projectRuntime = createProjectRuntime(getClass());
        
        AnalysisEngine engine = projectRuntime.getEngine();
        
        StringBuilder expected = new StringBuilder();
        StringBuilder actual = new StringBuilder();
        
        for (ISourceModule sourceModule : projectRuntime.getSourceModules()) {
            String headline = "==== " + sourceModule.getElementName() + " ====\n";
            expected.append(headline);
            actual.append(headline);
            checkFile(sourceModule, projectRuntime, engine, expected, actual);
        }
        
        Assert.assertEquals(expected.toString(), actual.toString());
    }
    
    private void checkFile(ISourceModule sourceModule, WholeProjectRuntime projectRuntime,
            AnalysisEngine engine, StringBuilder expected, StringBuilder actual) throws ModelException,
            Exception {
        Collection<IAssertion> assertions = new ArrayList<IAssertion>();
        
        String content = sourceModule.getSource();
        String[] lines = content.split("\n");
        int lineOffset = 0;
        for (int i = 0; i < lines.length; i++) {
            String originalLine = lines[i];
            String line = originalLine.trim();
            int delimiterPos = line.indexOf("##");
            if (delimiterPos >= 0) {
                StringTokenizer tok = new StringTokenizer(line.substring(delimiterPos + 2));
                String test = tok.nextToken();
                IAssertion assertion;
                if (test.equals("not-cached") || test.equals("cached")) {
                    boolean shouldBeCached = test.equals("cached");
                    test = tok.nextToken();
                    IAssertion ass2 = parseAssertion(tok, test, line, originalLine, lineOffset, delimiterPos);
                    assertion = new CachedAssertion(ass2, shouldBeCached);
                } else {
                    assertion = parseAssertion(tok, test, line, originalLine, lineOffset, delimiterPos);
                }
                assertions.add(assertion);
            }
            lineOffset += originalLine.length() + 1;
        }
        
        if (assertions.size() == 0)
            return;
        
        ModuleDeclaration rootNode = projectRuntime.getASTFor(sourceModule);
        FileScope scope = projectRuntime.getScopeFor(sourceModule);
        for (IAssertion assertion : assertions)
            assertion.check(scope, rootNode, sourceModule, engine, expected, actual);
    }
    
    private IAssertion parseAssertion(StringTokenizer tok, String test, String line, String originalLine,
            int lineOffset, int delimiterPos) {
        IAssertion assertion;
        if ("expr".equals(test)) {
            String expr = tok.nextToken();
            int namePos = locate(expr, line, originalLine, delimiterPos, lineOffset);
            needToken(tok, "=>");
            String correctClassRef = tok.nextToken();
            assertion = new ExpressionTypeAssertion(expr, namePos, correctClassRef);
        } else if ("value".equals(test)) {
            String expr = tok.nextToken();
            int namePos = locate(expr, line, originalLine, delimiterPos, lineOffset);
            needToken(tok, "=>");
            String correctClassRef = tok.nextToken();
            assertion = new ExpressionValueAssertion(expr, namePos, correctClassRef);
        } else if ("localvar-type".equals(test)) {
            String expr = tok.nextToken();
            int namePos = locate(expr, line, originalLine, delimiterPos, lineOffset);
            assertion = new LocalVarTypeAssertion(expr, namePos);
        } else if ("has-method".equals(test)) {
            String expr = tok.nextToken();
            int namePos = locate(expr, line, originalLine, delimiterPos, lineOffset);
            String methodName = tok.nextToken();
            assertion = new HasMethodAssertion(expr, namePos, methodName);
        } else {
            throw new IllegalArgumentException("Unknown assertion: " + test);
        }
        return assertion;
    }
    
    private void needToken(StringTokenizer tok, String token) {
        String arrow = tok.nextToken();
        assertTrue(arrow.equals(token));
    }
    
    private int locate(String expr, String line, String originalLine, int delimiterPos, int lineOffset) {
        Pattern pat = Pattern.compile("(?<!\\w)" + Pattern.quote(expr) + "(?!\\w)");
        Matcher matcher = pat.matcher(originalLine);
        assertTrue(matcher.find());
        int namePosInsideLine = matcher.start();
        assertTrue(matcher.find());
        
        // check that there are no more possible matches
        int secondPos = matcher.start();
        if (secondPos < delimiterPos)
            throw new IllegalArgumentException("Ambiguous match of " + expr + " in " + line);
        
        int namePos = namePosInsideLine + lineOffset;
        return namePos;
    }
    
    protected static void assertCorrectString0(String fileName, Object object) throws IOException {
        String content = TestUtils.readFile(fileName);
        assertEquals(content.trim(), object.toString().trim());
    }
    
    public interface IAssertion {
        
        void check(FileScope scope, ModuleDeclaration rootNode, ISourceModule cu, AnalysisEngine engine,
                StringBuilder expected, StringBuilder actual) throws Exception;
        
        ValueInfoGoal createGoal(FileScope fileScope, ModuleDeclaration rootNode);
        
    }
    
    class ExpressionTypeAssertion implements IAssertion {
        
        private final String correctClassRef;
        
        private final String expression;
        
        private final int namePos;
        
        public ExpressionTypeAssertion(String expression, int namePos, String correctClassRef) {
            this.expression = expression;
            this.namePos = namePos;
            this.correctClassRef = correctClassRef;
        }
        
        public void check(FileScope fileScope, ModuleDeclaration rootNode, ISourceModule cu,
                AnalysisEngine engine, StringBuilder expected, StringBuilder actual) throws Exception {
            ExpressionValueInfoGoal goal = createGoal(fileScope, rootNode);
            engine.evaluate(goal);
            ValueInfo result = goal.weakResult();
            String[] possibleTypes = result.describePossibleTypes();
            Arrays.sort(possibleTypes, Strings.getNaturalComparator());
            String types = Strings.join(possibleTypes, ",");
            String prefix = expression + " : ";
            expected.append(prefix).append(correctClassRef).append('\n');
            actual.append(prefix).append(types).append('\n');
        }
        
        public ExpressionValueInfoGoal createGoal(FileScope fileScope, ModuleDeclaration rootNode) {
            ASTNode node = ASTUtils.findMinimalNode(rootNode, namePos, namePos);
            assertNotNull(node);
            Scope scope = RubyUtils.restoreScope(fileScope, node);
            return new ExpressionValueInfoGoal(scope, node, InfoKind.TYPE);
        }
        
    }
    
    class LocalVarTypeAssertion implements IAssertion {
        
        private final int namePos;
        
        public LocalVarTypeAssertion(String expression, int namePos) {
            this.namePos = namePos;
        }
        
        public void check(FileScope fileScope, ModuleDeclaration rootNode, ISourceModule cu,
                AnalysisEngine engine, StringBuilder expected, StringBuilder actual) throws Exception {
            throw new UnsupportedOperationException();
        }
        
        public ValueInfoGoal createGoal(FileScope fileScope, ModuleDeclaration rootNode) {
            ASTNode node = ASTUtils.findMinimalNode(rootNode, namePos, namePos);
            Scope scope = RubyUtils.restoreScope(fileScope, node);
            if (!(node instanceof SimpleReference))
                throw new IllegalArgumentException();
            RubyVariable variable = scope.variableLookup().findVariable(((SimpleReference) node).getName());
            return Goals.createVariableTypeGoal(variable, InfoKind.TYPE, scope);
        }
        
    }
    
    class ExpressionValueAssertion implements IAssertion {
        
        private final String correctClassRef;
        
        private final String expression;
        
        private final int namePos;
        
        public ExpressionValueAssertion(String expression, int namePos, String ClassRecorrectClassReff) {
            this.expression = expression;
            this.namePos = namePos;
            this.correctClassRef = ClassRecorrectClassReff;
        }
        
        public void check(FileScope fileScope, ModuleDeclaration rootNode, ISourceModule cu,
                AnalysisEngine engine, StringBuilder expected, StringBuilder actual) throws Exception {
            ExpressionValueInfoGoal goal = createGoal(fileScope, rootNode);
            engine.evaluate(goal);
            ValueInfo result = goal.weakResult();
            String[] possibleValues = result.describePossibleValues();
            Arrays.sort(possibleValues, Strings.getNaturalComparator());
            String values = Strings.join(possibleValues, ",");
            if (values.length() == 0)
                values = "none";
            String prefix = expression + " : ";
            expected.append(prefix).append(correctClassRef).append('\n');
            actual.append(prefix).append(values).append('\n');
        }
        
        public ExpressionValueInfoGoal createGoal(FileScope fileScope, ModuleDeclaration rootNode) {
            ASTNode node = ASTUtils.findMinimalNode(rootNode, namePos, namePos);
            assertNotNull(node);
            Scope scope = RubyUtils.restoreScope(fileScope, node);
            return new ExpressionValueInfoGoal(scope, node, InfoKind.VALUE);
        }
        
    }
    
    class HasMethodAssertion implements IAssertion {
        
        private final String methodName;
        
        private final String expression;
        
        private final int namePos;
        
        public HasMethodAssertion(String expression, int namePos, String methodName) {
            this.expression = expression;
            this.namePos = namePos;
            this.methodName = methodName;
        }
        
        public void check(FileScope fileScope, ModuleDeclaration rootNode, ISourceModule cu,
                AnalysisEngine engine, StringBuilder expected, StringBuilder actual) throws Exception {
            ExpressionValueInfoGoal goal = createGoal(fileScope, rootNode);
            engine.evaluate(goal);
            ValueInfo result = goal.weakResult();
            AnyMethodRequestor requestor = new AnyMethodRequestor();
            result.findMethod(methodName, requestor);
            
            MethodNamesRequestor namesRequestor = new MethodNamesRequestor();
            result.findMethodsByPrefix("", namesRequestor);
            
            String prefix = expression + "." + methodName + "() : ";
            expected.append(prefix).append("yes").append('\n');
            actual.append(prefix).append(
                    requestor.anythingFound() ? "yes" : "no, has: " + join(namesRequestor.asArray(), ", "))
                    .append('\n');
        }
        
        public ExpressionValueInfoGoal createGoal(FileScope fileScope, ModuleDeclaration rootNode) {
            ASTNode node = ASTUtils.findMinimalNode(rootNode, namePos, namePos);
            assertNotNull(node);
            Scope scope = RubyUtils.restoreScope(fileScope, node);
            return new ExpressionValueInfoGoal(scope, node, InfoKind.VALUE);
        }
        
    }
    
    class CachedAssertion implements IAssertion {
        
        private final IAssertion assertion;
        private final boolean shouldBeCached;
        
        public CachedAssertion(IAssertion assertion, boolean shouldBeCached) {
            this.assertion = assertion;
            this.shouldBeCached = shouldBeCached;
        }
        
        public void check(FileScope fileScope, ModuleDeclaration rootNode, ISourceModule cu,
                AnalysisEngine engine, StringBuilder expected, StringBuilder actual) throws Exception {
            ValueInfoGoal goal = assertion.createGoal(fileScope, rootNode);
            boolean isCached = engine.isCached(goal);
            
            String prefix = goal.toString() + " : ";
            expected.append(prefix).append(cachedString(shouldBeCached)).append('\n');
            actual.append(prefix).append(cachedString(isCached)).append('\n');
            
        }
        
        private String cachedString(boolean cached) {
            return cached ? "CACHED" : "NOT CACHED";
        }
        
        public ValueInfoGoal createGoal(FileScope fileScope, ModuleDeclaration rootNode) {
            return null;
        }
        
    }
    
}
