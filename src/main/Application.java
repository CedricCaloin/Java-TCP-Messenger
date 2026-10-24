package main;
import serveur.Serveur;
import client.Client;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean ok = false;

        System.out.println("Bienvenue !!!");
        System.out.print("Veuillez entrer votre pseudo : ");
        String pseudo = scanner.nextLine();

        while (!ok) {
            System.out.print("=>");
            String choix = scanner.nextLine();
            String[] parties = choix.split(" ");

            if (parties[0].equalsIgnoreCase("/create")) {
                if (parties.length > 1) {
                    Thread threadServeur = new Thread(() -> {
                        Serveur serveur = new Serveur(Integer.parseInt(parties[1]));
                        serveur.demarrer();
                    });
                    threadServeur.start();
                    ok = true;

                    Client client = new Client(pseudo, "localhost", Integer.parseInt(parties[1]));
                    client.demarrer();
                }
                else System.out.println("/create <port>");
            }

            if (parties[0].equalsIgnoreCase("/join")) {
                if (parties.length > 2) {
                    Client client = new Client(pseudo, parties[1], Integer.parseInt(parties[2]));
                    client.demarrer();
                }
                else System.out.println("/join <ip> <port>");
            }
        }
    }
}
