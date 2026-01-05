package saveManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class SaveManager {
    private static final String FILE_NAME = "./saveGame/saveFile.dat";
    private ArrayList<LevelEntity> levels;

    public SaveManager() {
        this.levels = new ArrayList<>();
    }

    public void loadSave() {
        File file = new File(FILE_NAME);

        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<LevelEntity>>(){}.getType();
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
            System.out.println("File does not exist");
        }
    }

    public void saveGame() {
        File file = new File(FILE_NAME);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        try (FileWriter writer = new FileWriter(file)) {
            Gson gson = new Gson();
            gson.toJson(this.levels, writer);
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Problem with saving");
        }
    }

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
        for (LevelEntity level : this.levels) {
            level.setNotCompleted();
            level.setLocked();
        }
        this.levels.get(0).setUnlocked();
        this.saveGame();
    }

    public LevelEntity getFirstLevel() {
        return this.levels.get(0);
    }

    public void unlockNextLevel(LevelEntity level) {
        int currentIndex = this.levels.indexOf(level);
        if (currentIndex != -1 && currentIndex < (this.levels.size() - 1)) {
            LevelEntity nextLevel = this.levels.get(currentIndex + 1);
            nextLevel.setUnlocked();
            this.saveGame();
        }
    }
}
