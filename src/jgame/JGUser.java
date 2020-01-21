package jgame;

import jgame.generics.Field;

public class JGUser {
    public Field<String> nick = new Field<>("Justin");

    public JGUser(String n) {
        nick.set(n);
    }
}
