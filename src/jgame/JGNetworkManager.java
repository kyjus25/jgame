package jgame;

import jgame.generics.Field;
import jgame.generics.FieldList;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JGNetworkManager {

    private Socket socket;
    private BufferedReader enter;
    private BufferedWriter leave;

    public Field<Boolean> running = new Field<>(false);
    public JGUser self = new JGUser("Justin");

    public FieldList<JGUser> users = new FieldList<>();

    public JGNetworkManager() {
        try {
            socket = new Socket("localhost", 80);
            enter = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            leave = new BufferedWriter(new PrintWriter(socket.getOutputStream()));
            running.set(true);
            submit("NICK " + self.nick.get());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        hearServer();
                    } catch (Exception e) {
                    }
                }
            }).start();
        } catch (Exception e) {
            System.out.println("Server not responding");
        }
    }

    private void hearServer() {
        initializeHeartBeat();
        while (running.get()) {
            String packet = recieve();
            if (packet.startsWith("LIST")) {
                users.clear();
                for (int i = 1; i < packet.split("[ ]").length; i++) {
                    users.add(new JGUser(packet.split("[ ]")[i]));
                }
            }
            if (packet.startsWith("500") || packet.startsWith("400")) { running.set(false); }
            if (!packet.isEmpty() && !packet.startsWith("Room") && !packet.startsWith("OK")) { handlePacket(packet); }
        }
    }

    public void submit(String var) {
        if (!var.isEmpty()) {
            try {
                leave.write(var + "\n");
                leave.flush();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

    public void submit(String type, double newX, double newY, String uuid) {
        submit(type + " " + newX + " " + newY + " " + uuid + " \n");
    }

    private String recieve() {
        String s = "";
        try {
            s = enter.readLine();
        } catch (IOException ex) {

        }
        return s;
    }

    public int getPlayerNumber() {
        int playerNumber = 0;
        // System.out.println("Connected Users " + getConnectedUsers().size());
        for (int i = 0; i < getConnectedUsers().size(); i ++) {
            if (getConnectedUsers().get(i).nick.get().equals(self.nick.get())) {
                playerNumber = i + 1;
            }
        }

        if (playerNumber == 0) {
            JGame.stage.get().close();
        }

        return playerNumber;
    }

    private void handlePacket(String packet) {

        System.out.println("Got Packet: " + packet);

//        if (packet.contains(":") && !packet.startsWith(nick)) {
//            System.out.println(packet);
//            // if (packet.contains(":")) {
//            // System.out.println(packet);
//            String[] splitter = packet.split(" ");
//            String type = splitter[1];
//            String posX = splitter[2];
//            String posY = splitter[3];
//            String uuid = splitter[4];
//
//            if (uuid.equals(nick)) {
//                type = "player";
//            }
//
//            JGame.spriteManager.handlePacket(type, posX, posY, uuid);
//        }
    }

    public List<JGUser> getConnectedUsers() {
        return users.stream().filter(i -> !i.nick.get().equals("Host")).collect(Collectors.toList());
    }

    private void initializeHeartBeat() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long heartbeat = System.currentTimeMillis();
                while (true) {
                    if (System.currentTimeMillis() - heartbeat >= 5000) {
                        submit("BEAT " + System.currentTimeMillis());
                        heartbeat = System.currentTimeMillis();
                    }
                }
            }
        }).start();
    }
}
