import java.util.*;

public class Trabajo3{
    public static void main(String[] args) {
        int[][] grafo = {
            {0, 4, 0, 0, 0, 0, 0, 8, 0},
            {4, 0, 8, 0, 0, 0, 0, 11, 0},
            {0, 8, 0, 7, 0, 4, 0, 0, 2},
            {0, 0, 7, 0, 9, 14, 0, 0, 0},
            {0, 0, 0, 9, 0, 10, 0, 0, 0},
            {0, 0, 4, 14, 10, 0, 2, 0, 0},
            {0, 0, 0, 0, 0, 2, 0, 1, 6},
            {8, 11, 0, 0, 0, 0, 1, 0, 7},
            {0, 0, 2, 0, 0, 0, 6, 7, 0}
        };

        dijkstra(grafo, 0); 
    }

    public static void dijkstra(int[][] grafo, int inicio) {
        int n = grafo.length;
        int[] distancias = new int[n];
        boolean[] visitados = new boolean[n];

        Arrays.fill(distancias, Integer.MAX_VALUE);
        distancias[inicio] = 0;

        for (int i = 0; i < n - 1; i++) {
            int u = nodoMinimo(distancias, visitados);
            visitados[u] = true;

            for (int v = 0; v < n; v++) {
                if (!visitados[v] && grafo[u][v] != 0 &&
                    distancias[u] != Integer.MAX_VALUE &&
                    distancias[u] + grafo[u][v] < distancias[v]) {
                    distancias[v] = distancias[u] + grafo[u][v];
                }
            }
        }

        imprimirDistancias(distancias);
    }

    private static int nodoMinimo(int[] distancias, boolean[] visitados) {
        int min = Integer.MAX_VALUE, minIndex = -1;
        for (int i = 0; i < distancias.length; i++) {
            if (!visitados[i] && distancias[i] <= min) {
                min = distancias[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    private static void imprimirDistancias(int[] distancias) {
        System.out.println("Nodo \t Distancia desde el inicio");
        for (int i = 0; i < distancias.length; i++) {
            System.out.println(i + " \t\t " + distancias[i]);
        }
    }
}
