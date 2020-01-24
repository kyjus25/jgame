package jgame;

import javafx.event.ActionEvent;
import jgame.generics.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class JGNetworkManager extends CommonControls {

    private JGServer server;

    private Socket socket;
    private BufferedReader enter;
    private BufferedWriter leave;

    public Field<Boolean> running = new Field<>(false);
    public JGUser self = new JGUser("Grant");
    public Field<Boolean> hosting = new Field<>(false);

    public FieldList<JGUser> users = new FieldList<>();

    public FieldList<String> packetStream = new FieldList<>();
    private static List<NetworkEvent> listeners = new ArrayList<>();
    public HashMap<String, JGCreateRequest> lastKnownPos = new HashMap<>();

    private JGScene activeScene;

    public Field<String> playerSprite = new Field<>();
    public Field<String> networkSprite = new Field<>();

    public JGNetworkManager() {

        JGame.sceneManager.activeScene.addEventHandler(scene -> {
            activeScene = scene;
            playerSprite.set(activeScene.playerSprite.get());
            networkSprite.set(activeScene.networkSprite.get());
            if (hosting.get()) {
                sendAll("SCENE " + scene.name.get());
            }
        });

        if (hosting.get()) {

            server = new JGServer();
            new Thread(server).start();

        } else {

            JGame.spriteManager.activeSprites.addEventHandler((sprites, i) -> {
                sprites.forEach(sprite -> {
                    if (!sprite.type.get().equals(playerSprite.get())) {
                        sprite.velocityY.set(0.0);
                        sprite.velocityX.set(0.0);
                    }
                });
            });

        }

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
            JGame.exit();
        }
    }

    private void hearServer() {
        initializeHeartBeat();
        while (running.get()) {
            String packet = recieve();

            if (!packet.contains("enemy") && !packet.contains("arrow")) {
                System.out.println(packet);
            }

            if (packet.startsWith("LISTUSERS")) {
                users.clear();

                for (int i = 1; i < packet.split("[ ]").length; i++) {
                    users.add(new JGUser(packet.split("[ ]")[i]));
                }

                List<JGSprite> activeUsers = JGame.spriteManager.getSpritesByType(networkSprite.get());
                activeUsers.forEach(activeUser -> {
                    List<JGUser> foundUser = users.stream().filter(p -> p.nick.get().equals(activeUser.uuid.get())).collect(Collectors.toList());
                    if (foundUser.size() == 0) {
                        JGame.spriteManager.deleteSprite(activeUser);
                    }
                });
            }
            if (packet.startsWith("EVENT")) {
                if (packet.split("[ ]")[1].equals("DELETE")) {
                    JGSprite sprite = JGame.spriteManager.getSpriteByUUID(packet.split("[ ]")[2]);
                    JGame.spriteManager.deleteSprite(sprite);
                } else {
                    listeners.forEach((item) -> {
                        String substring = packet.split("EVENT " + packet.split("[ ]")[1] + " ")[1];
                        item.changed(packet.split("[ ]")[1], substring);
                    });
                }
            }
            if (packet.startsWith("500") || packet.startsWith("400")) { running.set(false); }
            if (!packet.isEmpty() && !packet.startsWith("OK") && !packet.startsWith("EVENT")) { packetStream.add(packet); }
        }
    }

    public void sendAll(String s) {
        if (hosting.get()) {
            server.sendAll(s);
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

    public void send(String type, double newX, double newY, String uuid) {
        submit(type + " " + newX + " " + newY + " " + uuid + " \n");
    }

    public void sendEvent(String eventType, String data) {
        if (hosting.get()) {
            submit("EVENT" + " " + eventType + " " + data + " \n");
        }
    }

    public void sendDelete(String uuid) {
        if (hosting.get()) {
            sendEvent("DELETE", uuid);
        }
    }

    public void sendAll(String type, double newX, double newY, String uuid, boolean savePos) {
        // TELL THE HOST TO CREATE IT
        submit(type + " " + newX + " " + newY + " " + uuid + " SENDHOST " + savePos + " \n");
    }

    public void sendAll(String type, double newX, double newY, String uuid) {
        // TELL THE HOST TO CREATE IT
        submit(type + " " + newX + " " + newY + " " + uuid + " SENDHOST true" + " \n");
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
        for (int i = 0; i < users.size(); i ++) {
            if (users.get(i).nick.get().equals(self.nick.get())) {
                playerNumber = i + 1;
            }
        }

        return playerNumber;
    }

    public void onGameLoop(ActionEvent e) {

        // HANDLE OUTGOING TRAFFIC
        JGame.spriteManager.activeSprites.forEach(sprite -> {
            String type = sprite.type.get();
            if ((hosting.get() && !type.equals(networkSprite.get())) || type.equals(playerSprite.get())) {

                if (sprite.type.get().equals(playerSprite.get())) {
                    type = networkSprite.get();
                }
                String posX = sprite.positionX.get().toString();
                String posY = sprite.positionY.get().toString();
                String uuid = sprite.uuid.get();
                if (!lastKnownPos.containsKey(uuid)) {
                    lastKnownPos.put(uuid, new JGCreateRequest(self.nick.get(), type, posX, posY, uuid));
                    submit(type + " " + posX + " " + posY + " " + uuid);
                } else {
                    String oldPosX = lastKnownPos.get(sprite.uuid.get()).posX.get();
                    String oldPosY = lastKnownPos.get(sprite.uuid.get()).posY.get();
                    if (!posX.equals(oldPosX) || !posY.equals(oldPosY)) {
                        lastKnownPos.put(uuid, new JGCreateRequest(self.nick.get(), type, posX, posY, uuid));
                        submit(type + " " + posX + " " + posY + " " + uuid);
                    }
                }
            }
        });



        // HANDLE INCOMING TRAFFIC

        if (packetStream.size() > 0) {
            List<String> packets = new ArrayList<>(packetStream);
            packetStream.clear();
            packets.forEach(packet -> {
                if (packet != null) {
                    if (packet.startsWith("SCENE") && !hosting.get()) {
                        String sceneName = packet.split("[ ]")[1];
                        JGame.sceneManager.changeSceneByName(sceneName);
                    }

                    if (packet.contains(":") && !packet.startsWith(self.nick.get())) {

                        // if (packet.contains(":")) {
                        // System.out.println(packet);
                        String[] splitter = packet.split(" ");
                        String type = splitter[1];
                        String posX = splitter[2];
                        String posY = splitter[3];
                        String uuid = splitter[4];

                        if (uuid.equals(self.nick.get())) {
                            type = playerSprite.get();
                        }

                        JGSprite sprite = JGame.spriteManager.getSpriteByUUID(uuid);
                        if (sprite != null) {
                            // Update it
                            sprite.positionX.set(Double.parseDouble(posX));
                            sprite.positionY.set(Double.parseDouble(posY));
                        } else {
                            // Create it
                            String finalType = type;
                            JGSceneManager.activeScene.get().layers.forEach(layer -> {
                                try {
                                    JGSprite newSprite = layer.create(finalType);
                                    if (newSprite != null) {
                                        if (!posX.equals("EMPTY")) { newSprite.positionX.set(Double.parseDouble(posX)); }
                                        if (!posY.equals("EMPTY")) { newSprite.positionY.set(Double.parseDouble(posY)); }
                                        if (!uuid.equals("EMPTY")) {newSprite.uuid.set(uuid); }
                                        layer.addToLayer(newSprite, true);
                                        if (!hosting.get()) { newSprite.addSpriteToManager(true); }
                                    }
                                } catch (Exception r) {}
                            });
                        }
                    }
                }
            });
        }
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

    public static void addEventHandler(NetworkEvent toAdd) {
        listeners.add(toAdd);
    }

    public void reset() {
        listeners.clear();
    }
}
