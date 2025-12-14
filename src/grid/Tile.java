package grid;

import java.awt.image.BufferedImage;

/**
 * Trieda Tile predstavuje jeden stvorec v sieti stvorcov na ktoru je rozdelena cela plocha
 */
public class Tile {

    private final BufferedImage image;
    private final int type;

    public static final int NORMAL = 0;
    public static final int BLOCKED = 1;

    /**
     * @param image subImage zo sandTiles
     * @param type  typ 0 alebo 1
     */
    public Tile(BufferedImage image, int type) {
        this.image = image;
        this.type = type;

    }

    /**
     * @return vrati subImage
     */
    public BufferedImage getImage() {
        return this.image;
    }

    /**
     * @return vrati typ tilu
     */
    public int getType() {
        return this.type;
    }

}
