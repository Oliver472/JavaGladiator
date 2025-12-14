package screens;

import grid.Pozadie;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Screen menu kde si pouzivatel vyberie z moznosti
 *
 * @author olivermrovcak
 */
public class MenuScreen extends Screen {

    private Pozadie pozadie;
    private MenuLabel label;

    private BufferedImage logo;
    private int selection;

    private final String[] optionsPressed = {
        "playBtnPressed",
        "optionsBtnPressed",
        "quitBtnPressed",
    };

    /**
     * Konstruktor vytvori pozadie, tlacidla a logo
     *
     * @param manager
     */
    public MenuScreen(ScreensManager manager) {
        super(manager);
        this.selection = 0;
        this.init();
    }


    /**
     * Inicializacia screenu
     */
    @Override
    public void init() {
        this.label = new MenuLabel("images/label.png");
        this.pozadie = new Pozadie("images/bgColloseum2.png");
        try {
            this.logo = ImageIO.read(new File("images/Gladiator_logo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update pozadia
     */
    @Override
    public void update() {
        this.pozadie.update();
    }

    /**
     * Vykreslenie pozadia, tlacidiel a loga
     *
     * @param graphics tvar
     */
    @Override
    public void draw(Graphics2D graphics) {
        this.pozadie.draw(graphics);
        graphics.drawImage(this.logo, 140, 20, 200, 50, null);

        for (int i = 0; i < this.label.getButtons().size(); i++) {
            if (i == this.selection) {

                graphics.drawImage(this.label.getButtonsPressed().get(i), 215, 140 + i * 28, 50, 25, null);
            } else {
                graphics.drawImage(this.label.getButtons().get(i), 215, 140 + i * 28, 50, 25, null);
            }


        }

    }

    /**
     * Reaguje na pustenie tlacidiel
     *
     * @param k keyCode
     */
    @Override
    public void keyReleased(int k) {

    }

    /**
     * Nastavuje a resetuje vyber z moznosti
     *
     * @param x vyber z moznosti v menu
     */
    private void optionSwitch(int x) {

        this.selection += x;

        if (this.selection < 0) {
            this.selection = 2;
        } else if (this.selection > 2) {
            this.selection = 0;
        }


    }

    /**
     * Reaguje na stlacenie tlacdila
     * vybera z moznosti menu
     *
     * @param k keyCode
     */
    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_DOWN) {
            this.optionSwitch(1);
        }

        if (k == KeyEvent.VK_UP) {
            this.optionSwitch(-1);
        }

        if (k == KeyEvent.VK_ENTER) {
            if (this.selection == 0) {
                super.getManager().nastavScreen(super.getManager().getScreens().get(1));
            }

            if (this.selection == 2) {
                System.exit(0);
            }
        }


    }
}
