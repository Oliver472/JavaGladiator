package main;

import saveManager.SaveManager;

import javax.swing.JFrame;

/**
 * Game vytvara JFrame a nacita saveFile
 */
public class Game {
    public static void main(String[] arguments) {
        JFrame window = new JFrame("GLADIATOR");
        window.setContentPane(new GamePanel());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.pack();
        window.setVisible(true);
        SaveManager saveManager = new SaveManager();
        saveManager.loadGame();
    }
}

