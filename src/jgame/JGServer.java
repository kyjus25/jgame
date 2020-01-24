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
    Field<Boolean> savePos = new Field<>(true);
    Field<Boolean> deleted = new Field<>(false);
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
        String nicks = users.stream().map(u -> u.nick.get()).collect(Collectors.joining(" "));
        sendAll("LISTUSERS " + nicks);
    }

    public void sendLastKnownPositions(String nick) {

        JGCreateRequest player = lastKnownPos.get(nick);

        lastKnownPos.forEach((list, i) -> {
            if (!i.deleted.get() || (i.deleted.get() && (i.type.get().equals(JGame.networkManager.networkSprite.get()) || i.type.get().equals(JGame.networkManager.playerSprite.get())))) {
                i.sender.set("Host");
                sendQueue.add(i);
            }
        });

        if (player == null) {
            JGCreateRequest newPlayer = new JGCreateRequest("Host", JGame.networkManager.networkSprite.get(), "EMPTY", "EMPTY", nick);
            sendQueue.add(newPlayer);
        }
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

    public void sendHost(String s) {
        users.get(0).submit(s);
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

                    if (i.savePos.get() && i.uuid.get() != null) {
                        JGCreateRequest entry = lastKnownPos.get(i.uuid.get());
                        if (entry == null || (entry != null && !entry.deleted.get())) {
                            lastKnownPos.put(i.uuid.get(), i);
                        }
                    }

                    if (i.sender.get().equals("HostOnly")) {
                        i.sender.set("Host");
                        sendHost(i.sender.get() + ": " + i.type.get() + " " + i.posX.get() + " " + i.posY.get() + " " + i.uuid.get());
                    } else {
                        if (i.type.get() != null && i.type.get().contains("EVENT")) {
                            if (i.type.get().contains("DELETE")) {

                                JGCreateRequest entry = lastKnownPos.get(i.type.get().split("[ ]")[2]);
                                if (entry != null) {
                                    entry.deleted.set(true);
                                    lastKnownPos.put(entry.uuid.get(), entry);
                                }

                            }
                            sendAll(i.type.get());
                        } else {
                            sendAll(i.sender.get() + ": " + i.type.get() + " " + i.posX.get() + " " + i.posY.get() + " " + i.uuid.get(), i.sender.get());
                        }
                    }
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


