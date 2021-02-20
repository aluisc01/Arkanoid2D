package principal;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import pantalla.PantallaInicio;
import pantalla.PantallaJuego;

import java.awt.*;

/**
 * @author Alberto Luis Calero
 */
public class PanelJuego extends JPanel
        implements Runnable, MouseListener, MouseMotionListener, ComponentListener, KeyListener {

    private static final long serialVersionUID = 1L;

    private Pantalla pantallaActual;

    private boolean esPantallaInicio = true;

    private int nivel = -5;

    // private ArrayList<Sprite> asteroides = new ArrayList<Sprite>();

    /**
     * Constructor
     */
    public PanelJuego() {
        pantallaActual = new PantallaInicio();
        this.addMouseListener(this);
        this.addComponentListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);
        this.setFocusable(true);
        new Thread(this).start();

    }

    @Override
    public void run() {

        while (true) {
            if (esPantallaInicio) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {// Dormimos menos tiempo el hilo si estamos jugando
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            pantallaActual.ejecutarFrame(getWidth(), getHeight());

            repaint();
            Toolkit.getDefaultToolkit().sync();

        }

    }

    // Método que se llama automáticamente para pintar el componente.
    @Override
    public void paintComponent(Graphics g) {
        pantallaActual.pintarPantalla(g, this.getWidth(), this.getHeight());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e) && esPantallaInicio) {
            pantallaActual = new PantallaJuego(this, nivel);
            esPantallaInicio = false;
            this.setSize(getWidth(), getHeight());
        } else {
            pantallaActual.hacerClick(e);
        }

    }

    public Pantalla getPantalla() {
        return pantallaActual;
    }

    /**
     * Metodo con el que cambiaremos la pantalla
     */
    public void setPantallaActual(Pantalla pantallaActual) {
        this.pantallaActual = pantallaActual;
    }

    public void setEspantallaInicio(boolean inicio) {
        esPantallaInicio = inicio;
    }

    /**
     * Metodo con el que crearemos un nivel aumentando en 1 la dificultad
     */
    public void nuevoNivel() {
        nivel--;
        pantallaActual = new PantallaJuego(this, nivel);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    // MouseMotionListener
    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    /**
     * LLamamos al metodo de la pantalla correspondiente con
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        pantallaActual.moverRaton(e);

    }

    // ComponentListener
    @Override
    public void componentResized(ComponentEvent e) {
        pantallaActual.redimensionarPantalla(e, getWidth(), getHeight());
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void componentShown(ComponentEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void componentHidden(ComponentEvent e) {
        // TODO Auto-generated method stub

    }

    // KeyListener
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        pantallaActual.pulsarTecla(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pantallaActual.soltarTecla();

    }

}
