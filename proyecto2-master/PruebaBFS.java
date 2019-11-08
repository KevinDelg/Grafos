/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebabfs;
import java.io.IOException;
import libreriaBFS.Grafo;


/**
 *
 * @author CASTA
 */
public class PruebaBFS {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        Grafo unoErdos = new Grafo(30,false,true);
        unoErdos.modeloErdosRenyi(90); 
        unoErdos.BFS(5);
        unoErdos.writeFile("Demo\\PruebaErdos30.gv","Demo\\PruebaBfsErdos30.gv");
        unoErdos.DFSrecur(5);
        unoErdos.writeFile("Demo\\PruebaErdos30.gv","Demo\\PruebaDfsRecuErdos30.gv");
        unoErdos.DFSiterador(5);
        unoErdos.writeFile("Demo\\PruebaErdos30.gv","Demo\\PruebaDfsIteradoErdos30.gv");
        
//        Grafo unoGilbert = new Grafo(30,false,true);
//        unoGilbert.modeloGilbert(.33); 
//        unoGilbert.DFS(5);
//        unoGilbert.writeFile("Demo\\Gilbert30.gv","Demo\\PruebaBfsGilbert30.gv");
//        
//        
//        Grafo unoGeografico = new Grafo(30,true,true);
//        unoGeografico.modeloGeografico(.33);
//        unoGeografico.BFS(5);
//        unoGeografico.writeFile("Demo\\PruebaGeo30.gv","Demo\\PruebaBfsGeo30.gv");
////        // TODO code application logic here
    }
    
}
