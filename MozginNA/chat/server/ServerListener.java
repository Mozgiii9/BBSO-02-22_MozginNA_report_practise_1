package chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerListener {

    private ServerSocket serverSocket;
    private static List<ClientHandler> clients = new ArrayList<>();
    private ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public static void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(27015);
        System.out.println("Server started and listening on port 27015");
        ChatLog log = new ChatLog();

        while (true) {
            Socket incomingConnection = serverSocket.accept();
            ClientHandler client = new ClientHandler(incomingConnection, log);
            clients.add(client);
            log.addClient(client); // Добавляем клиента в лог
            threadPool.execute(client);
        }
    }

    public static void main(String[] args) throws IOException {
        ServerListener serverListener = new ServerListener();
        serverListener.start();
    }
}

