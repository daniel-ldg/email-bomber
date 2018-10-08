/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailbomber.gui;

import emailbomber.bomber.BomberUpdateListener;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

/**
 *
 * @author daniel
 */
public class SwingBomberUpdateListener implements BomberUpdateListener {

    private JProgressBar progressBar;
    private JTextField peticionesHechas;
    private JTextField peticionesRechazadas;

    public SwingBomberUpdateListener(JProgressBar progressBar, JTextField peticionesHechas, JTextField peticionesRechazadas, int totalPeticiones) {
        this.progressBar = progressBar;
        this.peticionesHechas = peticionesHechas;
        this.peticionesRechazadas = peticionesRechazadas;
        this.progressBar.setMaximum(totalPeticiones);
    }
    
    @Override
    public void update(int peticionesHechas, int peticionesRechazadas) {
        this.peticionesHechas.setText(String.valueOf(peticionesHechas));
        this.peticionesRechazadas.setText(String.valueOf(peticionesRechazadas));
        this.progressBar.setValue(peticionesHechas);
    }
    
}
