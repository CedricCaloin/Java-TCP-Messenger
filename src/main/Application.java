package main;
import serveur.Serveur;
import client.Client;

import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.net.URL;

import java.awt.*;

public class Application extends JFrame {
    private String pseudo;

    public Application() {

        JFrame fenetre = new JFrame();
        setSize(600,400);
        setTitle("Java TCP Messenger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.black);
        URL iconURL = getClass().getResource("/logo.png"); 
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            setIconImage(icon.getImage());
        }

        JPanel panneauPseudo = new JPanel();
        panneauPseudo.setBackground(Color.BLACK);
        JLabel labelPseudo = new JLabel("Pseudo :");
        labelPseudo.setForeground(Color.WHITE);
        JTextField champPseudo = new JTextField("anonyme", 15);
        panneauPseudo.add(labelPseudo);
        panneauPseudo.add(champPseudo);

        champPseudo.addActionListener(e -> {
            obtenirPseudo(champPseudo.getText());
            remove(panneauPseudo);
            hebergerRejoindre();
        });

        add(panneauPseudo);


    }

    private void obtenirPseudo(String pseudo) {
        if (pseudo.equalsIgnoreCase("") || pseudo.equalsIgnoreCase("serveur")) this.pseudo = "anonyme";
        else this.pseudo = pseudo;
    }

    private void hebergerRejoindre() {
        JPanel panneauSelection = new JPanel();
        panneauSelection.setBackground(Color.BLACK);
        JButton heberger = new JButton("Héberger");
        JButton rejoindre = new JButton("Rejoindre");
        panneauSelection.add(heberger);
        panneauSelection.add(rejoindre);

        heberger.addActionListener(e -> {
            setVisible(false);
            creerServeur();
        });

        rejoindre.addActionListener(e -> {
            remove(panneauSelection);
            choisirServeur();
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

    private void choisirServeur() {
        JPanel panneauIP = new JPanel();
        panneauIP.setBackground(Color.BLACK);
        JLabel labelIP = new JLabel("Adresse IP :");
        labelIP.setForeground(Color.WHITE);
        JTextField champIP = new JTextField("", 15);
        panneauIP.add(labelIP);
        panneauIP.add(champIP);

        champIP.addActionListener(e -> {
            setVisible(false);
            rejoindreServeur(champIP.getText());
        });

        add(panneauIP);
        revalidate();
        repaint();
    }

    private void rejoindreServeur(String ip) {
        Client client = new Client(pseudo, ip, 8080);
        client.demarrer();
    }

    public static void main(String[] args) {

        Application a = new Application();
        a.setVisible(true);

    }
}
