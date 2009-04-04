package com.yoursway.sadr.python.core.tests.typeinferencing;

import static com.yoursway.sadr.python.core.tests.TestingUtils.callerOutside;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.ast.ASTNode;
import org.junit.After;

import com.google.common.collect.Lists;
import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.blocks.foundation.values.Value;
import com.yoursway.sadr.engine.AnalysisEngine;
import com.yoursway.sadr.engine.util.Strings;
import com.yoursway.sadr.python.ASTUtils;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.goals.ExpressionValueGoal;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.constructs.ast.PythonFileC;
import com.yoursway.sadr.python.analysis.lang.constructs.ast.VariableReferenceC;
import com.yoursway.sadr.python.analysis.objectmodel.values.PythonValue;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;
import com.yoursway.sadr.python.analysis.project.FileSourceUnit;
import com.yoursway.sadr.python.analysis.project.ProjectRuntime;
import com.yoursway.sadr.python.analysis.project.ProjectUnit;
import com.yoursway.sadr.python.core.tests.Activator;
import com.yoursway.sadr.python.core.tests.internal.FileUtil;
import com.yoursway.sadr.python.core.tests.internal.StringInputStream;
import com.yoursway.utils.YsFileUtils;

public abstract class AbstractTypeInferencingTestCase {
    
    protected IProject testProject;
    
    private File createProject(String projectName, File projectLocation) throws Exception {
        final IWorkspace workspace = ResourcesPlugin.getWorkspace();
        testProject = workspace.getRoot().getProject(projectName);
        if (testProject.exists())
            testProject.delete(false, true, null);
        IProjectDescription description = workspace.newProjectDescription(testProject.getName());
        //        URL projectLocation = DtlTestsPlugin.getDefault().getBundle().getEntry("test_projects/" + projectName);
        //        if (projectLocation == null)
        //            throw new AssertionError("Test project not found.");
        //        URL resolvedProjectLocation = Platform.resolve(projectLocation);
        IPath tempPath = Activator.getDefault().getStateLocation().append("testprojects").append(projectName);
        File tempDirectory = tempPath.toFile();
        FileUtil.recursiveDelete(tempDirectory);
        recursiveCopy(projectLocation, tempDirectory);
        //      recursiveCopy(new File(resolvedProjectLocation.getPath()), tempDirectory);
        description.setLocation(tempPath);
        testProject.create(description, null);
        testProject.open(null);
        return tempDirectory;
    }
    
    @After
    public void tearDown() throws Exception {
        if (testProject != null) {
            testProject.delete(true, true, null);
        }
    }
    
    public ProjectRuntime createProjectRuntime(File tempDirectory) {
        Collection<File> files = new ArrayList<File>();
        findAllFiles(tempDirectory, files);
        return new ProjectRuntime(new ProjectUnit(findSourceModules(tempDirectory, files)));
    }
    
    void findAllFiles(File folder, Collection<File> result) {
        File[] children = folder.listFiles();
        if (children != null)
            for (File child : children) {
                if (YsFileUtils.isBogusFile(child.getName()))
                    continue;
                if (child.isFile())
                    result.add(child);
                else
                    findAllFiles(child, result);
            }
    }
    
    private Collection<FileSourceUnit> findSourceModules(File project, Collection<File> files) {
        Collection<FileSourceUnit> sourceUnits = Lists.newArrayListWithCapacity(files.size());
        String root = project.getAbsolutePath();
        if (!root.endsWith("/"))
            root += "/";
        for (File file : files)
            if (file.getName().toLowerCase().endsWith(".py")) {
                String path = file.getAbsolutePath();
                path = path.substring(0, path.length() - 3).replace(root, "").replace("/", ".");
                sourceUnits.add(new FileSourceUnit(file, path));
            }
        return sourceUnits;
    }
    
    protected void runTest() throws Exception {
        String methodName = callerOutside(AbstractTypeInferencingTestCase.class);
        String className = getClass().getSimpleName();
        String basePath = "/tests/" + className + "/" + methodName;
        
        URL projectLocation = Activator.getDefault().getBundle().getEntry(basePath);
        if (projectLocation == null)
            throw new AssertionError("Test project not found.");
        URL resolvedProjectLocation = FileLocator.resolve(projectLocation);
        File file = new File(resolvedProjectLocation.getPath());
        File rootDir = createProject("test", file);
        
        ProjectRuntime projectRuntime = createProjectRuntime(rootDir);
        
        AnalysisEngine engine = projectRuntime.getEngine();
        
        StringBuilder expected = new StringBuilder();
        StringBuilder actual = new StringBuilder();
        
        System.out.println("Running test " + basePath);
        
        for (FileSourceUnit sourceModule : projectRuntime.getModules()) {
            String headline = "==== " + sourceModule.getPathName() + " ====\n";
            expected.append(headline);
            actual.append(headline);
            checkFile(sourceModule, projectRuntime, engine, expected, actual);
        }
        
        Assert.assertEquals("Items should match:", expected.toString(), actual.toString());
    }
    
    private void checkFile(FileSourceUnit sourceModule, ProjectRuntime projectRuntime, AnalysisEngine engine,
            StringBuilder expected, StringBuilder actual) throws Exception {
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
                assertion = parseAssertion(tok, test, line, originalLine, lineOffset, delimiterPos);
                if (assertion != null)
                    assertions.add(assertion);
            }
            lineOffset += originalLine.length() + 1;
        }
        
        if (assertions.size() == 0)
            return;
        PythonFileC fileC = projectRuntime.getModule(sourceModule);
        for (IAssertion assertion : assertions)
            assertion.check(engine, fileC, sourceModule, expected, actual);
    }
    
    private IAssertion parseAssertion(StringTokenizer tok, String test, String line, String originalLine,
            int lineOffset, int delimiterPos) {
        IAssertion assertion = null;
        if (test.equals("not-cached") || test.equals("cached")) {
            //            boolean shouldBeCached = test.equals("cached");
            test = tok.nextToken();
            //            IAssertion ass2 = parseAssertion(tok, test, line, originalLine, lineOffset, delimiterPos);
            //            assertion = new CachedAssertion(ass2, shouldBeCached);
        } else if ("expr".equals(test)) {
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
            while (tok.hasMoreTokens()) {
                correctClassRef += " " + tok.nextToken();
            }
            
            assertion = new ExpressionValueAssertion(expr, namePos, correctClassRef);
            //        } else if ("localvar-type".equals(test)) {
            //            String expr = tok.nextToken();
            //            int namePos = locate(expr, line, originalLine, delimiterPos, lineOffset);
            //            assertion = new LocalVarTypeAssertion(expr, namePos);
            //        } else if ("swamp-test".equals(test)) { // not used yet
            //            String expr = tok.nextToken();
            //            int namePos = locate(expr, line, originalLine, delimiterPos, lineOffset);
            //            needToken(tok, "=>");
            //            String correctClassRef = tok.nextToken();
            //            while (tok.hasMoreTokens()) {
            //                correctClassRef += " " + tok.nextToken();
            //            }
            //            assertion = new SwampTestAssertion(expr, namePos, correctClassRef);
        } else if ("has-method".equals(test)) {
            //            String expr = tok.nextToken();
            //            int namePos = locate(expr, line, originalLine, delimiterPos, lineOffset);
            //            String methodName = tok.nextToken();
            //            assertion = new HasMethodAssertion(expr, namePos, methodName);
        } else if ("find-var".equals(test)) {
            String expr = tok.nextToken();
            int namePos = locate(expr, line, originalLine, delimiterPos, lineOffset);
            int lineNumber = Integer.valueOf(tok.nextToken()).intValue();
            assertion = new FindVariableAssertion(lineNumber, namePos);
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
        if (!matcher.find()) {
            throw new IllegalArgumentException("Incorrect test: can't find " + expr);
            //            assertTrue(matcher.find());
        }
        
        // check that there are no more possible matches
        int secondPos = matcher.start();
        if (secondPos < delimiterPos)
            throw new IllegalArgumentException("Ambiguous match of " + expr + " in " + line);
        
        int namePos = namePosInsideLine + lineOffset;
        return namePos;
    }
    
    protected static void assertCorrectString0(String fileName, Object object) throws IOException {
        String content = readFile(fileName);
        assertEquals(content.trim(), object.toString().trim());
    }
    
    protected static String joinPath(String c1, String c2) {
        IPath path = new Path(c1).append(c2);
        return path.toPortableString();
    }
    
    protected static String joinPath(String c1, String c2, String c3) {
        return joinPath(joinPath(c1, c2), c3);
    }
    
    protected static String readFile(final String fileName) throws IOException {
        InputStream in = Activator.openResource(fileName);
        return readAndClose(in);
    }
    
    public static String readAndClose(InputStream in) throws IOException {
        try {
            StringBuffer result = new StringBuffer();
            InputStreamReader reader = new InputStreamReader(in);
            char[] buf = new char[1024];
            while (true) {
                int read = reader.read(buf);
                if (read <= 0)
                    break;
                result.append(buf, 0, read);
            }
            return result.toString();
        } finally {
            in.close();
        }
    }
    
    private static void recursiveCopy(File source, File destination) throws IOException {
        if (source.isDirectory()) {
            destination.mkdirs();
            File[] files = source.listFiles();
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                recursiveCopy(file, new File(destination, file.getName()));
            }
        } else {
            copyFile(source, destination);
        }
    }
    
    private static void copyFile(File source, File destination) throws IOException {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(source);
            out = new FileOutputStream(destination);
            copyStream(in, out);
        } finally {
            if (in != null)
                in.close();
            if (out != null)
                out.close();
        }
    }
    
    private static void copyStream(InputStream in, OutputStream out) throws IOException {
        byte[] buf = new byte[10240];
        int size = in.read(buf);
        while (size > 0) {
            out.write(buf, 0, size);
            size = in.read(buf);
        }
    }
    
    protected static String removeExtension(final String fileName) {
        return new Path(fileName).removeFileExtension().lastSegment();
    }
    
    protected static void replaceContents(IFile oldFile, IFile newFile) throws IOException, CoreException {
        String oldName = removeExtension(oldFile.getName());
        String newName = removeExtension(newFile.getName());
        String original = readAndClose(newFile.getContents());
        String fixed = original.replaceAll("class " + newName, "class " + oldName);
        oldFile.setContents(new StringInputStream(fixed), true, false, null);
    }
    
    public interface IAssertion {
        
        void check(AnalysisEngine engine, PythonFileC fileC, FileSourceUnit sourceModule,
                StringBuilder expected, StringBuilder actual) throws Exception;
    }
    
    public ExpressionValueGoal createSelfGoal(PythonFileC fileC, final PythonConstruct construct) {
        //        if (construct instanceof MethodDeclarationC && construct.staticContext() instanceof ClassDeclarationC) {
        //            final MethodDeclarationC func = (MethodDeclarationC) construct.staticContext();
        //            ClassDeclarationC classC = (ClassDeclarationC) construct.parentScope();
        //            PythonValueSet klasses = classC.evaluate(PythonDynamicContext.EMPTY);
        //            for (PythonValue klass : klasses) {
        //                PythonValueSet results = CallResolver.callFunction(klass, new RuntimeArguments(),
        //                        PythonDynamicContext.EMPTY, classC);
        //                for (PythonValue result : results) {
        //                    List<PythonArgument> args = func.getArguments();
        //                    PythonDynamicContext context;
        //                    try {
        //                        context = new PythonDynamicContext(PythonDynamicContext.EMPTY, func, new ContextImpl(
        //                                args, new RuntimeArguments(result)));
        //                        return construct.evaluate(context);
        //                    } catch (PythonException e) {
        //                        e.printStackTrace();
        //                        return new PythonValueSet();
        //                    }
        //                }
        //            }
        //            throw new IllegalStateException("No method found!");
        //        } else {
        return new ExpressionValueGoal(construct, PythonDynamicContext.EMPTY);
        //        }
    }
    
    public ExpressionValueGoal createGoal(PythonFileC fileC, int namePos) {
        ASTNode node = ASTUtils.findNodeAt(fileC.node(), namePos);
        assertNotNull(node);
        PythonConstruct construct = fileC.subconstructFor(node);
        return createSelfGoal(fileC, construct);
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
        
        public void check(AnalysisEngine engine, PythonFileC fileC, FileSourceUnit cu,
                StringBuilder expected, StringBuilder actual) throws Exception {
            ExpressionValueGoal goal = createGoal(fileC, namePos);
            ValueInfo result = engine.evaluate(goal).getResult();
            String[] possibleTypes = result.describePossibleTypes();
            Arrays.sort(possibleTypes, Strings.getNaturalComparator());
            String types = Strings.join(possibleTypes, ",");
            String prefix = expression + " : ";
            expected.append(prefix).append(correctClassRef).append('\n');
            actual.append(prefix).append(types).append('\n');
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
        
        public void check(AnalysisEngine engine, PythonFileC fileC, FileSourceUnit cu,
                StringBuilder expected, StringBuilder actual) throws Exception {
            ExpressionValueGoal goal = createGoal(fileC, namePos);
            ValueInfo result = engine.evaluate(goal).getResult();
            String[] possibleValues = result.describePossibleValues();
            Arrays.sort(possibleValues, Strings.getNaturalComparator());
            String values = Strings.join(possibleValues, ",");
            if (values.length() == 0)
                values = "(none)";
            String prefix = expression + " : ";
            expected.append(prefix).append(correctClassRef).append('\n');
            actual.append(prefix).append(values).append('\n');
        }
        
    }
    
    //    class SwampTestAssertion implements IAssertion {
    //        
    //        private final String correctClassRef;
    //        
    //        private final String expression;
    //        
    //        private final int namePos;
    //        
    //        public SwampTestAssertion(String expression, int namePos, String ClassRecorrectClassReff) {
    //            this.expression = expression;
    //            this.namePos = namePos;
    //            this.correctClassRef = ClassRecorrectClassReff;
    //        }
    //        
    //        public void check(PythonFileC fileC, FileSourceUnit cu, Engine engine, StringBuilder expected,
    //                StringBuilder actual) throws Exception {
    //            PythonValueSetAcceptor acceptor = new PythonValueSetAcceptor(Krocodile.EMPTY) {
    //                @Override
    //                public <T> void checkpoint(IGrade<T> grade) {
    //                    System.out.println("Done");
    //                    //do nothing;
    //                }
    //                
    //                @Override
    //                protected <T> void acceptIndividualResult(PythonObject result, IGrade<T> grade) {
    //                    // TODO Auto-generated method stub
    //                    
    //                }
    //            };
    //            IGoal goal = createGoal(fileC, acceptor);
    //            engine.run(goal);
    //            ValueInfo result = acceptor.getResult();
    //            String[] possibleValues = result.describePossibleValues();
    //            Arrays.sort(possibleValues, Strings.getNaturalComparator());
    //            String values = Strings.join(possibleValues, ",");
    //            if (values.length() == 0)
    //                values = "(none)";
    //            String prefix = expression + " : ";
    //            expected.append(prefix).append(correctClassRef).append('\n');
    //            actual.append(prefix).append(values).append('\n');
    //        }
    //        
    //        public IGoal createGoal(PythonFileC fileC, PythonValueSetAcceptor acceptor) {
    //            ASTNode node = ASTUtils.findNodeAt(fileC.node(), namePos);
    //            assertNotNull(node);
    //            //            PythonConstruct construct = fileC.subconstructFor(node);
    //            return new CreateSwampGoal(null, acceptor);//FIXME?
    //        }
    //    }
    
    //    class HasMethodAssertion implements IAssertion {
    //        
    //        private final String methodName;
    //        
    //        private final String expression;
    //        
    //        private final int namePos;
    //        
    //        public HasMethodAssertion(String expression, int namePos, String methodName) {
    //            this.expression = expression;
    //            this.namePos = namePos;
    //            this.methodName = methodName;
    //        }
    //        
    //        public void check(PythonFileC fileC, ISourceModule cu, Engine engine, StringBuilder expected,
    //                StringBuilder actual) throws Exception {
    //            ExpressionValueInfoGoal goal = createGoal(fileC);
    //            engine.run(goal);
    //            ValueInfo result = goal.roughResult();
    //            AnyMethodRequestor requestor = new AnyMethodRequestor();
    //            ValueInfoUtils.findMethod(result, methodName, requestor);
    //            
    //            MethodNamesRequestor namesRequestor = new MethodNamesRequestor();
    //            ValueInfoUtils.findMethodsByPrefix(result, "", namesRequestor);
    //            
    //            String prefix = expression + "." + methodName + "() : ";
    //            expected.append(prefix).append("yes").append('\n');
    //            actual.append(prefix).append(
    //                    requestor.anythingFound() ? "yes" : "no, has: " + join(namesRequestor.asArray(), ", "))
    //                    .append('\n');
    //        }
    //        
    //        public IGoal createGoal(PythonFileC fileC) {
    //            //            ASTNode node = ASTUtils.findMinimalNode(fileC.node(), namePos, namePos);
    //            //            assertNotNull(node);
    //            //            PythonConstruct construct = fileC.subconstructFor(node);
    //            return null;
    //            //return new ExpressionValueInfoGoal(construct, new EmptyDynamicContext(), InfoKind.VALUE);
    //        }
    //        
    //    }
    
    //    class CachedAssertion implements IAssertion {
    //        
    //        private final IAssertion assertion;
    //        private final boolean shouldBeCached;
    //        
    //        public CachedAssertion(IAssertion assertion, boolean shouldBeCached) {
    //            this.assertion = assertion;
    //            this.shouldBeCached = shouldBeCached;
    //        }
    //        
    //        public void check(PythonFileC fileC, ISourceModule cu, Engine engine, StringBuilder expected,
    //                StringBuilder actual) throws Exception {
    //            ValueInfoGoal goal = assertion.createGoal(fileC);
    //            boolean isCached = engine.isCached(goal);
    //            
    //            String prefix = goal.toString() + " : ";
    //            expected.append(prefix).append(cachedString(shouldBeCached)).append('\n');
    //            actual.append(prefix).append(cachedString(isCached)).append('\n');
    //            
    //        }
    //        
    //        private String cachedString(boolean cached) {
    //            return cached ? "CACHED" : "NOT CACHED";
    //        }
    //        
    //        public IGoal createGoal(PythonFileC fileC) {
    //            return null;
    //        }
    //        
    //    }
    
    private static int getLine(FileSourceUnit module, ASTNode node) {
        String source;
        try {
            source = module.getSource();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        String[] lines = source.split("\n");
        int line = 0;
        int chars = 0;
        for (String string : lines) {
            line++;
            chars += string.length() + 1;
            if (chars >= node.sourceEnd())
                return line;
        }
        return 0; // not found
    }
    
    class FindVariableAssertion implements IAssertion {
        private final int line;
        private final int namePos;
        
        public FindVariableAssertion(int line, int namePos) {
            this.line = line;
            this.namePos = namePos;
        }
        
        public void check(AnalysisEngine engine, PythonFileC fileC, FileSourceUnit cu,
                StringBuilder expected, StringBuilder actual) throws Exception {
            ExpressionValueGoal goal = createGoal(fileC);
            PythonValueSet values = engine.evaluate(goal);
            int actualLine = -2;
            String prefix = "";
            if (!values.isEmpty()) {
                ValueInfo result = values.getResult();
                if (result != null) {
                    Collection<Value> vals = result.getValueSet().containedValues();
                    PythonConstruct decl = ((PythonValue) vals.iterator().next()).getDecl();//FIXME I am Dirty Hack! Fix me, please.
                    actualLine = getLine(cu, decl.node());
                }
                
                prefix = values.toString() + " : ";
            }
            expected.append(prefix).append(String.valueOf(line)).append('\n');
            actual.append(prefix).append(String.valueOf(actualLine)).append('\n');
            
        }
        
        public ExpressionValueGoal createGoal(PythonFileC fileC) {
            ASTNode node = ASTUtils.findMinimalNode(fileC.node(), namePos, namePos);
            assertNotNull(node);
            PythonConstruct construct = fileC.subconstructFor(node);
            if (construct instanceof VariableReferenceC) {
                return new ExpressionValueGoal(construct, PythonDynamicContext.EMPTY);
            } else {
                throw new IllegalStateException("Should be VariableReferenceC, but found "
                        + construct.getClass().getSimpleName());
            }
        }
    }
    
}
