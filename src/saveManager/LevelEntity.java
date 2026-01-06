package saveManager;

/**
 * Trieda reprezentujuca jeden level v hre
 */
public class LevelEntity {

    private int id;
    private boolean completed;
    private boolean unlocked;
    private String pathToMapFile;

    /**
     * Konstruktor inicializuje level s danym ID, stavom dokoncenia, odomknutia a cestou k suboru mapy
     *
     * @param id            identifikacne cislo levelu
     * @param completed     ci je level dokonceny
     * @param unlocked      ci je level odomknuty
     * @param pathToMapFile cesta k suboru s mapou levelu
     */
    public LevelEntity(int id, boolean completed, boolean unlocked, String pathToMapFile) {
        this.id = id;
        this.completed = completed;
        this.unlocked = unlocked;
        this.pathToMapFile = pathToMapFile;
    }

    /**
     * Vrati identifikacne cislo levelu
     *
     * @return ID levelu
     */
    public int getId() {
        return this.id;
    }

    /**
     * Nastavi identifikacne cislo levelu
     *
     * @param id nove ID levelu
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Vrati, ci je level dokonceny
     *
     * @return true ak je dokonceny, inak false
     */
    public boolean isCompleted() {
        return this.completed;
    }

    /**
     * Vrati, ci je level odomknuty
     *
     * @return true ak je odomknuty, inak false
     */
    public boolean isUnlocked() {
        return this.unlocked;
    }

    /**
     * Zamkne level
     */
    public void setLocked() {
        this.unlocked = false;
    }

    /**
     * Odomkne level
     */
    public void setUnlocked() {
        this.unlocked = true;
    }

    /**
     * Označi level ako dokonceny
     */
    public void setCompleted() {
        this.completed = true;
    }

    /**
     * Označi level ako nedokonceny
     */
    public void setNotCompleted() {
        this.completed = false;
    }

    /**
     * Vrati cestu k suboru mapy levelu
     *
     * @return cesta k suboru mapy
     */
    public String getPathToMapFile() {
        return this.pathToMapFile;
    }

    /**
     * Textova reprezentacia levelu pre vypis alebo debug
     *
     * @return retazec s informaciami o leveli
     */
    @Override
    public String toString() {
        return "Level " + this.id + ": " + (this.completed ? "Hotovo" : "Nedokončené ") + (this.unlocked ? "odomknute" : "zamknute") + " " + this.pathToMapFile;
    }
}