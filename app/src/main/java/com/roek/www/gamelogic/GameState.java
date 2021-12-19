package com.roek.www.gamelogic;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.roek.www.framework.simple.FileIO;
import com.roek.www.gamelogic.GameTree.Node;
import com.roek.www.visual.MainMenuScreen;

import java.io.File;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.Random;

public class GameState {

    public static int playerInTurn;
    private Node node;
    Random random;

    /*
     boolean esRaton define si el player1 escogio o no jugar con el raton
     */

    private boolean esRaton;

    /*
    Los valores en la tabla seran los siguientes: (No se usaron enum por la relacion numerica entre los conjuntos gato y raton)
        0: Nada
        1: Raton
        2: Gato Naranja
        3: Gato Azul
        4: Gato Rojo
        5: Gato Violeta
     */

    private int [][]tabla;

    private GameTree gameTree;

    public GameState (boolean esRaton){

        node = new Node(0,0,new int[4], new int[4],true);
        tabla = new int[8][8];
        playerInTurn = 1;
        random = new Random();
        gameTree = new GameTree(esRaton);

        this.esRaton = esRaton;

        tabla[7][3]=1;
        tabla[0][0]=2;
        tabla[0][2]=3;
        tabla[0][4]=4;
        tabla[0][6]=5;

    }

    private boolean flag;
    //retorna verdadero y ejecuta el cambio si un movimiento es valido
    public boolean isValidMove(int sX, int sY, int fX, int fY){

        if(sX > 7 || sX < 0 || sY > 7 || sY < 0 || fX > 7 || fX < 0 || fY > 7 || fY < 0) return false;

        flag = false;
        if(playerInTurn == 1){
            if(esRaton) {
                flag = tabla[sY][sX] == 1 && (fX == (sX + 1) || fX == (sX - 1)) && (fY == (sY + 1) || fY == (sY - 1)) && tabla[fY][fX] == 0;
            }
            else {
                flag = tabla[sY][sX] > 1 && (fX == (sX + 1) || fX == (sX - 1)) && (fY == (sY + 1)) && tabla[fY][fX] == 0;
            }
        }else {
            if(!esRaton) {
                flag = tabla[sY][sX] == 1 && (fX == (sX + 1) || fX == (sX - 1)) && (fY == (sY + 1) || fY == (sY - 1)) && tabla[fY][fX] == 0;
            }
            else {
                flag = tabla[sY][sX] > 1 && (fX == (sX + 1) || fX == (sX - 1)) && (fY == (sY + 1)) && tabla[fY][fX] == 0;
            }
        }

        return flag;
    }

    public void setMove(int sX, int sY, int fX, int fY){
        if(isValidMove(sX,sY,fX,fY)){
            tabla[fY][fX]=tabla[sY][sX];
            tabla[sY][sX]=0;

            playerInTurn = 3 - playerInTurn;
        }

    }

    //Actualiza la informacion en Node.node del actual estado del juego

    public boolean gananEnUnaLosGatos(){

        for(int i= 0; i< 2; i++) {          //Itera en los movimientos

            for (int x = 0; x < 4; x++) {           //Itera en los gatos

                node.gatoY[x] += mvY[i];                //
                node.gatoX[x] += mvX[i];                //  Se efectua la transicion
                node.juegaRaton = !node.juegaRaton;     //
                boolean f = false;

                // No se comprueba si la jugada del gato es valida ya q solo se quiere comprobar si el raton se queda sin opciones
                // Y una jugada fuera del tablero no incapacita al raton de moverse dentro del propio tablero
                // Y si el raton resulta encerrado luego de una jugada fuera del tablero quiere decir q no tenia opciones
                // desde algunas jugadas lo cual no es posible por tanto para una mayor simplicidad se toma la decision de
                // no tratarlos como un caso adicional

                for(int j=0; j<4; j++){
                    if(node.ratonY+mvY[j]<8 && node.ratonY+mvY[j] > -1 && node.ratonX+mvX[j]<8 && node.ratonX+mvX[j] > -1 &&tabla[node.ratonY+mvY[j]][node.ratonX+mvX[j]]==0){
                        f=true;
                        break;
                    }
                }


                node.gatoY[x] -= mvY[i];                //
                node.gatoX[x] -= mvX[i];                // Se recupera el estado antes de la transicion
                node.juegaRaton = !node.juegaRaton;     //

                if(!f){
                    return true;
                }

            }
        }
        return false;

    }

    public void nodeSetVal(){
        if(esRaton){
            node.juegaRaton = playerInTurn == 1;
        }
        else{
            node.juegaRaton = playerInTurn != 1;
        }
        int c = 0;

        for(int i = 0; i< 8; i++){
            for(int x= 0 ; x< 8; x++){
                if(tabla[i][x] == 1){
                    node.ratonY=i;
                    node.ratonX=x;
                }
                else if(tabla[i][x] > 1){
                    node.gatoY[c]=i;
                    node.gatoX[c]=x;
                    c++;
                }
            }
        }
    }

    /*
        Los posibles movimientos en el tablero para el raton son todos y para los gatos los 2 primeros solamente siempre que se
        mantengan en los limites del tablero
     */
    private int mvY[]= new int[]{1,1,-1,-1};
    private int mvX[]= new int[]{1,-1,1,-1};
    /*
        (posX,posY) salva la posiciosion actual de un gato o raton y en
        (smvX,smvY) el movimiento valido tanto para gatos como para el raton
        el cual puede ser usado si no se encontro transicion ganadora
     */
    private int smvX,smvY,posX,posY;

    public int[] jugadaPerfecta (){

        smvY=smvX=posX=posY =-1;
        nodeSetVal();

        if((playerInTurn==1&&esRaton)||(playerInTurn == 2 && !esRaton)){
            boolean f = false;

            for(int i= 0; i< 4; i++){

                node.ratonY+=mvY[i];                    //
                node.ratonX+=mvX[i];                    // Se efectua la transicion
                node.juegaRaton = !node.juegaRaton;     //

                if(node.ratonY>-1 && node.ratonY < 8 && node.ratonX>-1 && node.ratonX < 8 && (tabla[node.ratonY][node.ratonX] == 0)){ // Valida el movimiento

                    boolean b = !gameTree.esGanadora(node);

                    //Movimiento en caso de no encontrar jugada ganadora hasta el momento

                    if(!b && !f &&(!gananEnUnaLosGatos() || posY == -1)) {

                        //Ramdomizando Probabilidad de moverse hacia adelante o hacia detras priorizando los movimientos hacia adelante

                        if( (posY == -1) ||
                                (smvY == 1 && mvY[i]==-1)  ||
                                (!(smvY == -1 && mvY[i] == 1) && random.nextInt()%2 == 0 )) {

                            smvX = mvX[i];                //  SE SALVA LA POSICION ACTUAL
                            smvY = mvY[i];                //  Y LA TRANSICION EN CASO DE NO
                            posY = node.ratonY - smvY;    //  EXISTIR JUGADA GANADORA
                            posX = node.ratonX - smvX;    //
                        }

                    }
                    //En caso de q la jugada actual sea ganadora
                    if(b){
                        if(!f){
                            smvX = mvX[i];                //  SE SALVA LA POSICION ACTUAL
                            smvY = mvY[i];                //  Y LA TRANSICION PARA GUARDAR LA
                            posY = node.ratonY - smvY;    //  PRIMERA JUGADA GANADORA ENCONTRADA
                            posX = node.ratonX - smvX;    //
                        }else if(mvY[i] == -1){

                            //Ramdomizamos el movimiento hacia adelante en caso de ya posser una transicion ganadora hacia adelante

                            if(smvY == -1 && random.nextInt()%2 == 0) {
                                smvX = mvX[i];
                                smvY = mvY[i];
                                posY = node.ratonY - smvY;
                                posX = node.ratonX - smvX;
                            }

                            //
                            if(smvY != -1){
                                smvX = mvX[i];                //  SE SALVA LA POSICION ACTUAL
                                smvY = mvY[i];                //  Y LA TRANSICION SI LA NUEVA
                                posY = node.ratonY - smvY;    //  JUGADA GANADORA ES HACIA
                                posX = node.ratonX - smvX;    //  ADELANTE Y ES EL PRIMER MOVIMIENTO
                                //  GANADOR HACIA ADELANTE
                            }
                        }
                        f=true;
                    }
                }

                node.ratonY-=mvY[i];                //
                node.ratonX-=mvX[i];                // Se recupera el estado antes de la transicion
                node.juegaRaton = !node.juegaRaton; //
            }

            return new int[]{posX, posY, posX + smvX, posY + smvY};// Cuando no existe movimiento ganador
        }

        for(int i= 0; i< 2; i++) {          //Itera en los movimientos

            for (int x = 0; x < 4; x++) {           //Itera en los gatos

                node.gatoY[x] += mvY[i];                //
                node.gatoX[x] += mvX[i];                //  Se efectua la transicion
                node.juegaRaton = !node.juegaRaton;     //

                if (node.gatoY[x] > -1 && node.gatoY[x] < 8 && node.gatoX[x] > -1 && node.gatoX[x] < 8 && (tabla[node.gatoY[x]][node.gatoX[x]] == 0)) {  // Valida el movimiento

                    smvX = mvX[i];                  //  SE SALVA LA POSICION ACTUAL
                    smvY = mvY[i];                  //  Y LA TRANSICION EN CASO DE NO
                    posY = node.gatoY[x] - smvY;    //  EXISTIR JUGADA GANADORA
                    posX = node.gatoX[x] - smvX;    //

                    if (!gameTree.esGanadora(node)) { //SI EL SIGUIENTE ESTADO DE JUEGO ES PERDEDOR PARA EL RIVAL PUES ESTA TRANSICION ES GANADORA
                        return new int[]{posX, posY, posX + smvX, posY + smvY};
                    }
                }

                node.gatoY[x] -= mvY[i];                //
                node.gatoX[x] -= mvX[i];                // Se recupera el estado antes de la transicion
                node.juegaRaton = !node.juegaRaton;     //

            }
        }
        return new int[]{posX, posY, posX + smvX, posY + smvY}; // Cuando no hay mov ganador se devuelve lo salvado y si no existe movimiento devuelvue { -1 , -1 , -1, -1}
    }

    private int temp[];
    public boolean pierdeJugadorActual(){
        nodeSetVal();
        if((playerInTurn == 1 && !esRaton) || (playerInTurn == 2 && esRaton)){

            if(node.ratonY == 0){
                return true;
            }

        }

        temp = jugadaPerfecta();
        for(int i = 0; i< temp.length; i++){
            if(temp[i]>-1)return false;
        }
        return true;
    }


    public int[][] getTablero(){
        return tabla;
    }

    public boolean isReady(){
        return gameTree.isReady();
    }

    public int[] posibleMoves(int Y, int X) {
        int posibleMoves[] = new int[8];
        Arrays.fill(posibleMoves,-1);

        if(tabla[Y][X]==1 && ((esRaton && playerInTurn == 1) || (!esRaton && playerInTurn == 2)) ){
            for(int i=0; i<4; i++){
                if(Y+mvY[i]<8 && Y+mvY[i]>-1 && X+mvX[i] < 8 && X+mvX[i] > -1 && tabla[Y+mvY[i]][X+mvX[i]]==0){
                    posibleMoves[i*2] = Y+mvY[i];
                    posibleMoves[i*2+1] = X+mvX[i];
                }
            }
        }

        else if(tabla[Y][X]>1 && ((esRaton && playerInTurn == 2) || (!esRaton && playerInTurn == 1)) ){
            for(int i=0; i<2; i++){
                if(Y+mvY[i]<8 && Y+mvY[i]>-1 && X+mvX[i] < 8 && X+mvX[i] > -1 && tabla[Y+mvY[i]][X+mvX[i]]==0){
                    posibleMoves[i*2] = Y+mvY[i];
                    posibleMoves[i*2+1] = X+mvX[i];
                }
            }
        }
        Log.d("Possible", Arrays.toString(posibleMoves));
        return posibleMoves;
    }
}
