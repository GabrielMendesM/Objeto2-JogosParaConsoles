import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.swing.JFrame;

public class Jogo extends JFrame implements Runnable {
    private static final int INTERVALO = 100;
    private static final int NUM_CARROS = 5;

    private static boolean isRodando;
    private static boolean acabou = false;
    private static String vencedor;

    private Thread gameLoop = null;
    private CarroProcessor[] carrosProcessors = new CarroProcessor[NUM_CARROS];
    private Cena cena = new Cena();
    private List<Carro> carros = new ArrayList<>();
    
    private Semaphore sem = new Semaphore(1);
    private Graphics2D g2;

    public Jogo() {
        configurarJanela();        
    }

    private void criarCena() {
        cena.add(new Fundo(this));

        for (int i = 0; i < NUM_CARROS; i++) {
            carros.add(new Carro(i, 0, (i * 80) + 120, 1, 3, 730, this));
        }
    }

    public void comecar() {
        setPreferredSize(new Dimension(908, 600));
        g2 = (Graphics2D) getGraphics();
        
        pack();

        criarCena();

        setIsRodando(true);
        gameLoop = new Thread(this);
        gameLoop.start();

        for (int i = 0; i < carrosProcessors.length; i++) {
            carrosProcessors[i] = new CarroProcessor(carros.get(i), g2);
            carrosProcessors[i].start();
        }
    }

    public void parar() {
        setIsRodando(false);
        
        try {
            gameLoop.join();
            for (CarroProcessor c : carrosProcessors) {
                c.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setVisible(false);
        dispose();        
    }

    private void draw(Graphics2D g) {
        cena.draw(g);

        try {
            sem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sem.release();
    }

    private void configurarJanela() {
        setTitle("Objeto 2");
        setLayout(new BorderLayout());

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e) {
                parar();                
            }

            @Override
            public void windowClosed(WindowEvent e) {}

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {}

            @Override
            public void windowDeactivated(WindowEvent e) {}
        });

        setVisible(true);
    }

    @Override
    public void run() {
        while (getIsRodando()) {
            draw(g2);

            for (Carro c : carros) {
                if (c.getChegou()) {
                    acabou = true;
                    if (vencedor == null) {
                        vencedor = c.getNome();
                    }
                } else {
                    acabou = false;
                }
            }
            if (acabou) {
                System.out.println("=========================\n" + vencedor.toUpperCase() + " FOI O VENCEDOR!\n=========================");
                setIsRodando(false);
            }

            try {
                Thread.sleep(INTERVALO);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getIntervalo() {
        return INTERVALO;
    }

    public static boolean getIsRodando() {
        return isRodando;
    }

    public static void setIsRodando(boolean rodando) {
        isRodando = rodando;
    }
}
