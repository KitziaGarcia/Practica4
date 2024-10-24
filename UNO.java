import java.lang.reflect.Array;
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
    private boolean accionEspecial;
    private boolean seComioCarta;
    //private int winnerTurn;

    public UNO(int cantidadDeJugadores) {
        baraja = new Baraja();
        this.cantidadDeJugadores = cantidadDeJugadores;
        this.turno = 0;
        this.direccion = 1;
        this.accionEspecial = false;
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

    public boolean jugadorTieneMovimientos(int turno, Carta cartaPuesta) {
        int cantidadCartasDisponibles;
        cantidadCartasDisponibles = (int) cartasJugadores.get(turno).stream().filter(carta -> (carta.getValor() == cartaPuesta.getValor()) || carta.getFigura().equals(cartaPuesta.getFigura())).count();

        return cantidadCartasDisponibles > 0;
    }

    public boolean cementerioEstaVacio() {
        return cementerio.isEmpty();
    }

    public void actualizarTurno(int direccion) {
        int cantidadDeJugadores = 3;

        if (!juegoFinalizado) {
            this.turno += this.direccion;

            if (turno >= cantidadDeJugadores) {
                this.turno = 0;
            } else if (turno < 0) {
                this.turno = cantidadDeJugadores - 1;
            }
        }

    }

    public void comerDeCementerio(int turno) {
        if (!cementerioEstaVacio()) {
            cartasJugadores.get(turno).add(cementerio.getFirst());
            cementerio.removeFirst();
        }
    }

    public boolean esCartaValida(Carta cartaSeleccionada, Carta cartaPuesta) {
        boolean cartaValida = true;
        System.out.println("Puesta: " + cartaPuesta.getFigura() + " , " + cartaPuesta.getValor());
        System.out.println("Seleccionada: " + cartaSeleccionada.getFigura() + " , " + cartaSeleccionada.getValor());
        if ((cartaSeleccionada.getFigura().equals(cartaPuesta.getFigura())) || (cartaSeleccionada.getValor() == cartaPuesta.getValor())) {
            cartaValida = true;
        } else {
            System.out.println("Carta invalida.");
            cartaValida = false;
        }
        return cartaValida;
    }

    public boolean noTieneCartasUnJugador(int turno) {
        return cartasJugadores.get(turno).isEmpty();
    }

    public int getIndexGanador() {
        int index = 0;

        for (int i = 0; i < cartasJugadores.size(); i ++) {
            if (cartasJugadores.get(i).isEmpty()) {
                index = i;
            }
        }

        return index;
    }

    public void jugarCarta(boolean cartaValida, Carta cartaSeleccionada) {
        setCartaPuesta(cartaSeleccionada);
        cartasJugadores.get(turno).remove(cartaSeleccionada);
        cartasJugadores.get(turno).remove(cartaSeleccionada);
    }

    public boolean noTieneCartas() {
        int c = 0;
        for (ArrayList<Carta> cartasJugador : cartasJugadores) {
            if (cartasJugador.isEmpty()) {
                c++;
            }
        }

        return c > 0;
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

    public void saltarTurnoJugador(int cantidadDejugadores, int direccion, int turno) {
        if (turno == cantidadDeJugadores - 1 && direccion == 1) {
            this.turno = 0;
        } else if (turno == 0 && direccion == -1) {
            this.turno = cantidadDeJugadores -1;
        } else {
            this.turno += direccion;
        }
    }

    public void cambiarDireccion(int direccion) {
        this.direccion *= -1;
    }

    public void hacerComerAJugador() {
        turno = getTurno();
        direccion = getDireccion();
        int siguienteJugador = turno + direccion;
        int cantidad = 2;

        if (siguienteJugador >= cantidadDeJugadores) {
            siguienteJugador = 0;
        } else if (siguienteJugador < 0) {
            siguienteJugador = cantidadDeJugadores - 1;
        }

        if ((!cementerioEstaVacio() && cementerio.size() < cantidad)) {
            cantidad = cementerio.size();
        }

        for (int i = 0; i < cantidad; i++) {
            cartasJugadores.get(siguienteJugador).add(cementerio.getFirst());
            cementerio.removeFirst();
        }
    }

    public void robarCartas(int jugador, int cantidad) {
        turno = getTurno();
        direccion = getDireccion();
        int siguienteJugador = turno + direccion;

        if (siguienteJugador >= cantidadDeJugadores) {
            siguienteJugador = 0;
        } else if (siguienteJugador < 0) {
            siguienteJugador = cantidadDeJugadores - 1;
        }

        if ((!cartasJugadores.get(siguienteJugador).isEmpty()) && (cartasJugadores.get(siguienteJugador).size() < cantidad)) {
            cantidad = cartasJugadores.get(siguienteJugador).size();
        }

        for (int i = 0; i < cantidad; i++) {
            cartasJugadores.get(turno).add(cartasJugadores.get(siguienteJugador).getFirst());
            cartasJugadores.get(siguienteJugador).removeFirst();
        }
    }


    public int getCantidadDeJugadores() {
        return this.cantidadDeJugadores;
    }

    public boolean getAccionEspecial() {
        return this.accionEspecial;
    }

    public void setAccionEspecial(boolean accionEspecial) {
        this.accionEspecial = accionEspecial;
    }

    public void setSeComioCarta(boolean seComioCarta) {
        this.seComioCarta = seComioCarta;
    }

    public boolean getPrimerTurno() {
        return this.primerTurno;
    }

    public void setPrimerTurno(boolean primerTurno) {
        this.primerTurno = primerTurno;
    }
}