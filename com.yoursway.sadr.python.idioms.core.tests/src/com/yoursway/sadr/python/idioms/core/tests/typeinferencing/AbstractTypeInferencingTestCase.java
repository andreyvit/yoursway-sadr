package com.yoursway.sadr.python.idioms.core.tests.typeinferencing;

import static com.yoursway.sadr.python.idioms.core.tests.TestingUtils.callerOutside;
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
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.python.core.PythonNature;
import org.junit.After;

import com.yoursway.sadr.engine.AnalysisEngine;
import com.yoursway.sadr.python.ASTUtils;
import com.yoursway.sadr.python.core.runtime.WholeProjectRuntime;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonFileC;
import com.yoursway.sadr.python.core.typeinferencing.goals.ExpressionValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoGoal;
import com.yoursway.sadr.python.core.typeinferencing.scopes.FileScope;
import com.yoursway.sadr.python.idioms.core.Idiom;
import com.yoursway.sadr.python.idioms.core.IdiomMatch;
import com.yoursway.sadr.python.idioms.core.IdiomMatcher;
import com.yoursway.sadr.python.idioms.core.TreePrinter;
import com.yoursway.sadr.python.idioms.core.tests.Activator;
import com.yoursway.sadr.python.idioms.core.tests.internal.FileUtil;
import com.yoursway.sadr.python.idioms.core.tests.internal.StringInputStream;

public abstract class AbstractTypeInferencingTestCase {

	protected IProject testProject;
	private File tempDirectory;

	private void createProject(String projectName, File projectLocation)
			throws Exception {
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		testProject = workspace.getRoot().getProject(projectName);
		if (testProject.exists())
			testProject.delete(false, true, null);
		IProjectDescription description = workspace
				.newProjectDescription(testProject.getName());
		description.setNatureIds(new String[] { PythonNature.NATURE_ID });
		// URL projectLocation =
		// DtlTestsPlugin.getDefault().getBundle().getEntry("test_projects/" +
		// projectName);
		// if (projectLocation == null)
		// throw new AssertionError("Test project not found.");
		// URL resolvedProjectLocation = Platform.resolve(projectLocation);
		IPath tempPath = Activator.getDefault().getStateLocation().append(
				"testprojects").append(projectName);
		tempDirectory = tempPath.toFile();
		FileUtil.recursiveDelete(tempDirectory);
		recursiveCopy(projectLocation, tempDirectory);
		// recursiveCopy(new File(resolvedProjectLocation.getPath()),
		// tempDirectory);
		description.setLocation(tempPath);
		testProject.create(description, null);
		testProject.open(null);
	}

	@After
	public void tearDown() throws Exception {
		testProject.delete(true, true, null);
	}

	protected void runTest() throws Exception {
		String methodName = callerOutside(AbstractTypeInferencingTestCase.class);
		String className = getClass().getSimpleName();
		String basePath = "/tests/" + className + "/" + methodName;

		URL projectLocation = Activator.getDefault().getBundle().getEntry(
				basePath);
		if (projectLocation == null)
			throw new AssertionError("Test project not found.");
		URL resolvedProjectLocation = FileLocator.resolve(projectLocation);
		File file = new File(resolvedProjectLocation.getPath());
		createProject("test", file);

		IScriptProject scriptProject = DLTKCore.create(testProject);
		WholeProjectRuntime projectRuntime = new WholeProjectRuntime(
				scriptProject);

		AnalysisEngine engine = projectRuntime.getEngine();

		StringBuilder expected = new StringBuilder();
		StringBuilder actual = new StringBuilder();

		for (ISourceModule sourceModule : projectRuntime.getSourceModules()) {
			String headline = "==== " + sourceModule.getElementName()
					+ " ====\n";
			expected.append(headline);
			actual.append(headline);
			checkFile(sourceModule, projectRuntime, engine, expected, actual);
		}

		Assert.assertEquals(expected.toString(), actual.toString());
	}

	private void checkFile(ISourceModule sourceModule,
			WholeProjectRuntime projectRuntime, AnalysisEngine engine,
			StringBuilder expected, StringBuilder actual)
			throws ModelException, Exception {
		Collection<IAssertion> assertions = new ArrayList<IAssertion>();

		String content = sourceModule.getSource();
		String[] lines = content.split("\n");
		int lineOffset = 0;
		for (int i = 0; i < lines.length; i++) {
			String originalLine = lines[i];
			String line = originalLine.trim();
			int delimiterPos = line.indexOf("##");
			if (delimiterPos >= 0) {
				StringTokenizer tok = new StringTokenizer(line
						.substring(delimiterPos + 2));
				String test = tok.nextToken();
				IAssertion assertion;
				assertion = parseAssertion(tok, test, line, originalLine,
						lineOffset, delimiterPos);
				assertions.add(assertion);
			}
			lineOffset += originalLine.length() + 1;
		}

		if (assertions.size() == 0)
			return;

		PythonFileC fileC = projectRuntime.getConstructFor(sourceModule);
		for (IAssertion assertion : assertions)
			assertion.check(fileC, sourceModule, engine, expected,
					actual);
	}

	private IAssertion parseAssertion(StringTokenizer tok, String test,
			String line, String originalLine, int lineOffset, int delimiterPos) {
		IAssertion assertion;
		if ("idiom".equals(test)) {
			int namePos = locate(line, originalLine, delimiterPos, lineOffset);
			String pattern = tok.nextToken();
			if(pattern.equals("no")) {
	            pattern = tok.nextToken();
				assertion = new IdiomAppliesAssertion(namePos, pattern, false);
			} else {
				assertion = new IdiomAppliesAssertion(namePos, pattern, true);
			}
		} else {
			throw new IllegalArgumentException("Unknown assertion: " + test);
		}
		return assertion;
	}

	protected void needToken(StringTokenizer tok, String token) {
		String arrow = tok.nextToken();
		assertTrue(arrow.equals(token));
	}

	private int locate(String line, String originalLine,
			int delimiterPos, int lineOffset) {
		Pattern pat = Pattern.compile("\\w");
		Matcher matcher = pat.matcher(originalLine);
		assertTrue(matcher.find());
		int namePosInsideLine = matcher.start();
		assertTrue(matcher.find());

		// check that there are no more possible matches
		if (namePosInsideLine >= delimiterPos)
			throw new IllegalArgumentException("No symbols before comment!"
					+ " in " + line);

		int namePos = namePosInsideLine + lineOffset;
		return namePos;
	}

	protected static void assertCorrectString0(String fileName, Object object)
			throws IOException {
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

	private static void recursiveCopy(File source, File destination)
			throws IOException {
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

	private static void copyFile(File source, File destination)
			throws IOException {
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

	private static void copyStream(InputStream in, OutputStream out)
			throws IOException {
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

	protected static void replaceContents(IFile oldFile, IFile newFile)
			throws IOException, CoreException {
		String oldName = removeExtension(oldFile.getName());
		String newName = removeExtension(newFile.getName());
		String original = readAndClose(newFile.getContents());
		String fixed = original.replaceAll("class " + newName, "class "
				+ oldName);
		oldFile.setContents(new StringInputStream(fixed), true, false, null);
	}

	public interface IAssertion {

		void check(PythonFileC fileC,
				ISourceModule cu, AnalysisEngine engine,
				StringBuilder expected, StringBuilder actual) throws Exception;

		ValueInfoGoal createGoal(PythonFileC fileC);

	}

	class IdiomAppliesAssertion implements IAssertion {

		private final int lineOffset;
		private final String pattern;
		private final boolean should;

		public IdiomAppliesAssertion(int namePos, String pattern, boolean should) {
			this.lineOffset = namePos;
			this.pattern = pattern;
			this.should = should;
		}

		public void check(PythonFileC fileC,
				ISourceModule cu, AnalysisEngine engine,
				StringBuilder expected, StringBuilder actual) throws Exception {
			System.out.println();
			ASTNode node = ASTUtils.findNodeAt(fileC.node(), lineOffset);
			 assertNotNull(node);
			PythonConstruct construct = fileC.subconstructFor(node);
			while(construct.node() instanceof VariableReference)
				construct = construct.parent();
			assertNotNull(construct);
			Idiom idiom = new Idiom(this.pattern);
			IdiomMatch match = idiom.match(construct);
			if(should){
				if(match == null) {
					idiom.printCompareInfo(construct);
				}
				assertNotNull("Idiom should match here!", match);
				match.apply();
			} else {
				if(match != null) {
					idiom.printCompareInfo(construct);
				}
                assertTrue("Idiom should never match here!", match == null);
			}
			
			// ExpressionValueInfoGoal goal = createGoal(fileScope, rootNode);
			// engine.evaluate(goal);
			// ValueInfo result = goal.weakResult();
			// String[] possibleTypes = result.describePossibleTypes();
			// Arrays.sort(possibleTypes, Strings.getNaturalComparator());
			// String types = Strings.join(possibleTypes, ",");
			// String prefix = expression + " : ";
			// expected.append(prefix).append(correctClassRef).append('\n');
			// actual.append(prefix).append(types).append('\n');
		}

		public ExpressionValueInfoGoal createGoal(PythonFileC fileC) {
			// ASTNode node = ASTUtils.findNodeAt(rootNode, namePos);
			// assertNotNull(node);
			// PythonConstruct construct = new PythonFileC(fileScope,
			// fileScope.node()).subconstructFor(node);
			// return new ExpressionValueInfoGoal(construct, new
			// EmptyDynamicContext(), InfoKind.TYPE);
			return null;
		}

	}
}
