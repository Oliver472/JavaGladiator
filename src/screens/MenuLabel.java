package screens;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;


/**
 * Trieda sluzi na nacitanie tlacidiel a stlacenych tlacidiel
 *
 * @author olivermrovcak
 */
public class MenuLabel {


    private final ArrayList<BufferedImage> buttons;
    private final ArrayList<BufferedImage> buttonsPressed;

    private final String[] options = {
        "playBtn",
        "optionsBtn",
        "quitBtn",

    };
    private final String[] optionsPressed = {
        "playBtnPressed",
        "optionsBtnPressed",
        "quitBtnPressed",
    };

    /**
     * Konstruktor vytvori ArrayListy pre tlacidla a nacita obrazky tlacidiel
     *
     * @param fileName cesta k png
     */
    public MenuLabel(String fileName) {
        this.buttons = new ArrayList<BufferedImage>();
        this.buttonsPressed = new ArrayList<BufferedImage>();
        this.init();

    }


    /**
     * Nacitanie obrazkov tlacidiel
     */
    private void init() {

        for (int i = 0; i < this.options.length; i++) {
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File("images/" + this.options[i] + ".png"));
                this.buttons.add(img);


            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        this.loadPressed();

    }


    /**
     * Nacitanie stlacenych tlacidiel
     */
    private void loadPressed() {

        for (int i = 0; i < this.optionsPressed.length; i++) {
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File("images/" + this.optionsPressed[i] + ".png"));
                this.buttonsPressed.add(img);


            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    /**
     * Vrati tlacidla
     *
     * @return ArrayList obrazkov
     */
    public ArrayList<BufferedImage> getButtons() {
        return this.buttons;
    }

    /**
     * Vrati stlacene tlacidla
     *
     * @return ArrayList obrazkov
     */
    public ArrayList<BufferedImage> getButtonsPressed() {
        return this.buttonsPressed;
    }


}
