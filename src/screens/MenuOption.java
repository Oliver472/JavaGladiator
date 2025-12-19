package screens;

public enum MenuOption {
    PLAY("images/playBtn.png", "images/playBtnPressed.png"),
    NEW_GAME("images/playBtn.png", "images/playBtnPressed.png"),
    LOAD_SAVE("images/loadBtn.png", "images/loadBtnPressed.png"),
    OPTIONS("images/optionsBtn.png", "images/optionsBtnPressed.png"),
    QUIT("images/quitBtn.png", "images/quitBtnPressed.png");

    private final String normalPath;
    private final String pressedPath;

    MenuOption(String normalPath, String pressedPath) {
        this.normalPath = normalPath;
        this.pressedPath = pressedPath;
    }

    public String getNormalPath() { return normalPath; }
    public String getPressedPath() { return pressedPath; }

    public MenuOption next() {
        int nextIndex = (this.ordinal() + 1) % values().length;
        return values()[nextIndex];
    }

    public MenuOption previous() {
        int prevIndex = (this.ordinal() - 1 + values().length) % values().length;
        return values()[prevIndex];
    }
}