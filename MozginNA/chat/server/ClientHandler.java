package chat.server;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final ChatLog chatLog;
    private BufferedReader in;
    private BufferedWriter out;
    private String nickName;

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8));

            out.write("Enter your username: ");
            out.newLine();
            out.flush();

            nickName = in.readLine();
            chatLog.put(nickName + " has joined the chat.", this);
            System.out.println(nickName + " has joined the chat.");

            while (!Thread.currentThread().isInterrupted()) {
                out.write("Enter your message: ");
                out.newLine();
                out.flush();
                String message = in.readLine();
                if (Objects.isNull(message)) {
                    break;
                }
                System.out.println(nickName + ": " + message);
                chatLog.put(nickName + ": " + message, this);
            }
            chatLog.put(nickName + " has left the chat.", this);
            System.out.println(nickName + " has left the chat.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ServerListener.removeClient(this);
    }

    public void sendMessageToClient(String msg) throws IOException {
        if (!clientSocket.isOutputShutdown()) {
            out.write(msg);
            out.newLine();
            out.flush();
        }
    }

    public ClientHandler(Socket clientSocket, ChatLog chatLog) {
        this.clientSocket = clientSocket;
        this.chatLog = chatLog;
    }
}

