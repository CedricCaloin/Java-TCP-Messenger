package client;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class LecteurServeur implements Runnable {
    BufferedReader lecteur;
    Socket socket;

    public LecteurServeur(BufferedReader lecteur, Socket socket) {
        this.lecteur = lecteur;
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader lecture = new BufferedReader(new InputStreamReader(this.socket.getInputStream()))) {
            String message;
            while ((message = lecture.readLine()) != null) {
                System.out.println(message);
                System.out.print("=>");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
