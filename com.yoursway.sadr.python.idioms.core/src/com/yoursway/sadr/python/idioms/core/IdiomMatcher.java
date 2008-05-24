package com.yoursway.sadr.python.idioms.core;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;

import com.yoursway.sadr.python_v2.constructs.PythonConstruct;

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

	public void printMatchTree() {
		TreePrinter.printTree(parsedSource.getStatements());
	}
}
