package emailbomber.gui;

import javax.swing.Icon;
import javax.swing.JLabel;

public class JLabelEstadoConexion extends Thread {

    public enum estado {
        NO_CONECTADO,
        ESCUCHANDO,
        ATACANDO
    }
    private Icon iconActive;
    private Icon iconOnline;
    private Icon iconOffline;
    private JLabel jLabel;
    private estado estado;
    private boolean needUpdateGUI;

    public JLabelEstadoConexion(JLabel jLabel) {
        this.jLabel = jLabel;
        this.iconActive = new javax.swing.ImageIcon(getClass().getResource("/emailbomber/gui/icons8-fire_alarm_button_1.png"));
        this.iconOnline = new javax.swing.ImageIcon(getClass().getResource("/emailbomber/gui/icons8-fire_alarm_button_2.png"));
        this.iconOffline = new javax.swing.ImageIcon(getClass().getResource("/emailbomber/gui/icons8-fire_alarm_button_3.png"));
        this.estado = estado.NO_CONECTADO;
    }

    public void setEstado(estado estado) {
        this.estado = estado;
        this.needUpdateGUI = true;
    }

    @Override
    public void run() {
        while (true) {
            if (this.needUpdateGUI) {
                switch (this.estado) {
                    case NO_CONECTADO:
                        this.jLabel.setText("Offline");
                        this.jLabel.setIcon(this.iconOffline);
                        break;
                    case ESCUCHANDO:
                        this.jLabel.setText("Online");
                        this.jLabel.setIcon(this.iconOnline);
                        break;
                    case ATACANDO:
                        this.jLabel.setText("Ataque remoto");
                        this.jLabel.setIcon(iconActive);
                        break;
                }
                this.needUpdateGUI = false;
            }
            try {
                sleep(500);
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }

}
