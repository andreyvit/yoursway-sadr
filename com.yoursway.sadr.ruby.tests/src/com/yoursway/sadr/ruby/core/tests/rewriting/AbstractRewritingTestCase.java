package com.yoursway.sadr.ruby.core.tests.rewriting;

import static junit.framework.Assert.assertEquals;

import java.io.IOException;

import junit.framework.Assert;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.ruby.internal.parsers.jruby.ASTUtils;

import com.yoursway.sadr.engine.AnalysisEngine;
import com.yoursway.sadr.ruby.core.rewriting.RewritingSession;
import com.yoursway.sadr.ruby.core.rewriting.WCall;
import com.yoursway.sadr.ruby.core.rewriting.WMethodDeclaration;
import com.yoursway.sadr.ruby.core.runtime.WholeProjectRuntime;
import com.yoursway.sadr.ruby.core.tests.AbstractTestCase;
import com.yoursway.sadr.ruby.core.tests.TestUtils;

public abstract class AbstractRewritingTestCase extends AbstractTestCase {
    
    @Override
    protected void runTest() throws Exception {
        WholeProjectRuntime projectRuntime = createProjectRuntime(getClass());
        
        AnalysisEngine engine = projectRuntime.getEngine();
        
        StringBuilder expected = new StringBuilder();
        StringBuilder actual = new StringBuilder();
        
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        
        for (ISourceModule sourceModule : projectRuntime.getSourceModules()) {
            String headline = "==== " + sourceModule.getElementName() + " ====\n";
            IFile oldFile = (IFile) sourceModule.getUnderlyingResource();
            IFile newFile = root.getFile(TestUtils.appendToFileName(oldFile.getFullPath(), ".new"));
            String expectedContent = TestUtils.readAndClose(newFile.getContents());
            String actualContent = processFile(sourceModule, projectRuntime, engine);
            
            expected.append(headline).append(expectedContent);
            actual.append(headline).append(actualContent);
        }
        
        Assert.assertEquals(expected.toString(), actual.toString());
    }
    
    private String processFile(ISourceModule sourceModule, WholeProjectRuntime projectRuntime,
            AnalysisEngine engine) throws ModelException, Exception {
        String content = sourceModule.getSource();
        int offset = content.indexOf("renameme");
        ModuleDeclaration rootNode = projectRuntime.getASTFor(sourceModule);
        //        FileScope scope = projectRuntime.getScopeFor(sourceModule);
        //        DtlFileC fileC = new DtlFileC(scope, rootNode);
        ASTNode node = ASTUtils.findMinimalNode(rootNode, offset, offset);
        ASTNode[] way = ASTUtils.restoreWayToNode(rootNode, node);
        RewritingSession session = new RewritingSession(content);
        
        executeRename(node, way, session, offset, "newname");
        return session.commit(content);
    }
    
    private void executeRename(ASTNode node, ASTNode[] way, RewritingSession session, int offset,
            String newName) {
        if (node instanceof MethodDeclaration) {
            MethodDeclaration md = (MethodDeclaration) node;
            if (offset == md.getNameStart()) {
                WMethodDeclaration wmethod = (WMethodDeclaration) session.map(md);
                wmethod.setName(newName);
                return;
            }
        }
        if (node instanceof CallExpression) {
            CallExpression ce = (CallExpression) node;
            //            SimpleReference nameNode = ce.getCallName();
            if (offset >= ce.sourceStart() && offset < ce.sourceEnd()) {
                WCall wcall = (WCall) session.map(ce);
                wcall.setName(newName);
                return;
            }
        }
    }
    
    protected static void assertCorrectString0(String fileName, Object object) throws IOException {
        String content = TestUtils.readFile(fileName);
        assertEquals(content.trim(), object.toString().trim());
    }
    
}
