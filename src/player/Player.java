package player;

import grid.LevelGrid;
import mapObjects.Entity;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 * Trieda Player predstavuje hraca ktoreho ovlada pouzivatel
 */
public class Player extends Entity {
    private int health;
    private int invcTime;
    private int pom;
    private int timer;

    private boolean zobralMincu;

    /**
     * nastavi zakladne parametre, nacita sprites
     *
     * @param tileMap siet tilov
     */
    public Player(LevelGrid tileMap) {
        super(200, 100, tileMap);
        this.invcTime = 0;
        this.health = 3;
        this.pom = 0;
        this.timer = 0;
        super.loadSprites("Gladiator");
    }

    /**
     * vrati pocet zivotov hraca
     *
     * @return health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Reaguje na stlacenie tlacidla
     * posuva hraca tym smerom ktoru sipku stlacil pouzivatel
     * skok za pomoci space
     *
     * @param k tlacidlo
     */
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_RIGHT) {
            if (!super.isBlockedRight()) {
                super.setDx(this.walkSpeed);
            }
            if (!super.isHeadingR()) {
                super.setHeadingR(true);
            }
            super.setHeadingL(false);
            super.setMoving(true);
        }
        if (k == KeyEvent.VK_LEFT) {
            if (!super.isBlockedLeft()) {
                super.setDx(-this.walkSpeed);
            }
            if (!super.isHeadingL()) {
                super.setHeadingL(true);
            }
            super.setHeadingR(false);
            super.setMoving(true);
        }
        if (k == KeyEvent.VK_SPACE) {
            if (!super.isJumping() && super.isBlockedBottom()) {
                super.setySpeed(-this.jumpUpSpeed);
                super.setJumping(true);
                super.setMoving(true);
            }
        }
    }

    /**
     * reaguje na pustenie tlacidla
     * <p>
     * zastavuje hraca
     *
     * @param k tlacidlo
     */
    public void keyReleased(int k) {
        if (k == KeyEvent.VK_RIGHT) {
            super.setDx(0);
            super.setMoving(false);
        }
        if (k == KeyEvent.VK_LEFT) {
            super.setDx(0);
            super.setMoving(false);
        }
    }


    /**
     * Update
     */
    @Override
    public void update() {}

    /**
     * Vykreslenie
     * Stara sa o pohyb hraca a o aktualizaciu zivotov po zasahu nepriatela
     *
     * @param g tvar
     */
    @Override
    public void draw(Graphics2D g) {

        super.move();

        setMapPosition();

        super.draw(g);

        this.changeSprites(g, this.pom);

        if (this.timer % 5 == 0) {
            this.pom++;
        }
        if (this.timer % 5 == 0) {
            if (this.invcTime > 0) {
                this.invcTime -= 0.5;
            }
        }
        if (this.pom > 3) {
            this.pom = 0;
        }
        this.timer++;
    }

    /**
     * Nastavi cas pocas ktoreho hraca nie je mozne zasiahnut
     *
     * @param invcTime cas pocas ktoreho nie je mozne hraca zasiahnut
     */
    public void setInvcTime(int invcTime) {
        this.invcTime = invcTime;
    }

    /**
     * znizi pocet zivotov
     *
     * @param x pocet zivotov ktore chceme hracovi zobrat
     */
    public void setHealth(int x) {
        if (this.invcTime == 0) {
            this.health -= x;
        }

    }

    /**
     * nastavi zivoty na 3
     */
    public void setFullHealth() {
        this.health = 3;
    }

    /**
     * @return vrati cas pocas ktoreho nie je mozne hraca zasiahnut
     */
    public int getInvcTime() {
        return this.invcTime;
    }
}
