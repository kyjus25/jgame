package jgame;

import jgame.generics.Field;
import jgame.generics.FieldList;
import jgame.generics.ListListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

class JGCreateRequest {
    Field<String> sender = new Field<>();
    Field<String> type = new Field<>();
    Field<String> posX = new Field<>();
    Field<String> posY = new Field<>();
    Field<String> uuid = new Field<>();
    public JGCreateRequest(String sender, String type, String posX, String posY, String uuid) {
        this.sender.set(sender);
        this.type.set(type);
        this.posX.set(posX);
        this.posY.set(posY);
        this.uuid.set(uuid);
    }
}

public class JGServer implements Runnable  {
    public JGServer server;
    public Field<Boolean> running = new Field<>(false);
    public FieldList<JGUser> users = new FieldList<>();
    public FieldList<JGCreateRequest> sendQueue = new FieldList<>();
    public HashMap<String, JGCreateRequest> lastKnownPos = new HashMap<>();

//    public static void main(String[] args) throws IOException {
//        new Thread(new JGServer()).start();
//    }

    public void addUser(JGUser u) {
        users.add(u);
    }

    public void disconnectUser(JGUser u) {
        users.remove(u);
        sendUserList();
    }

    public void sendUserList() {
        System.out.println("Getting users");
        String nicks = users.stream().map(u -> u.nick.get()).collect(Collectors.joining(" "));
        System.out.println("nicks: " + nicks);
        sendAll("LISTUSERS " + nicks);
    }

    public void sendLastKnownPositions(String nick) {

        JGCreateRequest player = lastKnownPos.get(nick);

        lastKnownPos.forEach((list, i) -> {
            sendQueue.add(i);
        });

        if (player == null) {
            // TODO CHANGE THIS
            JGCreateRequest newPlayer = new JGCreateRequest("Host", "paddle", "EMPTY", "EMPTY", nick);
            sendQueue.add(newPlayer);
        }

        // System.out.println(player);
//        lastKnownPos.forEach((list, i) -> {
//
//        });
//        sendQueue.add()
    }

    public void sendAll(String s, String sender) {
        users.forEach(u -> {
            if (!u.nick.get().equals(sender)) {
                u.submit(s);
            }
        });
    }
    public void sendAll(String s) {
        users.forEach(u -> {
            u.submit(s);
        });
    }

    public JGCreateRequest getJGCreateRequest(String sender, String packet) {
        String[] splitter = packet.split(" ");
        String type = splitter[0];
        String posX = splitter[1];
        String posY = splitter[2];
        String uuid = splitter[3];
        return new JGCreateRequest(sender, type, posX, posY, uuid);
    }

    public void listenSendQueue() {
        sendQueue.addEventHandler((queue, i) -> {
            if (queue.size() > 0) {
                List<JGCreateRequest> copy = new ArrayList<>(queue);
                queue.clear();
                copy.forEach(item -> {
                    lastKnownPos.put(i.uuid.get(), i);
                    sendAll(i.sender.get() + ": " + i.type.get() + " " + i.posX.get() + " " + i.posY.get() + " " + i.uuid.get(), i.sender.get());
                });

            }
        });
    }

    @Override
    public void run() {
        try {
            System.out.println("Starting server");
            ServerSocket ss = new ServerSocket(80);
            running.set(true);
            while (running.get()) {
                listenSendQueue();
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


