package enemies;

import grid.LevelGrid;
import mapObjects.Entity;


/**
 * Trieda modeluje zakladne metody a atributy pre Enemies.
 *
 * @author Oliver Mrovcak
 */
public abstract class Enemy extends Entity {
  /**
     * Inicializacia konstruktora.
     *
     * @param x x suradnica na platne kde sa ma zobrazit nepriatel
     * @param y y suradnica na platne kde sa ma zobrazit nepriatel*
     **/
    public Enemy(int x, int y, LevelGrid tileMap) {
        super (x, y, tileMap);
    }




    /**
     * Meni x suradnicu nepriatela podla x suradnice hraca, priblizuje nepriatela k hracovi
     *
     * @param x poloha hraca X
     * @param y poloha hraca Y
     */
    public void moveEnemy(int x, int y) {
        int dx = 1;

        if (super.getX() < x) {
            super.setX(super.getX() + dx);
            super.setMoving(true);
            super.setHeadingL(false);
            super.setHeadingR(true);
        }

        if (super.getX() > x) {
            super.setX(super.getX() - dx);
            super.setMoving(true);
            super.setHeadingR(false);
            super.setHeadingL(true);
        }


    }


}
