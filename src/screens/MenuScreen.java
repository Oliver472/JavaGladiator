package screens;

import grid.Pozadie;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MenuScreen extends Screen {

    private Pozadie pozadie;
    private BufferedImage logo;
    private MenuOption currentOption;
    private final static int BUTTON_MARGIN = 28;

    // Cache pre načítané obrázky tlačidiel, aby sme ich nenačítavali v každom cykle draw
    private final Map<MenuOption, BufferedImage> normalImages = new HashMap<>();
    private final Map<MenuOption, BufferedImage> pressedImages = new HashMap<>();

    public MenuScreen(ScreensManager manager) {
        super(manager);
        this.currentOption = MenuOption.PLAY; // Predvolená možnosť
        this.init();
    }

    @Override
    public void init() {
        this.pozadie = new Pozadie("images/bgColloseum2.png");
        try {
            this.logo = ImageIO.read(new File("images/Gladiator_logo.png"));
            for (MenuOption option : MenuOption.values()) {
                normalImages.put(option, ImageIO.read(new File(option.getNormalPath())));
                pressedImages.put(option, ImageIO.read(new File(option.getPressedPath())));
            }
        } catch (IOException e) {
            System.err.println("Problem with loading menu: " + e.getMessage());
        }
    }

    @Override
    public void update() {
        this.pozadie.update();
    }

    @Override
    public void draw(Graphics2D graphics) {
        this.pozadie.draw(graphics);
        graphics.drawImage(this.logo, 140, 20, 200, 50, null);

        // Vykreslenie tlačidiel dynamicky podľa Enumu
        int i = 0;
        for (MenuOption option : MenuOption.values()) {
            BufferedImage toDraw = (option == currentOption) ? pressedImages.get(option) : normalImages.get(option);
            graphics.drawImage(toDraw, 215, 140 + i * BUTTON_MARGIN, 50, 25, null);
            i++;
        }
    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_DOWN) {
            currentOption = currentOption.next();
        }
        if (k == KeyEvent.VK_UP) {
            currentOption = currentOption.previous();
        }
        if (k == KeyEvent.VK_ENTER) {
            executeSelection();
        }
    }

    private void executeSelection() {
        switch (currentOption) {
            case PLAY:
                super.getManager().nastavScreen(super.getManager().getScreens().get(1));
                break;
            case LOAD_SAVE:
                super.getManager().nastavScreen(super.getManager().getScreens().get(3));
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
    public void keyReleased(int k) {}
}