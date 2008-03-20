package com.yoursway.sadr.python.idioms.core;

import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.parser.ISourceParser;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.compiler.problem.IProblem;
import org.eclipse.dltk.compiler.problem.IProblemReporter;
import org.eclipse.dltk.python.internal.core.parser.PythonSourceParser;
import org.eclipse.dltk.python.parser.ast.expressions.Assignment;

import com.google.common.collect.Maps;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;

public class IdiomMatcher {
	private final String source;
	private final String destination;
	private ModuleDeclaration parsedSource;
	private Map<String, Statement> matchMemory;

	public IdiomMatcher(String source, String destination) {
		this.source = source;
		this.destination = destination;
		this.parsedSource = parseSnippet(source);
	}

	private ModuleDeclaration parseSnippet(String source) {
		ISourceParser parser = new PythonSourceParser();
		ModuleDeclaration rootNode = parser.parse(null, source.toCharArray(),
				new IProblemReporter() {

					public void clearMarkers() {
					}

					public IMarker reportProblem(IProblem problem)
							throws CoreException {
						throw new RuntimeException("OMFG! "
								+ problem.getMessage());
					}

				});
		return rootNode;
	}

	public boolean match(PythonConstruct realConstruct) {
		matchMemory = Maps.newHashMap();
		return matchStatement(realConstruct, parsedSource.getStatements());
	}

	private boolean matchStatement(PythonConstruct realConstruct, List<? extends ASTNode> snippetStatements) {
		for(int i=0; i<snippetStatements.size(); i++){
			if(!matchStatement((Statement)realConstruct.node(), (Statement)snippetStatements.get(i))) return false;
			realConstruct = getNextSibling(realConstruct);
		}
		return true;
	}

	private PythonConstruct getNextSibling(PythonConstruct realConstruct) {
		return realConstruct;
	}

	private boolean matchStatement(Statement real, Statement snippet) {
		if(real.getKind() != snippet.getKind()) return false;
		if(snippet instanceof VariableReference) {
			fillWildExpression((VariableReference) snippet, real);
			return true;
		}
		if(snippet instanceof Block && isWildBlock(snippet) && real.getChilds().size()>0){
			VariableReference wildAction = (VariableReference) ((Block) snippet).getChilds().get(0);
			fillWildExpression(wildAction, real);
			return true;
		}
		return matchNodeList(real.getChilds(), snippet.getChilds());
	}

	private boolean isWildBlock(Statement snippet){
		List snippetChilds = snippet.getChilds();
		return(snippetChilds.size() == 1 && snippetChilds.get(0) instanceof VariableReference);
	}

	private boolean matchNodeList(List<? extends ASTNode> realChilds, List<? extends ASTNode> snippetChilds) {
		if(realChilds.size() != snippetChilds.size()) return false;
		for(int i=0; i<realChilds.size(); i++){
			ASTNode real = realChilds.get(i);
			ASTNode snippet = snippetChilds.get(i);
			if(real instanceof Statement && snippet instanceof Statement){
				if(!matchStatement((Statement)real, (Statement)snippet)) return false;
			}else
			if(real.getClass() != snippet.getClass()){
				return false;
			}
		}
		return true;
	}

	private void fillWildExpression(VariableReference snippet, Statement real) {
		matchMemory.put(((VariableReference)snippet).getName(), real);
	}

	/**
	 * We do suppose there was successful match before
	 * 
	 * @return
	 */
	public String apply(PythonConstruct node) {
		return destination;
	}
}
