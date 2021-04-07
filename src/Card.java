import java.util.ArrayList;

public class Card {

    // Attributes: color, value, points?

    // https://www.letsplayuno.com/news/guide/20181213/30092_732567.html
    private final static String[] colorCollection = {"R", "G", "B", "Y", "W"};
    private final static String[] valueCollection = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+2", "<->", "X", "W","W+4"};

    private final String color;
    private final String value;

    public Card(String color, String value) {
        this.color = color;
        this.value = value;
    }

    // empty constructor
    public Card() {
        this.color = " ";
        this.value = " ";
    };

    public static String[] getAllColors() {
        return colorCollection;
    }

    public static String[] getAllValues() {
        return valueCollection;
    }

}
