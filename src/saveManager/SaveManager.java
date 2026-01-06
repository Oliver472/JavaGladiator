package saveManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Trieda na spravu ukladania a nacitavania postupu hry
 */
public class SaveManager {
    private static final String FILE_NAME = "/saveGame/saveFile.dat";
    private ArrayList<LevelEntity> levels;

    /**
     * Konstruktor inicializuje prazdny zoznam levelov
     */
    public SaveManager() {
        this.levels = new ArrayList<>();
    }

    /**
     * Nacita save subor z resources a deserializuje ho do zoznamu levelov
     */
    public void loadSave() {
        InputStream inputStream = getClass().getResourceAsStream(FILE_NAME);
        if (inputStream != null) {
            try (Reader reader = new InputStreamReader(inputStream)) {

                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<LevelEntity>>() { }.getType();
                this.levels = gson.fromJson(reader, listType);

                if (this.levels != null) {
                    System.out.println("Game loaded, number of lvls: " + this.levels.size());
                    for (LevelEntity lvl : this.levels) {
                        System.out.println(lvl);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Problem with loading");
            }
        } else {
            System.out.println("File does not exist inside resources: " + FILE_NAME);
        }
    }

    /**
     * Vrati zoznam vsetkych levelov
     *
     * @return zoznam levelov
     */
    public ArrayList<LevelEntity> getLevels() {
        return this.levels;
    }

    /**
     * Vrati level podla jeho ID
     *
     * @param id identifikacne cislo levelu
     * @return najdeny level alebo null ak neexistuje
     */
    public LevelEntity getLevelById(int id) {
        if (this.levels != null) {
            for (LevelEntity level : this.levels) {
                if (level.getId() == id) {
                    return level;
                }
            }
        }
        return null;
    }

    /**
     * Vymaze postup, zamkne vsetky levely okrem prveho a oznací vsetky ako nedokoncene
     */
    public void eraseSave() {
        if (this.levels != null && !this.levels.isEmpty()) {
            for (LevelEntity level : this.levels) {
                level.setNotCompleted();
                level.setLocked();
            }
            this.levels.get(0).setUnlocked();
            // this.saveGame();
        }
    }

    /**
     * Vrati prvy level v zozname
     *
     * @return prvy level alebo null ak zoznam prazdny
     */
    public LevelEntity getFirstLevel() {
        if (this.levels != null && !this.levels.isEmpty()) {
            return this.levels.get(0);
        }
        return null;
    }

    /**
     * Po dokonceni aktualneho levelu odomkne nasledujuci level
     *
     * @param level dokonceny level
     */
    public void unlockNextLevel(LevelEntity level) {
        if (this.levels == null) {
            return;
        }

        int currentIndex = this.levels.indexOf(level);
        if (currentIndex != -1 && currentIndex < (this.levels.size() - 1)) {
            LevelEntity nextLevel = this.levels.get(currentIndex + 1);
            nextLevel.setUnlocked();
            // this.saveGame();
        }
    }
}