package screens;

import grid.Pozadie;

import java.awt.Graphics2D;

public class LoadScreen extends Screen {

    private Pozadie pozadie;

    /**
     * Konstruktor nastavi manazera
     *
     * @param manager manazero screenov
     */
    public LoadScreen(ScreensManager manager) {
        super(manager);
        this.init();
    }

    @Override
    public void init() {
        this.pozadie = new Pozadie("images/bgLoadLevels.png");
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics2D graphics) {
        this.pozadie.draw(graphics);
    }

    @Override
    public void keyReleased(int k) {

    }

    @Override
    public void keyPressed(int k) {

    }
}
