import java.awt.Graphics;

import javax.swing.JPanel;

public class ElementoPanel extends JPanel {
    private Pista pista;
    private Carro[] carros;
    
    public ElementoPanel(Carro[] carros) {
        super();
        pista = new Pista(this);
        this.carros = carros;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        pista.draw(g);
        for (Carro c : carros) {
            c.draw(g);
        }
    }
}
