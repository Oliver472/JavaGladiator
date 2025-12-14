package main;

import javax.swing.JFrame;

/**
 * Game vytvara JFrame
 */
public class Game {
    public static void main(String[] arguments) {
        JFrame window = new JFrame("GLADIATOR");
        window.setContentPane(new GamePanel());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.pack();
        window.setVisible(true);
    }
}

