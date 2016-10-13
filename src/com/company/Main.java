package com.company;

import java.io.*;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.LinkedList;

class Gnode {
    private int v;  // vertex index
    private double w;    // weight
    private double x, y;    // coordinate
    private static boolean[] visit;

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

    public static void initVisit(int N) { visit = new boolean[N]; }
    public static boolean getVisit(int v) { return visit[v]; }
    public static void setVisit(int v) { visit[v] = true; }
}

public class Main {

    public static void dfs(LinkedList<Gnode> L[], int v) {
        ListIterator<Gnode> itr = L[v].listIterator();
        Gnode.setVisit(v);
        Gnode cur;
        while(itr.hasNext()) {
            cur = itr.next();
            if(Gnode.getVisit(cur.getV()) == false)
                dfs(L, cur.getV());
        }
    }

    public static void main(String[] args) throws IOException {
        // Scanner sc = new Scanner(System.in);

        File file = new File("C:\\Users\\MO\\IdeaProjects\\ARCTIC\\src\\input");
        Scanner sc = new Scanner(file);

        for(int T = sc.nextInt(); T > 0; T--) {
            int N = sc.nextInt();

            /* construct graph data structure */
            LinkedList<Gnode>[] L;
            LinkedList<Double> Weights = new LinkedList<>();
            L = new LinkedList[N];
            Gnode[] V = new Gnode[N];

            /* init adjacency list */
            for(int i = 0; i < N; i++) {
                V[i] = new Gnode(i, sc.nextDouble(), sc.nextDouble());
                L[i] = new LinkedList<>();
                L[i].push(V[i]);
            }

            Weights.add(0.0);
            for(int i = 0; i < N; i++) {
                for(int j = 0; j < N; j++) {
                    if(i == j)
                        continue;
                    Gnode temp = new Gnode(V[j].getV(), V[j].getX(), V[j].getY());
                    temp.setWeight(temp.calWeight(L[i].get(0)));
                    L[i].addLast(temp);
                    /* insert the weight to the sorted weights */
                    ListIterator<Double> itr = Weights.listIterator();
                    int k = 0;
                    while(true) {
                        k++;
                        double d = itr.next();
                        if(d == 0) {
                            Weights.remove(0);
                            Weights.add(0, temp.getWeight());
                            break;
                        }
                        else if (d > temp.getWeight()) {
                            Weights.add(k-1, temp.getWeight());
                            break;
                        }
                        else if (d == temp.getWeight()) {
                            break;
                        }
                        else if (!itr.hasNext()) {
                            Weights.addLast(temp.getWeight());
                            break;
                        }
                    } // end sort
                } // end for(j)
            } //end for(i)

            ListIterator<Double> itrW = Weights.listIterator();
            boolean flag = false;
            while(itrW.hasNext())
                itrW.next();

            while(true) {
                Double maxW = itrW.previous();

                for (int i = 0; i < N; i++) {
                    ListIterator<Gnode> itr = L[i].listIterator();
                    itr.next();
                    int j = 0;
                    Gnode temp;
                    while (itr.hasNext()) {
                        temp = itr.next();
                        j++;
                        if (temp.getWeight() == maxW) {
                            itr.remove();
                            j--;
                        }
                    }
                }
                // check if connected (DFS)
                Gnode.initVisit(N);
                dfs(L, 0);

                int i;
                for(i = 0; i < N; i++)
                    if(Gnode.getVisit(i) == false)
                        break;

                if(i != N) {
                    System.out.println(itrW.next());    // print max weight
                    break;
                }
            }
        } //end for(T)
    } // end main()
} // end Main
