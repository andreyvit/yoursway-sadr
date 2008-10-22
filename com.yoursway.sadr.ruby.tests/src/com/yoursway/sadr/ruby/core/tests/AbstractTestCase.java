package com.yoursway.sadr.ruby.core.tests;

import static com.yoursway.sadr.ruby.core.tests.TestingUtils.callerOutside;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.ruby.core.RubyNature;
import org.junit.After;

import com.yoursway.sadr.ruby.core.runtime.WholeProjectRuntime;
import com.yoursway.sadr.ruby.core.tests.internal.FileUtil;

public abstract class AbstractTestCase {
    
    private IProject testProject;
    private File tempDirectory;
    
    public AbstractTestCase() {
        super();
    }
    
    @After
    public void tearDown() throws Exception {
        testProject.delete(true, true, null);
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
    
    private void createProject(String projectName, File projectLocation) throws Exception {
        final IWorkspace workspace = ResourcesPlugin.getWorkspace();
        testProject = workspace.getRoot().getProject(projectName);
        if (testProject.exists())
            testProject.delete(false, true, null);
        IProjectDescription description = workspace.newProjectDescription(testProject.getName());
        description.setNatureIds(new String[] { RubyNature.NATURE_ID });
        //        URL projectLocation = DtlTestsPlugin.getDefault().getBundle().getEntry("test_projects/" + projectName);
        //        if (projectLocation == null)
        //            throw new AssertionError("Test project not found.");
        //        URL resolvedProjectLocation = Platform.resolve(projectLocation);
        IPath tempPath = RubyTestsPlugin.getDefault().getStateLocation().append("testprojects").append(
                projectName);
        tempDirectory = tempPath.toFile();
        FileUtil.recursiveDelete(tempDirectory);
        recursiveCopy(projectLocation, tempDirectory);
        //      recursiveCopy(new File(resolvedProjectLocation.getPath()), tempDirectory);
        description.setLocation(tempPath);
        testProject.create(description, null);
        testProject.open(null);
    }
    
    protected WholeProjectRuntime createProjectRuntime(Class<?> klass) throws Exception {
        String basePath = calculateBasePath(klass);
        File file = locateBundleResourceFolder(basePath);
        createProject("test", file);
        
        IScriptProject scriptProject = DLTKCore.create(testProject);
        return new WholeProjectRuntime(scriptProject);
    }
    
    private File locateBundleResourceFolder(String basePath) throws AssertionError, IOException {
        URL projectLocation = RubyTestsPlugin.getDefault().getBundle().getEntry(basePath);
        if (projectLocation == null)
            throw new AssertionError("Test project not found.");
        URL resolvedProjectLocation = FileLocator.resolve(projectLocation);
        File file = new File(resolvedProjectLocation.getPath());
        return file;
    }
    
    private String calculateBasePath(Class<?> klass) {
        String methodName = callerOutside(klass.getSuperclass());
        String className = klass.getSimpleName();
        String prefix = "com.yoursway.sadr.ruby.core.tests.";
        if (!klass.getName().startsWith(prefix))
            throw new IllegalArgumentException("Test cases must be in " + prefix + "*");
        String testsDir = klass.getName().substring(prefix.length());
        testsDir = testsDir.substring(0, testsDir.lastIndexOf('.'));
        String basePath = "/tests/" + testsDir + "/" + className + "/" + methodName;
        return basePath;
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
    
}