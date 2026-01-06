package mapObjects;

import grid.LevelGrid;
import grid.TileType;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
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

        this.sirka = this.tilesize;
        this.vyska = this.tilesize;

        this.jumping = false;
        this.blockedRight = false;
        this.blockedLeft = false;
        this.blockedUp = false;
        this.r1 = new Rectangle(
                x,
                y,
                this.sirka,
                this.vyska);

        this.bottomRec = new Rectangle(
                x,
                y + this.vyska,
                this.sirka,
                this.vyska);

        this.leftRec = new Rectangle(
                x - this.sirka,
                y,
                this.sirka,
                this.vyska);

        this.rightRec = new Rectangle(
                x + this.sirka,
                y,
                this.sirka,
                this.vyska);

    }

    /**
     * vrati x suradnicu
     *
     * @return
     */
    public double getX() {
        return this.x;
    }


    /**
     * vrati y suradnicu
     *
     * @return Y suradnica
     */
    public double getY() {
        return this.y;
    }

    /**
     * vrati sirku objektu
     *
     * @return sirka
     */
    public int getSirka() {
        return this.sirka;
    }

    /**
     * vrati vysku objektu
     *
     * @return vyska
     */
    public int getVyska() {
        return this.vyska;
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
    public void collision() {
        int colOfBottomRec = ((int)this.x + this.sirka / 2) / this.tilesize;
        int rowOfBottomRec = ((int)this.y + this.vyska) / this.tilesize;

        int colOfLeftRec = ((int)this.x) / this.tilesize;
        int rowOfLeftRec = ((int)this.y) / this.tilesize;

        int colOfRightRec = ((int)this.x + this.sirka) / this.tilesize;
        int rowOfRightRec = ((int)this.y) / this.tilesize;

        int colOfTopRec = ((int)this.x) / this.tilesize;
        int rowOfTopRec = ((int)this.y) / this.tilesize;


        //check ci je pravy rec na urovni blocked tilu
        if (this.tileMap.getType(rowOfRightRec, colOfRightRec) == TileType.BLOCKED) {
            this.blockedRight = true;
            this.dx = 0;
        } else {
            this.blockedRight = false;
        }
        //check ci je horny rec na urovni blocked tilu
        if (this.tileMap.getType(rowOfTopRec, colOfTopRec) == TileType.BLOCKED) {
            this.blockedUp = true;
            this.dy = 0;
            this.ySpeed = 0;
        } else {
            this.blockedUp = false;
        }

        //check ci je lavy rec na urovni blocked tilu
        if (this.tileMap.getType(rowOfLeftRec, colOfLeftRec) == TileType.BLOCKED) {

            this.blockedLeft = true;
            this.dx = 0;
        } else {
            this.blockedLeft = false;
        }

        //check ci je spodny rec na urovni blocked tilu
        if (this.tileMap.getType(rowOfBottomRec, colOfBottomRec) == TileType.BLOCKED) {
            if (!this.jumping) {
                this.dy = 0;
            }

            this.blockedBottom = true;

        } else {
            this.blockedBottom = false;
            if (!this.jumping) {
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
        this.xmap = this.tileMap.getX();
        this.ymap = this.tileMap.getY();
    }



    /**
     * nacita obrazok pre objekt
     *
     * @param filePath cesta k png
     * @return Obrazok
     */
    public BufferedImage loadImage(String filePath) {
        try {
            BufferedImage img = ImageIO.read(getClass().getResource(filePath));
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

            //spodny rec
            g.draw(this.bottomRec);
        }
    }

    /**
     * Vrati tileMapu
     *
     */

    public LevelGrid getTileMap() {
        return this.tileMap;
    }

    /**
     * Nacita sprites pre mapObject
     *
     * @param nameOfEntity nazov entity
     */

    protected abstract void loadSprites(String nameOfEntity) ;

    /**
     * Meni sprites
     *
     * @param g tvar
     * @param x index spritu
     *
     */

    protected abstract void changeSprites(Graphics2D g, int x);
}
