package jgame.generics;

public class Field<T> {
    private T value;

    public Field() {}

    public Field(T value) {
    	this.set(value);
    }

    public T get() {
        return this.value;
    }

    public void set(T value) {
        this.value = value;
       
    }
}
