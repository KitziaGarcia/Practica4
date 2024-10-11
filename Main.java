import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int cantidadDeJugadores;

        cantidadDeJugadores = Utilidades.isInputValid(input, "Ingrese cantidad de jugadores: ", 2, 4);

        UNO juego = new UNO(cantidadDeJugadores);
        juego.jugar();
    }
}