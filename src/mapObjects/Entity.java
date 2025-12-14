package mapObjects;

import grid.LevelGrid;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Predok objektov ktore sa dokazu pohybovat
 */
public abstract class Entity extends MapObject {

    private ArrayList<BufferedImage> sprites;
    private ArrayList<BufferedImage> spritesReversed;

    private ArrayList<BufferedImage> spritesStand;

    private ArrayList<BufferedImage> spritesStandReversed;

    private BufferedImage img;

    private BufferedImage charImg;
    private int pom;
    private int timer;

    /**
     * @param x       poloha x kde sa ma vykreslit entita
     * @param y       poloha y kde sa ma vykreslit entita
     * @param tileMap siet tilov
     */
    public Entity(int x, int y, LevelGrid tileMap) {
        super(x, y, tileMap);

        this.sprites = new ArrayList<BufferedImage>();
        this.spritesReversed = new ArrayList<BufferedImage>();
        this.spritesStand = new ArrayList<BufferedImage>();
        this.spritesStandReversed = new ArrayList<BufferedImage>();

    }

    /**
     * Stara sa o "fyziku" entit
     * posuva entitu smerom dole ked pod nou nie je ziadny BLOCKED tile
     * a naopak posuva entitu smerom hore pri skoku
     */


    public void move() {
        //skok
        if (super.isJumping()) {
            super.setDy(-4.5);
            double g = super.getySpeed();
            super.setySpeed(g - 0.5 );
            if (super.getySpeed() <= 0) {

                super.setySpeed(0);
                super.setDy(4.5);
                super.setJumping(false);
            }
        }

        super.setX(super.getX() + super.getDx());
        super.setY(super.getY() + super.getDy());


        this.kolizia();




      /*  this.r1.setLocation((int)this.x, (int)this.y);
        this.leftRec.setLocation((int)this.x - this.sirka, (int)this.y);
        this.rightRec.setLocation((int)this.x + this.sirka, (int)this.y);
        this.bottomRec.setLocation((int)this.x, (int)this.y + vyska);*/
    }


    /**
     * Nacita a ulozi do arraylistov vsetky sprity zo spriteSheet
     *
     * @param nameOfEntity meno entity
     */
    protected void loadSprites(String nameOfEntity) {
        try {
            BufferedImage spriteSheet = ImageIO.read(new File("images/" + nameOfEntity + "-Sprite Sheet.png"));
            for (int i = 0; i < 4; i++) {

                this.img = spriteSheet.getSubimage(i * 31 + i, 31, 31, super.getVyska());
                this.sprites.add(this.img);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedImage spriteSheetRev = ImageIO.read(new File("images/" + nameOfEntity + "-Sprite SheetReversed.png"));
            for (int i = 1; i < 5; i++) {

                this.img = spriteSheetRev.getSubimage(spriteSheetRev.getWidth() - (i * 31 + i), 31, 31, super.getSirka());
                this.spritesReversed.add(this.img);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedImage spriteSheet = ImageIO.read(new File("images/" + nameOfEntity + "-Sprite Sheet.png"));
            for (int i = 0; i < 4; i++) {

                this.img = spriteSheet.getSubimage(i * 31 + i, 0, 31, super.getVyska());
                this.spritesStand.add(this.img);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedImage spriteSheetRev = ImageIO.read(new File("images/" + nameOfEntity + "-Sprite SheetReversed.png"));
            for (int i = 1; i < 5; i++) {

                this.img = spriteSheetRev.getSubimage(spriteSheetRev.getWidth() - (i * 31 + i), 0, 31, super.getVyska());
                this.spritesStandReversed.add(this.img);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Meni obrazky podla toho ktorym smerom je entita otocena
     *
     * @param g tvar
     * @param x index
     */
    public void changeSprites(Graphics2D g, int x) {
        if (super.isMoving()) {
            if (super.isHeadingR()) {
                g.drawImage(this.sprites.get(x), (int)super.getX(), (int)super.getY(), super.getSirka(), super.getVyska(), null);
            } else {
                g.drawImage(this.spritesReversed.get(x), (int)super.getX(), (int)super.getY(), super.getSirka(), super.getVyska(), null);
            }
        } else {
            if (super.isHeadingR()) {
                g.drawImage(this.spritesStand.get(x), (int)super.getX(), (int)super.getY(), super.getSirka(), super.getVyska(), null);
            } else {
                g.drawImage(this.spritesStandReversed.get(x), (int)super.getX(), (int)super.getY(), super.getSirka(), super.getVyska(), null);
            }
        }

    }


    /**
     * Vykreslenie
     *
     * @param g tvar
     */
    @Override
    public void draw(Graphics2D g) {
        super.draw(g);

        this.changeSprites(g, this.pom);

        if (this.timer % 5 == 0) {
            this.pom++;
        }
        if (this.pom > 3) {
            this.pom = 0;
        }
        this.timer++;


    }


}
