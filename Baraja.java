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
        imagenes.add(new ImageIcon(getClass().getResource("imagenes/oros1.png")));

        for (int i = 1; i <= 13; i++) {
            for (int j = 0; j < 4; j++) {
                int valor = i;
                String figura = figuras.get(j);

                //Carta nuevaCarta = new Carta(valor, figura, imagen);
               // baraja.add(nuevaCarta);
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

}
