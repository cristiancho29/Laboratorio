/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laberinto;

import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author cdsuarez
 */
public class Laberinto {

    /**
     * @param args the command line arguments
     */
    
    /*
    Dentro de la matriz:
    - 0 significa casilla no recorrida
    - 1 significa una pared
    - 2 significa punto de inicio
    - 3 camino solucion
    - 4 camino restringido, por ser un callejon
    - 5 significa punto final del laberinto
    - 7 recorrido temporal mientras no se encuentra la solución o se restrinja el camino
    por ser un callejon sin salida
    */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner lea = new Scanner(System.in);
        Stack Recorrido = new Stack();
        Stack Interseccion = new Stack();
        int[][] M = {
            {1, 1, 1, 1, 1, 1, 1, 1, 5, 1},
            {1, 0, 0, 0, 0, 0, 0, 1, 0, 1},
            {1, 0, 1, 1, 0, 1, 1, 1, 0, 1},
            {1, 1, 1, 0, 0, 1, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 1, 0, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 0, 1, 1, 1, 0, 1},
            {1, 0, 0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 0, 0, 0, 1, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},};
        int it, jt, tope1, tope2;
        System.out.println("Digite i=");
        it = lea.nextInt();
        System.out.println("Digite j=");
        jt = lea.nextInt();
        tope1 = 0;
        tope2 = 0;
        M[it][jt] = 2;
        Recorrido.push(Integer.toString(it).concat("-").concat(Integer.toString(jt)));
        tope1++;
        mostrarMatriz(M, 10);
        String[] pos;int dir=0;
        do {
            if (contarCaminos(M, it, jt) > 1) {
                Interseccion.push(Integer.toString(it).concat("-").concat(Integer.toString(jt)));
                tope2++;
            }
            M[it][jt] = 7;dir=proximoMovimiento( M, it, jt);
            System.out.println(dir);
            switch (dir) {
                case 0:
                    System.out.println("---------Se hace Backtracking---------");
                    String [] t=null;boolean sw=false;
                    while(!Recorrido.get(tope1-1).toString().equals(Interseccion.get(tope2-1).toString())){
                        t = Recorrido.get(tope1-1).toString().split("-");
                        M[Integer.parseInt(t[0])][Integer.parseInt(t[1])] = 4;
                        mostrarMatriz(M, 10);
                        Recorrido.pop();
                        tope1--;
                    }
                    pos = Interseccion.get(tope2-1).toString().split("-");
                    it = Integer.parseInt(pos[0]);
                    jt = Integer.parseInt(pos[1]);
                    Interseccion.pop();
                    tope2--;
                    System.out.println("---------Termina Backtracking----------");
                    break;
                case 1:
                    it--;
                    Recorrido.push(Integer.toString(it).concat("-").concat(Integer.toString(jt)));
                    tope1++;
                    break;
                case 2:
                    it++;
                    Recorrido.push(Integer.toString(it).concat("-").concat(Integer.toString(jt)));
                    tope1++;
                    break;
                case 3:
                    jt--;
                    Recorrido.push(Integer.toString(it).concat("-").concat(Integer.toString(jt)));
                    tope1++;
                    break;
                case 4:
                    jt++;
                    Recorrido.push(Integer.toString(it).concat("-").concat(Integer.toString(jt)));
                    tope1++;
                    break;
            }
            
            mostrarMatriz(M, 10);
        } while (M[it][jt] != 5);
        marcarRecorrido(M, Recorrido, tope1);
        System.out.println("----------Solucion Laberinto-------");
        mostrarMatriz(M, 10);
    }

    //Marcar recorrido valido
    public static void marcarRecorrido(int[][] M, Stack Recorrido, int tope) {
        String[] t;
        while (!Recorrido.empty()) {
            t = Recorrido.get(tope-1).toString().split("-");
            M[Integer.parseInt(t[0])][Integer.parseInt(t[1])] = 3;
            Recorrido.pop();
            tope--;
        }
    }

    //Restriccion de posibles movimientos
    public static int   restringirMovimiento(int[][] M, Stack Recorrido, Stack Interseccion, int tope1, int tope2) {
        //int [][]T = M;
        String[] t;
        int i=tope1;
        boolean sw=false;
        while(sw==false && !Recorrido.empty()){
            if(!Recorrido.get(i-1).toString().equals(Interseccion.get(tope2-1).toString())){
                t = Recorrido.get(i-1).toString().split("-");
                M[Integer.parseInt(t[0])][Integer.parseInt(t[1])] = 4;
                Recorrido.pop();
                i--;
            }
            else{
                sw=true;
            }
        }
        System.out.println(i);
        return i;
    }

    //Contar caminos
    public static int contarCaminos(int[][] M, int i, int j) {
        //Norte
        int cont = 0;
        if (M[i - 1][j] == 0 || M[i - 1][j] == 5) {
            cont++;
        }
        if (M[i + 1][j] == 0 || M[i + 1][j] == 5) {
            cont++;
        }
        if (M[i][j - 1] == 0 || M[i][j - 1] == 5) {
            cont++;
        }
        if (M[i][j + 1] == 0 || M[i][j + 1] == 5) {
            cont++;
        }
        return cont;
    }

    //Mostrar Matriz
    public static void mostrarMatriz(int[][] M, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(M[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    //Proximo movimiento
    public static int proximoMovimiento(int[][] M, int i, int j) {
        if(M[i-1][j]!=1 && M[i-1][j]!=4 && M[i-1][j]!=7 && M[i-1][j]!=2 ){
            return 1;
        }
        else if(M[i+1][j]!=1 && M[i+1][j]!=4 && M[i+1][j]!=7 && M[i+1][j]!=2){
            return 2;
        }
        else if(M[i][j-1]!=1 && M[i][j-1]!=4 && M[i][j-1]!=7 && M[i][j-1]!=2){
            return 3;
        }
        else if(M[i][j+1]!=1 && M[i][j+1]!=4 && M[i][j+1]!=7 && M[i][j+1]!=2){
            return 4;
        }
        else{
            return 0;
        }
    }
}
