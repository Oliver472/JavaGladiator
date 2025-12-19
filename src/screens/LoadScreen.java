package screens;

import grid.Pozadie;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class LoadScreen extends Screen {

    private Pozadie pozadie;
    private BufferedImage flag;

    private int focusedIndex = 0; // Ktorá vlajka je vybratá (0-9)
    private final int POCET_VLAJIEK = 7;
    private final int ROZOSTUP = 60; // Medzera medzi vlajkami

    public LoadScreen(ScreensManager manager) {
        super(manager);
        this.init();
    }

    @Override
    public void init() {
        try {
            this.pozadie = new Pozadie("images/bgLoadLevels.png");
            this.flag = ImageIO.read(new File("images/flag.png"));
        } catch (IOException e) {
            System.err.println("Problem with screen: " + e.getMessage());
        }
    }

    @Override
    public void update() {
        // Tu môžeš riešiť plynulé animácie, ak by si chcel
    }

    @Override
    public void draw(Graphics2D graphics) {
        this.pozadie.draw(graphics);

        for (int i = 0; i < POCET_VLAJIEK; i++) {
            int x = 50 + (i * ROZOSTUP);
            int y = 120;

            // Ak je vlajka focusnutá, zdvihneme ju o 15 pixelov vyššie
            if (i == focusedIndex) {
                y -= 15;
            }

            drawFlag(graphics, x, y, 50, 50);
        }
    }

    // Tvoj nový wrapper s veľkosťou
    private void drawFlag(Graphics2D g, int x, int y, int w, int h) {
        if (this.flag != null) {
            g.drawImage(this.flag, x, y, w, h, null);
        }
    }

    @Override
    public void keyPressed(int k) {
        // Predpokladajme, že používaš konštanty z KeyEvent (napr. 37 = Left, 39 = Right)
        if (k == 39) { // Šípka DOPRAVA
            focusedIndex++;
            if (focusedIndex >= POCET_VLAJIEK) focusedIndex = 0; // Skoč na začiatok
        }
        if (k == 37) { // Šípka DOĽAVA
            focusedIndex--;
            if (focusedIndex < 0) focusedIndex = POCET_VLAJIEK - 1; // Skoč na koniec
        }
    }

    @Override
    public void keyReleased(int k) {}
}