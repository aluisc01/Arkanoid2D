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

    private ArrayList<Sprite> bloques = new ArrayList<Sprite>();

    private BufferedImage img;
    private Image redimension;
    private int velocidadBola = 7;

    private File ruta = new File("Imagenes/fondo.png");
    private String rutaPelota = "Imagenes/nuevaPelota.png";
    private String rutaBarra = "Imagenes/barra.png";

    private double contador = 0.;

    private Sprite barra;
    private Sprite bola;
    private PanelJuego panel;

    private Color[] colores = { Color.GREEN, Color.BLUE, Color.YELLOW, Color.PINK, Color.ORANGE };

    boolean voyDerecha = false;
    boolean voidIzquierda = false;

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
        // Cada 5 niveles se añadira una fila y a cada nivel se le añade uno de
        // velocidad
        for (int i = 0; i < (Math.abs(velocidadBola) / 5); i++) {
            for (int j = 0; j < 9; j++) {
                x += 60;
                bloques.add(new Sprite(colores[i], x, y, 50, 20, 0, 0));
            }
            y += 30;
            x = 15;
        }
        try {
            img = ImageIO.read(ruta);
        } catch (IOException e) {
            e.printStackTrace();
        }
        barra = new Sprite(Color.RED, 100, 310, 50, 10, 0, 0);
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
                    if (bola.getPosX() + (bola.getAncho() / 2) < bloques.get(i).getPosX()
                            && bola.getPosY() < bloques.get(i).getPosY() + bloques.get(i).getAlto() - 10) {
                        System.out.println("Cambio izquierda");
                        bola.cambiarTrayectoriaX();
                    } else if (bola.getPosX() > bloques.get(i).getPosX() + (bloques.get(i).getAncho() / 2)
                            && bola.getPosY() < bloques.get(i).getPosY() + bloques.get(i).getAlto() - 10) {
                        System.out.println("Cambio derecha");
                        bola.cambiarTrayectoriaX();
                    } else {
                        bola.cambiarTrayectoriaY();
                    }
                    bloques.remove(i);
                }

            }
        }
        barra.mover(voidIzquierda, voyDerecha);

        if (bola != null) {
            if (comprobarChoque(barra, bola)) {
                bola.cambiarTrayectoriaY();
            }

            bola.mover(ancho, alto);
            if (bola.getPosY() > alto) {
                bola = null;
            }
        } else {
            panel.setPantallaActual(new PantallaFinal(false, contador, panel));
        }
        if (bloques.size() > 0) {
            for (int i = 0; i < bloques.size(); i++) {
                bloques.get(i).mover(ancho, alto);
            }
        } else if (bola != null) {
            panel.setPantallaActual(new PantallaFinal(true, contador, panel));
        }

    }

    /**
     * Cada vez que hagamos click crearemos un nuevo sprite con el disparo , si en
     * clickderecho crearemos la nave
     */
    @Override
    public void hacerClick(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e) && bola != null && bola.getVelX() == 0) {
            bola.setVelX(velocidadBola);
            bola.setVelY(velocidadBola);
        }

    }

    /**
     * Cada vez que movamos el raton moveremos la nave y comprobaremos si choca con
     * algun asteroide
     */
    @Override
    public void moverRaton(MouseEvent e) {

    }

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

    @Override
    public void soltarTecla() {
        voidIzquierda = false;
        voyDerecha = false;

    }
}
