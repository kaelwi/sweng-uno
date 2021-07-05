/* Diese Datei nicht ändern! */

import java.sql.SQLException;
import java.util.Scanner;

/**
 * This is the Main method of the UNO Program.
 * It starts and ends the Program
 */
public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner input = new Scanner(System.in);
        App app = new App(input, System.out);
        app.Run();
        input.close();
        System.out.println("The program is now ending ...");
    }
}

/* Diese Datei nicht ändern! */
