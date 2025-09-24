import java.util.*;

public class Arbol {

    Nodo raiz;

    private int nodosExpandidos;
    private long tiempoEjecucion;
    private String algoritmoUsado;

    public Arbol(Nodo raiz) {
        this.raiz = raiz;
    }

    public String obtenerMetricas() {
        return algoritmoUsado + " - Tiempo: " + "Nodos Generados: " + getNodosGenerados() + " Nodos expandidos: " + nodosExpandidos;
    }

    // ====================== BFS ======================
    public Nodo realizarBusquedaEnAnchura(String objetivo) {
        Queue<Nodo> cola = new LinkedList<>();
        HashSet<String> visitados = new HashSet<>();
        cola.add(raiz);
        nodosExpandidos = 0;
        long inicio = System.currentTimeMillis();

        while (!cola.isEmpty()) {
            Nodo actual = cola.poll();
            nodosExpandidos++;

            if (actual.estado.equals(objetivo)) {
                tiempoEjecucion = System.currentTimeMillis() - inicio;
                algoritmoUsado = "BFS";
                return actual;
            }

            visitados.add(actual.estado);
            for (String sucesor : actual.obtenerSucesores()) {
                if (!visitados.contains(sucesor)) {
                    cola.add(new Nodo(sucesor, actual));
                    visitados.add(sucesor);
                }
            }
        }
        algoritmoUsado = "BFS";
        return null;
    }

    // ====================== DFS ======================
    public Nodo realizarBusquedaEnProfundidad(String objetivo) {
        Stack<Nodo> frontera = new Stack<>();
        HashSet<String> visitados = new HashSet<>();
        frontera.push(raiz);
        nodosExpandidos = 0;
        long inicio = System.currentTimeMillis();

        while (!frontera.isEmpty()) {
            Nodo actual = frontera.pop();
            nodosExpandidos++;

            if (actual.estado.equals(objetivo)) {
                tiempoEjecucion = System.currentTimeMillis() - inicio;
                algoritmoUsado = "DFS";
                return actual;
            }

            if (visitados.contains(actual.estado)) {
                continue;
            }
            visitados.add(actual.estado);

            for (String sucesor : actual.obtenerSucesores()) {
                if (!visitados.contains(sucesor)) {
                    frontera.push(new Nodo(sucesor, actual));
                }
            }
        }

        tiempoEjecucion = System.currentTimeMillis() - inicio;
        algoritmoUsado = "DFS";
        return null;
    }

    // ====================== UCS ======================
    public Nodo realizarBusquedaCostoUniforme(String objetivo) {
        PriorityQueue<Nodo> frontera = new PriorityQueue<>(Comparator.comparingInt(n -> n.costo));
        HashSet<String> visitados = new HashSet<>();
        raiz.costo = 0;
        frontera.add(raiz);
        nodosExpandidos = 0;
        long inicio = System.currentTimeMillis();

        while (!frontera.isEmpty()) {
            Nodo actual = frontera.poll();
            nodosExpandidos++;

            if (actual.estado.equals(objetivo)) {
                tiempoEjecucion = System.currentTimeMillis() - inicio;
                algoritmoUsado = "UCS";
                return actual;
            }

            if (visitados.contains(actual.estado)) {
                continue;
            }
            visitados.add(actual.estado);

            for (String sucesor : actual.obtenerSucesores()) {
                if (!visitados.contains(sucesor)) {
                    Nodo hijo = new Nodo(sucesor, actual);
                    hijo.costo = actual.costo + 1;
                    frontera.add(hijo);
                }
            }
        }

        tiempoEjecucion = System.currentTimeMillis() - inicio;
        algoritmoUsado = "UCS";
        return null;
    }

    // ====================== Búsqueda Limitada ======================
    public Nodo realizarBusquedaLimitada(String objetivo, int limite) {
        Stack<Nodo> frontera = new Stack<>();
        HashSet<String> visitados = new HashSet<>();
        raiz.profundidad = 0;
        frontera.push(raiz);
        nodosExpandidos = 0;
        long inicio = System.currentTimeMillis();

        while (!frontera.isEmpty()) {
            Nodo actual = frontera.pop();
            nodosExpandidos++;

            if (actual.estado.equals(objetivo)) {
                tiempoEjecucion = System.currentTimeMillis() - inicio;
                algoritmoUsado = "Limitada";
                return actual;
            }

            if (visitados.contains(actual.estado)){
                continue;
            } 
            visitados.add(actual.estado);

            if (actual.profundidad < limite) {
                for (String sucesor : actual.obtenerSucesores()) {
                    if (!visitados.contains(sucesor)) {
                        Nodo hijo = new Nodo(sucesor, actual);
                        hijo.profundidad = actual.profundidad + 1;
                        frontera.push(hijo);
                    }
                }
            }
        }

        tiempoEjecucion = System.currentTimeMillis() - inicio;
        algoritmoUsado = "Limitada";
        return null;
    }

    public Nodo realizarBusquedaAEstrella(String objetivo) {
        PriorityQueue<Nodo> frontera = new PriorityQueue<>(Comparator.comparingInt(n -> n.costo + heuristicaPropia(n.estado, objetivo)));
        HashSet<String> visitados = new HashSet<>();
        raiz.costo = 0;
        frontera.add(raiz);
        nodosExpandidos = 0;
        long inicio = System.currentTimeMillis();

        while (!frontera.isEmpty()) {
            Nodo actual = frontera.poll();
            nodosExpandidos++;

            if (actual.estado.equals(objetivo)) {
                tiempoEjecucion = System.currentTimeMillis() - inicio;
                algoritmoUsado = "A*";
                return actual;
            }

            if (visitados.contains(actual.estado)) {
                continue;
            }
            visitados.add(actual.estado);

            for (String sucesor : actual.obtenerSucesores()) {
                if (!visitados.contains(sucesor)) {
                    Nodo hijo = new Nodo(sucesor, actual);
                    hijo.costo = actual.costo + 1;
                    frontera.add(hijo);
                }
            }
        }

        tiempoEjecucion = System.currentTimeMillis() - inicio;
        algoritmoUsado = "A*";
        return null;
    }

    // ====================== Heurística propia ======================
    private int heuristicaPropia(String estado, String meta) {
        int h = 0;
        for (int i = 0; i < estado.length(); i++) {
            char c = estado.charAt(i);
            if (c != meta.charAt(i) && c != ' ') {
                h++; // pieza fuera de lugar
                int filaEstado = i / 3;
                int filaMeta = meta.indexOf(c) / 3;
                if (filaEstado != filaMeta) h++;
            }
        }
        return h;
    }

    public int getNodosExpandidos() {
        return nodosExpandidos;
    }

    public int getNodosGenerados() {
        return nodosExpandidos + 1; 
    }

    public long getTiempoEjecucion() {
        return tiempoEjecucion;
    }
}
