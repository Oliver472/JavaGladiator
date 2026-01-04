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

    public static final double DEFAULT_WALK_SPEED = 3.5;
    public static final double DEFAULT_JUMP_UP_SPEED = -8.0;
    public static final double DEFAULT_JUMP_FALL_SPEED = 8.0;
    public static final double DEFAULT_JUMP_FUEL_CONSUMPTION = 0.65;

    protected double walkSpeed;
    protected double jumpUpSpeed;
    protected double jumpFallSpeed;
    protected double jumpFuelConsumption;

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

        this.walkSpeed = DEFAULT_WALK_SPEED;
        this.jumpUpSpeed = DEFAULT_JUMP_UP_SPEED;
        this.jumpFallSpeed = DEFAULT_JUMP_FALL_SPEED;
        this.jumpFuelConsumption = DEFAULT_JUMP_FUEL_CONSUMPTION;

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
        // Logika skoku
        if (super.isJumping()) {
            super.setDy(this.jumpUpSpeed);
            double remainingJumpEnergy = super.getySpeed();
            super.setySpeed(remainingJumpEnergy - this.jumpFuelConsumption);
            if (super.getySpeed() <= 0) {
                super.setySpeed(0);
                super.setDy(this.jumpFallSpeed);
                super.setJumping(false);
            }
        }

        super.setX(super.getX() + super.getDx());
        super.setY(super.getY() + super.getDy());
        this.kolizia();
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
        int mapX = super.getTileMap().getX();
        int mapY = super.getTileMap().getY();

        if (super.isMoving()) {
            if (super.isHeadingR()) {
                g.drawImage(this.sprites.get(x), (int)super.getX() + mapX, (int)super.getY() + mapY, super.getSirka(), super.getVyska(), null);
            } else {
                g.drawImage(this.spritesReversed.get(x), (int)super.getX() + mapX, (int)super.getY() + mapY, super.getSirka(), super.getVyska(), null);
            }
        } else {
            if (super.isHeadingR()) {
                g.drawImage(this.spritesStand.get(x), (int)super.getX() + mapX, (int)super.getY() + mapY, super.getSirka(), super.getVyska(), null);
            } else {
                g.drawImage(this.spritesStandReversed.get(x), (int)super.getX() + mapX, (int)super.getY() + mapY, super.getSirka(), super.getVyska(), null);
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
