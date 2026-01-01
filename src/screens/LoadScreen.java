package screens;

import grid.Background;
import saveManager.LevelEntity;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LoadScreen extends Screen {

    private Background background;
    private BufferedImage flag;
    private ArrayList<LevelEntity> levels;

    private int focusedIndex = 0;
    public static final int MARGIN = 60;

    public LoadScreen(ScreensManager manager) {
        super(manager);
        this.levels = super.getManager().getSaveManager().getLevels();
        this.init();
    }

    @Override
    public void init() {
        try {
            this.background = new Background("images/bgLoadLevels.png");
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
        this.background.draw(graphics);

        for (int i = 0; i < this.levels.size(); i++) {
            int x = 50 + (i * MARGIN);
            int y = 120;

            LevelEntity level = this.levels.get(i);

            if (i == this.focusedIndex ) {
                y -= 15;
            }

            this.drawFlag(graphics, x, y, 50, 50, level.getId());
        }
    }

    private void drawFlag(Graphics2D g, int x, int y, int w, int h, int id) {
        if (this.flag != null) {
            g.drawImage(this.flag, x, y, w, h, null);
        }
    }

    private void chooseLevel() {
        LevelEntity selectedLevel = this.levels.get(this.focusedIndex);
        super.getManager().setLevelScreen(selectedLevel);
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