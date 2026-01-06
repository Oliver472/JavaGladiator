package collectible;
import grid.LevelGrid;
import mapObjects.Entity;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Trieda coin je entita ktoru hrac musi zobrat (prejst cez nu) aby ukoncil level
 */

public class Coin extends Entity {

    /**
     * nastavi zakladne parametre, nacita sprites
     *
     * @param x suradnica x kde sa ma coin vykreslit
     * @param y suradnica y kde sa ma coin vykreslit
     * @param tileMap siet tilov
     */

    public Coin(int x, int y, LevelGrid tileMap) {
        super(x, y, tileMap);
        this.loadSprites("Coin");
    }

    /**
     * Nacita sprite sheet
     *
     * @param nameOfEntity nazov entity podla ktoreho sa nacita zo suboru
     */

    @Override
    protected void loadSprites(String nameOfEntity) {
        try {
            BufferedImage spriteSheet = ImageIO.read(getClass().getResource("/images/" + nameOfEntity + "-Sprite Sheet.png"));
            for (int i = 0; i < 4; i++) {
                super.setImg(spriteSheet.getSubimage(i * 31 , 0, 31, 31));
                super.getSprites().add(super.getImg());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Nacita sprite sheet
     *
     * @param g tvar
     * @param x index spritu
     */

    @Override
    protected void changeSprites(Graphics2D g, int x) {
        int mapX = super.getTileMap().getX();
        int mapY = super.getTileMap().getY();

        g.drawImage(this.getSprites().get(x), (int)super.getX() + mapX, (int)super.getY() + mapY, super.getSirka(), super.getVyska(), null);
    }

    /**
     * Update obrazovky
     *
     */

    @Override
    public void update() {

    }

    /**
     * Vykreslenie
     */

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }
}
