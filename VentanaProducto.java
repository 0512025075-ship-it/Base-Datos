import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class VentanaProducto extends JFrame {

    private final JTextField txtNombre, txtPrecio, txtStock;
  
    private final JButton btnGuardar, btnListar, btnEliminar, btnExportar;
    
    private final JTable tablaProductos;
    private final DefaultTableModel modelo;

    private final ProductoDAO productoDAO;
    private final GestorArchivo gestorArchivo;

    //Paleta de colores suaves
    private static final Color AZUL_TITULO   = new Color(41, 98, 158);
    private static final Color FONDO_GENERAL = new Color(238, 242, 247);
    private static final Color FONDO_FORM    = new Color(223, 233, 246);
    private static final Color FONDO_BOTONES = new Color(232, 238, 245);

    private static final Color VERDE_GUARDAR   = new Color(104, 173, 133);
    private static final Color AZUL_LISTAR     = new Color(107, 150, 201);
    private static final Color ROJO_ELIMINAR   = new Color(206, 118, 118);
    private static final Color MORADO_EXPORTAR = new Color(150, 130, 190);

    public VentanaProducto() {
        productoDAO = new ProductoDAO();
        gestorArchivo = new GestorArchivo();

        txtNombre = new JTextField(15);
        txtPrecio = new JTextField(15);
        txtStock = new JTextField(15);

        btnGuardar = new JButton("Guardar");
        btnListar = new JButton("Listar");
        btnEliminar = new JButton("Eliminar seleccionado");
        btnExportar = new JButton("Exportar a archivo");

        modelo = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Precio", "Stock"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tablaProductos = new JTable(modelo);
        tablaProductos.setRowHeight(26);
        tablaProductos.setSelectionBackground(new Color(197, 217, 240));
        tablaProductos.setGridColor(new Color(220, 224, 230));
        tablaProductos.setFont(new Font("SansSerif", Font.PLAIN, 13));

        configurarVentana();
        organizarComponentes();
        registrarEventos();
        listarProductos();
    }

    //Parte Frank
