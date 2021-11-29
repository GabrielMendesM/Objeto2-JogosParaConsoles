import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.Semaphore;

import java.awt.Graphics2D;
import java.awt.Component;

import javax.swing.ImageIcon;

public class Carro extends GameObject implements Renderizavel {
    private Semaphore controller;

    private final String NOME;

    private int velMin;
    private int velMax;
    private int posChegada;
    private boolean chegou = false;

    private ImageIcon img;
    private Component c;

    public Carro(int id, int posX, int posY, int velMin, int velMax, int posChegada, Component c) {
        this.controller = new Semaphore(3);

        this.NOME = "Carro_" + ((id + 1) > 9 ? "" : "0") + (id + 1);
        this.setPos(posX, posY);
        this.velMin = velMin;
        this.velMax = velMax;
        this.setPosChegada(posChegada);
        this.img = new ImageIcon(getClass().getResource("./img/carro" + ThreadLocalRandom.current().nextInt(0, 10) + ".png"));
        this.c = c;
    }

    @Override
    public void update() {
        try {
            controller.acquire();
            
            int velocidade = ThreadLocalRandom.current().nextInt(velMin, velMax);
            if (getPosX() <= getPosChegada() && !getChegou()) {
                int posAnterior = getPosX();
                setPosX(getPosX() + velocidade);
                System.out.println(NOME + " andou " + (getPosX() - posAnterior) + "m e já percorreu " + getPosX() + "m.");
            } else if (!getChegou()) {
                setChegou(true);
                System.out.println("=========================\n     "+ NOME + " chegou!\n=========================");
            } else if (getPosX() < (getPosChegada() + 80)) {
                setPosX(getPosX() + velocidade);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            controller.release();
        }
    }

    @Override
    public void render(Graphics2D g2) {
        img.paintIcon(c, g2, posX, posY);
    }

    public int getPosChegada() {
        return this.posChegada;
    }

    public void setPosChegada(int posChegada) {
        this.posChegada = posChegada;
    }

    public boolean getChegou() {
        return this.chegou;
    }

    public void setChegou(boolean chegou) {
        this.chegou = chegou;
    }

    public String getNome() {
        return NOME;
    }
}
