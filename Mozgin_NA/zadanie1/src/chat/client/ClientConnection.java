package chat.client;

import java.io.*;
import java.net.Socket;

public class ClientConnection {

    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;

    public ClientConnection(String host, int port) throws IOException {
        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
    }

    public BufferedReader getIn() {
        return in;
    }

    public BufferedWriter getOut() {
        return out;
    }

    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    public static void main(String[] args) {
        try {
            ClientConnection client = new ClientConnection("localhost", 27015);
            WriteThread writeThread = new WriteThread(client);
            writeThread.initUsername();
            new Thread(new ReadThread(client)).start();
            new Thread(writeThread).start();
        } catch (IOException e) {
            System.err.println("Failed to connect to the server: " + e.getMessage());
        }
    }
}
