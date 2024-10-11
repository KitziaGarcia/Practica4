import javax.swing.*;

public class Carta {
    private int valor;
    private String figura;
    private String[] figuras;
    private ImageIcon imagen;


    public Carta() {
        valor = 1;
        figura = "oros";
        //this.imagen = imagen;
    }

    //ImageIcon imagen
    public Carta(int valor, String figura) {
        this.valor = valor;
        this.figura = figura;
        //this.imagen = imagen;
    }

    /**
     * Método para obtener el valor de la carta.
     *
     * @return el valor de la carta.
     */
    public int getValor()
    {
        return valor;
    }

    /**
     * Método para obtener el valor de la letra de la carta.
     *
     * @return la letra de la carta.
     */
    /*public String getLetra()
    {
        return representarLetra(valor);
    }*/

    /**
     * Método para obtener la figura de la carta.
     *
     * @return la figura de la carta.
     */
    public String getFigura()
    {
        return figura;
    }

    /**
     * Método para definir el valor de la carta.
     *
     * @param valor el valor actual de la carta.
     */
    public void setValor(int valor)
    {
        this.valor = valor;
    }

    /**
     * Método para definir la figura de la carta.
     *
     * @param figura figura actual de la carta.
     */
    public void setFigura(String figura)
    {
        this.figura = figura;
    }

    public String toString(){
        String cadena;
        switch (valor)
        {
            case 0:
                cadena = "Joker";
            case 1:
                cadena = "A";
                break;
            case 11:
                cadena = "J";
                break;
            case 12:
                cadena = "Q";
                break;
            case 13:
                cadena = "K";
                break;
            default:
                cadena = Integer.toString(valor);
        }
        return cadena + " de " + figura;
    }

    public void printInConsole()
    {
        System.out.println(toString());
    }

}
