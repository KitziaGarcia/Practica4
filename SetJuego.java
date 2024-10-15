import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetJuego extends JFrame {
    private ArrayList<JButton> botonesCarta;
    private JButton botonComer;
    private JPanel panelBotones;
    private UNO juego;

    public SetJuego(UNO juego) {
        this.juego = juego;
        // Configurar el marco principal
        setTitle("UNO");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        botonComer = new JButton("Comer");
        botonComer.setPreferredSize(new Dimension(100, 50));
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel1.add(botonComer);
        add(panel1, BorderLayout.EAST);

        // Crear el panel para los botones
        panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Crear y agregar los botones al panel
        botonesCarta = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            JButton boton = new JButton("Carta " + i);
            boton.setPreferredSize(new Dimension(100, 150));
            botonesCarta.add(boton);
            panelBotones.add(boton);
        }

        // Agregar el panel de botones en la parte inferior de la ventana
        add(panelBotones, BorderLayout.SOUTH);

        // Hacer visible el marco
        setVisible(true);
    }

    public void crearBotones(ArrayList<ArrayList<Carta>> cartas, int turno) {
        // Limpiar botones anteriores
        panelBotones.removeAll();
        botonesCarta.clear();

        // Crear y agregar botones para las cartas del jugador actual
        for (Carta carta : cartas.get(turno)) {
            ImageIcon imagenOriginal = carta.getImagen(); // Obtener la imagen original
            Image imagenEscalada = imagenOriginal.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH); // Redimensionar la imagen
            JButton boton = new JButton(new ImageIcon(imagenEscalada));
            boton.setPreferredSize(new Dimension(100, 150));

            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //jugarCarta(carta);
                }
            });

            botonesCarta.add(boton);
            panelBotones.add(boton);
        }

        // Actualizar el panel
        panelBotones.revalidate();
        panelBotones.repaint();
    }
}