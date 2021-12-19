package com.roek.www.gamelogic;

//Crea el arbol de juego en otro hilo para no cargar la experiencia de juego


import android.content.Context;
import android.util.Log;

import com.roek.www.framework.simple.FileIO;
import com.roek.www.visual.Game;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.TreeSet;


public class GameTree{
    public static boolean ESTA_LISTO_EN_ARREGLO = false;

    private static Thread loadThread;
    static int win1[],win2[];

    public static void cargar(final Game game) {
        if (ESTA_LISTO_EN_ARREGLO)return;
        loadThread = new Thread(new Runnable() {
            @Override
            public void run() {
                        FileIO fileIO = new FileIO(game);
                        win1 = (int[]) fileIO.leerObjeto("juegaRaton.wn");
                        win2 = (int[]) fileIO.leerObjeto("juegaGato.wn");
                        ESTA_LISTO_EN_ARREGLO = true;
                    Log.d("AVISO", "YA SE CARGO EL OBJETO");

            }
        });
        loadThread.start();
    }

    public static void dispose() {
        win1=null;
        win2=null;
        ESTA_LISTO_EN_ARREGLO = false;
    }

    public boolean isReady() {
        return ESTA_LISTO_EN_ARREGLO;
    }

    public static class Node{
        int ratonX,ratonY;
        int gatoY[];
        int gatoX[];
        boolean juegaRaton;
        public  Node(int ratonY, int ratonX, int[] gatoY, int[] gatoX, boolean juegaRaton) {
            this.ratonX = ratonX;
            this.ratonY = ratonY;
            this.gatoY = gatoY;
            this.gatoX = gatoX;
            this.juegaRaton = juegaRaton;
        }

    }
    /*
        Se encuentran todos los estados de juego ya visitados por el arbol de juego para su correcta construccion
     */
    //private TreeSet<Integer> mark;


    /*
       Se encuentran codificados cada Nodo del arbol de juego que es ganador
     */
    //private TreeSet<Integer> ganador;

    int[] posGatos;

    /*
        win1 es el arbol de juego para cuando juega el raton primero
        win2 es cuando juga el gato primero
        win es la referencia a win1 o win2 depende de quien juegue
            primero en esta instancia
     */

    int win[];
    private boolean ratonPrimero;

    public GameTree(boolean ratonPrimero){
        this.ratonPrimero = ratonPrimero;

        posGatos= new int[8];


        /*
            Cargaremos la informacion del arbol de juego de ganador pero no lo haremos en forma de TreeSet sino en forma de arreglo
            y realizaremos buzqueda binaria para saber si es posicion ganadora o no

            ganador = (TreeSet<Integer>) fileIO.leerObjeto( ratonPrimero ? "juegaRaton.wn" : "juegaGato.wn");
        */



        /*
            Estas lineas se pueden comentar ya que se usan para dar valores a (mark) y este solo se usa para el calculo de (ganador) y es la informacion
            q se usa posteriormente pero en el caso de (mark) no tiene sentido cargarlo

            mark = (TreeSet<Integer>) fileIO.leerObjeto( ratonPrimero ? "juegaRaton.mk" : "juegaGato.mk");

            e = new Node(7,3,new int[]{0,0,0,0},new int[]{0,2,4,6},ratonPrimero);

            marcar();
        */


        /*
             Aqui se debe correr el metodo construct pero por cuestiones de
             performance se corrio en una PC para los dos posibles comienzos y se serializo el resultado de
             ( TreeSet<Integer> ganador ) y (TreeSet<Integer> mark)
        */
    }



    /*
    Corremos la carga en otro hilo para unificar las experiencias de usuario al no cargar el thread de UI
    Este metodo se carga los recursos en un promedio de 3 seg, Aun asi se chequeara si los recursos se cargaron
    a la hora de usarlos para evitar Errores en ejecucion
     */



     /*
         LLena mark y ganador en dependencia del valor ratonPrimero en el constructor
         **** Esta comentado aca pq se compilo una varariante de esta en una PC y
         **** se serializo la informacion asi evitando cargar los dispositivos moviles
         **** ya que estos no disponen del mismo poder de calculo que las pc
      */
     /*

      //Movimientos posibles en las Xs y Ys para cada indice i (Cada posible movimiento esta representado como {mvY[i], mvX[i]} )
    private  int mvX[]= new int[]{-1,1,-1,1};
    private  int mvY[]= new int[]{-1,1,1,-1};

    boolean k;

    private Node e;
    private void contruct(){
        boolean flag = false;
        if (e.juegaRaton) {
            if (e.ratonY == 0) flag = true;

            for (int i = 0; i < 4; i++) {
                e.ratonX += mvX[i];
                e.ratonY += mvY[i];
                e.juegaRaton = false;
                k=true;

                for (int j = 0; j < 4; j++) {
                    k = k && (e.ratonX!=e.gatoX[j] || e.ratonY != e.gatoY[j] );
                }

                if (e.ratonX < 8 && e.ratonX > -1 && e.ratonY < 8 && e.ratonY > -1 && k) {


                    if (marcar()) {
                        contruct();
                    }

                    flag = !esGanadora(e) || flag;
                }

                e.ratonX -= mvX[i];
                e.ratonY -= mvY[i];

            }

            e.juegaRaton = true;
        }

        else {
            for(int i = 0 ; i< 4; i++){
                for(int x = 1; x < 3; x++) {
                    e.gatoY[i] += mvY[x];
                    e.gatoX[i] += mvX[x];
                    e.juegaRaton = true;
                    k = true;
                    for (int j = 0; j < 4; j++) {
                        if (j == i) continue;
                        if (e.gatoY[i] == e.gatoY[j] && e.gatoX[i] == e.gatoX[j]) {
                            k = false;
                            break;
                        }
                    }

                    if (e.gatoY[i] == e.ratonY && e.gatoX[i] == e.ratonX) k = false;

                    if (e.gatoX[i] < 8 && e.gatoX[i] > -1 && e.gatoY[i] < 8 && e.gatoY[i] > -1 && k) {

                        if (marcar()) {
                            contruct();

                        }
                        flag = !esGanadora(e) || flag;

                    }
                    e.gatoY[i] -= mvY[x];
                    e.gatoX[i] -= mvX[x];
                }
            }
            e.juegaRaton = false;
        }

        if (flag){
            ganador.add(codificarNode(e));
        }


    }
    /*
    //Retorna falso si ya se ha pasado por el Nodo e, verdadero en caso contrario y lo marca
     /*
    private  boolean b;
    private  boolean marcar(){

        b = mark.contains(codificarNode(e));
        if(b){
            return false;
        }
        mark.add(codificarNode(e));
        return true;
    }
    */


    //Se uso para guardar el TreeSet en un arreglo serializado y mejorar el performance de la carga
    /*
        public int[] guardarGanadorEnArray(){
        TreeSet<Integer>R = (TreeSet<Integer>) ganador.clone();

        int array[]= new int[ganador.size()];
        for(int i = 0; i<ganador.size(); i++){
            array[i]=R.pollFirst();
        }
        return array;
    }
    */



    //Retorna si un Nodo es ganador o perdedo
    private  Boolean flagWin;
    int key,indice;
    public  boolean  esGanadora(Node v){

        /*
            Espera a que el hilo que carga la informacion de lso assets termine de cargar
            Y para este hilo (Cambiar por algo mas amigable si es posible), poco probable que
            suceda...
        */
        while(!ESTA_LISTO_EN_ARREGLO);
        if(win == null)
              win = (ratonPrimero ? win1 : win2);


        key = codificarNode(v);

        indice = Arrays.binarySearch(win,key);

        return indice>-1 && indice < win.length && win[indice] == key ;

        /*
         Si esta cargado el arbol de juego en (int [] win) se busca ahi ya q es mas rapido
         y tambien (TreeSet <> ganador == null) Ya que no se ejecuto el metodo que construye
         el arbol y se cargo el estado directamente en (int[] win)

         flagWin = ganador.contains(codificarNode(v));
         return flagWin;

         */
    }


    private  static int code,corrimiento,ii,xx,t;

    private  int codificarNode(Node e){
        for(int i=0; i<8; i+=2){
            posGatos[i]= e.gatoY[i/2];
        }
        for(int i=1; i<8; i+=2){
            posGatos[i]= e.gatoX[i/2];
        }
        corrimiento=0;
        code = 0;
        code = code | e.ratonY;
        corrimiento+=3;
        code = code | (e.ratonX<<corrimiento);

        for(ii=0; ii<4; ii++){
            for(xx=0; xx<6; xx+=2){
                if(posGatos[xx]<posGatos[xx+2] || (posGatos[xx]==posGatos[xx+2] && posGatos[xx+1]< posGatos[xx+3])){
                    t=posGatos[xx];
                    posGatos[xx]=posGatos[xx+2];
                    posGatos[xx+2]=t;

                    t=posGatos[xx+1];
                    posGatos[xx+1]=posGatos[xx+3];
                    posGatos[xx+3]=t;
                }
            }
        }

        for (int i=0; i<4; i++){
            corrimiento+=3;
            code = code | (posGatos[i*2]<<corrimiento);
            corrimiento+=3;
            code = code | (posGatos[i*2+1]<<corrimiento);
        }
        corrimiento+=3;
        code = code | ((e.juegaRaton ? 0 : 1 ) << corrimiento);

        return code;
    }


}
