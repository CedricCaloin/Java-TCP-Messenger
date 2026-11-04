package main;
import serveur.Serveur;
import client.Client;

import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

public class Application extends JFrame {
    private JTextField champPseudo;
    private String pseudo;
    private JButton heberger;
    private JButton rejoindre;

    public Application() {

        JFrame fenetre = new JFrame();
        setSize(600,400);
        setTitle("Java TCP Messenger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.black);

        JPanel panneauPseudo = new JPanel();
        panneauPseudo.setBackground(Color.BLACK);
        JLabel labelPseudo = new JLabel("Pseudo :");
        labelPseudo.setForeground(Color.WHITE);
        champPseudo = new JTextField("anonyme", 15);
        panneauPseudo.add(labelPseudo);
        panneauPseudo.add(champPseudo);

        champPseudo.addActionListener(e -> {
            obtenirPseudo(champPseudo.getText());
            remove(panneauPseudo);
            selectionServeur();
        });

        add(panneauPseudo);


    }

    private void obtenirPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    private void selectionServeur() {
        JPanel panneauSelection = new JPanel();
        panneauSelection.setBackground(Color.BLACK);
        heberger = new JButton("Héberger");
        rejoindre = new JButton("Rejoindre");
        panneauSelection.add(heberger);
        panneauSelection.add(rejoindre);

        heberger.addActionListener(e -> {
            setVisible(false);
            creerServeur();
        });

        add(panneauSelection);
        revalidate();
        repaint();
    }

    private void creerServeur() {
        Thread threadServeur = new Thread(() -> {
            Serveur serveur = new Serveur(8080);
            serveur.demarrer();
        });
        threadServeur.start();

        Client client = new Client(pseudo, "localhost", 8080);
        client.demarrer();
    }

    public static void main(String[] args) {

        Application a = new Application();
        a.setVisible(true);

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
