package screens;

public enum MenuOption {
    NEW_GAME("images/newGameBtn.png", "images/newGameBtnPressed.png"),
    LOAD_SAVE("images/loadGameBtn.png", "images/loadGameBtnPressed.png"),
    OPTIONS("images/optionsBtn.png", "images/optionsBtnPressed.png"),
    QUIT("images/exitBtn.png", "images/exitBtnPressed.png");

    private final String normalPath;
    private final String pressedPath;

    MenuOption(String normalPath, String pressedPath) {
        this.normalPath = normalPath;
        this.pressedPath = pressedPath;
    }

    public String getNormalPath() {
        return this.normalPath;
    }

    public String getPressedPath() {
        return this.pressedPath;
    }

    public MenuOption next() {
        int nextIndex = (this.ordinal() + 1) % values().length;
        return values()[nextIndex];
    }

    public MenuOption previous() {
        int prevIndex = (this.ordinal() - 1 + values().length) % values().length;
        return values()[prevIndex];
    }
}