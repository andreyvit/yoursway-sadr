/**
 * 
 */
package com.yoursway.sadr.succeeder.tests.internal;

import java.util.Map;

import com.google.common.collect.Maps;

class InstanceNumerator {
	private static Map<Class<?>, Integer> instances = Maps.newHashMap();

	static int generateNumber(Class<?> klass) {
		int value = 0;
		if(instances.containsKey(klass)){
			value = instances.get(klass);
		}
		instances.put(klass, value+1);
		return value+1;
	}
}