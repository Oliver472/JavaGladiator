package screens;

/**
 * Enum reprezentujuci polozky hlavneho menu hry s cestami k ich obrazkom
 */
public enum MenuOption {
    NEW_GAME("/images/newGameBtn.png", "/images/newGameBtnPressed.png"),
    LOAD_SAVE("/images/loadGameBtn.png", "/images/loadGameBtnPressed.png"),
    OPTIONS("/images/optionsBtn.png", "/images/optionsBtnPressed.png"),
    QUIT("/images/exitBtn.png", "/images/exitBtnPressed.png");

    private final String normalPath;
    private final String pressedPath;

    /**
     * Konstruktor enumu nastavi cesty k normalnemu a stlacenemu obrazku tlacidla
     *
     * @param normalPath  cesta k obrazku v normalnom stave
     * @param pressedPath cesta k obrazku v stlacenom stave
     */
    MenuOption(String normalPath, String pressedPath) {
        this.normalPath = normalPath;
        this.pressedPath = pressedPath;
    }

    /**
     * Vrati cestu k obrazku tlacidla v normalnom stave
     *
     * @return cesta k normalnemu obrazku
     */
    public String getNormalPath() {
        return this.normalPath;
    }

    /**
     * Vrati cestu k obrazku tlacidla v stlacenom stave
     *
     * @return cesta k stlacenemu obrazku
     */
    public String getPressedPath() {
        return this.pressedPath;
    }

    /**
     * Vrati nasledujucu polozku menu
     *
     * @return nasledujuca MenuOption
     */
    public MenuOption next() {
        MenuOption[] allOptions = values();
        int currentIndex = this.ordinal();
        int nextIndex = currentIndex + 1;

        if (nextIndex >= allOptions.length) {
            nextIndex = 0;
        }

        return allOptions[nextIndex];
    }

    /**
     * Vrati predchadzajucu polozku menu
     * @return predchadzajuca MenuOption
     */
    public MenuOption previous() {
        MenuOption[] allOptions = values();
        int currentIndex = this.ordinal();
        int prevIndex = currentIndex - 1;

        if (prevIndex < 0) {
            prevIndex = allOptions.length - 1; // vratime sa na koniec
        }

        return allOptions[prevIndex];
    }
}