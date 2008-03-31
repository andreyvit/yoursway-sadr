package com.yoursway.sadr.ruby.core.tests.refactoring;

import junit.framework.Assert;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.dltk.core.ISourceModule;

import com.yoursway.sadr.engine.AnalysisEngine;
import com.yoursway.sadr.engine.ContinuationScheduler;
import com.yoursway.sadr.engine.ContinuationRequestorCalledToken;
import com.yoursway.sadr.engine.SimpleContinuation;
import com.yoursway.sadr.ruby.core.refactoring.MethodRenameRefactoring;
import com.yoursway.sadr.ruby.core.rewriting.RewritingSession;
import com.yoursway.sadr.ruby.core.runtime.Callable;
import com.yoursway.sadr.ruby.core.runtime.RubyClass;
import com.yoursway.sadr.ruby.core.runtime.RubyRuntimeModel;
import com.yoursway.sadr.ruby.core.runtime.WholeProjectRuntime;
import com.yoursway.sadr.ruby.core.tests.AbstractTestCase;
import com.yoursway.sadr.ruby.core.tests.TestUtils;
import com.yoursway.sadr.ruby.core.tests.internal.StringInputStream;

public abstract class AbstractRefactoringTestCase extends AbstractTestCase {
    
    protected void runTest(String className, String methodName, String newName) throws Exception {
        WholeProjectRuntime projectRuntime = createProjectRuntime(getClass());
        
        AnalysisEngine engine = projectRuntime.getEngine();
        RubyRuntimeModel model = projectRuntime.getModel();
        
        Callable callable;
        if (className == null)
            callable = model.findProcedure(methodName);
        else {
            RubyClass klass = model.findClass(className);
            callable = klass.findMethod(methodName);
        }
        
        // FIXME this cycle is a hack, multifile refactorings are not supported yet
        for (ISourceModule sourceModule : projectRuntime.getSourceModules()) {
            String source = sourceModule.getSource();
            RewritingSession session = new RewritingSession(source);
            MethodRenameRefactoring refactoring = new MethodRenameRefactoring(session, callable, newName,
                    new SimpleContinuation() {
                        
                        public ContinuationRequestorCalledToken run(ContinuationScheduler requestor) {
                            return requestor.done();
                        }
                        
                    });
            engine.execute(refactoring);
            String newSource = session.commit(source);
            ((IFile) sourceModule.getUnderlyingResource()).setContents(new StringInputStream(newSource),
                    true, false, null);
        }
        
        StringBuilder expected = new StringBuilder();
        StringBuilder actual = new StringBuilder();
        
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        
        for (ISourceModule sourceModule : projectRuntime.getSourceModules()) {
            String headline = "==== " + sourceModule.getElementName() + " ====\n";
            IFile oldFile = (IFile) sourceModule.getUnderlyingResource();
            IFile newFile = root.getFile(TestUtils.appendToFileName(oldFile.getFullPath(), ".new"));
            String expectedContent = TestUtils.readAndClose(newFile.getContents());
            String actualContent = TestUtils.readAndClose(oldFile.getContents());
            
            expected.append(headline).append(expectedContent);
            actual.append(headline).append(actualContent);
        }
        
        Assert.assertEquals(expected.toString(), actual.toString());
    }
    
}
