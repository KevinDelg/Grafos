/*Clase que implementa modelos de grafos.

La clase forma parte del paquete 'Grafos'. Implementa cuatro modelos para
generar grafos aleatorios:
1. Erdös-Rényi
2. Gilbert
3. Geográfico simple
4. Barabási-Albert

La clase utiliza la clase 'Vertice' del mismo paquete, que incluye
constructores para el modelo geográfico simple, que necesita un par de
coordenadas, y otro para los otros tres modelos.

También utiliza la clase 'Arista' necesaria para poder generar Grafos
con pesos en las aristas.

Tiene implementados los algoritmos de DFS (iterativo y recursivo) y BFS.
Los métodos regresan un grafo con el árbol de expansión mínima.

Tiene implementado el algoritmo de Dijkstra para encontrar el camino
más corto en un grafo con pesos postivos en las aristas desde un nodo
fuente hasta el resto de los nodos. El método regresa un grafo que es
el árbol con el camino más corto.
}*/
package Grafos;

import java.util.*;
import java.io.FileNotFoundException;

public class Grafo {
    
    private Vertice[] nodes;
    private HashMap<Vertice, HashSet<Vertice>> graph;
    private HashMap<Vertice, HashSet<Arista>> dijkstraHash;
    private int edgesNum;
    private static Formatter output;
    private Boolean weighted;
    private final int vertexNum;



    public Grafo(int n) {
        dijkstraHash = new HashMap<>();
        graph = new HashMap<>();
        vertexNum = n;
        nodes = new Vertice[n];

        for (int i = 0; i < n; i++) {
            Vertice vertex = new Vertice(i);
            nodes[i] = vertex;
            graph.put(vertex, new HashSet<>());
            dijkstraHash.put(vertex, new HashSet<>());
        }

        weighted = false;
    }

    public Grafo(int n, String modelo) {
        dijkstraHash = new HashMap<>();
        graph = new HashMap<>();
        vertexNum = n;
        nodes = new Vertice[n];

        Random coordX = new Random();
        Random coordY = new Random();

        if (modelo.equals("geo-uniforme")) {
            for (int i = 0; i < n; i++) {
                Vertice vertex = new Vertice(i, coordX.nextDouble(), coordY.nextDouble());
                nodes[i] = vertex;
                graph.put(vertex, new HashSet<>());
                dijkstraHash.put(vertex, new HashSet<>());
            }
        }

        weighted = false;
    }

    private int getGradoVertice(int i) {
        return graph.get(getNode(i)).size();
    }

    private void conectarVertices(int i, int j) {
        Vertice vtx1 = getNode(i);
        Vertice vtx2 = getNode(j);
        HashSet<Vertice> edg1 = getEdge(i);
        HashSet<Vertice> edg2 = getEdge(j);
        edg1.add(vtx2);
        edg2.add(vtx1);
        this.edgesNum += 1;
    }

    private Boolean hayConexion(int i, int j) {
        Vertice vtx1 = getNode(i);
        Vertice vtx2 = getNode(j);
        HashSet<Vertice> edg1 = getEdge(i);
        HashSet<Vertice> edg2 = getEdge(j);

        return edg1.contains(vtx2) || edg2.contains(vtx1);
    }

    private double distanciaVertices(Vertice vtx1, Vertice vtx2) {
        return Math.sqrt(Math.pow((vtx1.getX() - vtx2.getX()), 2)
                + Math.pow((vtx1.getY() - vtx2.getY()), 2));
    }

    public int getNumNodes() {
        return vertexNum;
    }

    public int getNumEdges() {
        return edgesNum;
    }

    public Vertice getNode(int i) {
        return nodes[i];
    }

    public Boolean isWeighted() {
        return weighted;
    }

    public HashSet<Vertice> getEdge(int i) {
        return graph.get(getNode(i));
    }

    public HashSet<Arista> getWeightedEdges(int i) {
        return dijkstraHash.get(getNode(i));
    }

    public void setWeighted() {
        weighted = true;
    }

    public void setIncidencia(int i, HashSet<Arista> aristas) {
        dijkstraHash.put(getNode(i), aristas);
    }

    public void setAristaPeso(int i, int j, double peso) {
        if (!hayConexion(i, j)) { conectarVertices(i, j); }

        Arista edge1 = new Arista(i, j, peso);
        Arista edge2 = new Arista(j, i, peso);

        HashSet<Arista> edges_i = getWeightedEdges(i);
        HashSet<Arista> edges_j = getWeightedEdges(j);

        edges_i.add(edge1);
        edges_j.add(edge2);

        setIncidencia(i, edges_i);
        setIncidencia(j, edges_j);

        if (!isWeighted()) { setWeighted(); }
    }

    public String toString() {
        String cadena;

        if (isWeighted()) {
            cadena = "graph {\n";
            for (int i = 0; i < getNumNodes(); i++) {
                cadena += getNode(i).getName() + " [label=\""
                        + getNode(i).getName() + " (" + getNode(i).getDistance()
                        + ")\"];\n";
            }

            for (int i = 0; i < getNumNodes(); i++) {
                HashSet<Arista> edges = getWeightedEdges(i);
                for (Arista e : edges) {
                    cadena += e.getNode1() + " -- " + e.getNode2()
                            + " [weight=" + e.getWeight() + "" + " label=" + e.getWeight() + ""
                            + "];\n";
                }
            }

            cadena += "}\n";

        } else {
            cadena = "graph {\n";
            for (int i = 0; i < getNumNodes(); i++) {
                cadena += getNode(i).getName() + ";\n";
            }

            for (int i = 0; i < getNumNodes(); i++) {
                HashSet<Vertice> edges = getEdge(i);
                for (Vertice n : edges) {
                    cadena += getNode(i).getName() + " -- " + n.getName() + ";\n";
                }
            }

            cadena += "}\n";
        }

        return cadena;
    }


    public void modeloER(int n) {
        Random rand1 = new Random();
        Random rand2 = new Random();

        while (getNumEdges() < n) {
            int num1 = rand1.nextInt(getNumNodes());
            int num2 = rand2.nextInt(getNumNodes());
            if (num1 != num2) {
                if (!hayConexion(num1, num2)) {
                    conectarVertices(num1, num2);
                }
            }
        }
    }

    public void modeloGilbert(double probabilidad) {
        Random rand = new Random();
        for (int i = 0; i < getNumNodes(); i++) {
            for (int j = 0; j < getNumNodes(); j++) {
                if ((i != j) && (rand.nextDouble() <= probabilidad)) {
                    if (!hayConexion(i, j)) {
                        conectarVertices(i, j);
                    }
                }
            }
        }
    }

    public void modeloGeoSimple(double r) {
        for (int i = 0; i < getNumNodes(); i++) {
            for (int j = i + 1; j < getNumNodes(); j++) {
                double distancia = distanciaVertices(getNode(i), getNode(j));
                if (distancia <= r) {
                    conectarVertices(i, j);
                }
            }
        }
    }

    public void modeloBA(int d) {
        Random rand = new Random();
        double p;
        for (int i = 0; i < this.getNumNodes();i++) {
            for (int j = (i+1); j < this.getNumNodes(); j++) {

                p = 1 - (getGradoVertice(i)/d);

                if (0 < p) {
                    if ((!hayConexion(i,j))){
                        conectarVertices(i, j);
                    }
                }
            }

            if (getGradoVertice(i) >= d) { i++; }
        }
    }

    public void escribirArchivo(String file) {
        try {
            output = new Formatter(file);

        } catch (SecurityException securityException) {
            System.err.println("Sin permisos de archivo.");
            System.exit(1);

        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println("Error: No se pudo abrir archivo.");
            System.exit(1);
        }


        try {
            output.format("%s", this);

        } catch (FormatterClosedException formatterClosedException) {
            System.err.println("Error: al escribir archivo");
        }


        if (output != null) {
            output.close();
        }
    }

    ////// SEGUNDA ENTREGA //////////

    public Grafo BFS(int s) {
        Grafo arbol = new Grafo(getNumNodes());
        Boolean[] discovered = new Boolean[getNumNodes()];
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        discovered[s] = true;

        for (int i = 0; i < getNumNodes(); i++) {
            if (i != s) {
                discovered[i] = false;
            }
        }

        queue.add(s);
        while (queue.peek() != null) {
            int u = queue.poll();
            HashSet<Vertice> aristas = getEdge(u);

            for (Vertice n : aristas) {
                if (!discovered[n.getIndex()]) {
                    arbol.conectarVertices(u, n.getIndex());
                    discovered[n.getIndex()] = true;
                    queue.add(n.getIndex());
                }
            }
        }

        return arbol;
    }

    public Grafo DFS_R(int s) {
        Grafo arbol = new Grafo(getNumNodes());
        Boolean[] discovered = new Boolean[getNumNodes()];
        for (int i = 0; i < getNumNodes(); i++) {
            discovered[i] = false;
        }

        DFSIterator(s, discovered, arbol);
        return arbol;
    }

    private void DFSIterator(int v, Boolean[] discovered, Grafo arbol) {
        discovered[v] = true;
        for (Vertice e : getEdge(v)) {
            if (!discovered[e.getIndex()]) {
                arbol.conectarVertices(v, e.getIndex());
                DFSIterator(e.getIndex(), discovered, arbol);
            }
        }
    }

    public Grafo DFS_I(int s) {
        Grafo arbol = new Grafo(getNumNodes());
        Boolean[] explored = new Boolean[getNumNodes()];
        Stack<Integer> stack = new Stack<>();
        Integer[] parent = new Integer[getNumNodes()];

        for (int i = 0; i < getNumNodes(); i++) {
            explored[i] = false;
        }

        stack.push(s);
        while (!stack.isEmpty()) {
            int u = stack.pop();
            if (!explored[u]) {
                explored[u] = true;
                if (u != s) {
                    arbol.conectarVertices(u, parent[u]);
                }

                for (Vertice e :  getEdge(u)) {
                    stack.push(e.getIndex());
                    parent[e.getIndex()] = u;
                }
            }
        }

        return arbol;
    }


    ////////// TERCERA ENTREGA //////////


    public Grafo EdgeValues(double min, double max) {
        Grafo grafoPesado = new Grafo(getNumNodes());
        Random rand = new Random();
        double peso;

        for (int i = 0; i < getNumNodes(); i++) {
            for (int j = i; j < getNumNodes(); j++) {
                if (hayConexion(i, j)) {
                    peso = rand.nextFloat() * (max - min) + min;
                    grafoPesado.setAristaPeso(i, j, peso);
                }
            }
        }

        return grafoPesado;
    }

    public Grafo Dijkstra(int s) {
        Grafo arbol = new Grafo(getNumNodes());
        double inf = Double.POSITIVE_INFINITY;
        Integer[] ancestros = new Integer[arbol.getNumNodes()];

        for (int i = 0; i < arbol.getNumNodes(); i++) {
            getNode(i).setDistance(inf);
            ancestros[i] = null;
        }

        getNode(s).setDistance(0.0);

        ancestros[s] = s;

        PriorityQueue<Vertice> distPQ = new PriorityQueue<>(vertexDistanceComp);
        for (int i = 0; i < getNumNodes(); i++) {
            distPQ.add(getNode(i));
        }

        while (distPQ.peek() != null) {
            Vertice v = distPQ.poll();

            for (Arista e : getWeightedEdges(v.getIndex())) {
                if (getNode(e.getN2()).getDistance() > getNode(v.getIndex()).getDistance() + e.getWeight()) {
                    getNode(e.getN2()).setDistance( getNode(v.getIndex()).getDistance() + e.getWeight() );
                    ancestros[e.getN2()] = v.getIndex();
                }
            }
        }

        for (int i = 0; i < arbol.getNumNodes(); i++) {
            arbol.setAristaPeso(i, ancestros[i], 1);
            arbol.getNode(i).setDistance( getNode(i).getDistance() );
        }

        return arbol;
    }

    private Comparator<Vertice> vertexDistanceComp = Comparator.comparingDouble(Vertice::getDistance);

}
