package jgame;

import jgame.generics.Field;
import jgame.generics.FieldList;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
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
        sendList();
    }

    public void sendList() {
        System.out.println("Getting users");
        String nicks = users.stream().map(u -> u.nick.get()).collect(Collectors.joining(" "));
        System.out.println("nicks: " + nicks);
        sendAll("LISTUSERS " + nicks);
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
            lastKnownPos.put(i.uuid.get(), i);
//            System.out.println("could send " + sendQueue.size() + ": " + sendQueue);
//            System.out.println(i.type.get());
            sendAll(i.sender.get() + ": " + i.type.get() + " " + i.posX.get() + " " + i.posY.get() + " " + i.uuid.get());
            queue.removeAll(queue);
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


