package com.yoursway.sadr.python_v2.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.expressions.ExpressionList;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.python.parser.ast.PythonClassDeclaration;
import org.eclipse.dltk.python.parser.ast.expressions.Assignment;
import org.eclipse.dltk.python.parser.ast.expressions.ExtendedVariableReferenceInterface;
import org.eclipse.dltk.python.parser.ast.expressions.PythonLambdaExpression;

import com.yoursway.sadr.python_v2.model.builtins.Builtins;

/**
 * Builds module constructs structure. Adds all traversed constructs to a new
 * module object.
 */
public class ModuleModelBuilder extends ASTVisitor {
    
    private final LexicalScopeImpl model = new LexicalScopeImpl(Builtins.getBuiltinModule()); // module model being builded
    private final Stack<LexicalScopeImpl> scopes = new Stack<LexicalScopeImpl>(); // nested scopes stack
    private final Stack<PythonClass> classes = new Stack<PythonClass>(); // nested classes stack
    
    public ModuleModelBuilder() {
        scopes.push(model);
    }
    
    public LexicalScope getModel() {
        return model;
    }
    
    @Override
    public boolean visit(ASTNode s) throws Exception {
        // TODO Auto-generated method stub
        return super.visit(s);
    }
    
    private void createAttribute(String name, RuntimeObject object) {
        if (!classes.isEmpty())
            classes.peek().setAttribute(name, object);
        else
            scopes.peek().setName(name, object);
    }
    
    private void onVisitLambdaAssignnment(String name, PythonLambdaExpression lambdaExpression) {
        FunctionObject function = new FunctionObject(scopes.peek(), lambdaExpression);
        createAttribute(name, function);
    }
    
    private void onVisitVariableAssignment(SimpleReference var, Statement val) {
        RuntimeObject valueObject = new ObjectStub(val);
        createAttribute(var.getName(), valueObject);
    }
    
    private void onVisitInstanceVariableAssignment(ExtendedVariableReferenceInterface extendedVariable,
            Statement right) {
        //FIXME add statement to current context
    }
    
    private void onVisitTestListAssignment(ExpressionList left, Statement right) {
        Iterator iter = left.getChilds().iterator();
        if (right instanceof ExpressionList) {
            ExpressionList exprs = (ExpressionList) right;
            Iterator j = exprs.getChilds().iterator();
            while (iter.hasNext() && j.hasNext()) {
                Expression expr = (Expression) iter.next();
                processAssignment(expr, (Expression) j.next());
            }
        } else {
            while (iter.hasNext()) {
                Expression expr = (Expression) iter.next();
                processAssignment(expr, right);
            }
        }
    }
    
    private void processAssignment(Statement left, Statement right) {
        if (left instanceof Assignment) {
            Assignment assignment = (Assignment) left;
            processAssignment(assignment.getLeft(), right);
            processAssignment(assignment.getRight(), right);
        } else if (left instanceof SimpleReference && right instanceof PythonLambdaExpression)
            onVisitLambdaAssignnment(((SimpleReference) left).getName(), (PythonLambdaExpression) right);
        else if (left instanceof VariableReference) // scoped variable
            onVisitVariableAssignment((VariableReference) left, right);
        else if (left instanceof ExtendedVariableReferenceInterface) // This is for in class and in method.
            onVisitInstanceVariableAssignment((ExtendedVariableReferenceInterface) left, right);
        else if (left instanceof ExpressionList)
            onVisitTestListAssignment((ExpressionList) left, right);
    }
    
    @Override
    public boolean visit(Expression expression) throws Exception {
        if (expression instanceof Assignment) {
            Assignment assignment = (Assignment) expression;
            Statement left = assignment.getLeft();
            Statement right = assignment.getRight();
            if (left == null) {
                return true;
                // throw new RuntimeException("addVariable expression can't be null");
            }
            processAssignment(left, right);
            return false;
        }
        return true;
    }
    
    @Override
    public boolean visit(MethodDeclaration s) throws Exception {
        FunctionObject function = new FunctionObject(scopes.peek(), s);
        createAttribute(s.getName(), function);
        scopes.push((LexicalScopeImpl) function.getScope());
        return true;
    }
    
    @Override
    public boolean endvisit(MethodDeclaration s) throws Exception {
        classes.pop();
        return super.endvisit(s);
    }
    
    @Override
    public boolean visit(Statement s) throws Exception {
        // TODO Auto-generated method stub
        return super.visit(s);
    }
    
    @Override
    public boolean visit(TypeDeclaration s) throws Exception {
        if (!(s instanceof PythonClassDeclaration))
            throw new RuntimeException("PythonClassDeclaration expected.");
        List<ASTNode> superclassAstNodes = ((PythonClassDeclaration) s).getSupers();
        List<PythonClass> supers = new ArrayList<PythonClass>(superclassAstNodes.size());
        for (ASTNode astNode : superclassAstNodes) {
            if (!(astNode instanceof Statement))
                throw new RuntimeException("Statement object expected.");
            supers.add(new ClassStub((Statement) astNode));
        }
        PythonClass cls = new PythonClass(supers);
        classes.push(cls);
        return super.visit(s);
    }
    
    @Override
    public boolean endvisit(TypeDeclaration s) throws Exception {
        classes.pop();
        return super.endvisit(s);
    }
    
    @Override
    public boolean visitGeneral(ASTNode node) throws Exception {
        // TODO add statement to current scope
        return super.visitGeneral(node);
    }
    
}
