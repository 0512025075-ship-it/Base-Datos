import javax.swing.SwingUtilities;

public class Principal {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaProducto ventana = new VentanaProducto();
            ventana.setVisible(true);
        });
    }
}
