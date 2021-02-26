package pantalla;

import java.awt.event.ComponentEvent;

import java.awt.event.MouseEvent;

import java.io.File;
import java.io.IOException;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import javax.swing.SwingUtilities;
import java.awt.*;
import principal.*;

/**
 * @author Alberto Luis Calero
 */
public class PantallaJuego implements Pantalla {

    /**
     * Almacenara todos los bloques
     */
    private ArrayList<Sprite> bloques = new ArrayList<Sprite>();

    private BufferedImage img;
    private Image redimension;
    private int velocidadBola = 7;

    private File ruta = new File("Imagenes/fondo.png");
    private String rutaPelota = "Imagenes/nuevaPelota.png";
    private String rutaBarra = "Imagenes/barra.gif";

    private double contador = 0.;

    private Sprite barra;
    private Sprite bola;
    private PanelJuego panel;

    /**
     * Colores que cogera cada linea de bloques
     */
    private Color[] colores = { Color.GREEN, Color.CYAN, Color.YELLOW, Color.PINK, Color.ORANGE };
    /**
     * Booleano , si es true estamos pulsando la d
     */
    boolean voyDerecha = false;

    /**
     * Booleano que dice si estamos pulsando la a
     */
    boolean voidIzquierda = false;

    /**
     * Contructor que recibe el panel donde se muestra y un entero indicando el
     * nivel
     * 
     * @param panel
     * @param nivel
     */
    public PantallaJuego(PanelJuego panel, int nivel) {

        this.panel = panel;
        this.velocidadBola = nivel;
        inicializarPantalla();
    }

    /**
     * Iniciamos los asteroides que cada nivel saldran en una esquina aleatoria de
     * la pantalla
     */
    @Override
    public void inicializarPantalla() {
        img = null;
        int x = -30;
        int y = 11;
        // Cada 5 niveles se añadira una fila con cada nivel hasta 5 como maximo
        for (int i = 0; i < Math.min((Math.abs(velocidadBola) - 4), 5); i++) {
            for (int j = 0; j < 9; j++) {
                x += 60;
                bloques.add(new Sprite(colores[i], x, y, 50, 20, 0, 0));
            }
            y += 30;
            x = -30;
        }
        try {
            img = ImageIO.read(ruta);
        } catch (IOException e) {
            e.printStackTrace();
        }
        barra = new Sprite(100, 310, 50, 10, 0, 0, rutaBarra);
        bola = new Sprite(550, 270, 30, 30, 0, 0, rutaPelota);
    }

    /**
     * Metodo con el que pondremos la imagen de fondo
     */
    private void rellenarFondo(Graphics g) {
        g.drawImage(redimension, 0, 0, null);

    }

    /**
     * Metodo para poner el nivel en el que nos escontramos
     * 
     * @param g
     */
    private void rellenarPuntuacion(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Nivel " + (Math.abs(velocidadBola) - 5), 500, 350);
    }

    /**
     * Metodo con el que pintaremos todos los elementos de del juego
     */
    @Override
    public void pintarPantalla(Graphics g, int ancho, int alto) {
        if (redimension == null) {
            redimension = img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        }
        rellenarFondo(g);
        for (int i = 0; i < bloques.size(); i++) {
            bloques.get(i).estampar(g);
        }
        if (barra != null) {
            barra.estampar(g);
        }
        if (bola != null) {
            bola.estampar(g);
        }

        rellenarPuntuacion(g);

    }

    /**
     * Comprobamos si dos sprites que le mandemos chocan.Devuelve true si chocan y
     * falso si no lo hacen
     * 
     * @param sprite1
     * @param sprite2
     * @return retorna un booleano true si se chocan y false si no se chocan
     * 
     */
    public boolean comprobarChoque(Sprite sprite1, Sprite sprite2) {
        Sprite cercano, lejano;
        boolean choqueEnX = false;
        boolean choqueEnY = false;
        boolean choqueFinal = false;
        if (sprite1 != null && sprite2 != null) {
            if (sprite1.getPosX() < sprite2.getPosX()) {
                cercano = sprite1;
                lejano = sprite2;
            } else {
                cercano = sprite2;
                lejano = sprite1;
            }
            int bordeDerecho = cercano.getPosX() + cercano.getAncho();
            if (bordeDerecho >= lejano.getPosX()) {
                choqueEnX = true;
            }
            if (sprite1.getPosY() < sprite2.getPosY()) {
                cercano = sprite1;
                lejano = sprite2;
            } else {
                cercano = sprite2;
                lejano = sprite1;
            }
            int bordeAbajo = cercano.getPosY() + cercano.getAlto();
            if (bordeAbajo >= lejano.getPosY()) {
                choqueEnY = true;
            }

            if (choqueEnX && choqueEnY) {
                choqueFinal = true;
            }
        }
        return choqueFinal;
    }

    /**
     * Se ejecuta una vez cada 25 milisegundos y sera donde movamos y comprobemos
     * las colisiones del juego
     */
    @Override
    public void ejecutarFrame(int ancho, int alto) {
        if (bola != null) {
            for (int i = 0; i < bloques.size(); i++) {
                if (comprobarChoque(bola, bloques.get(i))) {
                    // Comprobamos que da por un lado y no por arriba y por abajo , le doy 10
                    // pixeles de margen para mejor funcionamiento
                    if (bola.getPosX() + (bola.getAncho() * 0.8) < bloques.get(i).getPosX()
                            && bola.getPosY() < bloques.get(i).getPosY() + bloques.get(i).getAlto() - 10) {

                        bola.cambiarTrayectoriaX();
                    } else if (bola.getPosX() > bloques.get(i).getPosX() + (bloques.get(i).getAncho() * 0.95)
                            && bola.getPosY() < bloques.get(i).getPosY() + bloques.get(i).getAlto() - 10) {

                        bola.cambiarTrayectoriaX();
                    } else {

                        bola.cambiarTrayectoriaY();
                    }
                    bloques.remove(i);
                }

            }
            if (comprobarChoque(barra, bola)) {// Si chocan se cambia la velocidad en Y
                bola.cambiarTrayectoriaYNegativa();
            }

            bola.mover(ancho, alto);
            if (bola.getPosY() > alto) {// Si la bola cae abajo perdemos
                bola = null;
                panel.setPantallaActual(new PantallaFinal(false, panel));
            }
        }
        barra.mover(voidIzquierda, voyDerecha, panel.getWidth());
        if (bloques.size() > 0) {
            for (int i = 0; i < bloques.size(); i++) {
                bloques.get(i).mover(ancho, alto);
            }
        } else if (bola != null) {// Si no hay bloques ganamos
            panel.setPantallaActual(new PantallaFinal(true, panel));
        }
    }

    /**
     * Al hacer clip empezaremos a mover la bola
     */
    @Override
    public void hacerClick(MouseEvent e) {// Click para
        if (SwingUtilities.isLeftMouseButton(e) && bola != null && bola.getVelX() == 0) {
            bola.setVelX(velocidadBola);
            bola.setVelY(velocidadBola);
        }

    }

    /**
     * 
     */
    @Override
    public void moverRaton(MouseEvent e) {

    }

    /**
     * Cuando pulsemos la a o la d cambiaremos el booleano para movernos al lado que
     * corresponda
     */
    @Override
    public void pulsarTecla(KeyEvent e) {
        char tecla = e.getKeyChar();
        if (tecla == 'a') {
            voidIzquierda = true;
        }
        if (tecla == 'd') {
            voyDerecha = true;
        }

    }

    /**
     * Cuando redimensionemos la pantalla redimensionaremos la imagen que estamos
     * poniendo de fondo
     */
    @Override
    public void redimensionarPantalla(ComponentEvent e, int ancho, int alto) {
        if (img != null) {
            redimension = img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        }

    }

    /**
     * Cuando dejemos de pulsar una tecla cambiaremos los booleanos para dejar de
     * movernos
     */
    @Override
    public void soltarTecla(KeyEvent e) {

        char tecla = e.getKeyChar();
        if (tecla == 'a') {
            voidIzquierda = false;
        }
        if (tecla == 'd') {
            voyDerecha = false;
        }

    }
}
