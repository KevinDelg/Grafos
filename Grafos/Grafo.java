package Grafos;
import java.util.*;
import java.io.FileNotFoundException;

/**
 *
 * @author kevindelgado
 */

public class Grafo {

    private Vertice[] vertex;
    private HashMap<Vertice, HashSet<Vertice>> graph;
    private final int numVertices;
    private int numAristas;
    private static Formatter salida;

    public Grafo(int numVertices) {
        this.numVertices = numVertices;
        this.vertex = new Vertice[numVertices];
        this.graph = new HashMap<>();
        for (int i = 0; i < numVertices; i++) {
            Vertice n = new Vertice(i);
            this.vertex[i] = n;
            this.graph.put(n, new HashSet<>());
        }
    }

    public Grafo(int numVertices, String modelo) {
        this.graph = new HashMap<>();
        this.numVertices = numVertices;
        this.vertex = new Vertice[numVertices];

        Random coordX = new Random();
        Random coordY = new Random();

        if (modelo.equals("geo-uniforme")) {
            for (int i = 0; i < numVertices; i++) {
                Vertice n = new Vertice(i, coordX.nextDouble(), coordY.nextDouble());

                this.vertex[i] = n;
                this.graph.put(n, new HashSet<>());
            }
        }
    }

    private int gradoVertice(int i) {
        Vertice n1 = this.getNode(i);
        return this.graph.get(n1).size();
    }

    private void conectarVertices(int i, int j) {
        Vertice n1 = this.getNode(i);
        Vertice n2 = this.getNode(j);

        HashSet<Vertice> aristas1 = this.getEdges(i);
        HashSet<Vertice> aristas2 = this.getEdges(j);

        aristas1.add(n2);
        aristas2.add(n1);
        this.numAristas +=1;
    }

    private Boolean existeConexion(int i, int j) {
        Vertice n1 = this.getNode(i);
        Vertice n2 = this.getNode(j);

        HashSet<Vertice> aristas1 = this.getEdges(i);
        HashSet<Vertice> aristas2 = this.getEdges(j);

        return aristas1.contains(n2) || aristas2.contains(n1);
    }

    private double distanciaVertices(Vertice n1, Vertice n2) {
        return Math.sqrt(Math.pow((n1.getX() - n2.getX()), 2) + Math.pow((n1.getY() - n2.getY()), 2));
    }

    private int getNumNodes() {return numVertices;}

    private int getNumEdges() {return numAristas;}

    private Vertice getNode(int i) {return this.vertex[i];}

    private HashSet<Vertice> getEdges(int i) {
        Vertice n = this.getNode(i);
        return this.graph.get(n);
    }

    public String toString() {
        StringBuilder salida;

        salida = new StringBuilder("graph {\n");
        for (int i = 0; i < this.getNumNodes(); i++) {
            salida.append(this.getNode(i).getName()).append(";\n");
        }

        for (int i = 0; i < this.getNumNodes(); i++) {
            HashSet<Vertice> aristas = this.getEdges(i);
            for (Vertice n : aristas) {
                salida.append(this.getNode(i).getName()).append(" -- ").append(n.getName()).append(";\n");
            }
        }

        salida.append("}\n");

        return salida.toString();
    }

    public void modeloER(int numAristas) {
        Random randomNum1 = new Random();
        Random randomNum2 = new Random();
        while (this.getNumEdges() < numAristas) {
            int num1 = randomNum1.nextInt(this.getNumNodes());
            int num2 = randomNum2.nextInt(this.getNumNodes());
            if (num1 != num2) {
                if (!existeConexion(num1, num2)) {
                    conectarVertices(num1, num2);
                }
            }
        }
    }

    /**
     * Para cada vértice i se recorren todos los vértices j con i<>j. En cada
     *     par se calcula un número pseudoaleatorio entre 0.0 y 1.0 y se compara
     *     con la probabilidad que se le pasó como argumento al modelo. Si es menor
     *     que la probabilidad y no existe ya una conexión, se realiza la conexión.
     *
     * @param probabilidad
     */
    public void modeloGilbert(double probabilidad) {
        Random randomNum = new Random();

        for (int i = 0; i < this.getNumNodes(); i++) {
            for (int j = 0; j <this.getNumNodes(); j++) {
                if ((i != j) && (randomNum.nextDouble() <= probabilidad)) {
                    if (!existeConexion(i, j)) {
                        conectarVertices(i, j);
                    }
                }
            }
        }
    }

    /**
     * Para cada vértice i se compara con el resto de los vértices con los que
     *     no se ha comparado y si están a una distancia r o menor se realiza la
     *     conexión.
     * @param r
     */
    public void modeloGeoSimple(double r) {
        for (int i = 0; i < this.getNumNodes(); i++) {
            for (int j = i + 1; j < this.getNumNodes(); j++) {
                double distancia = distanciaVertices(this.getNode(i), this.getNode(j));
                if (distancia <= r) {
                    conectarVertices(i, j);
                }
            }
        }
    }

    public void modeloBA(int d) {
        Random volado = new Random();

        /* Para los primeros d vértices, se conecta el vértice i con el vértice j
           con i distinto de j y recorriendo todos los vértices.*/
        for (int i = 0; i < d; i++){
            for (int j = 0; j < i; j++) {
                if (!existeConexion(i, j)) {
                    conectarVertices(i, j);
                }
            }
        }

        /* Del vértice d en adelante hasta el vértice n, el vértice i de trata de
           emparejar con cada vértice j desde el primero hasta i-1. La manera de hacer
           el emparejamiento es con probabilidad. La probabilidad de que el vértice i
           se conecte con j es proporcional al número de aristas del vértice j dividido
           por el número total de aristas en el grafo hasta ese momento. Un número
           pseudoaleatorio se compara con esa probabilidad, de ser menor, se realiza
          la conexión. Antes de realizar la conexión se verifica que no exista ya y que
           el vértice i no tenga ya d o más conexiones.*/

        // i no se incrementa hasta que ese vértice tiene d conexiones
        for (int i = d; i < this.getNumNodes();) {
            for (int j = 0; j < i; j++) {
                double probabilidad = 1 - ((double)gradoVertice(j)/(double)this.getNumEdges());

                if (volado.nextDouble() < probabilidad) {
                    if (!existeConexion(i, j) && (gradoVertice(i) < d)) {
                        conectarVertices(i, j);
                    }
                }
            }

            if (gradoVertice(i) >= d) { i++; }
        }
    }

    public void escribirArchivo(String nombre) {
        try {
            salida = new Formatter(nombre);

        } catch (SecurityException securityException) {
            System.err.println("No hay permiso de escritura.");
            System.exit(1);

        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println("Error al abrir el archivo.");
            System.exit(1);
        }

        try {
            salida.format("%s",this);

        } catch (FormatterClosedException formatterClosedException) {
            System.err.println("Error al escribir al archivo");
        }

        if (salida != null)
            salida.close();
    }
}
