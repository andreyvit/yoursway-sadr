package com.yoursway.sadr.python.idioms.core;

import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.statements.Statement;

public class TreePrinter {
	public static void printTree(List<ASTNode> slice) {
		System.out.println("RootNode");
		for (ASTNode node : slice) {
			printTree(node, 1);
		}
	}

	public static void printTree(ASTNode node) {
		printTree(node, 0);
		
	}

	private static void printTree(ASTNode node, int indent) {
		for (int i = 0; i < indent; i++) {
			System.out.print("  ");
		}
		System.out.print(node.getClass().getSimpleName());
		if (node instanceof Statement) {
			System.out.print(" [" + ((Statement) node).getKind()+"]");
		}
		System.out.println("");
		List<ASTNode> childs = (List<ASTNode>) node.getChilds();
		for (ASTNode child : childs) {
			printTree(child, indent + 1);
		}
	}

}
