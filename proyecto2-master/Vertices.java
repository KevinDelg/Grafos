package libreriaBFS;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author CASTA
 */
public class Vertices {
    
    private int id;
    private String nombre;
    private int aristas;
    private float x,y;
    private float distancia;

    public Vertices(int id) {
        this.id = id;
        this.aristas=0;
        this.nombre="vertice"+String.valueOf(id);
    }

    public Vertices(int id, int aristas) {
        this.id = id;
        this.nombre = "vertice"+String.valueOf(id);
        this.aristas = aristas;
    }

    
    public Vertices(int id,float x,float y){ //para geografico
        this.id=id;
        this.nombre= "vertice"+String.valueOf(id);
        this.aristas=0;
        this.x=x;
        this.y=y;
    }
    
    public int getId() {return id;}

    public String getNombre() {return nombre;}

    public int getAristas() {
        return aristas;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = "vertice" + nombre;
    }

    public void setAristas(int aristas) {
        this.aristas = aristas;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getDistancia() {
        return distancia;
    }

    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }
    
    
    
    
    
    
    
}

