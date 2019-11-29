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
    /*Estas variables solo son usadas en el modelo geográfico simple
    por lo que solo se usan cuando se llama al constructor correspondiente*/
    private double x, y;

    /*Variable para usar en Dijkstra*/
    private double distance;

    /*Constructor que toma una cadena como nombre del vértice*/
    public Vertice(String name) {
        this.name = name;
        this.numAristas = 0;
    }

    /*Constructor que toma un entero como argumento. Asigna a la variable
    de instancia 'name' la cadena formada por la letra 'n' concatenada con
    la representación en cadena del número entero que tomó como argumento*/
    public Vertice(int name) {
        this.index = name;
        this.name = "n" + name;
        this.numAristas = 0;
    }

    /*Constructor usado en el modelo geográfico simple. Toma un entero que sirve
    como nombre del vértice y dos números de punto flotante como coordenadas
    del vértice. El modelo geofráfico simple toma coordenadas en un cuadrado
    unitario*/
    public Vertice(int name, double x, double y) {
        this.index = name;
        this.name = "n" + name;
        this.x = x;
        this.y = y;
    }


    /*getters de variables de instancia */

    public String getName() { return name; }

    public Integer getNumEdges() { return numAristas; }

    public Integer getIndex() { return index; }

    public double getDistance() { return distance; }

    public void setDistance(double d) { this.distance = d; }

    /*Variables de instancia usadas en el modelo geográfico simple*/
    public double getX() { return x; }

    public double getY() { return y; }
}
