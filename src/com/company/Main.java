package com.company;

import java.io.*;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.LinkedList;

class Gnode {
    private int v;  // vertex index
    private double w;    // weight
    private double x, y;    // coordinate

    public Gnode(int i, double d1, double d2) {
        this.v = i;
        this.x = d1;
        this.y = d2;
    }

    public int getV() { return this.v; }
    public double getX() { return this.x; }
    public double getY() { return this.y; }
    public double getWeight() { return this.w; }
    public void setWeight(double d1) { this.w = d1; }
    public double calWeight(Gnode V) { return Math.sqrt(Math.pow(this.x - V.getX(), 2) + Math.pow(this.y - V.getY(), 2)); }
}

public class Main {

    public static void main(String[] args) throws IOException {
        // Scanner sc = new Scanner(System.in);

        File file = new File("C:\\Users\\MO\\IdeaProjects\\ARCTIC\\src\\input");
        Scanner sc = new Scanner(file);

        for(int T = sc.nextInt(); T > 0; T--) {
            int N = sc.nextInt();

            /* construct graph data structure */
            LinkedList<Gnode>[] L;
            L = new LinkedList[N];
            Gnode[] V = new Gnode[N];

            /* init adjacency list */
            for(int i = 0; i < N; i++) {
                V[i] = new Gnode(i, sc.nextDouble(), sc.nextDouble());
                L[i] = new LinkedList<>();
                L[i].push(V[i]);
            }

            for(int i = 0; i < N; i++) {
                for(int j = 0; j < N; j++) {
                    if(i == j)
                        continue;
                    Gnode temp = new Gnode(V[j].getV(), V[j].getX(), V[j].getY());
                    temp.setWeight(temp.calWeight(L[i].get(0)));
                    L[i].addLast(temp);
                }
            }

            for(int i = 0; i < N; i++) {
                ListIterator<Gnode> itr = L[i].listIterator();
                itr.next();
                while(itr.hasNext()) {
                    Gnode temp = itr.next();
                    System.out.println(i + " " + temp.getV() + " (" + temp.getX() + ", " + temp.getY() + ") " + temp.getWeight());
                }
                System.out.println("\n");
            }
            System.out.println("\n");
        } //end for(T)
    } // end main()
} // end Main
