package jgame;

import jgame.generics.Field;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class JGUser implements Runnable {
    public Field<String> nick = new Field<>("Justin");
    public Field<Boolean> running = new Field<>(false);

    private JGServer server;

    private BufferedReader entry;
    private BufferedWriter leave;

    public JGUser(String n) {
        nick.set(n);
    }

    public JGUser(Socket s, JGServer server) throws IOException {
        this.server = server;
        System.out.println(s);
        running.set(true);
        entry = new BufferedReader(new InputStreamReader(s.getInputStream()));
        leave = new BufferedWriter(new PrintWriter(s.getOutputStream()));
    }

    @Override
    public void run() {
        String login = recieve();
        nick.set(login.split("[ ]")[1]);
        server.sendUserList();
        server.sendLastKnownPositions(nick.get());
        do {
            String packet = recieve();
            if (packet != null && packet.startsWith("EXIT")) {
                try {
                    server.disconnectUser(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (packet != null && !packet.isEmpty() && !packet.startsWith("BEAT") && !packet.startsWith("EXIT")) {
                try {
                    JGCreateRequest request = server.getJGCreateRequest(nick.get(), packet);
                    if (packet.contains("SENDHOST true")) {
                        request.sender.set("HostOnly");
                    }
                    if (packet.contains("SENDHOST false")) {
                        request.sender.set("HostOnly");
                        request.savePos.set(false);
                    }
                    server.sendQueue.add(request);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } while (running.get());
    }

    public void submit(String s) {
        try {
            leave.write(s + "\n");
            leave.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String recieve() {
        String s = "";
        try {
            s = entry.readLine();
        } catch (Exception ex) {}
        return s;
    }
}
