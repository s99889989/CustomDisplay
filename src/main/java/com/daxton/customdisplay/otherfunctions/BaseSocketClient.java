package com.daxton.customdisplay.otherfunctions;

import com.daxton.customdisplay.CustomDisplay;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class BaseSocketClient {

    private String serverHost;
    private int serverPort;
    private Socket socket;
    private OutputStream outputStream;
    private InputStream inputStream;

    public static boolean disconnected;

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public BaseSocketClient(String host, int port) {
        this.serverHost = host;
        this.serverPort = port;
    }
    public void connetServer() throws IOException {
        this.socket = new Socket(this.serverHost, this.serverPort);
        this.outputStream = socket.getOutputStream();
        // why the output stream?
    }

    public void sendSingle(String message) throws IOException {
        try {
            this.outputStream.write(message.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        }
        this.outputStream.close();
        this.socket.close();
    }
    public void sendMessage(String message) throws IOException {
        this.socket = new Socket(this.serverHost,this.serverPort);
        this.outputStream = socket.getOutputStream();
        this.outputStream.write(message.getBytes(StandardCharsets.UTF_8));
        this.socket.shutdownOutput(); // 告訴服務器，所有的發送動作已經結束，之後只能接收
        this.inputStream = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String content = br.readLine();
        while (content != null) {
            String receipt = content;
            cd.getLogger().info("got receipt: " + receipt);
            content = br.readLine();
        }

        this.inputStream.close();
        this.socket.close();
    }

    public void send(String message) throws IOException {
        String sendMsg = message + "\n"; // we mark \n as a end of line.
        try {
            this.outputStream.write(sendMsg.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            disconnected = true;
            //cd.getLogger().info("斷線");
            //cd.getLogger().info(e.getMessage());
        }
//        this.outputStream.close();
//        this.socket.shutdownOutput();
    }
}
