import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    private ArrayList<JButton> botonesCarta;
    private JButton botonComer;
    private JButton botonComer2;
    private JPanel panel;
    private JPanel panelBotones;
    private JPanel panelEtiqueta;
    private UNO juego;
    private JButton botonRobar;
    private JLabel jugadorEtiqueta;
    private JPanel panel1;
    private JButton botonSaltarTurno;
    private JButton botonCambiarDireccion;
    private boolean accionEspecial;
    private boolean seComioCarta;
    boolean primerTurno;
    private ImageIcon imagenCartaPuesta;
    private Image imagenEscaladaCartaPuesta;
    private JLabel cartaPuesta;
    private ImageIcon imagenMazo;
    private Image imagenEscaladaMazo;
    private JLabel mazo;


    public GUI() {
        juego = new UNO(3);
        inicializarGUI();
        jugar();
    }

    public void inicializarGUI() {
        // Configurar el marco principal
        setTitle("UNO");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear el panel para las cartas.
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

        mostrarTextoJugador();
        juego.inicializarJuego();
        crearLayoutPrincipal();

        setVisible(true);
    }

    public void actualizarCartaPuestaEnGUI() {
        // Imagen de la carta puesta en el juego.esconder
        imagenCartaPuesta = juego.getCartaPuesta().getImagen();
        imagenEscaladaCartaPuesta = imagenCartaPuesta.getImage().getScaledInstance(200, 300, Image.SCALE_SMOOTH);
        cartaPuesta.setIcon(new ImageIcon(imagenEscaladaCartaPuesta));
        cartaPuesta.setPreferredSize(new Dimension(200, 300));

        if (!juego.cementerioEstaVacio()) {
            // Imagen del mazo.
            imagenMazo = new ImageIcon(getClass().getResource("imagen0.png"));
            imagenEscaladaMazo = imagenMazo.getImage().getScaledInstance(200, 300, Image.SCALE_SMOOTH);
            mazo.setIcon(new ImageIcon(imagenEscaladaMazo));
            mazo.setPreferredSize(new Dimension(200, 300));
            panel.add(mazo);
        }

        System.out.println("CARTA PUESTA EN METODO ACTUALIZAR: " + juego.getCartaPuesta());
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
        primerTurno = juego.getPrimerTurno();

        // Mostrar la carta puesta en el GUI
        actualizarCartaPuestaEnGUI();

        // Crear botones para el jugador actual
        crearBotones(juego.getCartasJugadores(), turno, juego.getCartaPuesta(), direccion, cartaValida);
    }

    public void crearBotones(ArrayList<ArrayList<Carta>> cartas, int turno, Carta cartaPuesta, int direccion, boolean cartaValida) {
        accionEspecial = juego.getAccionEspecial();

        if (juego.noTieneCartas()) {
            displayCartasVolteadas();
            avisoJuegoTerminado();
        }

        boolean masMovimientos = true;
        turno = juego.getTurno();
        direccion = juego.getDireccion();
        System.out.println("TURNO: " + turno);

        panelBotones.removeAll();
        botonesCarta.clear();

        int finalTurno = turno;
        int finalDireccion = direccion;

        actualizarTextoJugador();

        // Crear y agregar botones para las cartas del jugador actual
        System.out.println("Cartas jugador " + (0) + ": " + juego.getCartasJugadores().get(0).toString());
        System.out.println("Cartas jugador " + (1) + ": " + juego.getCartasJugadores().get(1).toString());
        System.out.println("Cartas jugador " + (2) + ": " + juego.getCartasJugadores().get(2).toString());

        for (Carta carta : cartas.get(finalTurno)) {
            ImageIcon imagenOriginal = carta.getImagen();
            Image imagenEscalada = imagenOriginal.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
            JButton boton = new JButton(new ImageIcon(imagenEscalada));
            boton.setPreferredSize(new Dimension(100, 150));
            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    /*if (carta.getValor() == 11 || carta.getValor() == 1) {
                        juego.setAccionEspecial(true);
                        System.out.println("BLOQUEAR MOVIMIENTOS: " + juego.getAccionEspecial());
                    }*/

                    jugarCarta(boton, carta, finalTurno, cartaPuesta, finalDireccion, cartaValida);
                    System.out.println("CARTA PUESTA DEPUES DE JUGARLA EN GUI: " + juego.getCartaPuesta());
                    actualizarCartaPuestaEnGUI();

                    // Verificar si el juego ha terminado
                    if (juego.noTieneCartasUnJugador(finalTurno)) {
                        displayCartasVolteadas();
                        avisoJuegoTerminado();
                    }

                    // Recrear los botones para el siguiente jugador
                    crearBotones(juego.getCartasJugadores(), finalTurno, juego.getCartaPuesta(), finalDireccion, cartaValida);

                    // Actualizar el panel
                    panelBotones.revalidate();
                    panelBotones.repaint();
                }
            });
            botonesCarta.add(boton);
            panelBotones.add(boton);
            /*masMovimientos = juego.jugadorTieneMovimientos(finalTurno, cartaPuesta);
            System.out.println();
            System.out.println("MAS MOVIMIENTOS TURNO " + finalTurno + " " + masMovimientos);
            System.out.println();*/
        }

        masMovimientos = juego.jugadorTieneMovimientos(finalTurno, cartaPuesta);
        System.out.println();
        System.out.println("MAS MOVIMIENTOS TURNO " + finalTurno + " " + masMovimientos);
        System.out.println();

        System.out.println("BLOQUEAR MOVIMIENTO ANTES DEL IF: " + juego.getAccionEspecial());
        if (!juego.getAccionEspecial()) {
            if (!masMovimientos) {
                if (!juego.cementerioEstaVacio()) {
                    JOptionPane.showMessageDialog(this, "No tienes movimientos, debes comer una carta.");
                    botonComer.setVisible(true);
                } else if (juego.cementerioEstaVacio()) {
                    // Si no hay cartas para comer, se pasa el turno automáticamente
                    JOptionPane.showMessageDialog(this, "No hay cartas para comer, pasas automáticamente.");
                    juego.actualizarTurno(finalTurno);
                    actualizarPanelBotones(juego.getTurno(), cartaPuesta, direccion, cartaValida); // Crear botones para el siguiente jugador
                }
            }
        }

        // Actualizar el panel
        //actualizarPanelBotones(juego.getTurno(), cartaPuesta, direccion, cartaValida);
        panelBotones.revalidate();
        panelBotones.repaint();
        juego.setPrimerTurno(false);
    }

    private void jugarCarta(JButton botonCarta, Carta cartaSeleccionada, int turno, Carta cartaPuesta, int direccion, boolean cartaValida) {
        int index = botonesCarta.indexOf(botonCarta);
        cartaSeleccionada = juego.getCartasJugadores().get(turno).get(index);

        if (juego.esCartaValida(cartaSeleccionada, cartaPuesta)) {
            if ((cartaSeleccionada.getValor() == 11 || cartaSeleccionada.getValor() == 1 || cartaSeleccionada.getValor() == 3) && !juego.getPrimerTurno()) {
                juego.setAccionEspecial(true);
                manejarEfectoCarta(cartaSeleccionada.getValor(), direccion, cartaPuesta, turno, true);
                juego.jugarCarta(true, cartaSeleccionada);
                System.out.println("CARTA PUESTA EN JUGAR CARTA: " + juego.getCartaPuesta());
            } else {
                manejarEfectoCarta(cartaSeleccionada.getValor(), direccion, cartaPuesta, turno, true);
                juego.jugarCarta(true, cartaSeleccionada);
                juego.actualizarTurno(turno);
            }
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

    public void mostrarTextoJugador() {
        panelEtiqueta = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Alinear a la izquierda
        jugadorEtiqueta = new JLabel();
        jugadorEtiqueta.setHorizontalAlignment(SwingConstants.LEFT); // Alineación del texto a la izquierda
        jugadorEtiqueta.setPreferredSize(new Dimension(200, 30)); // Cambia el tamaño según sea necesario
        jugadorEtiqueta.setForeground(Color.BLACK); // Color del texto
        jugadorEtiqueta.setFont(new Font("Arial", Font.PLAIN, 16)); // Fuente del texto
        panelEtiqueta.add(jugadorEtiqueta);
        add(panelEtiqueta, BorderLayout.NORTH);

        System.out.println("TURNO EN MENSAJE: " + (juego.getTurno() + 1));
        jugadorEtiqueta.setText("Cartas jugador: " + (juego.getTurno() + 1));

        panelEtiqueta.revalidate();
        panelEtiqueta.repaint();
    }

    public void actualizarTextoJugador() {
        jugadorEtiqueta.setText("Cartas jugador: " + (juego.getTurno() + 1));
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
        crearBotones(juego.getCartasJugadores(), finalTurno, cartaPuesta, direccion, cartaValida);
    }

    public void manejarEfectoCarta(int valorCarta, int direccion, Carta cartaPuesta, int turno, boolean cartaValida) {
        System.out.println("BLOQUEAR MOVIMIENTO: " + juego.getAccionEspecial());
        switch (valorCarta) {
            case 1:
                botonComer2.setVisible(true);
                actualizarPanelBotones(juego.getTurno(), cartaPuesta, direccion, true);
                JOptionPane.showMessageDialog(null, "Tienes que comer una carta.");

                break;
            case 2:
                JOptionPane.showMessageDialog(null, "El siguiente jugador come dos cartas.");
                juego.hacerComerAJugador();
                break;
            case 3:
                botonRobar.setVisible(true);
                JOptionPane.showMessageDialog(null, "Roba cuatro cartas al siguiente jugador.");
                actualizarPanelBotones(juego.getTurno(), cartaPuesta, direccion, true);

                break;
            case 11:
                hacerBotonesVisibles();
                System.out.println("CARTA PUESTA EN MANEJAR EFECTO: " + juego.getCartaPuesta());
                actualizarCartaPuestaEnGUI();
                //System.out.println("TURNO: " + turno);
                //displayCartas(juego.getCartasJugadores(), turno);
                //actualizarPanelBotones(juego.getTurno(), cartaPuesta, direccion, true);

                break;
            case 12:
                //Que pida el numero del jugador a quitar cartas, las muestre volteadas y pueda escoger cual quiere.

                break;
        }
    }

    private void hacerBotonesVisibles() {
        botonSaltarTurno.setVisible(true);
        botonCambiarDireccion.setVisible(true);
        panel1.revalidate();
        panel1.repaint();
    }

    public void crearLayoutPrincipal() {
        botonComer = new JButton("Comer");
        botonComer.setPreferredSize(new Dimension(100, 50));
        panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel1.setBackground(new Color(3, 60, 0));
        panel1.add(botonComer);
        add(panel1, BorderLayout.EAST);
        botonComer.setVisible(false);
        //botonComer.revalidate();
        //botonComer.repaint();

        botonComer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botonComer.setVisible(false);
                comerDeCementerio(juego.getTurno(), juego.getCartaPuesta(), juego.getDireccion(), true);
            }
        });


        botonComer2 = new JButton("Comer");
        botonComer2.setPreferredSize(new Dimension(100, 50));
        panel1.add(botonComer2);
        add(panel1, BorderLayout.EAST);
        botonComer2.setVisible(false);

        botonComer2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botonComer2.setVisible(false);
                comerDeCementerio(juego.getTurno(), juego.getCartaPuesta(), juego.getDireccion(), true);
                juego.setAccionEspecial(false);
                actualizarPanelBotones(juego.getTurno(), juego.getCartaPuesta(), juego.getDireccion(), true);

                Timer timer = new Timer(3000, i -> {
                    juego.actualizarTurno(juego.getTurno());
                    actualizarPanelBotones(juego.getTurno(), juego.getCartaPuesta(), juego.getDireccion(), true);
                });
                timer.setRepeats(false); // Asegurarse de que se ejecute solo una vez
                timer.start();
            }
        });

        System.out.println("BLOQUEAR MOVIMIENTO: " + juego.getAccionEspecial());
        //botonComer2.revalidate();
        //botonComer2.repaint();

        botonRobar = new JButton("Robar");
        panel1.add(botonRobar);
        add(panel1, BorderLayout.EAST);
        botonRobar.setVisible(false);

        botonRobar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botonRobar.setVisible(false);
                juego.robarCartas(juego.getTurno(), 4);
                juego.setAccionEspecial(false);
                actualizarPanelBotones(juego.getTurno(), juego.getCartaPuesta(), juego.getDireccion(), true);
                Timer timer = new Timer(3000, i -> {
                    panel1.remove(botonRobar);
                    panel1.revalidate();
                    panel1.repaint();
                    juego.actualizarTurno(juego.getTurno());
                    actualizarPanelBotones(juego.getTurno(), juego.getCartaPuesta(), juego.getDireccion(), true);
                });
                timer.setRepeats(false); // Asegurarse de que se ejecute solo una vez
                timer.start();
            }
        });

        //botonRobar.revalidate();
        //botonRobar.repaint();


        botonSaltarTurno = new JButton("Saltar turno.");
        botonCambiarDireccion = new JButton("Cambiar de direccion");
        panel1.add(botonSaltarTurno);
        panel1.add(botonCambiarDireccion);
        add(panel1, BorderLayout.EAST);
        botonSaltarTurno.setVisible(false);
        botonCambiarDireccion.setVisible(false);

        botonSaltarTurno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botonSaltarTurno.setVisible(false);
                juego.saltarTurnoJugador(3, juego.getDireccion(), juego.getTurno());
                juego.actualizarTurno(juego.getTurno());
                System.out.println("NUEVO TURNO SALTADO: " + juego.getTurno());
                limpiarPanelBotones11();
                juego.setAccionEspecial(false);
                actualizarPanelBotones(juego.getTurno(), juego.getCartaPuesta(), juego.getDireccion(), true);
            }
        });

        botonCambiarDireccion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botonCambiarDireccion.setVisible(false);
                // Cambiar de dirección y luego actualizar el turno
                juego.cambiarDireccion(juego.getDireccion());
                juego.actualizarTurno(juego.getTurno());
                limpiarPanelBotones11();
                juego.setAccionEspecial(false);
                actualizarPanelBotones(juego.getTurno(), juego.getCartaPuesta(), juego.getDireccion(), true);
            }
        });

        panel1.revalidate();
        panel1.repaint();

        // Crear el panel principal para las cartas.
        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 40));
        panel.setBackground(new Color(3, 60, 0));
        add(panel, BorderLayout.CENTER);

        // Imagen de la carta puesta en el juego.
        imagenCartaPuesta = juego.getCartaPuesta().getImagen();
        imagenEscaladaCartaPuesta = imagenCartaPuesta.getImage().getScaledInstance(200, 300, Image.SCALE_SMOOTH);
        cartaPuesta = new JLabel(new ImageIcon(imagenEscaladaCartaPuesta));
        cartaPuesta.setPreferredSize(new Dimension(200, 300));

        imagenMazo = new ImageIcon(getClass().getResource("imagen0.png"));
        imagenEscaladaMazo = imagenMazo.getImage().getScaledInstance(200, 300, Image.SCALE_SMOOTH);
        mazo = new JLabel(new ImageIcon(imagenEscaladaMazo));
        mazo.setPreferredSize(new Dimension(200, 300));
        panel.add(mazo);

        panel.removeAll();

        panel.add(cartaPuesta);

        // Actualizar el panel.
        panel.revalidate();
        panel.repaint();
    }

    // Limpia el panel de los botones después de la acción
    private void limpiarPanelBotones11() {
        botonSaltarTurno.setVisible(false);
        botonCambiarDireccion.setVisible(false);
        panel1.revalidate();
        panel1.repaint();
    }

    private void avisoJuegoTerminado() {
        JOptionPane.showMessageDialog(null, "El juego ha terminado. Ganador: Jugador " + (juego.getIndexGanador() + 1));
    }

    private void displayCartasVolteadas()  {
        panelBotones.removeAll();
        botonesCarta.clear();

        for (int i = 0; i < 7; i++) {
            ImageIcon imagenOriginal = new ImageIcon(getClass().getResource("imagen0.png"));
            Image imagenEscalada = imagenOriginal.getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH);
            JButton boton = new JButton(new ImageIcon(imagenEscalada));
            boton.setPreferredSize(new Dimension(150, 200));

            botonesCarta.add(boton);
            panelBotones.add(boton);
        }

        panelBotones.revalidate();
        panelBotones.repaint();
    }
}