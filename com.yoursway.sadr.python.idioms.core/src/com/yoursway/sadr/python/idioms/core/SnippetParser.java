package com.yoursway.sadr.python.idioms.core;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.parser.ISourceParser;
import org.eclipse.dltk.compiler.problem.IProblem;
import org.eclipse.dltk.compiler.problem.IProblemReporter;
import org.eclipse.dltk.python.internal.core.parser.PythonSourceParser;

public class SnippetParser {

	public static ModuleDeclaration parseSnippet(String source) {
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

}