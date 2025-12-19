package screens;

import enemies.Enemy;

import grid.LevelGrid;
import grid.Pozadie;
import player.Player;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Trieda Level je Predok vsetkych levelov a zaroven potom triedy Screen
 */
public abstract class Level extends Screen {

    private Pozadie pozadie;
    private Player player;
    private LevelGrid grid;
    private int lvl;
    private BufferedImage heart;
    private BufferedImage minca;
    private boolean zobralMincu;
    private int xminca;
    private int yminca;

    /**
     * Konstruktor priradi Objektu manazera
     */
    public Level(ScreensManager manager, String pathName, int xMinca, int yMinca) {
        super(manager);
        this.xminca = xMinca;
        this.yminca = yMinca;
        this.pozadie = new Pozadie(pathName);
        this.grid = new LevelGrid(30);
        this.player = new Player(this.grid);
        int mapHeight = this.grid.getHeight();
        //this.player.setX(100);
        //this.player.setY(mapHeight );

        try {
            this.minca = ImageIO.read(new File("images/coin.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        init();
    }

    /**
     * Inicializicia
     * nacita obrazok zivota
     */
    @Override
    public void init() {
        this.loadHeart();
    }


    /**
     * Nacitanie obrazku zivota
     */
    public void loadHeart() {
        try {
            this.heart = ImageIO.read(new File("images/heart.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * vrati grid
     * @return grid
     */
    public LevelGrid getGrid() {
        return this.grid;
    }

    /**
     * vrati hraca
     * @return player
     */
    public Player getPlayer() {
        return this.player;
    }


    /**
     * vrati png
     * @return img
     */
    public BufferedImage getHeart() {
        return this.heart;
    }


    /**
     * Update
     */
    @Override
    public void update() {

    }

    /**
     * vykreslenie pozadia, siete stvorcov, hraca a zivotov hraca
     *
     * @param graphics tvar
     */
    @Override
    public void draw(Graphics2D graphics) {

        this.pozadie.draw(graphics);
        this.grid.draw(graphics);
        this.player.draw(graphics);
        this.drawPlayerHealth(graphics);
        this.vratDoLobby();

        this.koliziaHracaAMince();
        graphics.drawImage(this.minca, this.xminca, this.yminca, 23, 17, null);

    }

    /**
     * Prepne na dalsi lvl ked je podm. splnena
     */
    private void dajDalsiLvl() {
        super.getManager().nastavScreen(super.getManager().getScreens().get(2));
    }

    /**
     * Vykreslenie zivotov hraca
     *
     * @param g tvar
     */
    protected void drawPlayerHealth(Graphics2D g) {
        for (int i = 0; i < this.player.getHealth(); i++) {
            g.drawImage(this.heart, 380 + (i * 20), 10, 15, 15, null);
        }
    }

    /**
     * Kontrola kolizie hraca a nepriatela
     * Vzorec na vypocet kolizie dvoch obdlznikov
     * (RectA.X1 < RectB.X2 && RectA.X2 > RectB.X1 && RectA.Y1 < RectB.Y2 && RectA.Y2 > RectB.Y1)
     */

    public void koliziaHracaAEnemy(Enemy enemy) {

        double x1Hraca = this.player.getX();
        double x2Hraca = this.player.getX() + this.player.getSirka();

        double y1Hraca = this.player.getY();
        double y2Hraca = this.player.getY() + this.player.getVyska();

        double x1Enemy = enemy.getX();
        double x2Enemy = enemy.getX() + enemy.getSirka();

        double y1Enemy = enemy.getY();
        double y2Enemy = enemy.getY() + enemy.getVyska();

        if (x1Hraca < x2Enemy && x2Hraca > x1Enemy && y1Hraca < y2Enemy && y2Hraca > y1Enemy) {
            if (this.player.getInvcTime() == 0) {
                this.player.setHealth(1);
                this.player.setInvcTime(10);
            }

        }
    }

    /**
     * Kontroluje koliziu hraca a mince
     */
    public void koliziaHracaAMince() {

        double x1Hraca = this.player.getX();
        double x2Hraca = this.player.getX() + this.player.getSirka();

        double y1Hraca = this.player.getY();
        double y2Hraca = this.player.getY() + this.player.getVyska();

        double x1Minca = this.xminca;
        double x2Minca = this.xminca + this.minca.getWidth();

        double y1Minca = this.yminca;
        double y2Minca = this.yminca + this.minca.getHeight();

        if (x1Hraca < x2Minca && x2Hraca > x1Minca && y1Hraca < y2Minca && y2Hraca > y1Minca) {

            this.zobralMincu = true;
            this.dajDalsiLvl();
        }
    }

    public void vratDoLobby() {
        if (this.player.getHealth() <= 0) {
            super.getManager().nastavScreen(super.getManager().getScreens().get(0));
            this.player.setFullHealth();
        }
    }

    /**
     * Rekcia na pustenie tlacidla
     * Posiela spravu hracovi
     *
     * @param k keyCode
     */
    @Override
    public void keyReleased(int k) {
        this.player.keyReleased(k);
    }

    /**
     * Reakcia na stlacenie tlacidla
     * Ked pouzivatel stlaci ESC vrati sa na menu screen
     * Posiela spravu hracovi
     *
     * @param k keyCode
     */
    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ESCAPE) {
            super.getManager().nastavScreen(super.getManager().getScreens().get(0));
        }

        this.player.keyPressed(k);

    }
}
