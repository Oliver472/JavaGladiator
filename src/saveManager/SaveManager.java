package saveManager;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SaveManager {

    private static SaveManager instance;
    private static final String FILE_NAME = "./saveGame/saveFile.dat";
    private ArrayList<LevelEntity> levels;

    public void SaveManager() {
        this.levels = new ArrayList<>();
    }

    public static SaveManager getInstance() {
        if (instance == null) {
            instance = new SaveManager();
        }
        return instance;
    }

    public void loadGame() {
        File file = new File(FILE_NAME);

        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<LevelEntity>>(){}.getType();
                this.levels = gson.fromJson(reader, listType);

                if (this.levels != null) {
                    System.out.println("Game loaded, number of lvls: " + levels.size());
                    for (LevelEntity lvl : levels) {
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
}
