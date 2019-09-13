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
        Grafo uno = new Grafo(30);//numero de nodos
        uno.modeloER(250); //número de aristas
        uno.escribirArchivo("Grafo-ER-30.gv");

        Grafo dos = new Grafo(500);//numero de nodos
        dos.modeloGilbert(0.25); //número de aristas
        dos.escribirArchivo("Grafo-Gilbert-500.gv");
        
        Grafo tres = new Grafo(30, "geo");//numero de nodos
        tres.modeloGeoSimple(0.3); //distancia a la que se deben conectar nodos
        tres.escribirArchivo("Grafo-Geo-30.gv");
       
        Grafo cuatro = new Grafo(500);//numero de nodos
        cuatro.modeloBA(75); //parámetro 'd' del modelo
        cuatro.escribirArchivo("Grafo-BA-500.gv");
    
    }
    
}
