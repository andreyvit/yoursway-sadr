package com.yoursway.sadr.python.completion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.codeassist.IAssistParser;
import org.eclipse.dltk.codeassist.ScriptCompletionEngine;
import org.eclipse.dltk.compiler.env.ISourceModule;
import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.python.parser.ast.expressions.CallHolder;
import org.eclipse.dltk.python.parser.ast.expressions.ExtendedVariableReference;

import com.yoursway.sadr.blocks.foundation.valueinfo.ValueInfo;
import com.yoursway.sadr.python.ASTUtils;
import com.yoursway.sadr.python.core.runtime.ProjectRuntime;
import com.yoursway.sadr.python.core.runtime.PythonBasicClass;
import com.yoursway.sadr.python.core.runtime.PythonMethod;
import com.yoursway.sadr.python.core.runtime.PythonProcedure;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfoUtils;
import com.yoursway.sadr.python.core.typeinferencing.scopes.MethodScope;
import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
import com.yoursway.sadr.python_v2.constructs.PythonFileC;
import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSetAcceptor;
import com.yoursway.sadr.succeeder.Engine;
import com.yoursway.sadr.succeeder.IGoal;
import com.yoursway.sadr.succeeder.IGrade;

public class PythonCompletionEngine extends ScriptCompletionEngine {
    
    private final static int RELEVANCE_FREE_SPACE = 10000000;
    
    private final static int RELEVANCE_KEYWORD = 1000000;
    
    //    private final static int RELEVANCE_METHODS = 100000;
    
    //    private final DLTKTypeInferenceEngine inferencer;
    //    private ISourceParser parser = null;
    //    private MixinModel model;
    private final HashSet<String> completedNames = new HashSet<String>();
    private final WeakHashSet intresting = new WeakHashSet();
    
    private Engine engine;
    
    private ProjectRuntime projectRuntime;
    
    private PythonFileC fileC;
    
    public PythonCompletionEngine() {
        
    }
    
    @Override
    protected int getEndOfEmptyToken() {
        return 0;
    }
    
    @Override
    protected String processMethodName(IMethod method, String token) {
        return null;
    }
    
    @Override
    protected String processTypeName(IType method, String token) {
        return null;
    }
    
    @Override
    public IAssistParser getParser() {
        return null;
    }
    
    private String getWordStarting(String content, int position, int maxLen) {
        int original = position;
        if (position <= 0)
            return "";
        if (position >= content.length())
            position = content.length();
        position--;
        int len = 0;
        while (position >= 0 && len < maxLen
                && PythonSyntaxUtils.isLessStrictIdentifierCharacter(content.charAt(position)))
            position--;
        if (position + 1 > original)
            return "";
        // I don't understand what Character.isWhitespace is supposed to do here. It was causing problems for
        // completion after ':'. -- A.T.
        if ((position >= 0 && (true || Character.isWhitespace(content.charAt(position)))) || position == -1)
            return content.substring(position + 1, original);
        return null;
    }
    
    public void complete(ISourceModule module, int position, int i) {
        completedNames.clear();
        this.actualCompletionPosition = position;
        this.requestor.beginReporting();
        org.eclipse.dltk.core.ISourceModule modelModule = (org.eclipse.dltk.core.ISourceModule) module;
        try {
            String content = module.getSourceContents();
            
            String wordStarting = getWordStarting(content, position, 20);
            
            if (wordStarting != null)
                projectRuntime = new ProjectRuntime(modelModule.getScriptProject());
            fileC = projectRuntime.getModule(modelModule.getElementName());
            //            fileC = projectRuntime.getConstructFor(modelModule);
            //            runtimeModel = projectRuntime.getModel();
            engine = projectRuntime.getEngine();
            
            boolean enableKeywordCompletion = true;
            
            ASTNode minimalNode = ASTUtils.findMinimalNode(fileC.node(), position, position);
            if (minimalNode != null) {
                if (minimalNode instanceof VariableReference) {
                    completeCallWithoutReceiver((VariableReference) minimalNode, position);
                }
                if (minimalNode instanceof ExtendedVariableReference) {
                    completeCall((ExtendedVariableReference) minimalNode, position);
                    enableKeywordCompletion = false;
                }

                else if (wordStarting == null || wordStarting.length() == 0) {
                    int rel = RELEVANCE_FREE_SPACE;
                    try {
                        IModelElement[] children = modelModule.getChildren();
                        if (children != null)
                            for (int j = 0; j < children.length; j++) {
                                if (children[j] instanceof IField)
                                    reportField((IField) children[j], rel);
                                if (children[j] instanceof IMethod) {
                                    IMethod method = (IMethod) children[j];
                                    if ((method.getFlags() & Modifiers.AccStatic) == 0)
                                        reportMethod(method, rel);
                                }
                                if (children[j] instanceof IType
                                        && !children[j].getElementName().trim().startsWith("<<"))
                                    reportType((IType) children[j], rel);
                            }
                    } catch (ModelException e) {
                        e.printStackTrace();
                    }
                }
            }
            
            if (enableKeywordCompletion && wordStarting != null)
                for (String element : PythonKeyword.findByPrefix(wordStarting))
                    reportKeyword(element);
            
        } finally {
            this.requestor.endReporting();
        }
    }
    
    private void completeCallWithoutReceiver(VariableReference node, int position) {
        String prefix = node.getName().substring(0, position - node.sourceStart());
        // procedures
        for (PythonProcedure procedure : runtimeModel.findProceduresMatching(prefix))
            reportProcedure(procedure, 4242);
        // self methods
        PythonConstruct construct = fileC.subconstructFor(node);
        if (construct.staticContext() instanceof MethodScope) {
            MethodScope methodScope = (MethodScope) construct.staticContext();
            PythonBasicClass klass = methodScope.getMethod().klass();
            Collection<PythonMethod> result = new ArrayList<PythonMethod>();
            klass.findMethodsByPrefix(prefix, result);
            for (PythonMethod m : result)
                reportMethod(m, 4242);
        }
    }
    
    protected void completeCall(ExtendedVariableReference node, int position) {
        Expression receiver = node.getExpression(0);
        if (node.getExpressionCount() < 2) {
            if (receiver instanceof VariableReference)
                completeCallWithoutReceiver((VariableReference) receiver, position);
            return;
        }
        Expression name = node.getExpression(1);
        if (name instanceof CallHolder && node.getExpression(2) instanceof VariableReference)
            name = node.getExpression(2);
        else {
            if (!(name instanceof VariableReference))
                return;
        }
        if (name.sourceStart() > position || name.sourceEnd() < position)
            return;
        String prefix = ((VariableReference) name).getName().substring(0, position - name.sourceStart());
        
        PythonConstruct construct = fileC.subconstructFor(receiver);
        
        //        ExpressionValueInfoGoal goal = new ExpressionValueInfoGoal(construct, new EmptyDynamicContext(),
        //                InfoKind.TYPE);
        
        final ValueInfo values[] = new ValueInfo[] { null };
        PythonValueSetAcceptor acceptor = new PythonValueSetAcceptor() {
            @Override
            public <T> void checkpoint(IGrade<T> grade) {
                values[0] = getResult();
            }
        };
        IGoal goal = construct.evaluate(null, acceptor);
        engine.run(goal);
        
        for (PythonMethod method : ValueInfoUtils.findMethodsByPrefix(values[0], prefix))
            reportMethod(method, 4242);
    }
    
    @Override
    protected String processFieldName(IField field, String token) {
        return field.getElementName();
    }
    
    private void reportProcedure(PythonProcedure procedure, int rel) {
        String elementName = procedure.name();
        if (completedNames.contains(elementName))
            return;
        completedNames.add(elementName);
        if (elementName.indexOf('.') != -1)
            elementName = elementName.substring(elementName.indexOf('.') + 1);
        char[] name = elementName.toCharArray();
        char[] compl = name;
        
        int relevance = rel;
        
        if (!requestor.isIgnored(CompletionProposal.METHOD_DECLARATION)) {
            CompletionProposal proposal = createProposal(CompletionProposal.METHOD_DECLARATION,
                    actualCompletionPosition);
            
            String[] params = procedure.parameterNames();
            
            if (params != null && params.length > 0) {
                char[][] args = new char[params.length][];
                for (int i = 0; i < params.length; ++i)
                    args[i] = params[i].toCharArray();
                proposal.setParameterNames(args);
            }
            
            proposal.setModelElement(null);
            proposal.setName(name);
            proposal.setCompletion(compl);
            //                proposal.setFlags(method.getFlags());
            proposal.setReplaceRange(this.startPosition - this.offset, this.endPosition - this.offset);
            proposal.setRelevance(relevance);
            this.requestor.accept(proposal);
        }
        
    }
    
    private void reportMethod(PythonMethod method, int rel) {
        String elementName = method.name();
        if (completedNames.contains(elementName))
            return;
        completedNames.add(elementName);
        if (elementName.indexOf('.') != -1)
            elementName = elementName.substring(elementName.indexOf('.') + 1);
        char[] name = elementName.toCharArray();
        char[] compl = name;
        
        int relevance = rel;
        
        if (!requestor.isIgnored(CompletionProposal.METHOD_DECLARATION)) {
            CompletionProposal proposal = createProposal(CompletionProposal.METHOD_DECLARATION,
                    actualCompletionPosition);
            
            String[] params = method.parameterNames();
            
            if (params != null && params.length > 0) {
                char[][] args = new char[params.length][];
                for (int i = 0; i < params.length; ++i)
                    args[i] = params[i].toCharArray();
                proposal.setParameterNames(args);
            }
            
            proposal.setModelElement(null);
            proposal.setName(name);
            proposal.setCompletion(compl);
            //                proposal.setFlags(method.getFlags());
            proposal.setReplaceRange(this.startPosition - this.offset, this.endPosition - this.offset);
            proposal.setRelevance(relevance);
            this.requestor.accept(proposal);
        }
        
    }
    
    private void reportMethod(IMethod method, int rel) {
        String elementName = method.getElementName();
        if (completedNames.contains(elementName))
            return;
        completedNames.add(elementName);
        if (elementName.indexOf('.') != -1)
            elementName = elementName.substring(elementName.indexOf('.') + 1);
        char[] name = elementName.toCharArray();
        char[] compl = name;
        
        int relevance = rel;
        
        if (!requestor.isIgnored(CompletionProposal.METHOD_DECLARATION)) {
            CompletionProposal proposal = createProposal(CompletionProposal.METHOD_DECLARATION,
                    actualCompletionPosition);
            
            String[] params = null;
            try {
                params = method.getParameters();
            } catch (ModelException e) {
                e.printStackTrace();
            }
            
            if (params != null && params.length > 0) {
                char[][] args = new char[params.length][];
                for (int i = 0; i < params.length; ++i)
                    args[i] = params[i].toCharArray();
                proposal.setParameterNames(args);
            }
            
            proposal.setModelElement(method);
            proposal.setName(name);
            proposal.setCompletion(compl);
            try {
                proposal.setFlags(method.getFlags());
            } catch (ModelException e) {
                e.printStackTrace();
            }
            proposal.setReplaceRange(this.startPosition - this.offset, this.endPosition - this.offset);
            proposal.setRelevance(relevance);
            this.requestor.accept(proposal);
        }
        
    }
    
    private void reportType(IType type, int rel) {
        this.intresting.add(type);
        String elementName = type.getElementName();
        if (completedNames.contains(elementName))
            return;
        completedNames.add(elementName);
        char[] name = elementName.toCharArray();
        if (name.length == 0)
            return;
        
        int relevance = rel;
        
        // accept result
        noProposal = false;
        if (!requestor.isIgnored(CompletionProposal.TYPE_REF)) {
            CompletionProposal proposal = createProposal(CompletionProposal.TYPE_REF,
                    actualCompletionPosition);
            
            proposal.setModelElement(type);
            proposal.setName(name);
            proposal.setCompletion(elementName.toCharArray());
            // proposal.setFlags(Flags.AccDefault);
            try {
                proposal.setFlags(type.getFlags());
            } catch (ModelException e) {
            }
            proposal.setReplaceRange(this.startPosition - this.offset, this.endPosition - this.offset);
            proposal.setRelevance(relevance);
            this.requestor.accept(proposal);
            if (DEBUG)
                this.printDebug(proposal);
        }
        
    }
    
    private void reportField(IField field, int rel) {
        this.intresting.add(field);
        String elementName = field.getElementName();
        if (completedNames.contains(elementName))
            return;
        completedNames.add(elementName);
        char[] name = elementName.toCharArray();
        if (name.length == 0)
            return;
        
        int relevance = rel;
        
        // accept result
        noProposal = false;
        if (!requestor.isIgnored(CompletionProposal.FIELD_REF)) {
            CompletionProposal proposal = createProposal(CompletionProposal.FIELD_REF,
                    actualCompletionPosition);
            
            proposal.setModelElement(field);
            proposal.setName(name);
            proposal.setCompletion(elementName.toCharArray());
            // proposal.setFlags(Flags.AccDefault);
            proposal.setReplaceRange(this.startPosition - this.offset, this.endPosition - this.offset);
            proposal.setRelevance(relevance);
            this.requestor.accept(proposal);
            if (DEBUG)
                this.printDebug(proposal);
        }
        
    }
    
    private void reportKeyword(String name) {
        // accept result
        noProposal = false;
        if (!requestor.isIgnored(CompletionProposal.FIELD_REF)) {
            CompletionProposal proposal = createProposal(CompletionProposal.KEYWORD, actualCompletionPosition);
            
            proposal.setName(name.toCharArray());
            proposal.setCompletion(name.toCharArray());
            // proposal.setFlags(Flags.AccDefault);
            proposal.setReplaceRange(this.startPosition - this.offset, this.endPosition - this.offset);
            proposal.setRelevance(RELEVANCE_KEYWORD);
            this.requestor.accept(proposal);
            if (DEBUG)
                this.printDebug(proposal);
        }
        
    }
    
}