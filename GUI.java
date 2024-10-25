import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    private ArrayList<JButton> botonesCarta;
    private JButton botonComer;
    private JButton botonComer2;
    private JButton botonComerJugador1;
    private JButton botonComerJugador2;
    private JButton botonComerJugador3;
    private JButton botonComerJugador4;
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
    private JTextField input;
    private JButton validarEntrada;
    private int cantidadDeJugadores;

    public GUI() {
        inputCampoJFrame();
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

    public void inputCampoJFrame() {
        JFrame inputFrame = new JFrame("Ingrese cantidad de jugadores");
        inputFrame.setSize(350, 100);
        inputFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inputFrame.setLayout(new FlowLayout());

        input = new JTextField(10);
        validarEntrada = new JButton("OK");

        inputFrame.add(input);
        inputFrame.add(validarEntrada);

        validarEntrada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cantidadDeJugadores = Integer.parseInt(input.getText());
                    if (cantidadDeJugadores >= 2 && cantidadDeJugadores <= 4) {
                        inputFrame.dispose();
                        juego = new UNO(cantidadDeJugadores);
                        inicializarGUI();
                        jugar();
                    } else {
                        JOptionPane.showMessageDialog(null, "Por favor, ingresa un número entre 2 y 4.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido.");
                }
            }
        });

        inputFrame.setLocationRelativeTo(null);
        inputFrame.setVisible(true);  // Muestra la ventana de entrada
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
        boolean cartaValida = false;
        int direccion = 1;
        int turno = 0;
        Carta cartaPuesta = juego.getCartaPuesta();
        primerTurno = juego.getPrimerTurno();

        // Mostrar la carta puesta en el GUI
        actualizarCartaPuestaEnGUI();

        // Crear botones para el jugador actual
        crearBotones(juego.getCartasJugadores(), juego.getTurno(), juego.getCartaPuesta(), direccion, cartaValida);
    }

    public void crearBotones(ArrayList<ArrayList<Carta>> cartas, int turno, Carta cartaPuesta, int direccion, boolean cartaValida) {
        accionEspecial = juego.getAccionEspecial();
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("ACCION ESPECIAL: " + juego.getAccionEspecial());
        System.out.println("CARTA PUESTA: " + juego.getCartaPuesta());
        System.out.println("MAS MOVIMIENTOS TURNO " + (juego.getTurno() + 1) + " " + juego.jugadorTieneMovimientos(juego.getTurno(), juego.getCartaPuesta()));

        if ((juego.noTieneCartas())) {
            displayCartasVolteadas();
            avisoJuegoTerminado();
        }

        if (juego.juegoCerrado() && juego.cementerioEstaVacio()) {
            int index;
            index = juego.obtenerGanadorEnJuegoCerrado();
            juego.setIndexGanador(index);
            displayCartasVolteadas();
            avisoJuegoTerminado();
        }

        boolean masMovimientos = true;
        turno = juego.getTurno();
        direccion = juego.getDireccion();
        System.out.println("TURNO: " + (turno + 1));

        panelBotones.removeAll();
        botonesCarta.clear();

        int finalTurno = turno;
        int finalDireccion = direccion;

        actualizarTextoJugador();


        for (int i = 0; i < cantidadDeJugadores; i++) {
            System.out.println("Cartas jugador " + (i + 1) + ": " + juego.getCartasJugadores().get(i).toString());
        }

        // Crear y agregar botones para las cartas del jugador actual
        for (Carta carta : cartas.get(juego.getTurno())) {
            ImageIcon imagenOriginal = carta.getImagen();
            Image imagenEscalada = imagenOriginal.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
            JButton boton = new JButton(new ImageIcon(imagenEscalada));
            boton.setPreferredSize(new Dimension(100, 150));
            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jugarCarta(boton, carta, juego.getTurno(), juego.getCartaPuesta(), finalDireccion, cartaValida);
                    actualizarCartaPuestaEnGUI();

                    // Verificar si el juego ha terminado
                    if ((juego.esCartaValida(carta, juego.getCartaPuesta()) && (carta.getValor() != 1 && carta.getValor() != 3) && juego.noTieneCartasUnJugador(finalTurno))) {
                        displayCartasVolteadas();
                        avisoJuegoTerminado();
                    }

                    // Recrear los botones para el siguiente jugador
                    crearBotones(juego.getCartasJugadores(), juego.getTurno(), juego.getCartaPuesta(), finalDireccion, cartaValida);

                    // Actualizar el panel
                    panelBotones.revalidate();
                    panelBotones.repaint();
                }
            });
            botonesCarta.add(boton);
            panelBotones.add(boton);
        }

        if (!juego.getAccionEspecial()) {
            masMovimientos = juego.jugadorTieneMovimientos(juego.getTurno(), juego.getCartaPuesta());
            System.out.println();

            if (!masMovimientos) {
                if (!juego.cementerioEstaVacio()) {
                    JOptionPane.showMessageDialog(this, "No tienes movimientos, debes comer una carta.");
                    botonComer.setVisible(true);
                } else if (juego.cementerioEstaVacio()) {

                    JOptionPane.showMessageDialog(this, "No hay cartas para comer, pasas automáticamente.");
                    juego.actualizarTurno(juego.getTurno());
                    actualizarPanelBotones(juego.getTurno(), juego.getCartaPuesta(), direccion, cartaValida);
                }
            }
        }

        // Actualizar el panel
        panelBotones.revalidate();
        panelBotones.repaint();
        juego.setPrimerTurno(false);
    }

    private void jugarCarta(JButton botonCarta, Carta cartaSeleccionada, int turno, Carta cartaPuesta, int direccion, boolean cartaValida) {
        int index = botonesCarta.indexOf(botonCarta);
        cartaSeleccionada = juego.getCartasJugadores().get(juego.getTurno()).get(index);

        System.out.println("Puesta local: " + cartaPuesta.getFigura() + " , " + cartaPuesta.getValor());
        System.out.println("Puesta juego: " + juego.getCartaPuesta());
        System.out.println("Seleccionada: " + cartaSeleccionada.getFigura() + " , " + cartaSeleccionada.getValor());

        if (juego.esCartaValida(cartaSeleccionada, juego.getCartaPuesta())) {
            if ((cartaSeleccionada.getValor() == 11 || cartaSeleccionada.getValor() == 1 || cartaSeleccionada.getValor() == 3 ||  cartaSeleccionada.getValor() == 12) && !juego.getPrimerTurno()) {
                juego.setAccionEspecial(true);
                manejarEfectoCarta(cartaSeleccionada.getValor(), direccion, juego.getCartaPuesta(), juego.getTurno(), true);
                juego.jugarCarta(true, cartaSeleccionada);
            } else {
                manejarEfectoCarta(cartaSeleccionada.getValor(), direccion, juego.getCartaPuesta(), juego.getTurno(), true);
                juego.jugarCarta(true, cartaSeleccionada);
                juego.actualizarTurno(juego.getTurno());
            }
        } else {
            mensajeCartaInvalida();
        }
    }

    public void mensajeCartaInvalida() {
        JOptionPane.showMessageDialog(this, "Carta no válida. Seleccione otra.");
    }

    public void comerDeCementerio(int turno, Carta cartaPuesta, int direccion, boolean cartaValida) {
        juego.comerDeCementerio(juego.getTurno());
        crearBotonCartaComida(juego.getTurno(), juego.getCartaPuesta(), direccion, cartaValida);
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

        jugadorEtiqueta.setText("Cartas jugador: " + (juego.getTurno() + 1));

        panelEtiqueta.revalidate();
        panelEtiqueta.repaint();
    }

    public void actualizarTextoJugador() {
        jugadorEtiqueta.setText("Cartas jugador: " + (juego.getTurno() + 1));
    }

    public void crearBotonCartaComida(int turno, Carta cartaPuesta, int direccion, boolean cartaValida) {
        // Obtener la carta comida
        Carta cartaComida = juego.getCartasJugadores().get(juego.getTurno()).getLast();

        // Mostrar siempre la carta comida en el GUI
        ImageIcon imagenOriginal = cartaComida.getImagen();
        Image imagenEscalada = imagenOriginal.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
        JButton boton = new JButton(new ImageIcon(imagenEscalada));
        boton.setPreferredSize(new Dimension(100, 150));
        panelBotones.add(boton); // Añadir botón al panel para que se muestre la carta comida
        panelBotones.revalidate();
        panelBotones.repaint();

        System.out.println("ACCION ESPECIAL EN BOTON CARTA COMIDA: " + juego.getAccionEspecial());
        if (!juego.getAccionEspecial()) {
            // Verificar si hay movimientos disponibles después de comer
            boolean masMovimientosDespuesDeJugar = juego.jugadorTieneMovimientos(juego.getTurno(), juego.getCartaPuesta());

            if (!masMovimientosDespuesDeJugar) {
                // Si no hay movimientos, avisar al jugador y pasar el turno automáticamente
                JOptionPane.showMessageDialog(null, "No tienes más movimientos, pasa automáticamente.");
                juego.actualizarTurno(juego.getTurno());
                actualizarPanelBotones(juego.getTurno(), juego.getCartaPuesta(), direccion, cartaValida); // Actualizar botones para el siguiente jugador
            } else {
                // Si hay más movimientos, permitir que el jugador juegue la carta comida
                botonesCarta.add(boton);
                boton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        jugarCarta(boton, cartaComida, juego.getTurno(), juego.getCartaPuesta(), direccion, cartaValida);
                        actualizarCartaPuestaEnGUI(); // Actualizar la carta puesta en el GUI
                        actualizarPanelBotones(juego.getTurno(), juego.getCartaPuesta(), direccion, cartaValida);

                    }
                });
            }
        }
    }

    private void actualizarPanelBotones(int finalTurno, Carta cartaPuesta, int direccion, boolean cartaValida) {
        crearBotones(juego.getCartasJugadores(), juego.getTurno(), juego.getCartaPuesta(), direccion, cartaValida);
    }

    public void manejarEfectoCarta(int valorCarta, int direccion, Carta cartaPuesta, int turno, boolean cartaValida) {
        //System.out.println("BLOQUEAR MOVIMIENTO: " + juego.getAccionEspecial());
        switch (valorCarta) {
            case 1:
                botonComer2.setVisible(true);
                actualizarPanelBotones(juego.getTurno(), juego.getCartaPuesta(), direccion, true);

                if(juego.cementerioEstaVacio()) {
                    JOptionPane.showMessageDialog(this, "No hay cartas para comer, pasas automáticamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Tienes que comer una carta.");
                }

                break;
            case 2:
                JOptionPane.showMessageDialog(null, "El siguiente jugador come dos cartas automaticamente.");
                juego.hacerComerAJugador();
                break;
            case 3:
                botonRobar.setVisible(true);
                JOptionPane.showMessageDialog(null, "Roba cuatro cartas al siguiente jugador.");
                actualizarPanelBotones(juego.getTurno(), juego.getCartaPuesta(), direccion, true);

                break;
            case 11:
                hacerBotonesVisibles();
                actualizarCartaPuestaEnGUI();

                break;
            case 12:
                switch (juego.getTurno()) {
                    case 0:
                        if (juego.getCantidadDeJugadores() == 2) {
                            botonComerJugador2.setVisible(true);
                        } else if (juego.getCantidadDeJugadores() == 3) {
                            botonComerJugador2.setVisible(true);
                            botonComerJugador3.setVisible(true);
                        } else {
                            botonComerJugador2.setVisible(true);
                            botonComerJugador3.setVisible(true);
                            botonComerJugador4.setVisible(true);
                        }
                        break;

                    case 1:
                        if (juego.getCantidadDeJugadores() == 2) {
                            botonComerJugador1.setVisible(true);
                        } else if (juego.getCantidadDeJugadores() == 3) {
                            botonComerJugador1.setVisible(true);
                            botonComerJugador3.setVisible(true);
                        } else {
                            botonComerJugador1.setVisible(true);
                            botonComerJugador3.setVisible(true);
                            botonComerJugador4.setVisible(true);
                        }
                        break;

                    case 2:
                        if (juego.getCantidadDeJugadores() == 3) {
                            botonComerJugador1.setVisible(true);
                            botonComerJugador2.setVisible(true);
                        } else {
                            botonComerJugador1.setVisible(true);
                            botonComerJugador2.setVisible(true);
                            botonComerJugador4.setVisible(true);
                        }
                        break;

                    case 3:
                        botonComerJugador1.setVisible(true);
                        botonComerJugador2.setVisible(true);
                        botonComerJugador3.setVisible(true);
                        break;
                }
                actualizarPanelBotones(juego.getTurno(), juego.getCartaPuesta(), direccion, true);
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
        botonComer.setVisible(false);

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
        botonComer2.setVisible(false);

        botonComer2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botonComer2.setVisible(false);
                comerDeCementerio(juego.getTurno(), juego.getCartaPuesta(), juego.getDireccion(), true);
                actualizarPanelBotones(juego.getTurno(), juego.getCartaPuesta(), juego.getDireccion(), true);
                juego.setAccionEspecial(false);

                Timer timer = new Timer(3000, i -> {
                    juego.actualizarTurno(juego.getTurno());
                    actualizarPanelBotones(juego.getTurno(), juego.getCartaPuesta(), juego.getDireccion(), true);
                });
                timer.setRepeats(false); // Asegurarse de que se ejecute solo una vez
                timer.start();
            }
        });

        //System.out.println("BLOQUEAR MOVIMIENTO: " + juego.getAccionEspecial());
        //botonComer2.revalidate();
        //botonComer2.repaint();

        botonRobar = new JButton("Robar");
        panel1.add(botonRobar);
        botonRobar.setVisible(false);

        botonRobar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botonRobar.setVisible(false);
                juego.robarCartas(juego.getTurno(), 4);
                actualizarPanelBotones(juego.getTurno(), juego.getCartaPuesta(), juego.getDireccion(), true);
                juego.setAccionEspecial(false);

                Timer timer = new Timer(3000, i -> {
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
        botonSaltarTurno.setVisible(false);
        botonCambiarDireccion.setVisible(false);

        botonSaltarTurno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botonSaltarTurno.setVisible(false);
                juego.saltarTurnoJugador(3, juego.getDireccion(), juego.getTurno());
                juego.actualizarTurno(juego.getTurno());
                System.out.println("NUEVO TURNO SALTADO: " + juego.getTurno());
                limpiarPanelBotones();
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
                limpiarPanelBotones();
                juego.setAccionEspecial(false);
                actualizarPanelBotones(juego.getTurno(), juego.getCartaPuesta(), juego.getDireccion(), true);
            }
        });

        botonComerJugador1 = new JButton("Comer de jugador 1.");
        botonComerJugador1.setPreferredSize(new Dimension(200, 50));
        panel1.add(botonComerJugador1);
        botonComerJugador1.setVisible(false);

        botonComerJugador2 = new JButton("Comer de jugador 2.");
        botonComerJugador2.setPreferredSize(new Dimension(200, 50));
        panel1.add(botonComerJugador2);
        botonComerJugador2.setVisible(false);

        botonComerJugador3 = new JButton("Comer de jugador 3.");
        botonComerJugador3.setPreferredSize(new Dimension(200, 50));
        panel1.add(botonComerJugador3);
        botonComerJugador3.setVisible(false);

        botonComerJugador4 = new JButton("Comer de jugador 4.");
        botonComerJugador4.setPreferredSize(new Dimension(200, 50));
        panel1.add(botonComerJugador4);
        botonComerJugador4.setVisible(false);

        botonComerJugador1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarPanelBotones();
                botonComerJugador1.setVisible(false);
                int jugadorSeleccionado = 0;
                Carta cartaRobada = juego.getCartasJugadores().get(jugadorSeleccionado).getFirst();
                juego.getCartasJugadores().get(jugadorSeleccionado).removeFirst();
                juego.getCartasJugadores().get(juego.getTurno()).add(cartaRobada);
                crearBotonCartaComida(juego.getTurno(), juego.getCartaPuesta(), juego.getDireccion(), true);
                actualizarPanelBotones(juego.getTurno(), juego.getCartaPuesta(), juego.getDireccion(), true);
                juego.setAccionEspecial(false);

                Timer timer = new Timer(3000, i -> {
                    panel1.revalidate();
                    panel1.repaint();
                    juego.actualizarTurno(juego.getTurno());
                    limpiarPanelBotones();
                    actualizarPanelBotones(juego.getTurno(), juego.getCartaPuesta(), juego.getDireccion(), true);
                });
                timer.setRepeats(false);
                timer.start();
            }
        });

        botonComerJugador2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarPanelBotones();
                botonComerJugador2.setVisible(false);
                int jugadorSeleccionado = 1;
                Carta cartaRobada = juego.getCartasJugadores().get(jugadorSeleccionado).getFirst();
                juego.getCartasJugadores().get(jugadorSeleccionado).removeFirst();
                juego.getCartasJugadores().get(juego.getTurno()).add(cartaRobada);
                crearBotonCartaComida(juego.getTurno(), juego.getCartaPuesta(), juego.getDireccion(), true);
                actualizarPanelBotones(juego.getTurno(), juego.getCartaPuesta(), juego.getDireccion(), true);
                juego.setAccionEspecial(false);

                Timer timer = new Timer(3000, i -> {
                    panel1.revalidate();
                    panel1.repaint();
                    juego.actualizarTurno(juego.getTurno());
                    limpiarPanelBotones();
                    actualizarPanelBotones(juego.getTurno(), juego.getCartaPuesta(), juego.getDireccion(), true);
                });
                timer.setRepeats(false);
                timer.start();
            }
        });

        botonComerJugador3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarPanelBotones();
                botonComerJugador3.setVisible(false);
                int jugadorSeleccionado = 2;
                Carta cartaRobada = juego.getCartasJugadores().get(jugadorSeleccionado).getFirst();
                juego.getCartasJugadores().get(jugadorSeleccionado).removeFirst();
                juego.getCartasJugadores().get(juego.getTurno()).add(cartaRobada);
                crearBotonCartaComida(juego.getTurno(), juego.getCartaPuesta(), juego.getDireccion(), true);
                actualizarPanelBotones(juego.getTurno(), juego.getCartaPuesta(), juego.getDireccion(), true);
                juego.setAccionEspecial(false);

                Timer timer = new Timer(3000, i -> {
                    panel1.revalidate();
                    panel1.repaint();
                    juego.actualizarTurno(juego.getTurno());
                    actualizarPanelBotones(juego.getTurno(), juego.getCartaPuesta(), juego.getDireccion(), true);
                });
                timer.setRepeats(false);
                timer.start();
            }
        });

        botonComerJugador4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarPanelBotones();
                int jugadorSeleccionado = 3;
                Carta cartaRobada = juego.getCartasJugadores().get(jugadorSeleccionado).getFirst();
                juego.getCartasJugadores().get(jugadorSeleccionado).removeFirst();
                juego.getCartasJugadores().get(juego.getTurno()).add(cartaRobada);
                crearBotonCartaComida(juego.getTurno(), juego.getCartaPuesta(), juego.getDireccion(), true);
                actualizarPanelBotones(juego.getTurno(), juego.getCartaPuesta(), juego.getDireccion(), true);
                juego.setAccionEspecial(false);

                Timer timer = new Timer(3000, i -> {
                    panel1.revalidate();
                    panel1.repaint();
                    juego.actualizarTurno(juego.getTurno());
                    actualizarPanelBotones(juego.getTurno(), juego.getCartaPuesta(), juego.getDireccion(), true);
                });
                timer.setRepeats(false);
                timer.start();
            }
        });

        add(panel1, BorderLayout.EAST);
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
    private void limpiarPanelBotones() {
        botonSaltarTurno.setVisible(false);
        botonCambiarDireccion.setVisible(false);
        botonComerJugador1.setVisible(false);
        botonComerJugador2.setVisible(false);
        botonComerJugador3.setVisible(false);
        botonComerJugador4.setVisible(false);
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