public class Card {

    private final static String[] colorCollection = {"R", "G", "B", "Y", ""};
    private final static String[] valueCollection = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+2", "<->", "X", "W","W+4", ""};
    private final static int[] pointCollection = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 20, 50};

    private String color;
    private final String value;
    private final int points;

    public Card(String color, String value, int points) {
        this.color = color;
        this.value = value;
        this.points = points;
    }

    public Card() {
        this.color = " ";
        this.value = " ";
        this.points = 0;
    };

    public static String[] getAllColors() {
        return colorCollection;
    }

    public static String[] getAllValues() {
        return valueCollection;
    }

    public static int[] getAllPoints() {
        return pointCollection;
    }

    @Override
    public String toString() {
        return color + value;
    }

    public String getColor() {
        return color;
    }

    public String getValue() {
        return value;
    }

    public int getPoints() {
        return points;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
