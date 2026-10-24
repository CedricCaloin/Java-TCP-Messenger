package serveur;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Serveur {
    private int port;
    private List<Socket> clients;
    private ExecutorService pool;

    public Serveur(int port) {
        this.port = port;
        this.clients = new ArrayList<>();
        this.pool = Executors.newCachedThreadPool();
    }

    public void demarrer() {
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Nouvelle connection");
                this.clients.add(socket);
                String pseudo = "anonyme";
                BufferedReader lecture = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                pseudo = lecture.readLine();
                GestionClient client = new GestionClient(socket,this,pseudo);
                pool.execute(client);
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void envoyerMessage(String message) {
        for(Socket client: clients) {
            try {
                PrintWriter sortieClient = new PrintWriter(client.getOutputStream(), true);
                sortieClient.println(message);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        Serveur serveur = new Serveur(8080);
        serveur.demarrer();
    }
}