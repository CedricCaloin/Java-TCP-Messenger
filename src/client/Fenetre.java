package client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.io.PrintWriter;

public class Fenetre extends JFrame {
    private JTextPane output;
    private JTextField input;
    private PrintWriter out;
    private String pseudo;

    public Fenetre (PrintWriter out, String host, String pseudo) {
        this.out = out;
        this.pseudo = pseudo;

        JFrame fenetre = new JFrame();
        setSize(600,400);
        setTitle(host + " - " + pseudo);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        
        this.output = new JTextPane();
        output.setEditable(false);
        output.setBackground(Color.black);
        output.setMargin(new Insets(10, 10, 10, 10));
        add(new JScrollPane(output),BorderLayout.CENTER);

        this.input = new JTextField();
        input.setBackground(Color.DARK_GRAY);
        input.setForeground(Color.white);
        input.setBorder(BorderFactory.createCompoundBorder(input.getBorder(), new EmptyBorder(5, 10, 5, 10)));
        
        input.addActionListener(e -> {
            this.out.println(input.getText());
            input.setText("");
        });

        add(input,BorderLayout.SOUTH);

    }

    public void ecrireMessage(String message) {
        String[] part = message.split(" : ",2);
        StyledDocument doc = output.getStyledDocument();

        Style styleMessage = output.addStyle("message", null);
        StyleConstants.setForeground(styleMessage, Color.WHITE);
        
        if (part[0].equalsIgnoreCase("serveur")) {

        }
        if (part[0].equalsIgnoreCase(pseudo)) {
            try {
                Style stylePseudo = output.addStyle("pseudo", null);
                StyleConstants.setForeground(stylePseudo, Color.GREEN);
                doc.insertString(doc.getLength(), "moi : ", stylePseudo);
                doc.insertString(doc.getLength(), part[1] + "\n", styleMessage);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
        else {
            try {
                Style stylePseudo = output.addStyle("pseudo", null);
                StyleConstants.setForeground(stylePseudo, Color.RED);
                doc.insertString(doc.getLength(), part[0] + " : ", stylePseudo);
                doc.insertString(doc.getLength(), part[1] + "\n", styleMessage);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        output.setCaretPosition(doc.getLength());
    }


    public static void main(String[] args) {
        Fenetre f = new Fenetre(null, "localhost", "cedric");
        f.setVisible(true);
    }
}