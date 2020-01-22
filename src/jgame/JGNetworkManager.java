package jgame;

import javafx.event.ActionEvent;
import jgame.generics.CommonControls;
import jgame.generics.Field;
import jgame.generics.FieldEvent;
import jgame.generics.FieldList;

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
    public JGUser self = new JGUser("Guy2");
    public Field<Boolean> hosting = new Field<>(false);

    public FieldList<JGUser> users = new FieldList<>();

    public FieldEvent<String> packetStream = new FieldEvent<>();
    public HashMap<String, JGCreateRequest> lastKnownPos = new HashMap<>();

    public JGNetworkManager() {

        if (hosting.get()) {
            server = new JGServer();
            new Thread(server).start();

            JGame.sceneManager.activeScene.addEventHandler(scene -> {
                sendAll("SCENE " + scene.name.get());
            });

        } else {

            JGame.spriteManager.activeSprites.addEventHandler((sprites, i) -> {
                sprites.forEach(sprite -> {
                    if (!sprite.type.get().equals("player")) {
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
            if (packet.startsWith("LISTUSERS")) {
                users.clear();
                for (int i = 1; i < packet.split("[ ]").length; i++) {
                    users.add(new JGUser(packet.split("[ ]")[i]));
                }
            }
            if (packet.startsWith("500") || packet.startsWith("400")) { running.set(false); }
            if (!packet.isEmpty() && !packet.startsWith("OK")) { packetStream.set(packet); }
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
        for (int i = 0; i < users.size(); i ++) {
            if (users.get(i).nick.get().equals(self.nick.get())) {
                playerNumber = i + 1;
            }
        }

        return playerNumber;
    }

    public void onGameLoop(ActionEvent e) {

        // HANDLE OUTGOING TRAFFIC
        if (hosting.get()) {
            JGame.spriteManager.activeSprites.forEach(sprite -> {

                    String type = sprite.type.get();

                    if (sprite.type.get().equals("player")) {
                        // TODO CHANGE THIS
                        type = "paddle";
                    }

                    String posX = sprite.positionX.get().toString();
                    String posY = sprite.positionY.get().toString();
                    String uuid = sprite.uuid.get();

                    if (!lastKnownPos.containsKey(uuid)) {
                        lastKnownPos.put(uuid, new JGCreateRequest(self.nick.get(), type, posX, posY, uuid));
                        submit(type + " " + posX + " " + posY + " " + uuid);
                        // System.out.println("sent not contains: " + type + " " + posX + " " + posY + " " + uuid );
                    } else {
                        String oldPosX = lastKnownPos.get(sprite.uuid.get()).posX.get();
                        String oldPosY = lastKnownPos.get(sprite.uuid.get()).posY.get();

                        if (!posX.equals(oldPosX) || !posY.equals(oldPosY)) {
                            lastKnownPos.put(uuid, new JGCreateRequest(self.nick.get(), type, posX, posY, uuid));
                            submit(type + " " + posX + " " + posY + " " + uuid);
                            // System.out.println("sent contains: " + type + " " + posX + " " + posY + " " + uuid );
                        }
                    }

            });
        }


        // HANDLE INCOMING TRAFFIC

        String packet = packetStream.get();

        if (packet.startsWith("SCENE") && !hosting.get()) {
            String sceneName = packet.split("[ ]")[1];
            JGame.sceneManager.changeSceneByName(sceneName);
        }

        if (packet.contains(":") && !packet.startsWith(self.nick.get())) {

            if (packet.contains("paddle")) {
                System.out.println("Heyyyy packet" + packet);
            }

            // if (packet.contains(":")) {
            // System.out.println(packet);
            String[] splitter = packet.split(" ");
            String type = splitter[1];
            String posX = splitter[2];
            String posY = splitter[3];
            String uuid = splitter[4];

            if (uuid.equals(self.nick.get())) {
                type = "player";
            }

            JGSprite sprite = JGame.spriteManager.getSpriteByUUID(uuid);
            if (sprite != null) {
                // Update it
                sprite.positionX.set(Double.parseDouble(posX));
                sprite.positionY.set(Double.parseDouble(posY));
            } else {
                // Create it
                System.out.println("CREATING");
                JGLayer layer = JGSceneManager.activeScene.get().layers.get(0);
                JGSprite newSprite = layer.create(type);
                newSprite.uuid.set(uuid);
                newSprite.positionX.set(Double.parseDouble(posX));
                newSprite.positionY.set(Double.parseDouble(posY));
                layer.addToLayer(newSprite, true);
                JGame.spriteManager.spriteList.add(newSprite);
                JGame.spriteManager.activeSprites.add(newSprite);
            }
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
}
