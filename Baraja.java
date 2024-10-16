import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Baraja {
    private int valor;
    private ArrayList<String> figuras;
    private ArrayList<Carta> baraja;
    private ArrayList<ImageIcon> imagenes;

    public Baraja() {
        baraja = new ArrayList<>();
        figuras = new ArrayList<String>(Arrays.asList("oros", "copas", "espadas", "bastos"));
        imagenes = new ArrayList<ImageIcon>();
        ImageIcon imagen1 = new ImageIcon(getClass().getResource("imagen1.png"));
        ImageIcon imagen2 = new ImageIcon(getClass().getResource("imagen2.png"));
        ImageIcon imagen3 = new ImageIcon(getClass().getResource("imagen3.png"));
        ImageIcon imagen4 = new ImageIcon(getClass().getResource("imagen4.png"));
        ImageIcon imagen5 = new ImageIcon(getClass().getResource("imagen5.png"));
        ImageIcon imagen6 = new ImageIcon(getClass().getResource("imagen6.png"));
        ImageIcon imagen7 = new ImageIcon(getClass().getResource("imagen7.png"));
        ImageIcon imagen8 = new ImageIcon(getClass().getResource("imagen8.png"));
        ImageIcon imagen9 = new ImageIcon(getClass().getResource("imagen9.png"));
        ImageIcon imagen10 = new ImageIcon(getClass().getResource("imagen10.png"));
        ImageIcon imagen11 = new ImageIcon(getClass().getResource("imagen11.png"));
        ImageIcon imagen12 = new ImageIcon(getClass().getResource("imagen12.png"));
        ImageIcon imagen13 = new ImageIcon(getClass().getResource("imagen13.png"));
        ImageIcon imagen14 = new ImageIcon(getClass().getResource("imagen14.png"));
        ImageIcon imagen15 = new ImageIcon(getClass().getResource("imagen15.png"));
        ImageIcon imagen16 = new ImageIcon(getClass().getResource("imagen16.png"));
        ImageIcon imagen17 = new ImageIcon(getClass().getResource("imagen17.png"));
        ImageIcon imagen18 = new ImageIcon(getClass().getResource("imagen18.png"));
        ImageIcon imagen19 = new ImageIcon(getClass().getResource("imagen19.png"));
        ImageIcon imagen20 = new ImageIcon(getClass().getResource("imagen20.png"));
        ImageIcon imagen21 = new ImageIcon(getClass().getResource("imagen21.png"));
        ImageIcon imagen22 = new ImageIcon(getClass().getResource("imagen22.png"));
        ImageIcon imagen23 = new ImageIcon(getClass().getResource("imagen23.png"));
        ImageIcon imagen24 = new ImageIcon(getClass().getResource("imagen24.png"));
        ImageIcon imagen25 = new ImageIcon(getClass().getResource("imagen25.png"));
        ImageIcon imagen26 = new ImageIcon(getClass().getResource("imagen26.png"));
        ImageIcon imagen27 = new ImageIcon(getClass().getResource("imagen27.png"));
        ImageIcon imagen28 = new ImageIcon(getClass().getResource("imagen28.png"));
        ImageIcon imagen29 = new ImageIcon(getClass().getResource("imagen29.png"));
        ImageIcon imagen30 = new ImageIcon(getClass().getResource("imagen30.png"));
        ImageIcon imagen31 = new ImageIcon(getClass().getResource("imagen31.png"));
        ImageIcon imagen32 = new ImageIcon(getClass().getResource("imagen32.png"));
        ImageIcon imagen33 = new ImageIcon(getClass().getResource("imagen33.png"));
        ImageIcon imagen34 = new ImageIcon(getClass().getResource("imagen34.png"));
        ImageIcon imagen35 = new ImageIcon(getClass().getResource("imagen35.png"));
        ImageIcon imagen36 = new ImageIcon(getClass().getResource("imagen36.png"));
        ImageIcon imagen37 = new ImageIcon(getClass().getResource("imagen37.png"));
        ImageIcon imagen38 = new ImageIcon(getClass().getResource("imagen38.png"));
        ImageIcon imagen39 = new ImageIcon(getClass().getResource("imagen39.png"));
        ImageIcon imagen40 = new ImageIcon(getClass().getResource("imagen40.png"));
        ImageIcon imagen41 = new ImageIcon(getClass().getResource("imagen41.png"));
        ImageIcon imagen42 = new ImageIcon(getClass().getResource("imagen42.png"));
        ImageIcon imagen43 = new ImageIcon(getClass().getResource("imagen43.png"));
        ImageIcon imagen44 = new ImageIcon(getClass().getResource("imagen44.png"));
        ImageIcon imagen45 = new ImageIcon(getClass().getResource("imagen45.png"));
        ImageIcon imagen46 = new ImageIcon(getClass().getResource("imagen46.png"));
        ImageIcon imagen47 = new ImageIcon(getClass().getResource("imagen47.png"));
        ImageIcon imagen48 = new ImageIcon(getClass().getResource("imagen48.png"));

        imagenes.add(imagen1);
        imagenes.add(imagen2);
        imagenes.add(imagen3);
        imagenes.add(imagen4);
        imagenes.add(imagen5);
        imagenes.add(imagen6);
        imagenes.add(imagen7);
        imagenes.add(imagen8);
        imagenes.add(imagen9);
        imagenes.add(imagen10);
        imagenes.add(imagen11);
        imagenes.add(imagen12);
        imagenes.add(imagen13);
        imagenes.add(imagen14);
        imagenes.add(imagen15);
        imagenes.add(imagen16);
        imagenes.add(imagen17);
        imagenes.add(imagen18);
        imagenes.add(imagen19);
        imagenes.add(imagen20);
        imagenes.add(imagen21);
        imagenes.add(imagen22);
        imagenes.add(imagen23);
        imagenes.add(imagen24);
        imagenes.add(imagen25);
        imagenes.add(imagen26);
        imagenes.add(imagen27);
        imagenes.add(imagen28);
        imagenes.add(imagen29);
        imagenes.add(imagen30);
        imagenes.add(imagen31);
        imagenes.add(imagen32);
        imagenes.add(imagen33);
        imagenes.add(imagen34);
        imagenes.add(imagen35);
        imagenes.add(imagen36);
        imagenes.add(imagen37);
        imagenes.add(imagen38);
        imagenes.add(imagen39);
        imagenes.add(imagen40);
        imagenes.add(imagen41);
        imagenes.add(imagen42);
        imagenes.add(imagen43);
        imagenes.add(imagen44);
        imagenes.add(imagen45);
        imagenes.add(imagen46);
        imagenes.add(imagen47);
        imagenes.add(imagen48);

        int c = 0;
        for (int i = 0; i < 4; i ++) {
            for (int j = 1; j < 13; j++) {
                int valor = j;
                String figura = figuras.get(i);
                ImageIcon imagen = imagenes.get(c);

                Carta nuevaCarta = new Carta(valor, figura, imagen);
                baraja.add(nuevaCarta);
                c++;
            }
        }
    }

    public static void main(String [] args) {
        Baraja baraja = new Baraja();
        baraja.mezclarBaraja();
        baraja.mostrarBaraja();
    }

    public void mostrarBaraja() {
        baraja.forEach(carta ->{
            System.out.println(carta.toString());
        });
    }

    public void mezclarBaraja() {
        Collections.shuffle(baraja);
    }

    public Carta getCarta(int i) {
        return baraja.get(i);
    }

    public void borrarCarta(int i) {
        baraja.remove(i);
    }

    public int getTamano() {
        return baraja.size();
    }

}
