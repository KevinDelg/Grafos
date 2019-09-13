/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba;
import Grafos.Grafo;


/**
 *
 * @author kevindelgado
 */
public class Prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Grafo uno = new Grafo(500);
        uno.modeloER(75); //número de aristas
        uno.escribirArchivo("Grafo-ER-500.gv");
        
        Grafo dos = new Grafo(500);
        dos.modeloGilbert(0.25); //número de aristas
        dos.escribirArchivo("Grafo-Gilbert-500.gv");
        
        Grafo ocho = new Grafo(30, "geo");
        ocho.modeloGeoSimple(0.3); //distancia a la que se deben conectar nodos
        ocho.escribirArchivo("Grafo-Geo-30.gv");
        
        Grafo doce = new Grafo(500);
        doce.modeloBA(75); //parámetro 'd' del modelo
        doce.escribirArchivo("Grafo-BA-500.gv");
    
    }
    
}
