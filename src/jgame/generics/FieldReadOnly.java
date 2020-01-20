package jgame.generics;

public class FieldReadOnly<T> {
    private T value;

    public FieldReadOnly(T value) {
    	this.value = value;
    }

    public T get() {
        return this.value;
    }
}
