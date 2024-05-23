package chat.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatLog {

    private final List<ClientHandler> clients = new ArrayList<>();

    public synchronized void put(String message, ClientHandler sender) throws IOException {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessageToClient(message);
            }
        }
    }

    public synchronized void addClient(ClientHandler clientHandler) {
        clients.add(clientHandler);
    }
}
