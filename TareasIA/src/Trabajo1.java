class Nodo {
    String Centro;
    Nodo Izquierda;
    Nodo Derecha;

    public Nodo(String Centro) {
        this.Centro = Centro;
        this.Izquierda = null;
        this.Derecha = null;
    }
}

class Arbol {
    Nodo raiz;

    public Arbol() {
        this.raiz = null;
    }

    public boolean vacio() {
        return raiz == null;
    }

    public void insertar(String Centro) {
        raiz = insertarRec(raiz, Centro);
    }

    private Nodo insertarRec(Nodo nodo, String Centro) {
        if (nodo == null) {
            return new Nodo(Centro);
        }
        if (Centro.compareTo(nodo.Centro) < 0) {
            nodo.Izquierda = insertarRec(nodo.Izquierda, Centro);
        } else {
            nodo.Derecha = insertarRec(nodo.Derecha, Centro);
        }
        return nodo;
    }

    public Nodo buscarNodo(String Centro) {
        return buscarRec(raiz, Centro);
    }

    private Nodo buscarRec(Nodo nodo, String Centro) {
        if (nodo == null || nodo.Centro.equals(Centro)) {
            return nodo;
        }
        if (Centro.compareTo(nodo.Centro) < 0) {
            return buscarRec(nodo.Izquierda, Centro);
        } else {
            return buscarRec(nodo.Derecha, Centro);
        }
    }

    public void imprimirArbol() {
        imprimirRec(raiz);
        System.out.println();
    }

    private void imprimirRec(Nodo nodo) {
        if (nodo != null) {
            imprimirRec(nodo.Izquierda);
            System.out.print(nodo.Centro + " ");
            imprimirRec(nodo.Derecha);
        }
    }
}

public class Trabajo1 {
    public static void main(String[] args) {
        Arbol arbol = new Arbol();

        arbol.insertar("Jupiter");
        arbol.insertar("Saturno");
        arbol.insertar("Tierra");
        arbol.insertar("Pluton");
        arbol.insertar("Mercurio");

        System.out.print("Árbol en orden: ");
        arbol.imprimirArbol();

        String nombreBuscar = "Pluton";
        Nodo encontrado = arbol.buscarNodo(nombreBuscar);
        if (encontrado != null) {
            System.out.println("Nodo '" + nombreBuscar + "' encontrado.");
        } else {
            System.out.println("Nodo '" + nombreBuscar + "' no encontrado.");
        }

        System.out.println("Arbol Vacio? " + arbol.vacio());
    }
}
