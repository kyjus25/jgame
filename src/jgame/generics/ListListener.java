package jgame.generics;

import java.util.ArrayList;

public interface ListListener<T> {
    void changed(ArrayList<T> obj, T item);
}