package screens;

import grid.Pozadie;
import saveManager.LevelEntity;
import saveManager.SaveManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class LoadScreen extends Screen {

    private Pozadie pozadie;
    private BufferedImage flag;
    private ArrayList<LevelEntity> levels;

    private int focusedIndex = 0;
    private final int MARGIN = 60;

    public LoadScreen(ScreensManager manager) {
        super(manager);
        SaveManager saveManager = SaveManager.getInstance();
        this.levels = saveManager.getLevels();
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

    }

    @Override
    public void draw(Graphics2D graphics) {
        this.pozadie.draw(graphics);

        for (int i = 0; i < this.levels.size(); i++) {
            int x = 50 + (i * this.MARGIN);
            int y = 120;

            if (i == this.focusedIndex) {
                y -= 15;
            }

            LevelEntity level = this.levels.get(i);

            this.drawFlag(graphics, x, y, 50, 50, level.getId());
        }
    }

    private void drawFlag(Graphics2D g, int x, int y, int w, int h, int id) {
        if (this.flag != null) {
            g.drawImage(this.flag, x, y, w, h, null);
        }

        String text = String.valueOf(id);

        // Set Font styling (Adjust size '20' to fit your flag image)
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.WHITE); // Choose a color that stands out against your flag

        // 3. Calculate Center Position
        FontMetrics metrics = g.getFontMetrics();

        // Math to center horizontally
        int textX = x + (w - metrics.stringWidth(text)) / 2;

        // Math to center vertically (Ascent is the distance from baseline to top of char)
        int textY = y + ((h - metrics.getHeight()) / 2) + metrics.getAscent();

        // 4. Draw the String
        g.drawString(text, textX, textY);


    }

    private void chooseLevel() {
        LevelEntity selectedLevel = this.levels.get(this.focusedIndex);
        System.out.println("Loading Level: " + selectedLevel.toString());
    }

    @Override
    public void keyPressed(int k) {
        if (k == 39) {
            this.focusedIndex++;
            if (this.focusedIndex >= this.levels.size()) {
                this.focusedIndex = 0;
            }
        }
        if (k == 37) {
            this.focusedIndex--;
            if (this.focusedIndex < 0) {
                this.focusedIndex = this.levels.size() - 1;
            }
        }
        if (k == 10) {
            this.chooseLevel();
        }
    }

    @Override
    public void keyReleased(int k) {}
}