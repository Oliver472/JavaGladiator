package screens;

import collectible.Coin;
import enemies.Enemy;
import grid.LevelGrid;
import grid.Background;
import main.GamePanel;
import player.Player;
import saveManager.LevelEntity;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Trieda Level je Predok vsetkych levelov a zaroven potom triedy Screen
 */
public  class Level extends Screen {

    private LevelEntity lvlEntity;
    private Background background;
    private String levelPathName;
    private Player player;
    private LevelGrid grid;
    private BufferedImage heart;
    private Coin coin;

    /**
     * Konstruktor priradi Objektu manazera
     */
    public Level(ScreensManager manager, LevelEntity lvlEntity) {
        super(manager);
        this.lvlEntity = lvlEntity;
        this.levelPathName = this.lvlEntity.getPathToMapFile();
        this.background = new Background("images/bgColloseum2.png");
        this.grid = new LevelGrid(30);
        this.player = new Player(this.grid);
        this.coin = new Coin(100, 1 * 31, this.grid);
        this.init();
    }

    /**
     * Inicializicia
     * nacita obrazok zivota
     */
    @Override
    public void init() {
        this.getGrid().loadTiles("images/SandTiles.png");
        this.getGrid().loadMap(this.levelPathName);
        int mapHeight = this.getGrid().getHeight();
        this.getPlayer().setX(100);
        this.getPlayer().setY(mapHeight - (2 * 31));

        this.getGrid().setPosition(
                (double) GamePanel.WIDTH / 2 - this.getPlayer().getX(),
                (double) GamePanel.HEIGHT / 2 - this.getPlayer().getY()
        );
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
        this.getGrid().setPosition(
                (double) GamePanel.WIDTH / 2 - this.getPlayer().getX(),
                (double) GamePanel.HEIGHT / 2 - this.getPlayer().getY()
        );
        this.checkCollisionOfPlayerAndCoin();
    }

    /**
     * vykreslenie pozadia, siete stvorcov, hraca a zivotov hraca
     *
     * @param graphics tvar
     */
    @Override
    public void draw(Graphics2D graphics) {
        this.background.draw(graphics);
        this.grid.draw(graphics);
        this.player.draw(graphics);
        this.coin.draw(graphics);
    }

    /**
     * Prepne na dalsi lvl ked je podm. splnena
     */

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
    public void checkCollisionOfPlayerAndCoin() {

        double x1Player = this.player.getX();
        double x2Player = this.player.getX() + this.player.getSirka();

        double y1Player = this.player.getY();
        double y2Player = this.player.getY() + this.player.getVyska();

        double x1Coin = this.coin.getX();
        double x2Coin = this.coin.getX() + this.coin.getSirka();

        double y1Coin = this.coin.getY();
        double y2Coin = this.coin.getY() + this.coin.getVyska();

        if (x1Player < x2Coin && x2Player > x1Coin && y1Player < y2Coin && y2Player > y1Coin) {
            this.finishLevel();
        }
    }

    public void finishLevel() {
        this.lvlEntity.setCompleted();
        super.getManager().getSaveManager().unlockNextLevel(this.lvlEntity);
        super.getManager().setLoadScreen();
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
            super.getManager().setMenuScreen();
        }
        this.player.keyPressed(k);
    }
}
