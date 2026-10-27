package serveur;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class GestionClient implements Runnable{
    private Socket socket;
    private Serveur serveur;
    private String pseudo;

    public GestionClient(Socket socket, Serveur serveur, String pseudo) {
        this.socket = socket;
        this.serveur = serveur;
        this.pseudo = pseudo;
    }

    @Override
    public void run() {
        try (BufferedReader lecture = new BufferedReader(new InputStreamReader(this.socket.getInputStream()))) {
            String message;
            while ((message = lecture.readLine()) != null) {
                if (message.startsWith("GET /") || message.startsWith("Host:") || message.startsWith("User-Agent:"));
                else serveur.envoyerMessage(pseudo + " : " + message);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
