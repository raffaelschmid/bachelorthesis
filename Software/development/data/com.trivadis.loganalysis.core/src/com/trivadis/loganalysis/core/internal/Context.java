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
package com.trivadis.loganalysis.core.internal;

import com.trivadis.loganalysis.core.IContentReader;
import com.trivadis.loganalysis.core.IContext;

/**
 * @author els
 * 
 */
public class Context implements IContext {
	private final IContentReader contentReader;

	public Context() {
		this.contentReader = new ContentReader();
	}

	
	public IContentReader contentReader() {
		return this.contentReader;
	}

}
