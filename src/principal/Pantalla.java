package principal;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.ComponentEvent;

public interface Pantalla {
    public void inicializarPantalla();

    public void pintarPantalla(Graphics g, int ancho, int alto);

    public void ejecutarFrame(int alto, int ancho);

    public void hacerClick(MouseEvent e);

    public void moverRaton(MouseEvent e);

    public void pulsarTecla(KeyEvent e);

    public void soltarTecla();

    public void redimensionarPantalla(ComponentEvent e, int ancho, int alto);
}
