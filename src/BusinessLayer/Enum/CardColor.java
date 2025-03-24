package src.BusinessLayer.Enum;

public enum CardColor {
    BLUE,
    GREEN,
    RED,
    YELLOW,
    BLACK;

    public static CardColor[] getAllColorsExceptBlack() {
        return new CardColor[] {BLUE, GREEN, RED, YELLOW};
    }
}
