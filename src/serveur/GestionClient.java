package serveur;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class GestionClient implements Runnable{
    private Socket socket;
    private Serveur serveur;

    public GestionClient(Socket socket, Serveur serveur) {
        this.socket = socket;
        this.serveur = serveur;
    }

    @Override
    public void run() {
        try (BufferedReader lecture = new BufferedReader(new InputStreamReader(this.socket.getInputStream()))) {
            String message;
            while ((message = lecture.readLine()) != null) {
                serveur.envoyerMessage(message);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
