import java.awt.Graphics2D;

public class CarroProcessor extends Thread {
    private static final int INTERVALO = 100;

    Carro carro;
    Graphics2D g2;

    public CarroProcessor(Carro carro, Graphics2D g2) {
        this.carro = carro;
        this.g2 = g2;
    }

    @Override
    public void run() {
        while (Jogo.getIsRodando()) {
            carro.render(g2);
            carro.update();
        }
        try {
            this.sleep(INTERVALO);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
