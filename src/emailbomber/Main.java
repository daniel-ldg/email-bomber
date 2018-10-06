package emailbomber;

import emailbomber.bomber.BomberThread;
import gui.Ventana;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            Ventana v = new Ventana();
            v.setVisible(true);
        });
        
        
    }

}
