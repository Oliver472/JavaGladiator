package screens;

import java.util.ArrayList;

/**
 * Trieda sa stara o prepinanie mezdi screenmi a ich vykreslovanie
 */
public class ScreensManager {

    private final ArrayList<Screen> screens;
    private Screen currScreen;

    /**
     * Konstruktor vytovri ArrayList screenov a vytvori vsetky screeny ktore sa budu dat zobrazit
     * nastavi aktualny screen
     */
    public ScreensManager() {
        this.screens = new ArrayList<Screen>();
        Screen menuScreen = new MenuScreen(this);
        this.screens.add(menuScreen);
    }

    /**
     * vrati ArrayList screenov
     *
     * @return screeny
     */
    public ArrayList<Screen> getScreens() {
        return this.screens;
    }

    /**
     * Nastavi screen na aktualny
     *
     * @param screen screen
     */
    public void setScreen(Screen screen) {
        try {
            this.currScreen = screen;
            this.currScreen.init();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void setLoadScreen() {
        Screen screen = new LoadScreen(this);
        this.setScreen(screen);
    }

    /**
     * Vrati aktualny screen
     *
     * @return aktualny screen
     */
    public Screen getCurrScreen() {
        return this.currScreen;
    }

    /**
     * Update aktualneho screenu
     */
    public void update() {
        this.currScreen.update();
    }

    /**
     * vykreslenie aktualneho screenu
     *
     * @param g tvar
     */
    public void draw(java.awt.Graphics2D g) {
        this.currScreen.draw(g);
    }

    /**
     * Reaguje na stlacenie tlacidla a posieka spravu aktualne zobrazenemu screenu
     *
     * @param k tlacidlo
     */
    public void keyPressed(int k) {
        this.currScreen.keyPressed(k);
    }

    /**
     * Reaguje na pustenie tlacidla a posieka spravu aktualne zobrazenemu screenu
     *
     * @param k tlacidlo
     */
    public void keyReleased(int k) {
        this.currScreen.keyReleased(k);
    }

}
