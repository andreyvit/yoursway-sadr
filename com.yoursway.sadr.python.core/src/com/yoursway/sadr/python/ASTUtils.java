package com.yoursway.sadr.python;

import java.util.Stack;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.parser.ISourceParser;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.DLTKLanguageManager;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.python.core.PythonNature;

public class ASTUtils {
    
    private ASTUtils() {
        throw new AssertionError("Cannot instantiate utility class");
    }
    
    public static void setVisibility(MethodDeclaration methodDeclaration, int newVisibility) {
        int modifiers = methodDeclaration.getModifiers();
        modifiers = modifiers
                & ~(Modifiers.AccPublic | Modifiers.AccProtected | Modifiers.AccPrivate | Modifiers.AccDefault);
        methodDeclaration.setModifiers(modifiers | newVisibility);
    }
    
    public static ASTNode[] restoreWayToNode(ModuleDeclaration module, final ASTNode nde) {
        final Stack<ASTNode> stack = new Stack<ASTNode>();
        ASTVisitor visitor = new ASTVisitor() {
            
            boolean found = false;
            
            @Override
            public boolean visitGeneral(ASTNode node) throws Exception {
                if (found)
                    return super.visitGeneral(node);
                stack.push(node);
                if (node.equals(nde))
                    found = true;
                return super.visitGeneral(node);
            }
            
            @Override
            public void endvisitGeneral(ASTNode node) throws Exception {
                super.endvisitGeneral(node);
                if (found)
                    return;
                stack.pop();
            }
            
        };
        
        try {
            module.traverse(visitor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stack.toArray(new ASTNode[stack.size()]);
    }
    
    /**
     * 
     * Finds minimal ast node, that covers given position
     * 
     * 
     * 
     * @param unit
     * 
     * @param position
     * 
     * @return
     * 
     */
    
    public static ASTNode findMinimalNode(ModuleDeclaration unit, int start, int end) {
        
        class Visitor extends ASTVisitor {
            
            ASTNode result = null;
            
            int start, end;
            
            public Visitor(int start, int end) {
                
                this.start = start;
                
                this.end = end;
                
            }
            
            public ASTNode getResult() {
                
                return result;
                
            }
            
            private int calcLen(ASTNode s) {
                
                int realStart = s.sourceStart();
                
                int realEnd = s.sourceEnd();
                
                if (s instanceof TypeDeclaration) {
                    
                    TypeDeclaration declaration = (TypeDeclaration) s;
                    
                    realStart = declaration.getNameStart();
                    
                    realEnd = declaration.getNameEnd();
                    
                } else if (s instanceof MethodDeclaration) {
                    MethodDeclaration declaration = (MethodDeclaration) s;
                    realStart = declaration.getNameStart();
                    realEnd = declaration.getNameEnd();
                }
                
                return realEnd - realStart;
                
            }
            
            @Override
            public boolean visitGeneral(ASTNode s) throws Exception {
                
                int realStart = s.sourceStart();
                
                int realEnd = s.sourceEnd();
                
                if (s instanceof Block)
                    
                    realStart = realEnd = -42; // never select on blocks
                    
                else if (s instanceof TypeDeclaration) {
                    
                    TypeDeclaration declaration = (TypeDeclaration) s;
                    
                    realStart = declaration.getNameStart();
                    
                    realEnd = declaration.getNameEnd();
                    
                } else if (s instanceof MethodDeclaration) {
                    
                    MethodDeclaration declaration = (MethodDeclaration) s;
                    
                    realStart = declaration.getNameStart();
                    
                    realEnd = declaration.getNameEnd();
                    
                }
                
                if (realStart <= start && realEnd >= end) {
                    
                    if (result != null) {
                        
                        int oldlen = calcLen(result);
                        
                        int newlen = realEnd - realStart;
                        
                        if (newlen <= oldlen)
                            
                            result = s;
                        
                    } else
                        
                        result = s;
                    
                    if (DLTKCore.DEBUG_SELECTION)
                        
                        System.out.println("Found " + s.getClass().getName());
                    
                }
                
                return true;
                
            }
            
        }
        
        Visitor visitor = new Visitor(start, end);
        
        try {
            
            unit.traverse(visitor);
            
        } catch (Exception e) {
            
            e.printStackTrace();
            
        }
        
        return visitor.getResult();
        
    }
    
    public static ASTNode findNodeAt(ModuleDeclaration unit, final int pos) {
        class Visitor extends ASTVisitor {
            ASTNode result = null;
            
            public ASTNode getResult() {
                return result;
            }
            
            @Override
            public boolean visitGeneral(ASTNode s) throws Exception {
                int realStart = s.sourceStart();
                int realEnd = s.sourceEnd();
                
                if (realStart >= 0 && realEnd >= 0 && pos == realStart)
                    result = s;
                return true;
            }
            
        }
        
        Visitor visitor = new Visitor();
        try {
            unit.traverse(visitor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return visitor.getResult();
    }
    
    /**
     * 
     * Finds minimal ast node, that covers given position
     * 
     * 
     * 
     * @param unit
     * 
     * @param position
     * 
     * @return
     * 
     */
    
    public static ASTNode findMaximalNodeEndingAt(ModuleDeclaration unit, final int boundaryOffset) {
        
        class Visitor extends ASTVisitor {
            
            ASTNode result = null;
            
            public ASTNode getResult() {
                
                return result;
                
            }
            
            @Override
            public boolean visitGeneral(ASTNode s) throws Exception {
                
                if (s.sourceStart() < 0 || s.sourceEnd() < 0)
                    
                    return true;
                
                int sourceEnd = s.sourceEnd();
                
                if (Math.abs(sourceEnd - boundaryOffset) <= 0) { // XXX: was
                
                    // ... <= 1
                    
                    result = s;
                    
                    System.out.println("Found " + s.getClass().getName());
                    
                }
                
                return true;
                
            }
            
        }
        
        Visitor visitor = new Visitor();
        
        try {
            
            unit.traverse(visitor);
            
        } catch (Exception e) {
            
            e.printStackTrace();
            
        }
        
        return visitor.getResult();
        
    }
    
    public static ModuleDeclaration getAST(ISourceModule module) {
        
        return SourceParserUtil.getModuleDeclaration(module, null);
        
    }
    
    public static ModuleDeclaration getAST(char[] cs) {
        
        try {
            
            ISourceParser sourceParser = DLTKLanguageManager.getSourceParser(PythonNature.NATURE_ID);
            
            ModuleDeclaration declaration = sourceParser.parse("RawSource".toCharArray(), cs, null);
            return declaration;
            
        } catch (ModelException e) {
            
            e.printStackTrace();
            
        } catch (CoreException e) {
            
            e.printStackTrace();
            
        }
        
        return null;
        
    }
    
    //    public static boolean isNodeScoping(ASTNode node) {
    
    //        return (node instanceof RubyIfStatement || node instanceof RubyForStatement2
    
    //                || node instanceof RubyWhileStatement || node instanceof RubyBlock
    
    //                || node instanceof RubyUntilStatement || node instanceof RubyUnlessStatement
    
    //                || node instanceof TypeDeclaration || node instanceof MethodDeclaration);
    
    //    }
    
}
