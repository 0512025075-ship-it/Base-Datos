import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class GestorArchivo {

    public boolean exportarReporte(List<Producto> productos, String rutaArchivo) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaArchivo))) {

            escritor.write("REPORTE DE PRODUCTOS");
            escritor.newLine();
            escritor.write("=====================================");
            escritor.newLine();

            for (Producto producto : productos) {
                escritor.write(String.format(
                        "ID: %d | Nombre: %s | Precio: S/ %.2f | Stock: %d",
                        producto.getId(),
                        producto.getNombre(),
                        producto.getPrecio(),
                        producto.getStock()));
                escritor.newLine();
            }

            escritor.write("=====================================");
            escritor.newLine();
            escritor.write("Total de productos: " + productos.size());

            return true;

        } catch (IOException e) {
            System.err.println("Error al escribir el archivo: " + e.getMessage());
            return false;
        }
    }
}
