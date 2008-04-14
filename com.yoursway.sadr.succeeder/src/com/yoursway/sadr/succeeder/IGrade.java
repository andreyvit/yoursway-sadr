package com.yoursway.sadr.succeeder;

public interface IGrade<T extends IGrade<?>> extends Comparable<T>{
	
	boolean isDone();
	
}
