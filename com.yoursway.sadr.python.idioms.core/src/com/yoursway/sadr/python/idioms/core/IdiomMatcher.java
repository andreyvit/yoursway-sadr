package com.yoursway.sadr.python.idioms.core;

import java.util.List;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.python.parser.ast.expressions.Assignment;

import com.google.common.collect.Maps;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;

public class IdiomMatcher {
	private final String source;
	private final String destination;
	private ModuleDeclaration parsedSource;
	private ModuleDeclaration parsedDestination;

	public IdiomMatcher(String source, String destination) {
		this.source = source;
		this.destination = destination;
		this.parsedSource = SnippetParser.parseSnippet(source);
		this.parsedDestination = SnippetParser.parseSnippet(destination);
	}

	public IdiomMatch match(PythonConstruct realConstruct) {
		IdiomMatch idiomMatch = new IdiomMatch(parsedSource, parsedDestination);
		if(idiomMatch.match(realConstruct)) return idiomMatch;
		return null;
	}
}
