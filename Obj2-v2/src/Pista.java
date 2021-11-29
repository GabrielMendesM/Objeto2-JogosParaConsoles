import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Pista {
    private JPanel panel;
    private ImageIcon img;

    public Pista(JPanel panel) {
        this.panel = panel;
        this.img = new ImageIcon(getClass().getResource("./img/fundo.png"));
    }

    public void draw(Graphics g) {
        img.paintIcon(panel, g, 0, 0);
    }
}
