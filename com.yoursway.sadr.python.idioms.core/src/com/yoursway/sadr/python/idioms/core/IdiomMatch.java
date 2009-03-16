package com.yoursway.sadr.python.idioms.core;

import java.util.List;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ast.statements.Statement;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yoursway.sadr.python.constructs.PythonConstruct;

@SuppressWarnings("unchecked")
public class IdiomMatch {
	private Map<String, ASTNode> matchMemory;
	private final ModuleDeclaration parsedSource;
	private final ModuleDeclaration parsedDestination;
	private PythonConstruct realConstruct;

	IdiomMatch(ModuleDeclaration parsedSource,
			ModuleDeclaration parsedDestination) {
		this.parsedSource = parsedSource;
		this.parsedDestination = parsedDestination;
	}

	public boolean match(PythonConstruct realConstruct) {
		this.realConstruct = realConstruct;
		matchMemory = Maps.newHashMap();
		List<ASTNode> childs = realConstruct.parentScope().node().getChilds();
		int size = parsedSource.getStatements().size();
		ASTNode first = realConstruct.node();
		List<ASTNode> slice = sliceNodes(childs, first, size);
		return matchNodeList(slice, parsedSource.getStatements(), true);
	}

	private List<ASTNode> sliceNodes(List<ASTNode> childs, ASTNode first,
			int size) {
		List<ASTNode> after = Lists.newArrayList();
		boolean start = false;
		for (ASTNode node : childs) {
			if (node == first) {
				start = true;
			}
			if (start)
				after.add(node);
			if (after.size() == size)
				break;
		}
		return after;
	}

	private boolean matchStatement(Statement real, Statement snippet, boolean wild) {
		if (wild && isWildExpression(snippet)) {
			return fillPlaceholder((VariableReference) snippet, real);
		}
		if (wild && isWildBlock(snippet) && real.getChilds().size() > 0) {
			VariableReference name = getWildBlockName(snippet);
			return fillPlaceholder(name, real);
		}
		if (real.getKind() != snippet.getKind())
			return false;
		if (real instanceof VariableReference && snippet instanceof VariableReference){
			String snippetVarName = ((VariableReference) snippet).getName();
			return ((VariableReference) real).getName().equals(snippetVarName);
		}
		return matchNodeList(real.getChilds(), snippet.getChilds(), wild);
	}

	private VariableReference getWildBlockName(Statement snippet) {
		return (VariableReference) ((Block) snippet).getChilds().get(0);
	}

	private boolean isWildExpression(Statement snippet) {
		return snippet instanceof VariableReference;
	}

	private boolean isWildBlock(ASTNode snippet) {
		if (!(snippet instanceof Block))
			return false;
		List<ASTNode> children = snippet.getChilds();
		return (children.size() == 1 && children.get(0) instanceof VariableReference);
	}

	private boolean matchNodeList(List<ASTNode> realChilds,
			List<ASTNode> snippetChilds, boolean wild) {
		if (realChilds.size() < snippetChilds.size())
			return false;
		for (int i = 0; i < snippetChilds.size(); i++) {
			ASTNode real = realChilds.get(i);
			ASTNode snippet = snippetChilds.get(i);
			if (real instanceof Statement && snippet instanceof Statement) {
				if (!matchStatement((Statement) real, (Statement) snippet, wild))
					return false;
			} else if (real.getClass() != snippet.getClass()) {
				return false;
			}
		}
		return true;
	}

	private boolean fillPlaceholder(VariableReference name, Statement real) {
		Statement previous = (Statement) matchMemory.get(name.getName());
		if (previous != null && !matchStatement(previous, real, false)) return false;
		matchMemory.put(name.getName(), real);
		return true;
	}

	public String apply() {
		return null;
	}
}
