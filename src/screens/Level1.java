package screens;

import enemies.Scout;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


/**
 * LEVEL 1
 */
public class Level1 extends Level {

    private BufferedImage minca;

    private double f;
    private Scout scout;


    /**
     * Vytvori LEVEL 1
     *
     * @param manager manazer screenov
     */
    public Level1(ScreensManager manager) {
        super(manager, "images/sandBg.png", 370, 95 );


        init();
    }

    /**
     * Vytvori pozadie, siet stvorcov, hraca a nepriatelov
     */
    @Override
    public void init() {
        super.init();
        this.f = 0;

        super.getGrid().loadTiles("images/SandTiles.png");
        super.getGrid().loadMap("maps/level1.map");
        super.getGrid().setPosition(0, 0);


        this.scout = new Scout(100, 100, super.getGrid());


    }


    /**
     * Update
     */
    @Override
    public void update() {

    }

    /**
     * Vykreslenie hraca a nepriatelov
     * zmena x a y suradnic nepriatela
     * check kolizie
     *
     * @param graphics tvar
     */
    @Override
    public void draw(Graphics2D graphics) {
        super.draw(graphics);
        this.scout.draw(graphics);
        this.scout.moveEnemy((int)super.getPlayer().getX(), (int)super.getPlayer().getY());
        this.koliziaHracaAEnemy(this.scout);

    }


}
