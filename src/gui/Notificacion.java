package gui;

import javax.swing.Icon;
import javax.swing.JLabel;

public class Notificacion extends Thread {

    public enum estado {
        NO_CONECTADO,
        ESCUCHANDO,
        ATACANDO
    }
    private Icon icon1;
    private Icon icon2;
    private Icon icon3;
    private JLabel jLabel;
    private estado estado;

    public Notificacion(JLabel jLabel) {
        this.jLabel = jLabel;
        this.icon1 = new javax.swing.ImageIcon(getClass().getResource("/gui/icons8-fire_alarm_button_1.png"));
        this.icon2 = new javax.swing.ImageIcon(getClass().getResource("/gui/icons8-fire_alarm_button_2.png"));
        this.icon3 = new javax.swing.ImageIcon(getClass().getResource("/gui/icons8-fire_alarm_button_3.png"));
        this.estado = estado.NO_CONECTADO;
    }

    public void setEstado(estado estado) {
        this.estado = estado;
    }

    @Override
    public void run() {
        while (true) {
            try {
                switch (this.estado) {
                    case NO_CONECTADO:
                        this.jLabel.setText("offline");
                        this.jLabel.setIcon(this.icon3);
                        break;
                    case ESCUCHANDO:
                        this.jLabel.setText("online");
                        this.jLabel.setIcon(this.icon2);
                        sleep(1000);
                        this.jLabel.setIcon(this.icon3);
                        break;
                    case ATACANDO:
                        this.jLabel.setText("Ataque remoto");
                        this.jLabel.setIcon(this.icon1);
                        sleep(1000);
                        this.jLabel.setIcon(this.icon2);
                }
                sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

}
