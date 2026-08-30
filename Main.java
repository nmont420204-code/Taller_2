import java.util.Scanner;
import java.util.List;
public class Main {

    static Scanner leer = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            int opcion;
            do {
                mostrarMenu();
                opcion = leerEntero("Seleccione una opcion: ");

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
                        System.out.println("Cerrando el programa...");
                        break;
                    default:
                        System.out.println("Opción inválida, intente nuevamente.\n");
                }
            } while (opcion != 6);

        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado: " + e.getMessage());
        }
    }

    static void mostrarMenu() {
        System.out.println("Gestion Biblioteca");
        System.out.println("1. Registrar nuevo lector");
        System.out.println("2. Mostrar todos los lectores");
        System.out.println("3. Eliminar lector");
        System.out.println("4. Registrar préstamo");
        System.out.println("5. Listar préstamos");
        System.out.println("6. Salir");
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

    }

    static void registrarPrestamo() {

    }

    static void listarPrestamos() {

    }
}