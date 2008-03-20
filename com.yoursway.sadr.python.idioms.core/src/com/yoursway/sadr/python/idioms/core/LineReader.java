package com.yoursway.sadr.python.idioms.core;

public class LineReader {
	public String[] lines;
	public int readLines;
	
	public LineReader(String body) {
		lines = body.split("\n");
		readLines = 0;
	}
	
	public String readUntil(String line){
		StringBuffer collected = new StringBuffer();
		while(readLines < lines.length){
			if(lines[readLines++].equals(line)){
				return collected.toString();
			}else{
				if(collected.length() != 0){
					collected.append("\n");
				}
				collected.append(lines[readLines-1]);
			}
		}
		return collected.toString();
	}
	
	public boolean eof() {
		return readLines == lines.length;
	}
}