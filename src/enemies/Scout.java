package enemies;

import grid.LevelGrid;

import java.awt.Graphics2D;

/**
 * Potomok triedy Enemy, typ nepriatela -> Scout
 *
 * @author olivermrovcak
 */
public class Scout extends Enemy {


    /**
     * @param x       poloha X na ktorej sa vytvori enemy
     * @param y       poloha Y na ktorej sa vytvori enemy
     * @param tileMap mapa stvorcov
     */
    public Scout(int x, int y, LevelGrid tileMap) {
        super(x, y, tileMap);
        super.loadSprites("Scout");


    }


    /**
     * Update
     */
    @Override
    public void update() {

    }


    /**
     * Vykreslenie
     *
     * @param g 2d objekt
     */
    @Override
    public void draw(Graphics2D g) {

        super.draw(g);

        super.move();


    }
}
