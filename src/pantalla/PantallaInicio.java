package pantalla;

import java.awt.event.ComponentEvent;

import java.awt.event.MouseEvent;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import principal.Pantalla;

import java.awt.*;

/**
 * @author Alberto Luis Calero
 */
public class PantallaInicio implements Pantalla {

    private Color color = Color.GREEN;
    private BufferedImage img;
    private Image redimension;

    /**
     * Contador para ir cambiando el color del mensaje
     */
    private int contador;

    /**
     * Llamamos al metodo para inicia la
     */
    public PantallaInicio() {
        inicializarPantalla();
        contador = 0;
    }

    /**
     * Iniciamos el fondo
     */
    @Override
    public void inicializarPantalla() {
        img = null;

        try {
            img = ImageIO.read(new File("Imagenes/inicio.jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Pintamos los componentes de la pantalla
     */
    @Override
    public void pintarPantalla(Graphics g, int ancho, int alto) {
        if (redimension == null) {
            redimension = img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        }
        g.drawImage(redimension, 0, 0, null);
        g.setColor(Color.BLUE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Bienvenido a ArKanoid!!", 155, 200);

        g.setColor(color);
        g.drawLine(150, 210, 410, 210);
        g.drawLine(150, 170, 410, 170);
        g.drawLine(150, 210, 150, 170);
        g.drawLine(410, 210, 410, 170);

        g.drawString("Haz Click para comenzar tu partida", 130, 300);
    }

    /**
     * Se ejecuta una vez cada 25 milisegundos y sera donde movamos y comprobemos
     * las colisiones del juego
     */
    @Override
    public void ejecutarFrame(int alto, int ancho) {
        if (contador == 4) {
            color = (color == Color.GREEN) ? Color.BLUE : Color.GREEN;
            contador = 0;
        }
        contador++;
    }

    @Override
    public void hacerClick(MouseEvent e) {

    }

    @Override
    public void moverRaton(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    /**
     * Cada vez que hagamos click crearemos un nuevo sprite con el disparo , si en
     * clickderecho crearemos la nave
     */
    @Override
    public void redimensionarPantalla(ComponentEvent e, int ancho, int alto) {
        if (img != null) {
            redimension = img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        }
    }

    @Override
    public void pulsarTecla(KeyEvent e) {
        System.out.println("tecla pulsada");
    }

    @Override
    public void soltarTecla() {
        // TODO Auto-generated method stub

    }

}
