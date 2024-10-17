import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    private ArrayList<JButton> botonesCarta;
    private JButton botonComer;
    private JPanel panelBotones;
    private UNO juego;
    private JLabel mensajeLabel;
    private JButton botonPasar;

    public GUI() {
        mensajeLabel = new JLabel();
        mensajeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mensajeLabel.setPreferredSize(new Dimension(400, 30));
        mensajeLabel.setForeground(Color.WHITE);
        mensajeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(mensajeLabel, BorderLayout.WEST);

        juego = new UNO(3, mensajeLabel);
        inicializarGUI();
        jugar();
    }

    public void inicializarGUI() {
        // Configurar el marco principal
        setTitle("UNO");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        botonComer = new JButton("Comer");
        botonComer.setPreferredSize(new Dimension(100, 50));
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel1.setBackground(new Color(3, 60, 0));
        panel1.add(botonComer);
        add(panel1, BorderLayout.EAST);

        /*botonPasar = new JButton("Pasar");
        botonPasar.setPreferredSize(new Dimension(100, 50));
        panel1.add(botonPasar);*/

        // Crear el panel para los botones.
        panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBackground(new Color(3, 60, 0));

        // Crear y agregar los botones al panel.
        botonesCarta = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            JButton boton = new JButton("Carta " + i);
            boton.setPreferredSize(new Dimension(100, 150));
            botonesCarta.add(boton);
            panelBotones.add(boton);
        }

        // Agregar el panel de botones en la parte inferior de la ventana.
        add(panelBotones, BorderLayout.SOUTH);

        juego.inicializarJuego();

        setVisible(true);
    }

    public void actualizarCartaPuestaEnGUI() {
        // Crear el panel principal para las cartas.
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 40));
        panel.setBackground(new Color(3, 60, 0));
        add(panel, BorderLayout.CENTER);

        // Imagen de la carta puesta en el juego.
        ImageIcon imagenCartaPuesta = juego.getCartaPuesta().getImagen();
        Image imagenEscaladaCartaPuesta = imagenCartaPuesta.getImage().getScaledInstance(200, 300, Image.SCALE_SMOOTH);
        JLabel cartaPuesta = new JLabel(new ImageIcon(imagenEscaladaCartaPuesta));
        cartaPuesta.setPreferredSize(new Dimension(200, 300));

        panel.removeAll();

        if (!juego.cementerioEstaVacio()) {
            // Imagen del mazo.
            ImageIcon imagenMazo = new ImageIcon(getClass().getResource("imagen0.png"));
            Image imagenEscaladaMazo = imagenMazo.getImage().getScaledInstance(200, 300, Image.SCALE_SMOOTH);
            JLabel mazo = new JLabel(new ImageIcon(imagenEscaladaMazo));
            mazo.setPreferredSize(new Dimension(200, 300));
            panel.add(mazo);
        }

        // Limpiar el panel y agregar las cartas una al lado de la otra.
        panel.add(cartaPuesta);

        // Actualizar el panel.
        panel.revalidate();
        panel.repaint();
    }

    public void jugar() {
        boolean juegoFinalizado = false;
        boolean pasar = false;
        boolean turnoFinalizado = false;
        boolean cartaValida = false;
        int direccion = 1;
        int turno = 0;
        Carta cartaPuesta = juego.getCartaPuesta();

        // Mostrar la carta puesta en el GUI
        actualizarCartaPuestaEnGUI();

        // Crear botones para el jugador actual
        crearBotones(juego.getCartasJugadores(), turno, juego.getCartaPuesta(), direccion, cartaValida);
    }

    public void crearBotones(ArrayList<ArrayList<Carta>> cartas, int turno, Carta cartaPuesta, int direccion, boolean cartaValida) {

        for (ActionListener al : botonComer.getActionListeners()) {
            botonComer.removeActionListener(al);
        }

        boolean masMovimientos = true;
        turno = juego.getTurno();
        System.out.println("TURNO INICIO DE CREAR BOTONES: " + turno);

        panelBotones.removeAll();
        botonesCarta.clear();

        int finalTurno = turno;

        //mostrarTextoJugador(turno);

        // Crear y agregar botones para las cartas del jugador actual
        System.out.println("Cartas jugador: " + juego.getCartasJugadores().get(turno).toString());
        for (Carta carta : cartas.get(finalTurno)) {
            ImageIcon imagenOriginal = carta.getImagen();
            Image imagenEscalada = imagenOriginal.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
            JButton boton = new JButton(new ImageIcon(imagenEscalada));
            boton.setPreferredSize(new Dimension(100, 150));

            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jugarCarta(boton, carta, finalTurno, cartaPuesta, direccion, cartaValida);

                    // Actualizar la carta puesta en el GUI
                    actualizarCartaPuestaEnGUI();

                    // Verificar si el juego ha terminado
                    if (juego.noTieneCartasElJugador(finalTurno)) {
                        System.out.println("El juego ha terminado.");
                        return;
                    }

                    // Recrear los botones para el siguiente jugador
                    crearBotones(juego.getCartasJugadores(), finalTurno, juego.getCartaPuesta(), direccion, cartaValida);

                    // Actualizar el panel
                    panelBotones.revalidate();
                    panelBotones.repaint();
                }
            });
            botonesCarta.add(boton);
            panelBotones.add(boton);
            masMovimientos = juego.jugadorTieneMovimientos(finalTurno, cartaPuesta);
        }

        System.out.println();
        System.out.println("MAS MOVIMIENTOS TURNO " + finalTurno + " " + masMovimientos);
        System.out.println();

        if (!masMovimientos) {
            if (!juego.cementerioEstaVacio()) {
                JOptionPane.showMessageDialog(this, "No tienes movimientos, debes comer una carta.");
                for (ActionListener al : botonComer.getActionListeners()) {
                    botonComer.removeActionListener(al);
                }

                botonComer.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        comerDeCementerio(finalTurno, cartaPuesta, direccion, cartaValida);

                        // Verificar si el jugador puede hacer más movimientos después de comer
                        //boolean masMovimientosDespuesDeComer = juego.jugadorTieneMovimientos(finalTurno, cartaPuesta);

                        /*if (masMovimientosDespuesDeComer) {
                            crearBotonCartaComida(finalTurno, cartaPuesta, direccion, cartaValida);
                        } else {
                            JOptionPane.showMessageDialog(null, "No tienes movimientos, pasas automáticamente.");
                            juego.actualizarTurno(finalTurno);
                            actualizarPanelBotones(juego.getTurno(), cartaPuesta, direccion, cartaValida); // Crear botones para el siguiente jugador
                        }*/

                    }
                });

            } else if (juego.cementerioEstaVacio()) {
                // Si no hay cartas para comer, se pasa el turno automáticamente
                JOptionPane.showMessageDialog(this, "No hay cartas para comer, pasas automáticamente.");
                juego.actualizarTurno(finalTurno);
                actualizarPanelBotones(juego.getTurno(), cartaPuesta, direccion, cartaValida); // Crear botones para el siguiente jugador
            }
        }

        // Actualizar el panel
        panelBotones.revalidate();
        panelBotones.repaint();
    }

    private void jugarCarta(JButton botonCarta, Carta cartaSeleccionada, int turno, Carta cartaPuesta, int direccion, boolean cartaValida) {
        int index = botonesCarta.indexOf(botonCarta);
        cartaSeleccionada = juego.getCartasJugadores().get(turno).get(index);

        if (juego.esCartaValida(cartaSeleccionada, cartaPuesta)) {
            juego.jugarCarta(true, cartaSeleccionada);
            juego.actualizarTurno(turno);
        } else {
            mensajeCartaInvalida();
        }
    }

    public void mensajeCartaInvalida() {
        JOptionPane.showMessageDialog(this, "Carta no válida. Seleccione otra.");
    }

    public void comerDeCementerio(int turno, Carta cartaPuesta, int direccion, boolean cartaValida) {
        juego.comerDeCementerio(turno);
        System.out.println("DESPUES DE COMER: " + juego.getCartasJugadores().get(turno));
        System.out.println("TURNO ANTES DE LLAMAR CREAR BOTON: " + turno);
        crearBotonCartaComida(turno, cartaPuesta, direccion, cartaValida);
    }

    public void mostrarTextoJugador(int turno) {
        JLabel jugadorEtiqueta = new JLabel();
        jugadorEtiqueta = new JLabel();
        jugadorEtiqueta.setHorizontalAlignment(SwingConstants.CENTER);
        jugadorEtiqueta.setPreferredSize(new Dimension(400, 30));
        jugadorEtiqueta.setForeground(Color.WHITE);
        jugadorEtiqueta.setFont(new Font("Arial", Font.PLAIN, 16));
        add(jugadorEtiqueta, BorderLayout.SOUTH);
        jugadorEtiqueta.setText("Jugador: " + turno);
    }

    public void crearBotonCartaComida(int turno, Carta cartaPuesta, int direccion, boolean cartaValida) {
        // Obtener la carta comida
        Carta cartaComida = juego.getCartasJugadores().get(turno).getLast();

        // Mostrar siempre la carta comida en el GUI
        ImageIcon imagenOriginal = cartaComida.getImagen();
        Image imagenEscalada = imagenOriginal.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
        JButton boton = new JButton(new ImageIcon(imagenEscalada));
        boton.setPreferredSize(new Dimension(100, 150));
        panelBotones.add(boton); // Añadir botón al panel para que se muestre la carta comida
        panelBotones.revalidate();
        panelBotones.repaint();

        // Verificar si hay movimientos disponibles después de comer
        boolean masMovimientosDespuesDeJugar = juego.jugadorTieneMovimientos(turno, cartaPuesta);

        if (!masMovimientosDespuesDeJugar) {
            // Si no hay movimientos, avisar al jugador y pasar el turno automáticamente
            JOptionPane.showMessageDialog(null, "No tienes más movimientos, pasa automáticamente.");
            juego.actualizarTurno(turno);
            actualizarPanelBotones(juego.getTurno(), cartaPuesta, direccion, cartaValida); // Actualizar botones para el siguiente jugador
        } else {
            // Si hay más movimientos, permitir que el jugador juegue la carta comida
            botonesCarta.add(boton);
            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("TURNO ANTES DE JUGAR: " + turno);
                    jugarCarta(boton, cartaComida, turno, cartaPuesta, direccion, cartaValida);
                    actualizarCartaPuestaEnGUI(); // Actualizar la carta puesta en el GUI
                    System.out.println("TURNO DESPUÉS DE JUGAR: " + turno);
                    actualizarPanelBotones(juego.getTurno(), cartaPuesta, direccion, cartaValida);

                }
            });
        }
    }

    private void actualizarPanelBotones(int finalTurno, Carta cartaPuesta, int direccion, boolean cartaValida) {
        panelBotones.revalidate();
        panelBotones.repaint();
        crearBotones(juego.getCartasJugadores(), finalTurno, cartaPuesta, direccion, cartaValida);
    }
}