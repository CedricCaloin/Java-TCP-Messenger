package client;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private String host;
    private int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void demarrer() {
        Scanner scanner = new Scanner(System.in);

        try (Socket socket = new Socket(host, port)) {
            System.out.println("Connecté au serveur !");

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            LecteurServeur lecteur = 
                new LecteurServeur(new BufferedReader(new InputStreamReader(socket.getInputStream())), socket);
            Thread threadEcoute = new Thread(lecteur);
            threadEcoute.start();

            System.out.print("=>");
            while(scanner.hasNextLine()) {
                out.println(scanner.nextLine());
                System.out.print("=>");
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client("localhost", 8080);
        client.demarrer();
    }
}