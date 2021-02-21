package principal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * @author Alberto Luis Calero
 */
public class Sprite {

    private Color color;
    private int posX;
    private int posY;
    private int ancho;
    private int alto;

    private int velX;
    private int velY;

    private String ruta;

    private BufferedImage buffer;

    /**
     * Constructor que usaremos para un sprite sin imagen
     * 
     * @param color
     * @param x
     * @param y
     * @param width
     * @param height
     * @param velX
     * @param velY
     */
    public Sprite(Color color, int x, int y, int width, int height, int velX, int velY) {
        this.color = color;
        this.posX = x;
        this.posY = y;
        this.ancho = width;
        this.alto = height;

        inicializarBuffer();

    }

    /**
     * Constructor que usaremos para un sprite con imagen
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     * @param velX
     * @param velY
     * @param ruta
     */
    public Sprite(int x, int y, int width, int height, int velX, int velY, String ruta) {

        this.posX = x;
        this.posY = y;
        this.ancho = width;
        this.alto = height;
        this.ruta = ruta;
        inicializarBuffer(this.ruta);

    }

    /**
     * Metodo que recive dos booleanos que indican la direccion del sprite y nos
     * moveremos horizontalmente sin salirnos
     * 
     * @param voyIzquierda
     * @param voyDerecha
     */
    public void mover(boolean voyIzquierda, boolean voyDerecha, int anchoPanel) {
        velX = 15;
        if (voyIzquierda) {
            if (posX > 0) {
                posX -= velX;
            }
        } else if (voyDerecha) {
            if (posX + ancho < anchoPanel) {
                posX += velX;
            }
        }

    }

    /**
     * Metodo que usaremos para mover un sprite segun su velocidad
     * 
     * @param anchoPanel
     * @param altoPanel
     */
    public void mover(int anchoPanel, int altoPanel) {
        posX += velX;
        posY += velY;

        // setPosX(getPosX() + getVelX());

        if (posX + ancho >= anchoPanel) {
            velX = -Math.abs(velX);
        }
        if (posX < 0) {
            velX = Math.abs(velX);
        }
        if (posY <= 0) {
            velY = Math.abs(velY);
        }
        if (posY + alto > altoPanel + 40) {
            velY = -Math.abs(velY);
        }

    }

    public void mover() {
        posY += velY;
        posX += velX;
    }

    /**
     * Para que la bola cambie sus trayectorias vamos a ir sumando y restando 1 a la
     * velocidad aleatoriamente , cambia de sentido la velocidad en Y
     */
    public void cambiarTrayectoriaY() {
        int rd = new Random().nextInt(5);
        velY = velY * -1;
        if (rd == 1) {
            velY += 1;
            velX = velX - 1;
        }

    }

    /**
     * Para que la bola cambie sus trayectorias vamos a ir sumando y restando 1 a la
     * velocidad aleatoriamente , cambia de sentido la velocidad en X
     */
    public void cambiarTrayectoriaX() {
        int rd = new Random().nextInt(5);
        velX = velX * -1;
        if (rd == 1) {
            velX += 1;
            velY = velY - 1;
        }
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     * Si es un sprite sin imagen inicializamos de esta manera
     */
    public void inicializarBuffer() {
        buffer = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
        Graphics g = buffer.getGraphics();
        g.setColor(color);
        g.fillRect(0, 0, ancho, alto);
        g.dispose();
    }

    /**
     * Si tenemos un sprite con imagen inicializamos la imagen y la rescalamos
     */
    public void inicializarBuffer(String ruta) {

        try {
            buffer = ImageIO.read(new File(ruta));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Graphics g = buffer.getGraphics();
        g.drawImage(buffer.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH), 10, 10, null);

    }

    /**
     * Pinta el sprite en pantalla
     * 
     * @param g
     */
    public void estampar(Graphics g) {
        g.drawImage(buffer.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH), posX, posY, null);
    }

    // Getters y setters
    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getAncho() {
        return this.ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getAlto() {
        return this.alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public int getVelX() {
        return this.velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public int getVelY() {
        return this.velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public BufferedImage getBuffer() {
        return this.buffer;
    }

    public void setBuffer(BufferedImage buffer) {
        this.buffer = buffer;
    }

}
