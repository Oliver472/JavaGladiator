package screens;

import enemies.Scout;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * LEVEL 2
 */
public class Level2 extends Level {


    private double f;
    private BufferedImage heart;
    private Scout scout;
    private Scout scout2;
    private Scout scout3;
    private Scout scout4;

    /**
     * Vytvori LEVEL 2
     *
     * @param manager manazer screenov
     */
    public Level2(ScreensManager manager) {
        super(manager, "images/sandBg.png", 370, 95);

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
        super.getGrid().loadMap("maps/level2.map");
        super.getGrid().setPosition(0, 0);


        this.scout = new Scout(100, 70,  super.getGrid());
        this.scout2 = new Scout(200, 50,  super.getGrid());
        this.scout3 = new Scout(300, 70,  super.getGrid());
        this.scout4 = new Scout(400, 50,  super.getGrid());


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
        this.scout2.draw(graphics);
        this.scout3.draw(graphics);
        this.scout4.draw(graphics);

        this.scout.moveEnemy((int)super.getPlayer().getX(), (int)super.getPlayer().getY());
        this.scout2.moveEnemy((int)super.getPlayer().getX(), (int)super.getPlayer().getY());
        this.scout3.moveEnemy((int)super.getPlayer().getX(), (int)super.getPlayer().getY());
        this.scout4.moveEnemy((int)super.getPlayer().getX(), (int)super.getPlayer().getY());
        this.koliziaHracaAEnemy(this.scout);

    }


}
