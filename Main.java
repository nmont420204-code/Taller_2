import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner leer = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion=1;
        try {
            do {
                opcion=mostrarMenu();
                switch (opcion) {
                    case 1:
                        registrarLector();
                        break;
                    case 2:
                        listarLectores();
                        break;
                    case 3:
                        eliminarLector();
                        break;
                    case 4:
                        registrarPrestamo();
                        break;
                    case 5:
                        listarPrestamos();
                        break;
                    case 6:
                        mostrarConsultas();
                        break;
                    case 7:
                        devolucionLibros();
                    case 8:
                        System.out.println("Cerrando el programa...");
                        break;
                    default:
                        System.out.println("Opción inválida, intente nuevamente.\n");
                }
            } while (opcion != 8);

        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado: " + e.getMessage());
        }
    }

    static void historialLector(){

    }
    static void mayorCantidad(){

    }
    static void librosPrestados(){

    }
    static void prestamosActivos(){

    }
    static void prestamosVencidos(){

    }
    static void mostrarConsultas() {
        int opcion_consultas = 0; // Se declara fuera del do para usarla en el while
        try {
            do {
                System.out.println("---- Consultas ----");
                System.out.println("1. Historial completo de un lector");
                System.out.println("2. Lectores con mayor cantidad de préstamos");
                System.out.println("3. Libros actualmente prestados");
                System.out.println("4. Generar reporte de lectores con préstamos activos");
                System.out.println("5. Generar reporte de préstamos vencidos");
                System.out.println("6. Volver al menú principal");

                opcion_consultas = leerEntero("Seleccione una opcion: ");

                switch (opcion_consultas) {
                    case 1:
                        historialLector(); // Llamada a los métodos correctos
                        break;
                    case 2:
                        mayorCantidad();
                        break;
                    case 3:
                        librosPrestados();
                        break;
                    case 4:
                        prestamosActivos();
                        break;
                    case 5:
                        prestamosVencidos();
                        break;
                    case 6:
                        System.out.println("Regresando al menú principal...\n");
                        break;
                    default:
                        System.out.println("Opción inválida, intente nuevamente.\n");
                }
            } while (opcion_consultas != 6);

        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado en consultas: " + e.getMessage());
        }
    }

    static void devolucionLibros(){

    }

    static int mostrarMenu() {
        System.out.println("\n---- Menú Principal ----");
        System.out.println("1. Registrar lector");
        System.out.println("2. Listar lectores");
        System.out.println("3. Eliminar lector");
        System.out.println("4. Registrar préstamo");
        System.out.println("5. Listar préstamos");
        System.out.println("6. Consultas");
        System.out.println("7. Devolución de libros");
        System.out.println("8. Salir");
        return leerEntero("Seleccione una opción: ");
    }

    static int leerEntero(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Integer.parseInt(leer.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Ingrese un número entero válido.");
            }
        }
    }

    static void registrarLector() {
        try {
            System.out.println("----Registrar Lectores---");
            System.out.print("Ingrese su nombre: ");
            String nombre = leer.nextLine();

            System.out.print("Ingrese su apellido: ");
            String apellido = leer.nextLine();

            System.out.print("Ingrese su teléfono: ");
            String telefono = leer.nextLine();

            Usuario gestor = new Usuario();
            List<Usuario> listaActual = gestor.leerUsuarios();

            int Id = 1;
            if (!listaActual.isEmpty()) {
                Id = listaActual.get(listaActual.size() - 1).getId() + 1;
            }

            Usuario nuevoLector = new Usuario(Id, nombre, apellido, telefono);
            gestor.crearUsuario(nuevoLector);

            System.out.println("Lector registrado correctamente.");
            System.out.println("Se le asigno el ID: " + Id + "\n");

        } catch (IllegalArgumentException e) {
            System.out.println("Error de validación: " + e.getMessage() + "\n");
        } catch (Exception e) {
            System.out.println("Error al guardar el lector: " + e.getMessage() + "\n");
        }
    }

    static void listarLectores() {
        try {
            System.out.println("\n--- Lista de Lectores ---");

            Usuario gestor = new Usuario();
            List<Usuario> lectores = gestor.leerUsuarios();

            if (lectores.isEmpty()) {
                System.out.println("No hay lectores registrados en el sistema.\n");
                return;
            }

            for (Usuario lector : lectores) {
                System.out.println("ID: " + lector.getId() +
                        " Nombre: " + lector.getName() + " " + lector.getLastname() +
                        " Teléfono: " + lector.getphone());
            }
            System.out.println();

        } catch (Exception e) {
            System.out.println("Error al leer la lista de lectores: " + e.getMessage() + "\n");
        }

    }

    static void eliminarLector() {
        try {
            System.out.println("\n--- Eliminar Lector ---");
            int idEliminar = leerEntero("Ingrese el ID del lector a eliminar: ");

            Usuario gestorUsuario = new Usuario();
            List<Usuario> lectores = gestorUsuario.leerUsuarios();

            // 1. Verificar si el lector existe
            boolean existeLector = false;
            for (Usuario u : lectores) {
                if (u.getId() == idEliminar) {
                    existeLector = true;
                    break;
                }
            }

            if (!existeLector) {
                System.out.println("Error: El lector con ID " + idEliminar + " no existe.\n");
                return;
            }

            // 2. Verificar si tiene préstamos asociados
            Prestamo gestorPrestamo = new Prestamo();
            if (gestorPrestamo.tienePrestamosAsociados(idEliminar)) {
                System.out.println("Error: El lector tiene préstamos asociados y no puede ser eliminado.\n");
                return;
            }

            // 3. Eliminar físicamente y actualizar índices
            gestorUsuario.eliminarUsuario(idEliminar);
            System.out.println("Lector eliminado físicamente y archivo de índices actualizado.\n");

        } catch (Exception e) {
            System.out.println("Error al eliminar el lector: " + e.getMessage() + "\n");
        }
    }
    static void registrarPrestamo() {
        try {
            System.out.println("\n--- Registrar Préstamo ---");
            int idLector = leerEntero("Ingrese el ID del lector: ");

            Usuario gestorUsuario = new Usuario();
            List<Usuario> lectores = gestorUsuario.leerUsuarios();

            // Validar si el lector existe
            boolean existeLector = false;
            for (Usuario u : lectores) {
                if (u.getId() == idLector) {
                    existeLector = true;
                    break;
                }
            }

            if (!existeLector) {
                System.out.println("Error: No se puede registrar el préstamo. El lector con ID " + idLector + " no existe.\n");
                return;
            }

            System.out.print("Ingrese el nombre del libro: ");
            String libro = leer.nextLine();

            System.out.print("Ingrese la fecha del préstamo (YYYY-MM-DD): ");
            String fechaP = leer.nextLine();

            // Autoincrementar ID de préstamo
            Prestamo gestorPrestamo = new Prestamo();
            List<Prestamo> listaPrestamos = gestorPrestamo.leerPrestamos();
            int idPrestamo = 1;
            if (!listaPrestamos.isEmpty()) {
                idPrestamo = listaPrestamos.get(listaPrestamos.size() - 1).getIdPrestamo() + 1;
            }

            Prestamo nuevoPrestamo = new Prestamo(idPrestamo, idLector, libro, fechaP );
            gestorPrestamo.crearPrestamo(nuevoPrestamo);

            System.out.println("Préstamo registrado exitosamente con ID: " + idPrestamo + "\n");

        } catch (Exception e) {
            System.out.println("Error al registrar el préstamo: " + e.getMessage() + "\n");
        }
    }

    static void listarPrestamos() {
        try {
            System.out.println("\n--- Consultar Préstamos de Lector ---");
            int idLector = leerEntero("Ingrese el ID del lector: ");

            // Validar si el lector existe
            Usuario gestorUsuario = new Usuario();
            List<Usuario> lectores = gestorUsuario.leerUsuarios();
            boolean existeLector = false;

            for (Usuario u : lectores) {
                if (u.getId() == idLector) {
                    existeLector = true;
                    break;
                }
            }

            if (!existeLector) {
                System.out.println("Error: El lector con ID " + idLector + " no existe.\n");
                return;
            }

            // Consultar préstamos
            Prestamo gestorPrestamo = new Prestamo();
            List<Prestamo> todos = gestorPrestamo.leerPrestamos();
            List<Prestamo> delLector = new ArrayList<>();

            for (Prestamo p : todos) {
                if (p.getIdLector() == idLector) {
                    delLector.add(p);
                }
            }

            if (delLector.isEmpty()) {
                System.out.println("El lector existe pero no tiene préstamos registrados.\n");
                return;
            }

            System.out.println("Préstamos asociados al lector " + idLector + ":");
            for (Prestamo p : delLector) {
                System.out.println("ID Préstamo: " + p.getIdPrestamo() +
                        " | Libro: " + p.getNombreLibro() +
                        " | Fecha Préstamo: " + p.getFechaPrestamo());
            }
            System.out.println();

        } catch (Exception e) {
            System.out.println("Error al listar préstamos: " + e.getMessage() + "\n");
        }
    }
}