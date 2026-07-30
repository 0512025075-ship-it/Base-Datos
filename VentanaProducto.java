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

    private void configurarVentana() {
        setTitle("Sistema de Gestion de Productos");
        setSize(840, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(FONDO_GENERAL);
    }

    private void organizarComponentes() {
        // Encabezado
        JLabel titulo = new JLabel("Gestion de Productos", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setForeground(AZUL_TITULO);
        titulo.setBorder(new EmptyBorder(18, 10, 8, 10));

        //Formulario 
        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 10, 10));
        panelFormulario.setBackground(FONDO_FORM);
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(AZUL_TITULO, 1),
                        "Datos del producto"),
                new EmptyBorder(12, 18, 12, 18)));

        JLabel lblNombre = crearEtiqueta("Nombre:");
        JLabel lblPrecio = crearEtiqueta("Precio:");
        JLabel lblStock = crearEtiqueta("Stock:");

        estilizarCampo(txtNombre);
        estilizarCampo(txtPrecio);
        estilizarCampo(txtStock);

        panelFormulario.add(lblNombre);
        panelFormulario.add(txtNombre);
        panelFormulario.add(lblPrecio);
        panelFormulario.add(txtPrecio);
        panelFormulario.add(lblStock);
        panelFormulario.add(txtStock);

        //Botones
        estilizarBoton(btnGuardar, VERDE_GUARDAR);
        estilizarBoton(btnListar, AZUL_LISTAR);
        estilizarBoton(btnEliminar, ROJO_ELIMINAR);
        estilizarBoton(btnExportar, MORADO_EXPORTAR);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 12));
        panelBotones.setBackground(FONDO_BOTONES);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnListar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnExportar);

        //Panel superior
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(FONDO_GENERAL);
        panelSuperior.add(titulo, BorderLayout.NORTH);
        panelSuperior.add(panelFormulario, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        //Scroll 
        JTableHeader encabezadoTabla = tablaProductos.getTableHeader();
        encabezadoTabla.setBackground(AZUL_TITULO);
        encabezadoTabla.setForeground(Color.WHITE);
        encabezadoTabla.setFont(new Font("SansSerif", Font.BOLD, 13));

        JScrollPane desplazamiento = new JScrollPane(tablaProductos);
        desplazamiento.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(8, 12, 12, 12),
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(AZUL_TITULO, 1),
                        "Productos registrados")));
        desplazamiento.getViewport().setBackground(Color.WHITE);

        //Ventana principal
        setLayout(new BorderLayout());
        add(panelSuperior, BorderLayout.NORTH);
        add(desplazamiento, BorderLayout.CENTER);
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("SansSerif", Font.BOLD, 13));
        etiqueta.setForeground(new Color(60, 70, 90));
        return etiqueta;
    }

    private void estilizarCampo(JTextField campo) {
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 195, 215), 1),
                new EmptyBorder(4, 6, 4, 6)));
        campo.setBackground(Color.WHITE);
    }

    private void estilizarBoton(JButton boton, Color color) {
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("SansSerif", Font.BOLD, 12));
        boton.setFocusPainted(false);
        boton.setOpaque(true);
        boton.setBorderPainted(false);
        boton.setBorder(new EmptyBorder(8, 16, 8, 16));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void registrarEventos() {
        btnGuardar.addActionListener(e -> guardarProducto());
        btnListar.addActionListener(e -> listarProductos());
        btnEliminar.addActionListener(e -> eliminarSeleccionado());
        btnExportar.addActionListener(e -> exportarReporte());
    }

    private void guardarProducto() {
        String nombre = txtNombre.getText().trim();
        String precioTexto = txtPrecio.getText().trim();
        String stockTexto = txtStock.getText().trim();

        if (nombre.isEmpty() || precioTexto.isEmpty() || stockTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Complete todos los campos.",
                    "Validacion",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        double precio;
        int stock;
        try {
            precio = Double.parseDouble(precioTexto);
            stock = Integer.parseInt(stockTexto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Precio debe ser numerico y Stock debe ser un entero.",
                    "Validacion",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Producto producto = new Producto(nombre, precio, stock);
        boolean registrado = productoDAO.registrar(producto);

        if (registrado) {
            JOptionPane.showMessageDialog(this, "Producto registrado.");
            limpiarCampos();
            listarProductos();
        } else {
            JOptionPane.showMessageDialog(this,
                    "No fue posible registrar el producto.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarProductos() {
        modelo.setRowCount(0);
        List<Producto> productos = productoDAO.listar();
        for (Producto producto : productos) {
            modelo.addRow(new Object[]{
                producto.getId(),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getStock()
            });
        }
    }

    private void eliminarSeleccionado() {
        int fila = tablaProductos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona una fila de la tabla para eliminar.",
                    "Validacion",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modelo.getValueAt(fila, 0);
        boolean eliminado = productoDAO.eliminar(id);

        if (eliminado) {
            JOptionPane.showMessageDialog(this, "Producto eliminado.");
            listarProductos();
        } else {
            JOptionPane.showMessageDialog(this,
                    "No fue posible eliminar el producto.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportarReporte() {
        List<Producto> productos = productoDAO.listar();

        if (productos.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay productos para exportar.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser selector = new JFileChooser();
        selector.setSelectedFile(new java.io.File("reporte_productos.txt"));
        int opcion = selector.showSaveDialog(this);

        if (opcion == JFileChooser.APPROVE_OPTION) {
            String ruta = selector.getSelectedFile().getAbsolutePath();
            boolean exportado = gestorArchivo.exportarReporte(productos, ruta);

            if (exportado) {
                JOptionPane.showMessageDialog(this, "Reporte guardado en:\n" + ruta);
            } else {
                JOptionPane.showMessageDialog(this,
                        "No fue posible generar el archivo.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
        txtNombre.requestFocus();
    }
}
