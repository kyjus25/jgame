package jgame.generics;

import java.util.ArrayList;
import java.util.List;

public class FieldEvent<T> extends Field<T> {
    private List<FieldListener<T>> listeners = new ArrayList<FieldListener<T>>();
    
    public FieldEvent() {}

    public FieldEvent(T value) {
    	set(value);
    }
    
    public void set(T value) {
    	super.set(value);
        List<FieldListener<T>> copy = new ArrayList<FieldListener<T>>();
        copy.addAll(listeners);
        copy.forEach((n) -> n.changed(this.get()));
    }
    
    public void addEventHandler(FieldListener<T> toAdd) {
        listeners.add(toAdd);
        if (super.get() != null) {
        	toAdd.changed(super.get()); 
        }
    }
}
