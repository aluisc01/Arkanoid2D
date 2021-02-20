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

    public Sprite(Color color, int x, int y, int width, int height, int velX, int velY) {
        this.color = color;
        this.posX = x;
        this.posY = y;
        this.ancho = width;
        this.alto = height;

        inicializarBuffer();
        // setVelocidadesAleatorias();

    }

    public Sprite(Color color, int x, int y, int width, int height, int velX, int velY, boolean comprobar) {
        this.color = color;
        this.posX = x;
        this.posY = y;
        this.ancho = width;
        this.alto = height;

        inicializarBuffer();
        // setVelocidadesAleatorias();
        this.velY = velY;
        this.velX = velX;
    }

    public Sprite(int x, int y, int width, int height, int velX, int velY, String ruta) {

        this.posX = x;
        this.posY = y;
        this.ancho = width;
        this.alto = height;
        this.ruta = ruta;
        inicializarBuffer(this.ruta);

        // setVelocidadesAleatorias();
    }

    public void mover(boolean voyIzquierda, boolean voyDerecha) {
        velX = 15;
        if (voyIzquierda) {
            if (posX > 0) {
                posX -= velX;
            }
        } else if (voyDerecha) {
            if (posX + ancho < 590) {
                posX += velX;
            }
        }

    }

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

    public void setVelocidadesAleatorias() {
        int nuevaVelX, nuevaVelY;
        nuevaVelX = new Random().nextInt(5);

        if (new Random().nextInt(2) == 0) {
            nuevaVelX *= -nuevaVelX;
        }

        do {
            nuevaVelY = new Random().nextInt(5);
        } while (nuevaVelX == 0 && nuevaVelY == 0);

        if (new Random().nextInt(2) == 0) {
            nuevaVelY *= -1;
        }

        if (new Random().nextInt(2) == 0) {
            velX = nuevaVelX;
            velY = nuevaVelY;
        } else {
            velX = nuevaVelY;
            velY = nuevaVelX;
        }

    }

    public void cambiarTrayectoriaY() {
        int rd = new Random().nextInt(3);
        if (rd == 1) {
            velY = -(velY + 1);
            velX = velX - 1;
        } else if (rd == 2) {
            velY = -(velY - 1);

        } else {
            velY = -velY;
        }

    }

    public void cambiarTrayectoriaX() {
        int rd = new Random().nextInt(3);
        if (rd == 1) {
            velX = -(velX + 1);
            velY = velY - 1;
        } else if (rd == 2) {
            velX = -(velX - 1);
            velY = velY + 1;

        } else {
            velX = -velX;
        }
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void inicializarBuffer() {
        buffer = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
        Graphics g = buffer.getGraphics();
        g.setColor(color);
        g.fillRect(0, 0, ancho, alto);
        g.dispose();
    }

    public void inicializarBuffer(String ruta) {

        try {
            buffer = ImageIO.read(new File(ruta));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Graphics g = buffer.getGraphics();
        g.drawImage(buffer.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH), 10, 10, null);

    }

    public void estampar(Graphics g) {
        g.drawImage(buffer.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH), posX, posY, null);
    }

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
