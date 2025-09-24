import java.util.*;

public class Puzzle {

    public static void main(String[] args) {
        String estadoInicial = "125 43768";
        String estadoFinal = " 12345678";

        Scanner c = new Scanner(System.in);
        System.out.println("Selecciona el algoritmo de búsqueda:");
        System.out.println("1. BFS (Anchura)");
        System.out.println("2. DFS (Profundidad)");
        System.out.println("3. UCS (Costo Uniforme)");
        System.out.println("4. Limitada");
        System.out.println("5. A*");
        int opcion = c.nextInt();

        Arbol arbol = new Arbol(new Nodo(estadoInicial, null));
        Nodo resultado = null;

        switch (opcion) {
            case 1:
                resultado = arbol.realizarBusquedaEnAnchura(estadoFinal);
                break;
            case 2:
                resultado = arbol.realizarBusquedaEnProfundidad(estadoFinal);
                break;
            case 3:
                resultado = arbol.realizarBusquedaCostoUniforme(estadoFinal);
                break;
            case 4:
                System.out.println("Introduce límite de profundidad:");
                int limite = c.nextInt();
                resultado = arbol.realizarBusquedaLimitada(estadoFinal, limite);
                break;
            case 5:
                resultado = arbol.realizarBusquedaAEstrella(estadoFinal);
                break;
            default:
                System.out.println("Opción inválida.");
                return;
        }

        if (resultado == null) {
            System.out.println("No se encontró solución.");
            return;
        }

        Stack<Nodo> pilaCamino = new Stack<>();
        Nodo actual = resultado;
        while (actual != null) {
            pilaCamino.push(actual);
            actual = actual.padre;
        }

        System.out.println("\n============== Camino de solución ==============");
        int pasoCont = 1;
        while (!pilaCamino.isEmpty()) {
            Nodo paso = pilaCamino.pop();
            System.out.println("Paso #" + pasoCont);
            imprimirEstado(paso.estado);
            System.out.println();
            pasoCont++;
        }

        System.out.println("============== Métricas ==============");
        System.out.println(arbol.obtenerMetricas());
        System.out.println("Profundidad de la solución: " + resultado.profundidad);
    }

    private static void imprimirEstado(String estado) {
        for (int i = 0; i < estado.length(); i++) {
            System.out.print(estado.charAt(i) + " ");
            if ((i + 1) % 3 == 0) System.out.println();
        }
    }
}
