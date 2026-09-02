import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Prestamo {

    private int idPrestamo;
    private int idLector;
    private String nombreLibro;
    private String fechaPrestamo;

    public Prestamo() {
    }

    public Prestamo(int idPrestamo, int idLector, String nombreLibro, String fechaPrestamo) {
        this.idPrestamo = idPrestamo;
        this.idLector = idLector;
        this.nombreLibro = nombreLibro;
        this.fechaPrestamo = fechaPrestamo;
    }

    public int getIdPrestamo() { return idPrestamo; }
    public int getIdLector() { return idLector; }
    public String getNombreLibro() { return nombreLibro; }
    public String getFechaPrestamo() { return fechaPrestamo; }

    public void crearPrestamo(Prestamo p) throws IOException {
        File archivo = new File("prestamos.csv");
        boolean existe = archivo.exists();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("prestamos.csv", true))) {
            if (!existe) {
                bw.write("id_prestamo,id_lector,libro,fecha_prestamo");
                bw.newLine();
            }
            bw.write(p.getIdPrestamo() + "," + p.getIdLector() + "," + p.getNombreLibro() + "," + p.getFechaPrestamo()); 
            bw.newLine();
        }
        actualizarIndicePrestamos();
    }

    public List<Prestamo> leerPrestamos() throws IOException {
        List<Prestamo> lista = new ArrayList<>();
        File archivo = new File("prestamos.csv");

        if (!archivo.exists()) {
            return lista;
        }

        try (BufferedReader br = new BufferedReader(new FileReader("prestamos.csv"))) {
            String linea = br.readLine(); // Encabezado
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] datos = linea.split(",");
                if (datos.length >= 4) {
                    int idP = Integer.parseInt(datos[0].trim());
                    int idL = Integer.parseInt(datos[1].trim());
                    String libro = datos[2].trim();
                    String fechaP = datos[3].trim();
                    lista.add(new Prestamo(idP, idL, libro, fechaP));
                }
            }
        }
        return lista;
    }

    public boolean tienePrestamosAsociados(int idLector) throws IOException {
        List<Prestamo> lista = leerPrestamos();
        for (Prestamo p : lista) {
            if (p.getIdLector() == idLector) {
                return true;
            }
        }
        return false;
    }

    private void actualizarIndicePrestamos() throws IOException {
        File archivoCsv = new File("prestamos.csv");
        if (!archivoCsv.exists()) return;

        try (java.io.RandomAccessFile raf = new java.io.RandomAccessFile(archivoCsv, "r");
             BufferedWriter bwIdx = new BufferedWriter(new FileWriter("prestamos.idx", false))) {

            String linea = raf.readLine();
            long posicionByte;

            while ((linea = raf.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                posicionByte = raf.getFilePointer() - (linea.getBytes().length + System.lineSeparator().getBytes().length);
                String[] datos = linea.split(",");
                if (datos.length > 0) {
                    int id = Integer.parseInt(datos[0].trim());
                    bwIdx.write(id + "," + posicionByte);
                    bwIdx.newLine();
                }
            }
        }
    }
}
