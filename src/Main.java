/* Diese Datei nicht ändern! */

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        App app = new App(input, System.out);
        app.Run();
        input.close();
        System.out.println("Das Programm wird beendet ...");
    }
}

/* Diese Datei nicht ändern! */
