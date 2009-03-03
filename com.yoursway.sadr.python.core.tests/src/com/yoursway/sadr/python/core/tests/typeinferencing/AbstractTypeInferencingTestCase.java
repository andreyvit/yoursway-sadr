package com.yoursway.sadr.python.core.tests.typeinferencing;

import static com.yoursway.sadr.python.core.tests.TestingUtils.callerOutside;
import static com.yoursway.sadr.python_v2.constructs.PythonConstructFactory.NULL_CONSTRUCT;
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
import org.eclipse.dltk.python.parser.ast.PythonArgument;
import org.junit.After;

import com.google.common.collect.Lists;
import com.yoursway.ide.application.model.Project;
import com.yoursway.ide.application.model.application.ApplicationModel;
import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.blocks.foundation.values.Value;
import com.yoursway.sadr.engine.util.Strings;
import com.yoursway.sadr.python.ASTUtils;
import com.yoursway.sadr.python.core.runtime.FileSourceUnit;
import com.yoursway.sadr.python.core.runtime.ProjectRuntime;
import com.yoursway.sadr.python.core.runtime.ProjectUnit;
import com.yoursway.sadr.python.core.tests.Activator;
import com.yoursway.sadr.python.core.tests.internal.FileUtil;
import com.yoursway.sadr.python.core.tests.internal.StringInputStream;
import com.yoursway.sadr.python_v2.constructs.ClassDeclarationC;
import com.yoursway.sadr.python_v2.constructs.MethodDeclarationC;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.constructs.PythonFileC;
import com.yoursway.sadr.python_v2.constructs.VariableReferenceC;
import com.yoursway.sadr.python_v2.croco.Krocodile;
import com.yoursway.sadr.python_v2.goals.CreateInstanceGoal;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
import com.yoursway.sadr.python_v2.model.ContextImpl;
import com.yoursway.sadr.python_v2.model.PythonArguments;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.succeeder.Engine;
import com.yoursway.sadr.succeeder.IGoal;
import com.yoursway.sadr.succeeder.IGrade;

public abstract class AbstractTypeInferencingTestCase {
    
    protected IProject testProject;
    protected Project project;
    protected ApplicationModel app;
    
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
        app = new ApplicationModel(tempDirectory);
        this.project = new Project(app, new PythonProjectType(), tempDirectory);
        return new ProjectRuntime(new ProjectUnit(findSourceModules(project)));
    }
    
    private Collection<FileSourceUnit> findSourceModules(Project project) {
        Collection<File> files = project.findAllFiles();
        Collection<FileSourceUnit> sourceUnits = Lists.newArrayListWithCapacity(files.size());
        String root = project.getLocation().getAbsolutePath();
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
        
        Engine engine = projectRuntime.getEngine();
        
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
    
    private void checkFile(FileSourceUnit sourceModule, ProjectRuntime projectRuntime, Engine engine,
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
            assertion.check(fileC, sourceModule, engine, expected, actual);
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
    
    private Krocodile createSelfContext(final MethodDeclarationC func, RuntimeObject self) {
        ArrayList<PythonArgument> list = new ArrayList<PythonArgument>();
        Krocodile context = new Krocodile(Krocodile.EMPTY, func, new ContextImpl(list, new PythonArguments()));
        PythonArgument arg = (PythonArgument) func.node().getArguments().get(0);
        context.put(arg.getName(), self);
        return context;
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
        
        void check(PythonFileC fileC, FileSourceUnit sourceModule, Engine engine, StringBuilder expected,
                StringBuilder actual) throws Exception;
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
        
        public void check(PythonFileC fileC, FileSourceUnit cu, Engine engine, StringBuilder expected,
                StringBuilder actual) throws Exception {
            PythonValueSet acceptor = new PythonValueSet() {
                
                @Override
                protected <T> void acceptIndividualResult(RuntimeObject result, IGrade<T> grade) {
                    // do nothing
                }
            };
            engine.run(createGoal(engine, fileC, acceptor));
            ValueInfo result = acceptor.getResult();
            String[] possibleTypes = result.describePossibleTypes();
            Arrays.sort(possibleTypes, Strings.getNaturalComparator());
            String types = Strings.join(possibleTypes, ",");
            String prefix = expression + " : ";
            expected.append(prefix).append(correctClassRef).append('\n');
            actual.append(prefix).append(types).append('\n');
        }
        
        public IGoal createSelfGoal(PythonFileC fileC, final PythonValueSet acceptor,
                final PythonConstruct construct, final Engine engine) {
            if (construct.scope() instanceof MethodDeclarationC
                    && construct.parentScope() instanceof ClassDeclarationC) {
                final MethodDeclarationC func = (MethodDeclarationC) construct.scope();
                ClassDeclarationC classC = (ClassDeclarationC) construct.parentScope();
                return new CreateInstanceGoal(classC, NULL_CONSTRUCT, new PythonArguments(), Krocodile.EMPTY,
                        new PythonValueSet(acceptor) {
                            
                            @Override
                            protected <T> void acceptIndividualResult(RuntimeObject result, IGrade<T> grade) {
                                Krocodile context = createSelfContext(func, result);
                                IGoal goal = construct.createGoal(context);
                                engine.schedule(null, goal);
                            }
                        });
            } else {
                return construct.createGoal(Krocodile.EMPTY);
            }
        }
        
        public IGoal createGoal(Engine engine, PythonFileC fileC, PythonValueSet acceptor) {
            ASTNode node = ASTUtils.findNodeAt(fileC.node(), namePos);
            assertNotNull(node);
            PythonConstruct construct = fileC.subconstructFor(node);
            return createSelfGoal(fileC, acceptor, construct, engine);
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
        
        public void check(PythonFileC fileC, FileSourceUnit cu, Engine engine, StringBuilder expected,
                StringBuilder actual) throws Exception {
            PythonValueSet acceptor = new PythonValueSet() {
                @Override
                public <T> void checkpoint(IGrade<T> grade) {
                    System.out.println("Done");
                    //do nothing;
                }
                
                @Override
                protected <T> void acceptIndividualResult(RuntimeObject result, IGrade<T> grade) {
                }
            };
            IGoal goal = createGoal(engine, fileC, acceptor);
            engine.run(goal);
            ValueInfo result = acceptor.getResult();
            String[] possibleValues = result.describePossibleValues();
            Arrays.sort(possibleValues, Strings.getNaturalComparator());
            String values = Strings.join(possibleValues, ",");
            if (values.length() == 0)
                values = "(none)";
            String prefix = expression + " : ";
            expected.append(prefix).append(correctClassRef).append('\n');
            actual.append(prefix).append(values).append('\n');
        }
        
        public IGoal createSelfGoal(PythonFileC fileC, final PythonValueSet acceptor,
                final PythonConstruct construct, final Engine engine) {
            if (construct.scope() instanceof MethodDeclarationC
                    && construct.scope().parentScope() instanceof ClassDeclarationC) {
                final MethodDeclarationC func = (MethodDeclarationC) construct.scope();
                ClassDeclarationC classC = (ClassDeclarationC) construct.scope().parentScope();
                return new CreateInstanceGoal(classC, NULL_CONSTRUCT, new PythonArguments(), Krocodile.EMPTY,
                        new PythonValueSet() {
                            @Override
                            protected <T> void acceptIndividualResult(RuntimeObject result, IGrade<T> grade) {
                                Krocodile context = createSelfContext(func, result);
                                IGoal goal = construct.createGoal(context);
                                engine.schedule(null, goal);
                            }
                        });
            } else {
                return construct.createGoal(Krocodile.EMPTY);
            }
        }
        
        public IGoal createGoal(Engine engine, PythonFileC fileC, PythonValueSet acceptor) {
            ASTNode node = ASTUtils.findNodeAt(fileC.node(), namePos);
            assertNotNull(node);
            PythonConstruct construct = fileC.subconstructFor(node);
            return createSelfGoal(fileC, acceptor, construct, engine);
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
    //                protected <T> void acceptIndividualResult(RuntimeObject result, IGrade<T> grade) {
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
        
        public void check(PythonFileC fileC, FileSourceUnit cu, Engine engine, StringBuilder expected,
                StringBuilder actual) throws Exception {
            PythonValueSet acceptor = new PythonValueSet() {
                @Override
                protected <T> void acceptIndividualResult(RuntimeObject result, IGrade<T> grade) {
                    
                }
            };
            IGoal goal = createGoal(fileC, acceptor);
            int actualLine = -2;
            String prefix = "";
            if (goal != null) {
                engine.run(goal);
                
                ValueInfo result = acceptor.getResult();
                if (result != null) {
                    Collection<Value> vals = result.getValueSet().containedValues();
                    PythonConstruct decl = ((PythonObject) vals.iterator().next()).getDecl();//FIXME I am Dirty Hack! Fix me, please.
                    actualLine = getLine(cu, decl.node());
                }
                
                prefix = goal.toString() + " : ";
            }
            expected.append(prefix).append(String.valueOf(line)).append('\n');
            actual.append(prefix).append(String.valueOf(actualLine)).append('\n');
            
        }
        
        public IGoal createGoal(PythonFileC fileC, PythonValueSet acceptor) {
            ASTNode node = ASTUtils.findMinimalNode(fileC.node(), namePos, namePos);
            assertNotNull(node);
            PythonConstruct construct = fileC.subconstructFor(node);
            if (construct instanceof VariableReferenceC) {
                return construct.createGoal(Krocodile.EMPTY);
            } else {
                throw new IllegalStateException("Should be VariableReferenceC, but found "
                        + construct.getClass().getSimpleName());
            }
        }
    }
    
}
