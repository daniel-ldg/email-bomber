package emailbomber;

import emailbomber.network.ApiEstadoCliente;
import emailbomber.gui.Ventana;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            Ventana v = new Ventana();
            v.setLocationRelativeTo(null);
            v.setVisible(true);
        });
    }

}
