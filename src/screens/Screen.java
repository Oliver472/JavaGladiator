package screens;

import java.awt.Graphics2D;


/**
 * Potomok vsetkych screenov ktore sa mozu vykreslit na ploche
 */
public abstract class Screen {

    private ScreensManager manager;

    /**
     * Konstruktor nastavi manazera
     *
     * @param manager manazero screenov
     */
    public Screen(ScreensManager manager) {
        this.manager = manager;
    }

    /**
     * Vrati manazera
     *
     * @return Manazer
     */
    protected ScreensManager getManager() {
        return this.manager;
    }

    /**
     * Inicializacia
     */
    public abstract void init();

    /**
     * Update
     */
    public abstract void update();

    /**
     * vykreslenie
     *
     * @param graphics tvar
     */
    public abstract void draw(Graphics2D graphics);

    /**
     * reakcia na pustenie tlacidla
     *
     * @param k keyCode
     */
    public abstract void keyReleased(int k);

    /**
     * reakcia na stlacenie tlacidla
     *
     * @param k keyCode
     */
    public abstract void keyPressed(int k);


}
