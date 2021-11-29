import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Cena {
    List<GameObject> go = new ArrayList<>();
    
    public void add(GameObject g) {
        this.go.add(g);
    }

    public void draw(Graphics2D g2) {
        for (GameObject g: go) {
            if (g instanceof Renderizavel) {
                ((Renderizavel) g).render(g2);
            }
        }
    }
}
