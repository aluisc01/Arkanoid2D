package pantalla;

import java.awt.event.ComponentEvent;

import java.awt.event.MouseEvent;

import java.io.File;
import java.io.IOException;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import javax.imageio.ImageIO;

import java.awt.*;
import principal.*;

/**
 * @author Alberto Luis Calero
 */
public class PantallaFinal implements Pantalla {

    private String fondoFile;
    private Image redimension;
    private BufferedImage img;
    private boolean victoria;
    private String mensaje;
    private String mensajeTiempo;
    private double tiempo;
    private DecimalFormat df;
    private DecimalFormatSymbols dfs;
    private PanelJuego panel;

    /**
     * Constructor
     * 
     * @param victoria
     * @param tiempo
     * @param panel
     */
    public PantallaFinal(boolean victoria, double tiempo, PanelJuego panel) {
        this.victoria = victoria;
        this.tiempo = tiempo;
        inicializarPantalla();
        this.panel = panel;
    }

    /**
     * Metodo para iniciar los componentes necesarios de la pantalla
     * 
     */
    @Override
    public void inicializarPantalla() {
        dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        df = new DecimalFormat("##.##", dfs);
        if (victoria) {
            fondoFile = "Imagenes/victoria.jpg";
            mensaje = "Victoria!!";
            mensajeTiempo = "Tu tiempo : " + df.format(tiempo);
        } else {
            fondoFile = "Imagenes/derrota.jpg";
            mensaje = "Has sido derrotado!";
        }

        img = null;
        try {
            img = ImageIO.read(new File(fondoFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Metodo para pintar todos los componentes que iran en la pantalla
     */
    @Override
    public void pintarPantalla(Graphics g, int ancho, int alto) {
        if (redimension == null) {
            redimension = img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        }
        g.drawImage(redimension, 0, 0, null);

        if (victoria) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 25));
            g.drawString(mensajeTiempo, 200, 250);
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 60));
            g.drawString(mensaje, 200, 150);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 25));
            g.drawString("Para volver a jugar haz click ", 150, 200);
        } else {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString(mensaje, 100, 250);
        }

    }

    /**
     * En este caso no hace nada
     */
    @Override
    public void ejecutarFrame(int alto, int ancho) {
        // TODO Auto-generated method stub

    }

    /**
     * Si hemos ganado y pulsamos click haremos un nivel mas
     */
    @Override
    public void hacerClick(MouseEvent e) {

        if (victoria) {
            panel.nuevoNivel();
        }

    }

    @Override
    public void moverRaton(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    /**
     * Metodo con el que redimensionaremos la imagen si redimensionamos la pantalla
     */
    @Override
    public void redimensionarPantalla(ComponentEvent e, int ancho, int alto) {
        if (img != null) {
            redimension = img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        }

    }

    @Override
    public void pulsarTecla(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void soltarTecla() {
        // TODO Auto-generated method stub

    }

}
