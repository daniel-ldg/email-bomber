/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import emailbomber.bomber.BomberThread;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

/**
 *
 * @author daniel
 */
public class MonitorBatchThread extends MonitorThread {

    public MonitorBatchThread(JProgressBar jProgressBar, JTextField exito, JTextField error) {
        super(jProgressBar, exito, error);
    }

    @Override
    public void run() {
        for (BomberThread thread : this.bomberThreads) {
            this.totalNumPeticiones += thread.getNumPeticiones();
        }
        update();
        this.jProgressBar.setMaximum(this.totalNumPeticiones);

        for (BomberThread thread : this.bomberThreads) {
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            update();
        }
    }

}
