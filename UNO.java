import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;

public class UNO {
    private Baraja baraja;
    private ArrayList<ArrayList<Carta>> cartasJugadores;
    private ArrayList<Carta> cementerio;
    private ArrayList<Boolean> masMovimientos;
    private int turno;
    private int cantidadDeJugadores;
    private int direccion;
    private Carta cartaPuesta;
    boolean juegoFinalizado;
    boolean pasar;
    //int index = 0;
    boolean primerTurno;
    boolean turnoFinalizado;
    boolean cartaValida;
    JLabel mensajeLabel;
    //private int winnerTurn;

    public UNO(int cantidadDeJugadores, JLabel mensajeLabel) {
        baraja = new Baraja();
        this.cantidadDeJugadores = cantidadDeJugadores;
        this.turno = 0;
        this.mensajeLabel = mensajeLabel;
        this.direccion = 1;
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

    public void inicializarJuego() {
        repartirCartas();
        cartaPuesta = new Carta();
        cartaPuesta = cementerio.getFirst();
        setCartaPuesta(cartaPuesta);
        cementerio.removeFirst();

        this.juegoFinalizado = false;
        this.pasar = false;
        this.primerTurno = true;
        this.turnoFinalizado = false;
        this.cartaValida = false;
    }

    public void jugar() {
        /*boolean juegoFinalizado = false;
        //cartaPuesta = new Carta();
        boolean pasar = false;
        boolean primerTurno = true;
        boolean turnoFinalizado;
        boolean cartaValida;
        direccion = 1;*/
        Scanner input = new Scanner(System.in);
        int index = 0;

        while (!juegoFinalizado) {
            turnoFinalizado = false;
            cartaValida = false;

            //turnoFinalizado = jugadorTieneMovimientos(turno, cartaPuesta, turnoFinalizado);

            if (!turnoFinalizado) {

                while (!cartaValida) {
                    System.out.println("Seleccione carta: ");
                    index = input.nextInt();
                    cartaValida = esCartaValida(cartasJugadores.get(turno).get(index), cartaPuesta);
                }

                cartaPuesta = cartasJugadores.get(turno).get(index);
                cartasJugadores.get(turno).remove(index);
                //direccion = manejarEfectoCarta(cartaPuesta.getValor(), direccion);

                //cartaPuesta = jugarCarta(cartaValida, cartaPuesta);
                direccion = manejarEfectoCarta(cartaPuesta.getValor(), direccion, cartaValida, cartaPuesta, turnoFinalizado);

                turnoFinalizado = true;

                // IMPRESIONES PARA PRUEBAS
                System.out.println("CARTAS JUGADOR " + (turno + 1) + ": " + cartasJugadores.get(turno).toString());
                System.out.println("NUEVA CARTA PUESTA: " + cartaPuesta.toString());
                // AQUI ACABAN
            }

            if (noTieneCartasElJugador(turno)) {
                juegoFinalizado = true;
            }

            //actualizarTurno(juegoFinalizado, direccion);

            primerTurno = false;
            System.out.println();
        }
    }

    public boolean jugadorTieneMovimientos(int turno, Carta cartaPuesta) {
        int cantidadCartasDisponibles;
        cantidadCartasDisponibles = (int) cartasJugadores.get(turno).stream().filter(carta -> (carta.getValor() == cartaPuesta.getValor()) || carta.getFigura().equals(cartaPuesta.getFigura())).count();

        return cantidadCartasDisponibles > 0;

        /*if (!(cantidadCartasDisponibles > 0) && !cementerioEstaVacio()) {
            mensajeLabel.setText("No tienes movimientos, debes tomar una carta.");
            comerDeCementerio(turno);
            cantidadCartasDisponibles = (int) cartasJugadores.get(turno).stream().filter(carta -> (carta.getValor() == cartaPuesta.getValor()) || carta.getFigura().equals(cartaPuesta.getFigura())).count();
            System.out.println("CARTAS JUGADOR: " + cartasJugadores.get(turno).toString());

            if (!(cantidadCartasDisponibles > 0)) {
                mensajeLabel.setText("Aún no tienes movimientos después de comer, pasa automáticamente.");
                System.out.println();
                turnoFinalizado = true;
            }
        } else if (!(cantidadCartasDisponibles > 0) && cementerioEstaVacio()) {

            //Poner en el GUI que no tiene movimientos, pasar.
            mensajeLabel.setText("No hay cartas para comer, pasas automáticamente.");
            turnoFinalizado = true;
        }*/

        //return turnoFinalizado;
    }

    public boolean cementerioEstaVacio() {
        return cementerio.isEmpty();
    }

    public void actualizarTurno(int direccion) {
        int cantidadDeJugadores = 3;

        if (!juegoFinalizado) {
            this.turno += this.direccion;
            //System.out.println("dir: " + direccion);

            if (turno >= cantidadDeJugadores) {
                this.turno = 0;
            } else if (turno < 0) {
                this.turno = cantidadDeJugadores - 1;
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
        System.out.println("Seleccionada: " + cartaSeleccionada.getFigura() + " , " + cartaSeleccionada.getValor());
        System.out.println("Puesta: " + cartaPuesta.getFigura() + " , " + cartaPuesta.getValor());
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

    public void jugarCarta(boolean cartaValida, Carta cartaSeleccionada) {
        //Scanner input = new Scanner(System.in);
        //int index = 0;

        /*while (!cartaValida) {
            System.out.println("Seleccione carta: ");
            index = input.nextInt();
            cartaValida = esCartaValida(cartasJugadores.get(turno).get(index), cartaPuesta);
        }*/

        this.cartaPuesta = cartaSeleccionada;
        System.out.println("Puesta en jugar UNO: " + cartaPuesta.getFigura() + " , " + cartaPuesta.getValor());
        cartasJugadores.get(turno).remove(cartaSeleccionada);
    }

    public int manejarEfectoCarta(int valorCarta, int direccion, boolean cartaValida, Carta cartaPuesta, boolean turnoFinalizado) {
        Scanner input = new Scanner(System.in);
        int opc = 0;
        System.out.println("VALOR CARTA: " + valorCarta);

        switch (valorCarta) {
            case 1:
                System.out.println("CARTA ANTES: " + cartaPuesta.toString());
                //turnoFinalizado = jugadorTieneMovimientos(turno, cartaPuesta, turnoFinalizado);
                System.out.println("CARTAS JUGADOR " + (turno + 1) + ": " + cartasJugadores.get(turno).toString());
                //cartaPuesta = jugarCarta(direccion, cartaValida, cartaPuesta);
                direccion = manejarEfectoCarta(cartaPuesta.getValor(), direccion, cartaValida, cartaPuesta, turnoFinalizado);
                System.out.println("CARTA DESPUES: " + cartaPuesta.toString());
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

    public ArrayList<ArrayList<Carta>> getCartasJugadores() {
        return cartasJugadores;
    }

    public int getTurno() {
        return this.turno;
    }

    public void setCartaPuesta(Carta carta) {
        this.cartaPuesta = carta;
    }

    public Carta getCartaPuesta() {
        return this.cartaPuesta;
    }

    public int getDireccion() {
        return this.direccion;
    }

    public boolean getJuegoFinalizado() {
        return this.juegoFinalizado;
    }

    public boolean getPasar() {
        return this.pasar;
    }

    public boolean getEsPrimerTurno() {
        return this.primerTurno;
    }

    public boolean getTurnoFinalizado() {
        return this.turnoFinalizado;
    }

    public boolean getEsCartaValida() {
        return this.cartaValida;
    }

    public void setTurnoFinalizado(boolean turnoFinalizado) {
        this.turnoFinalizado = turnoFinalizado;
    }

    public void setCartaValida(boolean cartaValida) {
        this.cartaValida = cartaValida;
    }
}