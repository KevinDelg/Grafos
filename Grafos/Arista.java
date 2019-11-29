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

public class Arista {
    private Integer a, b;
    private double peso;

    /*Constructor que toma núumeros de los nodos que conecta y su peso*/
    public Arista(int a, int b, double peso) {
        this.a = a;
        this.b = b;
        this.peso = peso;
    }

    public String getNode1() {
        return "n" + a.toString();
    }

    public String getNode2() {
        return "n" + b.toString();
    }

    public int getN1() {
        return a;
    }

    public int getN2() {
        return b;
    }

    public double getWeight() {
        return peso;
    }

}
