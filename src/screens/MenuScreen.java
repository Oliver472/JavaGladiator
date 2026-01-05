package screens;

import grid.Background;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MenuScreen extends Screen {

    private Background background;
    private BufferedImage logo;
    private MenuOption currentOption;
    private static final int BUTTON_MARGIN = 40;

    private final Map<MenuOption, BufferedImage> normalImages = new HashMap<>();
    private final Map<MenuOption, BufferedImage> pressedImages = new HashMap<>();

    public MenuScreen(ScreensManager manager) {
        super(manager);
        this.currentOption = MenuOption.NEW_GAME;
        this.init();
    }

    @Override
    public void init() {
        this.background = new Background("images/bgColloseum2.png");
        try {
            this.logo = ImageIO.read(new File("images/Gladiator_logo.png"));
            for (MenuOption option : MenuOption.values()) {
                this.normalImages.put(option, ImageIO.read(new File(option.getNormalPath())));
                this.pressedImages.put(option, ImageIO.read(new File(option.getPressedPath())));
            }
        } catch (IOException e) {
            System.err.println("Problem with loading menu: " + e.getMessage());
        }
    }

    @Override
    public void update() {
        this.background.update();
    }

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

    @Override
    public void keyReleased(int k) {
    }
}