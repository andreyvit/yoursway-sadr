package com.yoursway.sadr.python.idioms.core;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.compiler.problem.IProblem;
import org.eclipse.dltk.compiler.problem.IProblemReporter;
import org.eclipse.dltk.python.internal.core.parser.PythonSourceParser;

public class SnippetParser {

	public static ModuleDeclaration parseSnippet(String source) {
		PythonSourceParser parser = new PythonSourceParser();
		ModuleDeclaration rootNode = parser.parse(null, source.toCharArray(),
				new IProblemReporter() {
					public void reportProblem(IProblem problem) {
						throw new RuntimeException("OMFG!");
					}
	
				});
		return rootNode;
	}

}