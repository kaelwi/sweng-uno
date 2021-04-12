import java.util.ArrayList;

public class Card {

    // Attributes: color, value, points?

    // https://www.letsplayuno.com/news/guide/20181213/30092_732567.html
    private final static String[] colorCollection = {"R", "G", "B", "Y", ""};
    private final static String[] valueCollection = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+2", "<->", "X", "W","W+4"};
    private final static int[] pointCollection = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 20, 50};

    private final String color;
    private final String value;
    private final int points;

    // constructor
    public Card(String color, String value, int points) {
        this.color = color;
        this.value = value;
        this.points = points;
    }

    // empty constructor
    public Card() {
        this.color = " ";
        this.value = " ";
        this.points = 0;
    };

    // getter for all colors
    public static String[] getAllColors() {
        return colorCollection;
    }

    // getter for all values
    public static String[] getAllValues() {
        return valueCollection;
    }

    // getter for all points
    public static int[] getAllPoints() {
        return pointCollection;
    }

    // override tostring to be able to print the cards in a nice manner
    @Override
    public String toString() {
        return color + value;
    }

}
