package com.trivadis.loganalysis.core.common;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class Cache<K,V> {

	private final Map<K, V> cache = new HashMap<K, V>();
	
	public final V get(K k) {
		V v = cache.get(k);
		if (v == null) {
			v = create(k);
			cache.put(k, v);
		}
		return v;
	}

	public final void dispose() {
		for(V image : cache.values()){
			dispose(image);
		}
	}
	public abstract V create(K k);
	public abstract void dispose(V v);

	public Collection<V> values() {
		return cache.values();
	}
}
