package client;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private String host;
    private String pseudo;
    private int port;

    public Client(String pseudo, String host, int port) {
        if (pseudo == null) this.pseudo = "anonyme";
        else this.pseudo = pseudo;
        this.host = host;
        this.port = port;
    }

    public void demarrer() {
        Scanner scanner = new Scanner(System.in);

        try {
            Socket socket = new Socket(host, port);
            System.out.println("Connecté au serveur !");

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(pseudo);

            Fenetre fenetre = new Fenetre(out, host, pseudo);

            LecteurServeur lecteur = 
                new LecteurServeur(new BufferedReader(new InputStreamReader(socket.getInputStream())), socket, fenetre);
            Thread threadEcoute = new Thread(lecteur);
            threadEcoute.start();

            fenetre.setVisible(true);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client("anonyme", "localhost", 8080);
        client.demarrer();
    }
}