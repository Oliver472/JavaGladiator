package mapObjects;

import grid.LevelGrid;
import grid.TileType;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Predok vsetkych objektov na ploche
 */
public abstract class MapObject {

    private double x;
    private double y;

    private LevelGrid tileMap;

    private double dx;
    private double dy;

    private double xmap;
    private double ymap;

    private int sirka;
    private int vyska;

    private int currCol;
    private int currRow;

    private Rectangle r1;
    private Rectangle bottomRec;
    private Rectangle leftRec;

    private Rectangle rightRec;

    private double ySpeed;

    private boolean blockedUp;
    private boolean blockedBottom;
    private boolean blockedLeft;
    private boolean blockedRight;

    private boolean jumping;

    private static boolean mapObjHraniceVisible;

    private int tilesize;

    private boolean headingR;
    private boolean headingL;

    private boolean moving;

    /**
     * konstruktor nastavi zakladne parametre a vytvori pomyselne hitboxy objektu
     *
     * @param x       poloha x na ktoru sa vykresli
     * @param y       poloha y na ktoru sa vykresli
     * @param tileMap siet tilov
     */
    public MapObject(int x, int y, LevelGrid tileMap) {
        this.x = x;
        this.y = y;
        this.tileMap = tileMap;

        mapObjHraniceVisible = false;

        this.dx = 0;
        this.dy = 4.5;

        this.ySpeed = 0;

        this.tilesize = tileMap.getTileSize();

        this.sirka = tilesize;
        this.vyska = tilesize;

        this.jumping = false;


        this.blockedRight = false;
        this.blockedLeft = false;
        this.blockedUp = false;
        this.r1 = new Rectangle(
                x,
                y,
                sirka,
                vyska);

        this.bottomRec = new Rectangle(
                x,
                y + vyska,
                sirka,
                vyska);

        this.leftRec = new Rectangle(
                x - sirka,
                y,
                sirka,
                vyska);

        this.rightRec = new Rectangle(
                x + sirka,
                y,
                sirka,
                vyska);

    }

    /**
     * vrati x suradnicu
     *
     * @return
     */
    public double getX() {
        return x;
    }


    /**
     * vrati y suradnicu
     *
     * @return Y suradnica
     */
    public double getY() {
        return y;
    }

    /**
     * vrati sirku objektu
     *
     * @return sirka
     */
    public int getSirka() {
        return sirka;
    }

    /**
     * vrati vysku objektu
     *
     * @return vyska
     */
    public int getVyska() {
        return vyska;
    }


    /**
     * vrati dx
     * @return dx
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * vrati dy
     * @return dy
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * vrati ySpeed
     * @return ySpeed
     */
    public double getySpeed() {
        return this.ySpeed;
    }

    /**
     * vrati blockedBottom
     * @return blockedBottom
     */

    public boolean isBlockedBottom() {
        return this.blockedBottom;
    }
    /**
     * vrati blockedLeft
     * @return blockedLeft
     */
    public boolean isBlockedLeft() {
        return this.blockedLeft;
    }
    /**
     * vrati blockedRight
     * @return blockedRight
     */
    public boolean isBlockedRight() {
        return this.blockedRight;
    }
    /**
     * vrati isJumping
     * @return isJumping
     */
    public boolean isJumping() {
        return this.jumping;
    }


    /**
     * vrati smer objektu
     * @return headingR
     */
    public boolean isHeadingR() {
        return this.headingR;
    }
    /**
     * vrati smer objektu
     * @return headingL
     */
    public boolean isHeadingL() {
        return this.headingL;
    }

    /**
     * vrati boolean moving
     * @return moving
     */
    public boolean isMoving() {
        return this.moving;
    }

    /**
     * nastavi polohu x
     *
     * @param x poloha x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * nastavi polohu y
     *
     * @param y poloha y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * nastavi dx na prisl hodnotu
     * @param dx delta y
     */
    public void setDx(double dx) {
        this.dx = dx;
    }

    /**
     * nastavi dy na prisl hodnotu
     * @param dy delta y
     */
    public void setDy(double dy) {
        this.dy = dy;
    }


    /**
     * nastavi y speed na prisl hodnotu
     *
     * @param ySpeed rychlost
     */
    public void setySpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    /**
     * nastavi boolean na prisl. hodnotu
     *
     * @param jumping jumping
     */
    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    /**
     * nastavi boolean na prisl. hodnotu
     *
     * @param moving moving
     */
    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    /**
     * Kontroluje koliziu pomyselnych hitboxov objektu (viz. mapObjHraniceVisible = true)
     * s tilami na mape ktore su typu BLOCKED
     * ak su BLOCKED zastavi pohyb po x alebo y osi
     */
    public void kolizia() {
        int colOfBottomRec = ((int)x + sirka / 2) / tilesize;
        int rowOfBottomRec = ((int)y + vyska) / tilesize;

        int colOfLeftRec = ((int)x) / tilesize;
        int rowOfLeftRec = ((int)y) / tilesize;

        int colOfRightRec = ((int)x + sirka) / tilesize;
        int rowOfRightRec = ((int)y) / tilesize;

        int colOfTopRec = ((int)x) / tilesize;
        int rowOfTopRec = ((int)y) / tilesize;


        //check ci je pravy rec na urovni blocked tilu
        if (tileMap.getType(rowOfRightRec, colOfRightRec) == TileType.BLOCKED) {
            this.blockedRight = true;
            this.dx = 0;
        } else {
            this.blockedRight = false;
        }
        //check ci je horny rec na urovni blocked tilu
        if (tileMap.getType(rowOfTopRec, colOfTopRec) == TileType.BLOCKED) {
            this.blockedUp = true;
            this.dy = 0;
            this.ySpeed = 0;
        } else {
            this.blockedUp = false;
        }

        //check ci je lavy rec na urovni blocked tilu
        if (tileMap.getType(rowOfLeftRec, colOfLeftRec) == TileType.BLOCKED) {

            this.blockedLeft = true;
            this.dx = 0;
        } else {
            this.blockedLeft = false;
        }

        //check ci je spodny rec na urovni blocked tilu
        if (tileMap.getType(rowOfBottomRec, colOfBottomRec) == TileType.BLOCKED) {
            if (!jumping) {
                this.dy = 0;
            }

            this.blockedBottom = true;

        } else {
            this.blockedBottom = false;
            if (!jumping) {
                this.dy = 4.5;
            }
        }

    }

    /**
     * Nastavi smer
     *
     * @param headingR smer ktorym je otoceny objekt
     */
    public void setHeadingR(boolean headingR) {
        this.headingR = headingR;
    }

    /**
     * Nastavi smer
     *
     * @param headingL smer ktorym je otoceny objekt
     */
    public void setHeadingL(boolean headingL) {
        this.headingL = headingL;
    }

    /**
     * Nastavi poziciu mapy
     */
    public void setMapPosition() {
        xmap = tileMap.getX();
        ymap = tileMap.getY();
    }



    /**
     * nacita obrazok pre objekt
     *
     * @param filePath cesta k png
     * @return Obrazok
     */
    public BufferedImage loadImage(String filePath) {
        try {
            BufferedImage img = ImageIO.read(new File(filePath));
            return img;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Update
     */
    public abstract void update();

    /**
     * Vykreslenie
     *
     * @param g tvar
     */
    public void draw(Graphics2D g) {
        if (mapObjHraniceVisible) {
            //hlavny rec
            g.draw(this.r1);

            //lavy rec podla neho sa urcuje schopnost r1 posuvat dolava
            g.draw(this.leftRec);

            //lavy rec podla neho sa urcuje schopnost r1 posuvat dolava
            g.draw(this.rightRec);

            //spodny rec -||- nizsie
            g.draw(this.bottomRec);
        }
    }
}
