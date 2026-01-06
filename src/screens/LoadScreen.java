package screens;

import grid.Background;
import main.GamePanel;
import saveManager.LevelEntity;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Obrazovka na vyber a nacitanie levelov (Load Screen)
 */
public class LoadScreen extends Screen {

    private Background background;
    private BufferedImage flag;
    private BufferedImage lock;
    private ArrayList<LevelEntity> levels;

    private int focusedIndex = 0;
    public static final int MARGIN = 60;

    /**
     * Konstruktor nastavi manazera obrazoviek a nacita zoznam levelov zo save systemu
     *
     * @param manager manazer obrazoviek
     */
    public LoadScreen(ScreensManager manager) {
        super(manager);
        this.levels = super.getManager().getSaveManager().getLevels();
        this.init();
    }

    /**
     * Inicializuje pozadie a nacita obrazky pre vlajky a zamok
     */
    @Override
    public void init() {
        try {
            this.background = new Background("/images/bgLoadLevels.png");
            this.flag = ImageIO.read(getClass().getResource("/images/flag.png"));
            this.lock = ImageIO.read(getClass().getResource("/images/lock.png"));
        } catch (IOException e) {
            System.err.println("Problem with screen: " + e.getMessage());
        }
    }

    /**
     * Update
     */
    @Override
    public void update() {

    }

    /**
     * Vykresli pozadie a riadok vlajok reprezentujucich levely
     *
     * @param graphics graficky kontext
     */
    @Override
    public void draw(Graphics2D graphics) {
        this.background.draw(graphics);
        int middleOfWindowWidth = GamePanel.WIDTH / 2;

        int flagsRowWidth = this.levels.size() * MARGIN;
        int middleOfFlagsRowWidth = flagsRowWidth / 2;

        int pointOfRenderForFlags = middleOfWindowWidth - middleOfFlagsRowWidth;

        for (int i = 0; i < this.levels.size(); i++) {
            int x = pointOfRenderForFlags + (i * MARGIN);
            int y = GamePanel.HEIGHT / 2;

            LevelEntity level = this.levels.get(i);

            if (i == this.focusedIndex ) {
                y -= 15;
            }

            this.drawFlag(graphics, x, y, 50, 50, level.isUnlocked());
        }
    }

    /**
     * Vykresli vlajku levelu, pri zamknutom leveli pridá obrazok zamku
     *
     * @param g          graficky kontext
     * @param x          x-ova suradnica
     * @param y          y-ova suradnica
     * @param w          sirka vlajky
     * @param h          vyska vlajky
     * @param isUnlocked ci je level odomknuty
     */
    private void drawFlag(Graphics2D g, int x, int y, int w, int h, boolean isUnlocked) {
        int middleOfFlagWidth = w / 2;
        int middleOfFlagHeight = h / 2;

        if (this.flag != null) {
            g.drawImage(this.flag, x, y, w, h, null);
            if (!isUnlocked) {
                g.drawImage(this.lock, x + middleOfFlagWidth - 5, y + middleOfFlagHeight - 3, 10, 13, null);
            }
        }
    }

    /**
     * Spusti vybrany level ak je odomknuty, inak vypise informaciu o zamknuti
     */
    private void chooseLevel() {
        LevelEntity selectedLevel = this.levels.get(this.focusedIndex);
        if (selectedLevel.isUnlocked()) {
            super.getManager().setLevelScreen(selectedLevel);
            System.out.println("Loading Level: " + selectedLevel.toString());
        } else {
            System.out.println("Level locked");
        }
    }

    /**
     * Spracuje stlacenie klaves, pohyb medzi levelmi, vyber a navrat do menu
     *
     * @param k kod stlaceneho klavesu
     */
    @Override
    public void keyPressed(int k) {
        if (k == 39) { // sipka doprava
            this.focusedIndex++;
            if (this.focusedIndex >= this.levels.size()) {
                this.focusedIndex = 0;
            }
        }
        if (k == 37) { // sipka dolava
            this.focusedIndex--;
            if (this.focusedIndex < 0) {
                this.focusedIndex = this.levels.size() - 1;
            }
        }
        if (k == 10) { // Enter
            this.chooseLevel();
        }
        if (k == 27) { // Escape
            super.getManager().setMenuScreen();
        }
    }

    /**
     * Spracuje pustenie klavesu
     *
     * @param k kod pusteného klavesu
     */
    @Override
    public void keyReleased(int k) {

    }
}