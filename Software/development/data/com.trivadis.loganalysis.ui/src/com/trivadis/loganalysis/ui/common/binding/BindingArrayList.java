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
package com.trivadis.loganalysis.ui.common.binding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class BindingArrayList<E> implements List<E> {

	private final List<E> delegate;
	private final List<IListChangeListener> listeners = new ArrayList<IListChangeListener>();

	public BindingArrayList(final List<E> delegate) {
		this.delegate = delegate;
	}

	public void addChangeListener(final IListChangeListener listener) {
		listeners.add(listener);
	}

	private void notifyChangeListeners() {
		for (final IListChangeListener selectedFilesListeners : listeners) {
			selectedFilesListeners.listChanged();
		}
	}

	/*-- delegate methods -- */

	public int size() {
		return delegate.size();
	}

	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	public boolean contains(final Object o) {
		return delegate.contains(o);
	}

	public Iterator<E> iterator() {
		return delegate.iterator();
	}

	public Object[] toArray() {
		return delegate.toArray();
	}

	public <T> T[] toArray(final T[] a) {
		return delegate.toArray(a);
	}

	public boolean add(final E o) {
		final boolean retVal = delegate.add(o);
		notifyChangeListeners();
		return retVal;
	}

	public boolean remove(final Object o) {
		final boolean retVal = delegate.remove(o);
		notifyChangeListeners();
		return retVal;
	}

	public boolean containsAll(final Collection<?> c) {
		return delegate.containsAll(c);
	}

	public boolean addAll(final Collection<? extends E> c) {
		final boolean retVal = delegate.addAll(c);
		notifyChangeListeners();
		return retVal;
	}

	public boolean addAll(final int index, final Collection<? extends E> c) {
		final boolean retVal = delegate.addAll(index, c);
		notifyChangeListeners();
		return retVal;
	}

	public boolean removeAll(final Collection<?> c) {
		final boolean retVal = delegate.removeAll(c);
		notifyChangeListeners();
		return retVal;
	}

	public boolean retainAll(final Collection<?> c) {
		return delegate.retainAll(c);
	}

	public void clear() {
		delegate.clear();
		notifyChangeListeners();
	}

	@Override
	public boolean equals(final Object o) {
		return delegate.equals(o);
	}

	@Override
	public int hashCode() {
		return delegate.hashCode();
	}

	public E get(final int index) {
		return delegate.get(index);
	}

	public E set(final int index, final E element) {
		return delegate.set(index, element);
	}

	public void add(final int index, final E element) {
		delegate.add(index, element);
	}

	public E remove(final int index) {
		final E retVal = delegate.remove(index);
		notifyChangeListeners();
		return retVal;
	}

	public int indexOf(final Object o) {
		return delegate.indexOf(o);
	}

	public int lastIndexOf(final Object o) {
		return delegate.lastIndexOf(o);
	}

	public ListIterator<E> listIterator() {
		return delegate.listIterator();
	}

	public ListIterator<E> listIterator(final int index) {
		return delegate.listIterator(index);
	}

	public List<E> subList(final int fromIndex, final int toIndex) {
		return delegate.subList(fromIndex, toIndex);
	}

}
