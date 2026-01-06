package screens;

import grid.Background;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Hlavna obrazovka menu hry s vyberom moznosti
 */
public class MenuScreen extends Screen {

    private Background background;
    private BufferedImage logo;
    private MenuOption currentOption;
    private static final int BUTTON_MARGIN = 40;

    private final Map<MenuOption, BufferedImage> normalImages = new HashMap<>();
    private final Map<MenuOption, BufferedImage> pressedImages = new HashMap<>();

    /**
     * Konstruktor nastavi manazera obrazoviek a inicializuje aktualne vybranu moznost na New Game
     *
     * @param manager manazer obrazoviek
     */
    public MenuScreen(ScreensManager manager) {
        super(manager);
        this.currentOption = MenuOption.NEW_GAME;
        this.init();
    }

    /**
     * Inicializuje pozadie, logo a nacita obrazky pre vsetky tlacidla menu
     */
    @Override
    public void init() {
        this.background = new Background("/images/bgColloseum2.png");
        try {
            this.logo = ImageIO.read(getClass().getResource("/images/Gladiator_logo.png"));
            for (MenuOption option : MenuOption.values()) {
                this.normalImages.put(option, ImageIO.read(getClass().getResource(option.getNormalPath())));
                this.pressedImages.put(option, ImageIO.read(getClass().getResource(option.getPressedPath())));
            }
        } catch (IOException e) {
            System.err.println("Problem with loading menu: " + e.getMessage());
        }
    }

    /**
     * Aktualizuje pozadie
     */
    @Override
    public void update() {
        this.background.update();
    }

    /**
     * Vykresli pozadie, logo a vsetky tlacidla menu
     *
     * @param graphics tvar
     */
    @Override
    public void draw(Graphics2D graphics) {
        this.background.draw(graphics);
        graphics.drawImage(this.logo, 140, 20, 200, 50, null);

        // Vykreslenie tlačidiel dynamicky podľa Enumu
        int i = 0;
        for (MenuOption option : MenuOption.values()) {
            BufferedImage toDraw = (option == this.currentOption) ? this.pressedImages.get(option) : this.normalImages.get(option);
            graphics.drawImage(toDraw, 180, 70 + i * BUTTON_MARGIN, 90, 35, null);
            i++;
        }
    }

    /**
     * Spracuje stlacenie klaves
     *
     * @param k kod stlaceneho klavesu
     */
    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_DOWN) {
            this.currentOption = this.currentOption.next();
        }
        if (k == KeyEvent.VK_UP) {
            this.currentOption = this.currentOption.previous();
        }
        if (k == KeyEvent.VK_ENTER) {
            this.executeSelection();
        }
    }

    /**
     * Vykona akciu podla aktualne vybraneho tlacidla menu
     */
    private void executeSelection() {
        switch (this.currentOption) {
            case NEW_GAME:
                super.getManager().setNewGame();
                break;
            case LOAD_SAVE:
                super.getManager().setLoadScreen();
                break;
            case OPTIONS:
                System.out.println("Options clicked");
                break;
            case QUIT:
                System.exit(0);
                break;
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