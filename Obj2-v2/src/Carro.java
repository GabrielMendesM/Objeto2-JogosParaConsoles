import java.util.concurrent.ThreadLocalRandom;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.ImageIcon;

public class Carro extends Thread {
    private static final int FRAMES_SEG = 30;

    private int posX, posY, velMin, velMax;
    private final int posChegada;
    private final String nome;

    private JPanel panel;
    private ImageIcon img;

    private boolean chegou = false;
    private boolean acabou = false;

    public Carro(int posX, int posY, int velMin, int velMax, int posChegada, int id) {
        this.posX = posX;
        this.posY = posY;
        this.velMin = velMin;
        this.velMax = velMax;
        this.posChegada = posChegada;
        this.nome = "Carro_0" + id;
        this.img = new ImageIcon(getClass().getResource("./img/carro" + ThreadLocalRandom.current().nextInt(0, 10) + ".png"));
    }

    public void draw(Graphics g) {
        img.paintIcon(panel, g, posX, posY);
        
        panel.repaint();
    }

    public void mover() {
        int velocidade = ThreadLocalRandom.current().nextInt(velMin, velMax);
        
        if (posX <= posChegada && !chegou) {
            int posAnterior = posX;
            posX += velocidade;
            System.out.println(nome + " andou " + (posX - posAnterior) + "m e já percorreu " + posX + "m.");
        } else if (!chegou) {
            chegou = true;
            Jogo.setVencedor(nome);
            System.out.println("=========================\n     " + nome + " chegou!\n=========================");
        } else if (posX < (posChegada + 10)) {
            posX += velocidade;
        } else if (!acabou && posX >= (posChegada + 10)) {
            acabou = true;
            Jogo.parar();
        }
    }

    @Override
    public void run() {
        while (Jogo.isRodando()) {
            mover();

            try {
                Thread.sleep(1000 / FRAMES_SEG);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public boolean getChegou() {
        return this.chegou;
    }
}
