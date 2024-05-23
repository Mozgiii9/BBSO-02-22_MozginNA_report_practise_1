package chat.client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class WriteThread implements Runnable {

    private final ClientConnection client;
    private final BufferedWriter out;
    private final Scanner scanner;

    public WriteThread(ClientConnection client) {
        this.client = client;
        this.out = client.getOut();
        this.scanner = new Scanner(System.in);
    }

    public void initUsername() {
        try {
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
            out.write(username);
            out.newLine();
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.print("Enter your message: ");
                String message = scanner.nextLine();
                out.write(message);
                out.newLine();
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
