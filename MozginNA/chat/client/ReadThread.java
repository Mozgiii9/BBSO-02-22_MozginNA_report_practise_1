package chat.client;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadThread implements Runnable {

    private final ClientConnection client;

    public ReadThread(ClientConnection client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = client.getIn();
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
