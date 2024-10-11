import java.util.Scanner;

/**
 *
 * This class contains a method, isInputValid, that validates user input from the console.
 * It prompts the user with a message, reads the input, and checks whether the input
 * is an integer within a specified range (between a minimum and a maximum value).
 * If the input is invalid (not an integer or out of range), it continues to prompt
 * the user until a valid input is received.
 */
public class Utilidades {
    public static int isInputValid(Scanner input, String message, int min, int max) {
        int input2 = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.println(message);

            try {
                input2 = Integer.parseInt(input.nextLine());

                if (input2 >= min && input2 <= max) {
                    validInput = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida.");
                System.out.println();
            }
        }
        return input2;
    }
}
