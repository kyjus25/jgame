package jgame;

import jgame.generics.Field;
import jgame.generics.FieldList;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.stream.Collectors;

public class JGServer implements Runnable  {
    public JGServer server;
    public Field<Boolean> running = new Field<>(false);
    public FieldList<JGUser> users = new FieldList<>();

//    public static void main(String[] args) throws IOException {
//        new Thread(new JGServer()).start();
//    }

    public void addUser(JGUser u) {
        users.add(u);
    }

    public void disconnectUser(JGUser u) {
        users.remove(u);
        sendList();
    }

    public void sendList() {
        System.out.println("Getting users");
        String nicks = users.stream().map(u -> u.nick.get()).collect(Collectors.joining(" "));
        System.out.println("nicks: " + nicks);
        sendAll("LIST " + nicks);
    }

    public void sendAll(String s) {
        users.forEach(u -> {
            u.submit(s);
        });
    }

    @Override
    public void run() {
        try {
            System.out.println("Starting server");
            ServerSocket ss = new ServerSocket(80);
            running.set(true);
            while (running.get()) {
                Socket socket = ss.accept();
                System.out.println("Connection established" + socket.getInetAddress().getHostAddress());
                JGUser user = new JGUser(socket, this);
                addUser(user);
                new Thread(user).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


