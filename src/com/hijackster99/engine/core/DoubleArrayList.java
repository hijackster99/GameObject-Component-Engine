/**
 * 
 */
package com.hijackster99.engine.core;

import java.util.ArrayList;

/**
 * @author jonat
 *
 */
public class DoubleArrayList<E, F> {

	private ArrayList<E> e;
	private ArrayList<F> f;
	
	public DoubleArrayList(){
		e = new ArrayList<E>();
		f = new ArrayList<F>();
	}
	
	public int size() {
		return e.size();
	}
	
	public E getFirst(int i) {
		return e.get(i);
	}

	public F getSecond(int i) {
		return f.get(i);
	}
	
	public void add(E e, F f) {
		this.e.add(e);
		this.f.add(f);
	}
	
	public void remove(E e, F f) {
		this.e.remove(e);
		this.f.remove(f);
	}
	
	public void removeFirst(E e) {
		int i = this.e.indexOf(e);
		if(i >= 0) {
			this.e.remove(i);
			this.f.remove(i);
		}
	}
	
	public void removeSecond(F f) {
		int i = this.f.indexOf(f);
		if(i >= 0) {
			this.e.remove(i);
			this.f.remove(i);
		}
	}
	
	public void add(int i, E e, F f) {
		this.e.add(i, e);
		this.f.add(i, f);
	}
	
	public void set(int i, E e, F f) {
		this.e.set(i, e);
		this.f.set(i, f);
	}
	
	public void addAll(ArrayList<E> e, ArrayList<F> f) {
		this.e.addAll(e);
		this.f.addAll(f);
	}
	
	public void addAll(int i, ArrayList<E> e, ArrayList<F> f) {
		this.e.addAll(i, e);
		this.f.addAll(i, f);
	}
	
	public boolean isEmpty() {
		return this.e.isEmpty();
	}
	
	public int indexOfFirst(E e) {
		return this.e.indexOf(e);
	}
	
	public int indexOfSecond(F f) {
		return this.f.indexOf(f);
	}
	
	public boolean containsFirst(E e) {
		return this.e.contains(e);
	}

	public boolean containsSecond(F f) {
		return this.f.contains(f);
	}
	
	public boolean containsBoth(E e, F f) {
		return this.e.contains(e) && this.f.contains(f);
	}
	
	public boolean containsAllFirst(ArrayList<E> e) {
		return this.e.containsAll(e);
	}
	
	public boolean containsAllSecond(ArrayList<F> f) {
		return this.f.containsAll(f);
	}
	
	public boolean containsAll(DoubleArrayList<E, F> ef) {
		return this.e.containsAll(ef.getAllFirst()) && this.f.containsAll(ef.getAllSecond());
	}
	
	public ArrayList<E> getAllFirst(){
		return e;
	}
	
	public ArrayList<F> getAllSecond(){
		return f;
	}
	
	public void clear() {
		e.clear();
		f.clear();
	}
	
	@SuppressWarnings("unchecked")
	public DoubleArrayList<E, F> clone() {
		DoubleArrayList<E, F> res = new DoubleArrayList<E, F>();
		res.addAll((ArrayList<E>) e.clone(), (ArrayList<F>) f.clone());
		
		return res;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "First:{" + e.toString() + "}, Second:{" + f.toString() + "}";
	}
	
}
