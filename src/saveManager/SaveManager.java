package saveManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class SaveManager {
    // Cesta musí začínať lomítkom, ak je v root priečinku resources
    private static final String FILE_NAME = "/saveGame/saveFile.dat";
    private ArrayList<LevelEntity> levels;

    public SaveManager() {
        this.levels = new ArrayList<>();
    }

    public void loadSave() {
        // 1. Získame InputStream z classpath (z resources)
        InputStream inputStream = getClass().getResourceAsStream(FILE_NAME);

        if (inputStream != null) {
            // 2. Obalíme InputStream do Readeru, aby tomu GSON rozumel
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
            // Ak getResourceAsStream vráti null, súbor sa nenašiel (napr. zlá cesta)
            System.out.println("File does not exist inside resources: " + FILE_NAME);
        }
    }

    // ... zvyšok tvojho kódu (getLevels, getLevelById, atď.) ostáva rovnaký

    public ArrayList<LevelEntity> getLevels() {
        return this.levels;
    }

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

    public LevelEntity getFirstLevel() {
        if (this.levels != null && !this.levels.isEmpty()) {
            return this.levels.get(0);
        }
        return null;
    }

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