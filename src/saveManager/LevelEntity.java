package saveManager;

public class LevelEntity {

    private int id;
    private boolean completed;
    private boolean unlocked;
    private String pathToMapFile;

    public LevelEntity() {

    }

    public LevelEntity(int id, boolean completed, boolean unlocked, String pathToMapFile) {
        this.id = id;
        this.completed = completed;
        this.pathToMapFile = pathToMapFile;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCompleted() {
        return this.completed;
    }

    public boolean isUnlocked() {
        return this.unlocked;
    }

    public void setLocked() {
        this.unlocked = false;
    }

    public void setUnlocked() {
        this.unlocked = true;
    }

    public void setCompleted() {
        this.completed = true;
    }

    public void setNotCompleted() {
        this.completed = false;
    }

    public String getPathToMapFile() {
        return this.pathToMapFile;
    }

    @Override
    public String toString() {
        return "Level " + this.id + ": " + (this.completed ? "Hotovo" : "Nedokončené " + (this.unlocked ? "odomknute" : "zamknute") + this.pathToMapFile);
    }
}
