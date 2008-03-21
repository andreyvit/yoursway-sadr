package com.yoursway.sadr.python.idioms.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.yoursway.sadr.python.core.typeinferencing.constructs.PythonConstruct;

public class Idiom {
	private static final String NAME = "\"\"\"";
	private static final String SOURCE = "#source";
	private static final String DEST = "#destination";
	String name;
	IdiomMatcher matchers[];

	public Idiom(String name) throws IOException{
		List<IdiomMatcher> bodies = new ArrayList<IdiomMatcher>();
		String body = IdiomsCollection.readFile(name);
		LineReader reader = new LineReader(body);
		reader.readUntil(NAME);
		this.name = reader.readUntil(NAME);
		reader.readUntil(SOURCE);
		while(!reader.eof()){
			String source = reader.readUntil(DEST)+"\n";
			String dest = reader.readUntil(SOURCE)+"\n";
			bodies.add(new IdiomMatcher(source, dest));
		}
		matchers = bodies.toArray(new IdiomMatcher[bodies.size()]);
	}

	public IdiomMatch match(PythonConstruct construct) {
		for(IdiomMatcher matcher: matchers){
			IdiomMatch match = matcher.match(construct);
			if(match != null) return match;
		}
		return null;
	}
}