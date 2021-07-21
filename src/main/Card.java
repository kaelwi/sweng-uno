package main;

import java.util.Objects;

/**
 *  Card class to represent all cards of the UNO game.
 *
 *  Last Modified: 01.07.2021
 *  @author  Julia Summer
 *           Paulina Safar
 *           Karoline E. Wild
 */

public class Card {

    private final static String[] colorCollection = {"R", "G", "B", "Y", ""};
    private final static String[] valueCollection = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+2", "<->", "X", "W", "W+4"};
    private final static int[] pointCollection = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 20, 50};
    private final static String[] colorCodeCollection = {"\033[0;31m", "\033[0;32m", "\033[0;34m", "\033[0;33m", "\033[0m"};

    private final String color;
    private final String value;
    private final int points;
    private final String colorCode;

    public Card(String color, String value, int points, String colorCode) {
        this.color = color;
        this.value = value;
        this.points = points;
        this.colorCode = colorCode;
    }

    public Card() {
        this.color = " ";
        this.value = " ";
        this.points = 0;
        this.colorCode = " ";
    }

    public static String[] getAllColors() {
        return colorCollection;
    }

    public static String[] getAllValues() {
        return valueCollection;
    }

    public static int[] getAllPoints() {
        return pointCollection;
    }

    public static String[] getColorCodeCollection(){
        return colorCodeCollection;
    }

    @Override
    public String toString() {
        return colorCode + color + value + getColorCodeCollection()[4];
    }

    public String getColorValue(){
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

    public String getColorCode() {
        return colorCode;
    }

    public static String checkColorCode(String color){
        switch (color){
            case "R":
                return getColorCodeCollection()[0];
            case "G":
                return getColorCodeCollection()[1];
            case "B":
                return getColorCodeCollection()[2];
            case "Y":
                return getColorCodeCollection()[3];
            default:
                return getColorCodeCollection()[4];
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(color, card.color) && Objects.equals(value, card.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, value, points, colorCode);
    }
}
