package jgame.generics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FieldList<T> extends ArrayList<T> {
	private List<ListListener<T>> listeners = new ArrayList<ListListener<T>>();
	
	public boolean add(T value) {
		boolean response = super.add(value);
		listeners.forEach((n) -> n.changed(this, value));
		return response;
	}

//	public boolean addAll(T value) {
//		boolean response = super.addAll((Collection<? extends T>) value);
//		listeners.forEach((n) -> n.changed(this, value));
//		return response;
//	}
	
//	public boolean remove(Object o) {
//		System.out.println("removing " + o);
//		boolean response = super.remove(o);
//		listeners.forEach((n) -> n.changed(this));
//		System.out.println("this " + this);
//		return response;
//	}
	
    public void addEventHandler(ListListener<T> toAdd) {
//    	System.out.println("Listener Added");
    	listeners.add(toAdd);
        if (this != null) {
        	toAdd.changed(this, null); 
        }
    }
}


