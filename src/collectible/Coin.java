package collectible;
import grid.LevelGrid;
import mapObjects.Entity;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Coin extends Entity {

    public Coin(int x, int y, LevelGrid tileMap) {
        super(x, y, tileMap);
        this.loadSprites("Coin");
    }

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

    @Override
    protected void changeSprites(Graphics2D g, int x) {
        int mapX = super.getTileMap().getX();
        int mapY = super.getTileMap().getY();

        g.drawImage(this.getSprites().get(x), (int)super.getX() + mapX, (int)super.getY() + mapY, super.getSirka(), super.getVyska(), null);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }
}
