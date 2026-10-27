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

    public Fenetre (PrintWriter out, String host, String pseudo) {
        this.out = out;

        JFrame fenetre = new JFrame();
        setSize(600,400);
        setTitle(host + " - " + pseudo);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        
        JTextPane output = new JTextPane();
        output.setEditable(false);
        output.setBackground(Color.black);
        output.setMargin(new Insets(10, 10, 10, 10));
        add(new JScrollPane(output),BorderLayout.CENTER);

        JTextField input = new JTextField();
        input.setBackground(Color.DARK_GRAY);
        input.setForeground(Color.white);
        input.setBorder(BorderFactory.createCompoundBorder(input.getBorder(), new EmptyBorder(5, 10, 5, 10)));
        
        input.addActionListener(e -> {
            this.out.println(input.getText());
            input.setText("");
        });

        add(input,BorderLayout.SOUTH);

    }


    public static void main(String[] args) {
        Fenetre f = new Fenetre(null, "localhost", "cedric");
        f.setVisible(true);
    }
}