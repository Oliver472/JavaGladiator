package collectible;

import grid.LevelGrid;
import mapObjects.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Coin extends Entity {

    public Coin(int x, int y, LevelGrid tileMap) {
        super(x, y, tileMap);
        this.loadSprites("Coin");
    }

    @Override
    protected void loadSprites(String nameOfEntity) {
        try {
            BufferedImage spriteSheet = ImageIO.read(new File("images/" + nameOfEntity + "-Sprite Sheet.png"));
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
        this.changeSprites(g, this.pom);

        if (this.timer % 7 == 0) {
            this.pom++;
        }
        if (this.pom > 3) {
            this.pom = 0;
        }
        this.timer++;
    }
}
