package grid;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Trieda pozadie nastavi v urcitom screene obrazok do pozadia
 *
 * @author olivermrovcak
 */
public class Pozadie {


    private BufferedImage img;
    private final int x;
    private final int y;


    /**
     * nastavi x a y na 0 teda do laveho horneho rohu
     *
     * @param fileName cesta k png
     */
    public Pozadie(String fileName) {
        this.x = 0;
        this.y = 0;

        this.init(fileName);

    }


    /**
     * nacita obrazok a ulozi ho do img
     *
     * @param fileName cesta k png
     */
    private void init(String fileName) {
        this.img = null;
        try {
            this.img = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Vykresli na platno
     *
     * @param g tvar
     */
    public void draw(Graphics2D g) {
        g.drawImage(this.img, this.x, this.y, GamePanel.WIDTH, GamePanel.HEIGHT, null);

    }


    /**
     * Update
     */
    public void update() {
    }
}
