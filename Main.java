import java.util.Scanner;

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

    }
    static void listarLectores() { 

    }
    static void eliminarLector() { 

    }
    static void registrarPrestamo() { 

    }
    static void listarPrestamos() { 
        
    }
}