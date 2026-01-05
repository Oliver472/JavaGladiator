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
            for (int i = 0; i < 3; i++) {
                super.setImg(spriteSheet.getSubimage(i * 27 + 6, 31, 31, super.getVyska()));
                super.getSprites().add(super.getImg());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void changeSprites(Graphics2D g, int x) {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }
}
