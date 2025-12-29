package saveManager;

public class LevelEntity {
    public int id;
    public boolean completed;

    public LevelEntity() {

    }

    public LevelEntity(int id, boolean completed) {
        this.id = id;
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Level " + id + ": " + (completed ? "Hotovo" : "Nedokončené");
    }
}
