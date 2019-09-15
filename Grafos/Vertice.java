/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafos;

/**
 *
 * @author kevindelgado
 */
public class Vertice {
    private String name;
    private Integer numAristas, index;

    private double x, y;


    //contructores para los vertices
    public Vertice(String name) {
        this.name = name;
        this.numAristas = 0;
    }


    public Vertice(int name) {
        this.index = name;
        this.name = "n" + name;
        this.numAristas = 0;
    }

    public Vertice(int name, double x, double y) {
        this.index = name;
        this.name = "n" + name;
        this.x = x;
        this.y = y;
    }


    //getters

    public String getName() { return name; }

    public Integer getNumEdges() { return numAristas; }

    public Integer getIndex() { return index; }

    public double getDistance() { return distance; }

    public void setDistance(double d) { this.distance = d; }

  
    public double getX() { return x; }
  //Variables para usar en el modelo geográfico simple
    public double getY() { return y; }
}
