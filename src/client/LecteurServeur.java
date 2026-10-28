package client;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class LecteurServeur implements Runnable {
    private BufferedReader lecteur;
    private Socket socket;
    private Fenetre fenetre;

    public LecteurServeur(BufferedReader lecteur, Socket socket, Fenetre fenetre) {
        this.lecteur = lecteur;
        this.socket = socket;
        this.fenetre = fenetre;
    }

    @Override
    public void run() {
        try (BufferedReader lecture = new BufferedReader(new InputStreamReader(this.socket.getInputStream()))) {
            String message;
            while ((message = lecture.readLine()) != null) {
                System.out.println(message);
                fenetre.ecrireMessage(message);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
