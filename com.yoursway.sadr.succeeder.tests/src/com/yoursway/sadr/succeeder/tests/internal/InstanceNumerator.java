/**
 * 
 */
package com.yoursway.sadr.succeeder.tests.internal;

import java.util.Map;

class InstanceNumerator {
	private static Map<Class<?>, Integer> instances;

	static int generateNumber(Class<?> klass) {
		int value = 0;
		if(instances.containsKey(klass)){
			value = instances.get(klass);
		}
		instances.put(klass, value+1);
		return value+1;
	}
}