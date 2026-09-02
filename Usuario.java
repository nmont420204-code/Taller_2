import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Usuario {
    private int id;
    private String name;
    private String lastname;
    public String phone;

    public Usuario() {
    }

    public Usuario(int id, String name, String lastname, String phone) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!Character.isUpperCase(name.charAt(0)))
            throw new IllegalArgumentException("Debe digitar el nombre con la primera letra mayuscula.");
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        if (!Character.isUpperCase(lastname.charAt(0)))
            throw new IllegalArgumentException("Debe digitar el apellido con la primera letra mayuscula.");
        this.lastname = lastname;
    }

    public String getphone() {
        return phone;
    }

    public void setphone(String phone) {
        this.phone = phone;
    }

    public void crearUsuario(Usuario usuario) throws IOException {
        File archivo = new File("lectores.csv");
        boolean esNuevo = !archivo.exists() || archivo.length() == 0;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            // Escribir cabecera si el archivo está vacío o no existe
            if (esNuevo) {
                bw.write("id_lector,nombre,apellido,telefono");
                bw.newLine();
            }

            // Escribir los datos formateados en CSV
            String linea = usuario.getId() + "," + 
                           usuario.getName() + "," + 
                           usuario.getLastname() + "," + 
                           usuario.getphone();
                           
            bw.write(linea);
            bw.newLine();
        }

        // Actualizar el índice al crear
        actualizarIndiceLectores();
    }

    public List<Usuario> leerUsuarios() throws IOException {
        List<Usuario> lista = new ArrayList<>();
        File archivo = new File("lectores.csv");

        if (!archivo.exists()) {
            return lista;
        }

        try (Scanner sc = new Scanner(archivo)) {
            if (sc.hasNextLine()) {
                sc.nextLine(); // Ignorar la cabecera
            }

            while (sc.hasNextLine()) {
                String linea = sc.nextLine().trim();
                if (!linea.isEmpty()) {
                    String[] datos = linea.split(",");
                    if (datos.length >= 4) {
                        lista.add(new Usuario(
                                Integer.parseInt(datos[0].trim()),
                                datos[1].trim(),
                                datos[2].trim(),
                                datos[3].trim()));
                    }
                }
            }
        }
        return lista;
    }

    public void actualizarUsuario(int id, String nuevoNombre, String nuevoLastname) throws IOException {
        List<Usuario> lista = this.leerUsuarios();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("lectores.csv", false))) {
            bw.write("id_lector,nombre,apellido,telefono");
            bw.newLine();

            for (Usuario u : lista) {
                if (u.getId() == id) {
                    u.setName(nuevoNombre);
                    u.setLastname(nuevoLastname);
                }
                bw.write(u.getId() + "," + u.getName() + "," + u.getLastname() + "," + u.getphone());
                bw.newLine();
            }
        }
        actualizarIndiceLectores();
    }

    public void eliminarUsuario(int id) throws IOException {
        List<Usuario> lista = this.leerUsuarios();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("lectores.csv", false))) {
            bw.write("id_lector,nombre,apellido,telefono");
            bw.newLine();

            for (Usuario u : lista) {
                if (u.getId() != id) {
                    bw.write(u.getId() + "," + u.getName() + "," + u.getLastname() + "," + u.getphone());
                    bw.newLine();
                }
            }
        }

        actualizarIndiceLectores();
    }

    private void actualizarIndiceLectores() throws IOException {
        File archivoCsv = new File("lectores.csv");
        if (!archivoCsv.exists()) return;

        try (java.io.RandomAccessFile raf = new java.io.RandomAccessFile(archivoCsv, "r");
             BufferedWriter bwIdx = new BufferedWriter(new FileWriter("lectores.idx", false))) {

            String linea = raf.readLine(); // Descartar cabecera
            long posicionByte;

            while ((linea = raf.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                posicionByte = raf.getFilePointer() - (linea.getBytes().length + System.lineSeparator().getBytes().length);
                String[] datos = linea.split(",");
                if (datos.length > 0) {
                    int idLector = Integer.parseInt(datos[0].trim());
                    bwIdx.write(idLector + "," + posicionByte);
                    bwIdx.newLine();
                }
            }
        }
    }

    @Override
    public String toString() {
        return id + "," + name + "," + lastname + "," + phone;
    }
}