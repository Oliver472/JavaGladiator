package main;

import screens.ScreensManager;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

/**
 * Trieda GamePanel  nastavi oknu sirku a vysku a obsahuje gameLoop
 */
public class GamePanel extends JPanel implements Runnable, KeyListener {

    //parametre okna
    public static final int WIDTH = 480;
    public static final int HEIGHT = 240;
    public static final int SCALE = 3;

    private BufferedImage image;
    private Thread vlakno;
    private boolean alive;
    private static final int FRAME_PER_SEC = 60;
    private final long tTime = 1000 / FRAME_PER_SEC;

    private Graphics2D graphics;

    private ScreensManager manager;

    public GamePanel() {


        super();
        this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        this.setFocusable(true);

        this.requestFocus();

    }


    /**
     * Ak je okno vytvorene zisti ci uz existuje vlakno, ak nie vytvorí ho
     */
    public void addNotify() {
        super.addNotify();
        if (this.vlakno == null) {
            Thread vlaknos = new Thread(this);
            addKeyListener(this);
            vlaknos.start();
        }
    }


    /**
     * Spusti gameLoop ktory bezi pokym je alive true zaroven
     * neustale prekresluje celu obrazovku a tvary a obrazky na nej
     */
    public void run() {
        this.init();

        long start;
        long elapsed;
        long wait;


        while (this.alive) {
            update();
            draw();
            drawToScreen();
            start = System.nanoTime();
            elapsed = System.nanoTime() - start;
            wait = tTime - elapsed / 1000000;



            try {
                Thread.sleep(wait);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * Update screenu
     */
    private void update() {
        this.manager.update();
    }

    /**
     * Vykreslenie aktualneho screenu
     */
    private void draw() {
        this.manager.draw(this.graphics);
    }

    /**
     * Vykreslenie na obrazovku
     */
    private void drawToScreen() {
        Graphics g2 = getGraphics();
        g2.drawImage(this.image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);

    }

    /**
     * Inicializacia zakladneho tvaru na platne a vytvorenie manazera
     */
    private void init() {
        this.image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        this.alive = true;
        this.graphics = (Graphics2D)this.image.getGraphics();
        this.manager = new ScreensManager();
    }


    /**
     * @param k key event
     */
    public void keyTyped(KeyEvent k) {
    }

    /**
     * listener pre stlacene tlacidla
     * posiela keyCode manazerovi
     *
     * @param k key event
     */
    public void keyPressed(KeyEvent k) {

        this.manager.keyPressed(k.getKeyCode());
    }

    /**
     * listener pre pustene tlacidla
     * posiela keyCode manazerovi
     *
     * @param k the event to be processed
     */
    public void keyReleased(KeyEvent k) {
        this.manager.keyReleased(k.getKeyCode());
    }


}
