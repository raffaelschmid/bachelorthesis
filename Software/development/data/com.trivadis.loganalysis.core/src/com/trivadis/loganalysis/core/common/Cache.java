/**
 * Copyright (c) 2011 Loganalysis team and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Raffael Schmid - initial API and implementation
 */
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
	protected abstract V create(K k);
	protected abstract void dispose(V v);

	public Collection<V> values() {
		return cache.values();
	}
}
