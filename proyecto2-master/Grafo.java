package libreriaBFS;


import java.io.*;
import java.util.*;


/**
 *
 * @author Rodrigo Castañon
 */

public class Grafo {

//ATRIBUTOS DE LA CLASE
    private ArrayList<Vertices> nodo;
    private Aristas arista;
    private HashMap<Vertices, HashSet<Vertices>> grafo;
    private HashMap<Vertices, HashSet<Vertices>> arbol;
    private boolean dirigido;
    private int numeroDeNodos;
    private int numeroDeAristas;

//CONSTRUCTOR 
    public Grafo(int numNodos, boolean geografico, boolean dirigido) {
        //SE INICIALIZAN LOS ATRIBUTOS  
        grafo = new HashMap<>();
        arbol = new HashMap<>();
        nodo = new ArrayList<>();
        numeroDeNodos = numNodos;
        this.dirigido = dirigido;
        Random coorX = new Random();
        Random coorY = new Random();

        if (geografico) {
            for (int i = 0; i < numeroDeNodos; i++) {
                nodo.add(new Vertices(i, coorX.nextFloat(), coorY.nextFloat()));
                grafo.put(nodo.get(i), new HashSet<>());
            }
        } //SI NO ES EL MODELO GEOGRAFICO
        else {
            for (int i = 0; i < numNodos; i++) {
                nodo.add(new Vertices(i));
                grafo.put(nodo.get(i), new HashSet<>());
            }
        }
    }

//GETTERS
    public int getNumNodos() {
        return nodo.size();
    }

    public int getNumAristas() {
        return numeroDeAristas;
    }

    public Vertices getNodo(int i) {
        return nodo.get(i);
    }

    public HashSet<Vertices> getAristas(int i) {
        return grafo.get(getNodo(i));
    }

    //PARA CONECTAR EL NODO 1 Y 2 
    public void conectarVertices(int n1, int n2) {
        getAristas(n1).add(nodo.get(n2));
        numeroDeAristas++;
    }

    //PARA COMPROBAR SI EXITE CONEXION ENTRE EL NODO 1 Y 2
    private Boolean existeConexion(int n1, int n2) {
        return (getAristas(n2).contains(nodo.get(n1))
                || getAristas(n1).contains(nodo.get(n2)));
    }

    //PARA OBTENER LA DISTANCIA QUE HAY ENTRE EL NODO 1 Y NODO 2
    private double distancia(Vertices n1, Vertices n2) {
        return Math.sqrt(Math.pow((n1.getX() - n2.getX()), 2)
                + Math.pow((n1.getY() - n2.getY()), 2));
    }
    //PARA OBTENER EL GRADO DEL NODO 1
    public int gradoVertices(int i) {
        return grafo.get(nodo.get(i)).size();
    }
    


//MODELOS PARA LA GENERACION DE GRAFOS ALEATORIOS
    
    public void modeloErdosRenyi(int numeroDeAristas) {

        Random randomNum1 = new Random();
        Random randomNum2 = new Random();

        while (getNumAristas() < numeroDeAristas) {
            randomNum1.nextInt(getNumNodos());
            int num1 = randomNum1.nextInt(getNumNodos());
            int num2 = randomNum2.nextInt(getNumNodos());
            if (num1 != num2) {
                if (!existeConexion(num1, num2)) {
                    conectarVertices(num1, num2);
                }
            }
        }
    }

    public void modeloGilbert(double probabilidad) {
        Random randomNum = new Random();
        if (probabilidad >= 0) {
            for (int i = 0; i < getNumNodos(); i++) {
                for (int j = i + 1; j < getNumNodos(); j++) {
                    if ((randomNum.nextFloat() <= probabilidad)) {
                        if (!existeConexion(i, j)) {
                            conectarVertices(i, j);
                        }
                    }

                }
            }
        } else {
            System.err.println("La propabilidad tiene que ser mayor que cero");
        }
    }

    public void modeloGeografico(double r) {
        for (int i = 0; i < getNumNodos(); i++) {
            for (int j = i + 1; j < getNumNodos(); j++) {
                double distancia = distancia(getNodo(i), getNodo(j));
                if (distancia <= r) {
                    conectarVertices(i, j);
                }

            }
        }
    }

    public void modeloBarabasi(int d) {
        Random randomNum = new Random();

        for (int i = 0; i < d; i++) {
            for (int j = 0; j < i; j++) {
                if (!existeConexion(i, j)) {
                    conectarVertices(i, j);
                }
            }
        }

        for (int i = d; i < getNumNodos(); i++) {
            for (int j = 0; j < i; j++) {
                double probabilidad = (double) gradoVertices(j) / (double) getNumAristas();
                if (randomNum.nextDouble() <= probabilidad) {
                    if (!existeConexion(i, j) && (gradoVertices(i) <= d) && (gradoVertices(j) <= d)) {
                        conectarVertices(i, j);
                    }
                }
            }
        }
    }

//ALGORITMOS DE BUSQUEDA
    
    public void BFS(int s) {

       // Se crea una lista de nodos visitados
        boolean visited[] = new boolean[numeroDeNodos];

        // Se crea la cola en donde se pondran los vecinos del nodo analizado 
        LinkedList<Integer> queue = new LinkedList<>();

       
        // Marca el nodo actual como visitado 
        visited[s] = true;
        queue.add(s);

        while (!queue.isEmpty()) {
            // Se saca el primer elemento de la cola 
            s = queue.poll();
            //Se crea el arbol en donde se guardara el resultado del BFS
            arbol.put(nodo.get(s), new HashSet<>());
            
            // Se toman todos los vertices vecinos de s
            // Si el vecino no ha sido visitado, se marca y se coloca en la cola 
           
            Iterator<Vertices> i = this.grafo.get(getNodo(s)).iterator();
            while (i.hasNext()) {
                int n = i.next().getId();
                if (!visited[n]) {
                    visited[n] = true;
                    queue.add(n);
                    System.out.print(n + " ");
                    arbol.get(getNodo(s)).add(nodo.get(n));

                }
            }
        }
    }
    
   
    // The function to do DFS traversal. It uses recursive DFSUtil() 
    public void DFSrecur(int v) 
    { 
        // Mark all the vertices as not visited(set as 
        // false by default in java) 
        boolean visited[] = new boolean[numeroDeNodos];
        Stack<Integer> stack = new Stack<>();
        stack.push(v);
        // Call the recursive helper function to print DFS traversal 
        DFSHelper(v, visited,stack); 
    }
    
     // A function used by DFS 
    public void DFSHelper(int v,boolean visited[],Stack<Integer> stack ) 
    { 
        // Mark the current node as visited and print it 
        visited[v] = true;
        
         
        if(arbol.containsKey(nodo.get(v))==false)
            arbol.put(nodo.get(v), new HashSet<>());
        
        if(!stack.empty()) v = stack.peek();
        
        // Recur for all the vertices adjacent to this vertex 
       // Iterator<Integer> i = adj[v].listIterator(); 
        Iterator<Vertices> i = this.grafo.get(getNodo(v)).iterator();
        
        while (i.hasNext()) 
        { 
            int n = i.next().getId(); 
            if (!visited[n]) {
                stack.push(n);
                System.out.print(n + " r");
                arbol.get(getNodo(v)).add(nodo.get(n));
                DFSHelper(n, visited,stack);                
            }
        }
        
        if(!stack.empty()){ 
            v=stack.pop();
            DFSHelper(v, visited,stack);
        }
        
            
    } 
  
    // prints all not yet visited vertices reachable from s 
    public void DFSiterador(int s) 
        { 
     
            boolean visited[] = new boolean[numeroDeNodos]; 
            
            // Create a stack for DFS 
            Stack<Integer> stack = new Stack<>(); 
              
            // Push the current source node
            visited[s] = true;
            stack.push(s); 
              
            while(!stack.empty()) 
            { 
                // Pop a vertex from stack and store it in the tree
                s = stack.peek(); 
                if(arbol.containsKey(nodo.get(s))==false)
                    arbol.put(nodo.get(s), new HashSet<>());    
             
                  
                // Get all adjacent vertices of the popped vertex s 
                // If a adjacent has not been visited, then push it 
                // to the stack. 
                Iterator<Vertices> i = this.grafo.get(getNodo(s)).iterator();
               // int v=i.next().getId();  
                while (i.hasNext())  
                { 
                    int v = i.next().getId(); 
                    if(!visited[v]) {
                        visited[v] = true;
                        stack.push(v); 
                        System.out.print(v + " i");
                        arbol.get(getNodo(s)).add(nodo.get(v));
                        break;
                    }
                    //if(!i.hasNext()) stack.pop();
                } 
                if(i.hasNext()) continue;
                else stack.pop();
            } 
        } 
      
    
//GENERA EL FORMATO .GV PARA LOS MODELOS
    public String generarGV() {
        String salida = "";
        if (!dirigido) {
            salida = "graph{\n";

            for (int i = 0; i < getNumNodos(); i++) {
                salida += getNodo(i).getNombre() + ";\n";
            }
            for (int i = 0; i < nodo.size(); i++) {
                for (Vertices n : getAristas(i)) {
                    salida += getNodo(i).getNombre() + " -- " + n.getNombre() + ";\n";
                }
            }

            salida += "}\n";
        } else {
            salida = "graph{\n";

            for (int i = 0; i < getNumNodos(); i++) {
                salida += getNodo(i).getNombre() + ";\n";
            }
            for (int i = 0; i < nodo.size(); i++) {
                for (Vertices n : getAristas(i)) {
                    salida += getNodo(i).getNombre() + " -> " + n.getNombre() + ";\n";
                }
            }

            salida += "}\n";

        }
        return salida;
    }
    
//GENERA EL FORMATO .GV PARA EL ARBOL USANDO BFS Y DFS
    public String generarArbolGV() {

        String salida = "";

        salida = "graph{\n";

        for (Vertices n : arbol.keySet()) {
            salida += n.getNombre() + ";\n";
        }

        for (Vertices n : arbol.keySet()) {

            HashSet<Vertices> aristas = arbol.get(n);
            if (aristas == null) {
                continue;
            }
            for (Vertices n2 : aristas) {
                salida += n.getNombre() + " -> " + n2.getNombre() + ";\n";
            }
        }
        salida += "}\n";
        return salida;
    }

    public void writeFile(String fileNameGrafo, String fileNameBFS) throws IOException {
        File outputFileGrafo = new File(fileNameGrafo);
        File outputFileBFS = new File(fileNameBFS);
        
        FileWriter outputGrafo = new FileWriter(outputFileGrafo);
        FileWriter outputBFS = new FileWriter(outputFileBFS);

        outputGrafo.write(generarGV());
        outputBFS.write(generarArbolGV());
        
        outputGrafo.close();
        outputBFS.close();

    }

}
