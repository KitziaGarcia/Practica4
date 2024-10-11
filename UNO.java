import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class UNO {
    private Baraja baraja;
    private ArrayList<ArrayList<Carta>> cartasJugadores;
    private ArrayList<Carta> cementerio;
    private ArrayList<Boolean> masMovimientos;
    private int turno;
    private int cantidadDeJugadores;
    private int direccion;
    //private int winnerTurn;

    public UNO(int cantidadDeJugadores) {
        baraja = new Baraja();
        this.cantidadDeJugadores = cantidadDeJugadores;
        turno = 0;
        repartirCartas();
        System.out.println(cartasJugadores.toString());
    }

    public void repartirCartas() {
        int cartasPorJugador = 7;
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
        boolean juegoFinalizado = false;
        Carta cartaPuesta = new Carta();
        boolean pasar = false;
        Scanner input = new Scanner(System.in);
        int index = 0;
        boolean primerTurno = true;
        boolean turnoFinalizado;
        boolean cartaValida;
        direccion = 1;

        //masMovimientos = new ArrayList<Boolean>(Arrays.asList(new Boolean[cantidadDeJugadores]));
        //Collections.fill(masMovimientos, Boolean.TRUE);

        while (!juegoFinalizado) {
            turnoFinalizado = false;
            cartaValida = false;

            //System.out.println("CEMENTERIO: " + cementerio.toString());

            if (primerTurno) {
                cartaPuesta = cementerio.getFirst();
                cementerio.removeFirst();
            }

            System.out.println("CARTA PUESTA: " + cartaPuesta.toString());
            System.out.println("CARTAS JUGADOR " + (turno + 1) + ": " + cartasJugadores.get(turno).toString());

            //System.out.println("CEMENTERIO: " + cementerio.toString());

            //Revisar si el jugador tiene mas movimientos para continuar su turno.

            if (!jugadorTieneMovimientos(turno, cartaPuesta) && !cementerioEstaVacio()) {
                System.out.println("No tienes movimientos, come una carta.");
                comerDeCementerio(turno);
                System.out.println("CARTAS JUGADOR: " + cartasJugadores.get(turno).toString());

                if (!jugadorTieneMovimientos(turno, cartaPuesta)) {
                    System.out.println("Aún no tienes movimientos después de comer, pasa automáticamente.");
                    pressEnterToContinue();
                    System.out.println();
                    turnoFinalizado = true;
                }

            } else if (!jugadorTieneMovimientos(turno, cartaPuesta) && cementerioEstaVacio()) {
                //Poner en el GUI que no tiene movimientos, pasar.
                System.out.println("No tiene movimientos y no hay cartas para comer, pasa automaticamente.");
                turnoFinalizado = true;

            }

            if (!turnoFinalizado) {
                while (!cartaValida) {
                    System.out.println("Seleccione carta: ");
                    index = input.nextInt();
                    cartaValida = esCartaValida(cartasJugadores.get(turno).get(index), cartaPuesta);
                }

                cartaPuesta = cartasJugadores.get(turno).get(index);
                cartasJugadores.get(turno).remove(index);
                direccion = manejarEfectoCarta(cartaPuesta.getValor(), direccion);

                turnoFinalizado = true;

                // IMPRESIONES PARA PRUEBAS
                System.out.println("CARTAS JUGADOR " + (turno + 1) + ": " + cartasJugadores.get(turno).toString());
                System.out.println("NUEVA CARTA PUESTA: " + cartaPuesta.toString());
                // AQUI ACABAN

                pressEnterToContinue();
            }

            if (noTieneCartasElJugador(turno)) {
                juegoFinalizado = true;
            }

            actualizarTurno(juegoFinalizado, direccion);

            primerTurno = false;
            System.out.println();
        }
    }

    public boolean jugadorTieneMovimientos(int turno, Carta cartaPuesta) {
        int cantidadCartasDisponibles;
        cantidadCartasDisponibles = (int) cartasJugadores.get(turno).stream().filter(carta -> (carta.getValor() == cartaPuesta.getValor()) || carta.getFigura().equals(cartaPuesta.getFigura())).count();
        System.out.println("CARTAS DISPONIBLES: " + cantidadCartasDisponibles);
        return cantidadCartasDisponibles > 0;
    }

    public boolean cementerioEstaVacio() {
        return cementerio.isEmpty();
    }

    public void pressEnterToContinue() {
        Scanner input = new Scanner(System.in);
        input.nextLine();
    }

    public void actualizarTurno(boolean juegoFinalizado, int direccion) {
        if (!juegoFinalizado) {
            turno += direccion;

            if (turno >= cantidadDeJugadores) {
                turno = 0;
            } else if (turno < 0) {
                turno = cantidadDeJugadores - 1;
            }
        }
    }

    public int cambiarDireccion(int direccion) {
        direccion *= -1;
        return direccion;
    }

    public void comerDeCementerio(int turno) {
        if (!cementerioEstaVacio()) {
            cartasJugadores.get(turno).add(cementerio.getFirst());
            cementerio.removeFirst();
        }
    }

    public boolean esCartaValida(Carta cartaSeleccionada, Carta cartaPuesta) {
        boolean cartaValida = true;
        if ((cartaSeleccionada.getFigura().equals(cartaPuesta.getFigura())) || (cartaSeleccionada.getValor() == cartaPuesta.getValor())) {
            cartaValida = true;
        } else {
            System.out.println("Carta invalida.");
            cartaValida = false;
        }
        return cartaValida;
    }

    public boolean noTieneCartasElJugador(int turno) {
        return cartasJugadores.get(turno).isEmpty();
    }

    public int manejarEfectoCarta(int valorCarta, int direccion) {
        Scanner input = new Scanner(System.in);
        int opc = 0;
        System.out.println("VALOR CARTA: " + valorCarta);

        switch (valorCarta) {
            case 1:
                if (!cementerioEstaVacio()) {
                    cementerio.getFirst();
                }
            break;
            case 2:

            break;
            case 3:

            break;
            case 11:
                opc = Utilidades.isInputValid(input, "1. Saltar siguiente jugador.    2. Cambiar direccion de partida.", 1, 2);
                if (opc == 1) {
                    if (turno == cantidadDeJugadores - 1 && direccion == 1) {
                        turno = 0;
                    } else if (turno == 0 && direccion == -1) {
                        turno = cantidadDeJugadores -1;
                    } else {
                        turno+= direccion;
                    }
                } else {
                    direccion = cambiarDireccion(direccion);
                }

            break;
            case 12:
            //Que pida el numero del jugador a quitar cartas, las muestre volteadas y pueda escoger cual quiere.

            break;
        }
        return direccion;
    }
}
