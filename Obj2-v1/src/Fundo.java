import java.awt.Graphics2D;
import java.awt.Component;

import javax.swing.ImageIcon;

public class Fundo extends GameObject implements Renderizavel {
    private ImageIcon img;
    private Component c;
    
    public Fundo(Component c) {
        this.img = new ImageIcon(getClass().getResource("./img/fundo.png"));
        this.c = c;
    }
    
    @Override
    public void render(Graphics2D g2) {
        img.paintIcon(c, g2, 0, 0);
    }
}
