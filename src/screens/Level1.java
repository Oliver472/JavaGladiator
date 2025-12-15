package screens;

import enemies.Scout;
import main.GamePanel;

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

        //this.scout = new Scout(100, 100, super.getGrid());

        int mapHeight = super.getGrid().getHeight();

        // 3. NASTAVENIE HRÁČA
        // X = 100
        // Y = Výška mapy - (veľa pixelov), aby sme ho dali nad podlahu
        // Dajte napríklad -150 alebo -200, aby ste mali istotu, že sa spawne nad zemou
        super.getPlayer().setX(100);
        super.getPlayer().setY(mapHeight - 200);

        // 4. Nastavenie kamery na hráča
        super.getGrid().setPosition(
                GamePanel.WIDTH / 2 - super.getPlayer().getX(),
                GamePanel.HEIGHT / 2 - super.getPlayer().getY()
        );


    }


    /**
     * Update
     */
    @Override
    public void update() {
        super.getPlayer().update();

        // Update kamery (aby sledovala hráča)
        super.getGrid().setPosition(
                GamePanel.WIDTH / 2 - super.getPlayer().getX(),
                GamePanel.HEIGHT / 2 - super.getPlayer().getY()
        );
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
        //this.scout.draw(graphics);
        //this.scout.moveEnemy((int)super.getPlayer().getX(), (int)super.getPlayer().getY());
        //this.koliziaHracaAEnemy(this.scout);

    }


}
