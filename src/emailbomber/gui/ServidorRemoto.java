package emailbomber.gui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import emailbomber.network.ApiEstadoCliente;
import emailbomber.network.AtaqueRemoto;
import emailbomber.network.Estado;
import emailbomber.network.EstadoCliente;
import emailbomber.network.HttpClient;
import emailbomber.network.HttpException;
import java.awt.Container;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorRemoto extends javax.swing.JPanel {

    private ApiEstadoCliente apiEstadoCliente;
    private boolean running;
    private boolean update;
    private Estado estadoGUI;

    public ServidorRemoto() {
        initComponents();
        this.running = true;
        this.update = false;
        this.estadoGUI = null;
        new Thread(() -> {
            while (running) {
                if (this.update) {
                    apiEstadoCliente.refreshEstadoCliente();
                    EstadoCliente estadoCliente = apiEstadoCliente.getEstadoCliente();
                    updateGUI(
                            estadoCliente.getEstado(),
                            estadoCliente.getPeticionesAtaque(),
                            estadoCliente.getPeticionesHechas()
                    );
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    private void updateGUI(Estado estado, int progresoMax, int progresoValue) {
        if (this.estadoGUI == null || this.estadoGUI != estado) {
            switch (estado) {
                case ATAQUE_LOCAL:
                case ATAQUE_REMOTO:
                    this.estado.setText("Atacando");
                    break;
                case ESPERANDO:
                    this.estado.setText("Online");
                    break;
                case OFFLINE:
                    this.estado.setText("Offline");
            }
            this.estadoGUI = estado;
        }
        
        if (this.progreso.getMaximum() != progresoMax) {
            this.progreso.setMaximum(progresoMax);
        }
        
        if (this.progreso.getValue() != progresoValue) {
            this.progreso.setValue(progresoValue);
        }
    }

    public void enviarAtaque(AtaqueRemoto ataqueRemoto) {
        if (this.activo.isSelected()) {
            try {
                String json = new ObjectMapper().writeValueAsString(ataqueRemoto);
                HttpClient.postRequestJson(this.url.getText() + "api/datosataque/", json);
            } catch (JsonProcessingException | HttpException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void activar() {
        setCompEnabled(true);
        this.update = true;
    }

    public void desactivar() {
        setCompEnabled(false);
        this.update = false;
    }

    private void setCompEnabled(boolean estado) {
        this.activo.setEnabled(estado);
        this.url.setEnabled(estado);
        this.estado.setEnabled(estado);
        this.progreso.setEnabled(estado);
        this.quitar.setEnabled(estado);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        activo = new javax.swing.JCheckBox();
        url = new javax.swing.JTextField();
        estado = new javax.swing.JLabel();
        progreso = new javax.swing.JProgressBar();
        quitar = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createEtchedBorder());
        setMaximumSize(new java.awt.Dimension(32767, 45));

        activo.setSelected(true);
        activo.setToolTipText("");
        activo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                activoActionPerformed(evt);
            }
        });

        url.setText("http://");
        url.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                urlActionPerformed(evt);
            }
        });

        estado.setText("...");

        quitar.setText("-");
        quitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(activo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(url, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(estado, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progreso, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(quitar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(quitar))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(activo)
                            .addComponent(url, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(estado))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(progreso, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void quitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitarActionPerformed
        Container parent = getParent();
        parent.remove(this);
        parent.revalidate();
        parent.repaint();
        this.running = false;
    }//GEN-LAST:event_quitarActionPerformed

    private void urlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_urlActionPerformed
        if (this.apiEstadoCliente == null) {
            this.apiEstadoCliente = new ApiEstadoCliente(this.url.getText() + "api/estadocliente/", true);
        } else {
            apiEstadoCliente.setUrlServidor(this.url.getText() + "api/estadocliente/");
        }
        this.update = true;
    }//GEN-LAST:event_urlActionPerformed

    private void activoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_activoActionPerformed
        this.update = this.activo.isSelected();
    }//GEN-LAST:event_activoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox activo;
    private javax.swing.JLabel estado;
    private javax.swing.JProgressBar progreso;
    private javax.swing.JButton quitar;
    private javax.swing.JTextField url;
    // End of variables declaration//GEN-END:variables
}
