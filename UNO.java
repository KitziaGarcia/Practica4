import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class UNO {
    private Baraja baraja;
    private ArrayList<ArrayList<Carta>> cartasJugadores;
    private ArrayList<Carta> cementerio;
    private ArrayList<Boolean> masMovimientos;
    private int turn;
    private int cantidadDeJugadores;
    //private int winnerTurn;

    public UNO(int cantidadDeJugadores) {
        baraja = new Baraja();
        this.cantidadDeJugadores = cantidadDeJugadores;
        turn = 0;
        repartirCartas();
        System.out.println(cartasJugadores.toString());
    }

    public void repartirCartas() {
        int cartasPorJugador = 7;
        baraja.mostrarBaraja();
        baraja.mezclarBaraja();
        cartasJugadores = new ArrayList<ArrayList<Carta>>();

        for (int i = 0; i < cantidadDeJugadores; i++) {
            ArrayList<Carta> cartasJugador = new ArrayList<Carta>();

            for (int j = 0; j < cartasPorJugador; j++) {
                cartasJugador.add(baraja.getCarta(0));
                baraja.borrarCarta(0);
            }

            cartasJugadores.add(cartasJugador);
        }

        cementerio = new ArrayList<Carta>();

        while (baraja.getTamano() > 0) {
            cementerio.add(baraja.getCarta(0));
            baraja.borrarCarta(0);
        }
    }

    public void jugar() {
        boolean endGame = false;
        Carta cartaPuesta = new Carta();

        masMovimientos = new ArrayList<Boolean>(Arrays.asList(new Boolean[cantidadDeJugadores]));
        Collections.fill(masMovimientos, Boolean.TRUE);

        while (!endGame) {

        }



    }

}
