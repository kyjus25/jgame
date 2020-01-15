package jgame.generics;

import java.util.ArrayList;
import java.util.List;

public class Field<T> {
    private T value;
    private List<FieldListener<T>> listeners = new ArrayList<FieldListener<T>>();

    public Field() {}

    public Field(T value) {
    	this.set(value);
    }

    public T get() {
        return this.value;
    }

    public void set(T value) {
        this.value = value;
//    	System.out.println("Value Changing " + this.getClass());
        listeners.forEach((n) -> n.changed(this.get())); 
        
    }
    
    public void addListener(FieldListener<T> toAdd) {
    	System.out.println("Listener Added");
        listeners.add(toAdd);
        if (this.get() != null) {
        	toAdd.changed(this.get()); 
        }
    }
}
