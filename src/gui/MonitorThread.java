package gui;

import emailbomber.bomber.BomberThread;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

public class MonitorThread extends Thread {

    protected List<BomberThread> bomberThreads;
    protected int totalNumPeticiones;
    protected int totalAvance;
    protected int totalErrores;
    protected JProgressBar jProgressBar;
    protected JTextField exito;
    protected JTextField error;

    public MonitorThread(JProgressBar jProgressBar, JTextField exito, JTextField error) {
        this.bomberThreads = new ArrayList<>();
        this.jProgressBar = jProgressBar;
        this.exito = exito;
        this.error = error;
    }

    public void cancelar() {
        for (BomberThread thread : this.bomberThreads) {
            thread.cancelar();
        }
    }

    public void agregarThread(BomberThread bomberThread) {
        this.bomberThreads.add(bomberThread);
    }

    public List<BomberThread> getBomberThreads() {
        return bomberThreads;
    }

    public int getTotalNumPeticiones() {
        return totalNumPeticiones;
    }

    public int getTotalAvance() {
        return totalAvance;
    }

    public int getTotalErrores() {
        return totalErrores;
    }

    public boolean isTerminado() {
        boolean terminado = true;
        for (BomberThread thread : this.bomberThreads) {
            if (!thread.isTerminado()) {
                terminado = false;
                break;
            }
        }
        return terminado;
    }

    protected void update() {

        int petionesCompletadas = 0;
        int erroresTotales = 0;

        for (BomberThread bt : this.bomberThreads) {
            petionesCompletadas += bt.getAvance();
            erroresTotales += bt.getErrores();
        }

        //this.totalAvance = petionesCompletadas;
        //this.totalErrores = erroresTotales;
        this.exito.setText(String.valueOf(petionesCompletadas));
        this.error.setText(String.valueOf(erroresTotales));
        this.jProgressBar.setValue(petionesCompletadas);

        //System.out.println(petionesCompletadas + " / " + this.totalNumPeticiones);
    }

    @Override
    public void run() {

        for (BomberThread thread : this.bomberThreads) {
            this.totalNumPeticiones += thread.getNumPeticiones();
            if (!thread.isAlive()) {
                thread.start();
            }
        }
        this.jProgressBar.setMaximum(this.totalNumPeticiones);

        while (!isTerminado()) {
            update();
            try {
                sleep(500);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        // final update
        update();
    }

}
