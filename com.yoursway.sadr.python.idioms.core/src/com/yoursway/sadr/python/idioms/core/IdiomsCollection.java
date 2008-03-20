package com.yoursway.sadr.python.idioms.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;

import com.google.common.collect.Lists;
import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;

public class IdiomsCollection {
	
	private static final String PATH = "/snippets/";
	private static IdiomsCollection INSTANCE;

	public static IdiomsCollection getInstance() {
		if(null == IdiomsCollection.INSTANCE){
			INSTANCE = new IdiomsCollection();
		}
		return INSTANCE;
	}

	private Map<String, Idiom> idiomList;
	
	IdiomsCollection() {
		
	}
	
	protected Collection<Idiom> getAllIdioms() {
		return idiomList.values();
	}
	
	protected Idiom getIdiom(String name){
		addIdiom(name);
		return idiomList.get(name);
	}

	public void addIdiom(String name){
		if(!idiomList.containsKey(name)){
			Idiom idiom = loadIdiom(name);
			idiomList.put(name, idiom);
		}
	}
	
	private Idiom loadIdiom(String name) {
		try {
			return new Idiom(name);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String readFile(String name) throws IOException {
		InputStream stream = Activator.openResource(PATH+name+".py");
		return Activator.readAndClose(stream);
	}

	List<IdiomMatcher> findAvailableIdiomsAt(PythonConstruct construct) {
		List<IdiomMatcher> applicable = Lists.newArrayList();
		for(Idiom idiom: getAllIdioms()){
			IdiomMatcher match = idiom.match(construct);
			if(match != null){
				applicable.add(match);
			}
		}
		return applicable;
	}
}
