package com.roek.www.visual;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

import com.roek.www.framework.Assets;
import com.roek.www.framework.OnTouchHandler;
import com.roek.www.framework.Screen;
import com.roek.www.framework.simple.AndroidMusic;
import com.roek.www.framework.simple.PixMap;
import com.roek.www.gamelogic.GameState;
import com.roek.www.render.AnimatedCharacter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class GameScreen extends Screen {



    enum Transition{
        Esperando, Moviendo, GameOver
    }

    private Transition TRANSITION;


    private Game game;

    private MainMenuScreen.GameMode GAME_MODE;
    private GameState gameState;
    private AndroidMusic currentMusic;
    private Random random;
    private LinkedList<int[][]> jugadas;
    private int currentX = -1;
    private int currentY = -1;
    private boolean estaEsperandoAtras;
    private boolean juegaRaton;
    private int posibleMoves[] = {-1, -1, -1, -1, -1, -1, -1, -1};
    private static ArrayList<OnTouchHandler.Touch> touches;

    public GameScreen(Game game, MainMenuScreen.GameMode GAME_MODE, boolean juegaRaton){
        super(game);
        this.game = game;
        this.GAME_MODE = GAME_MODE;
        this.juegaRaton = juegaRaton;

        jugadas = new LinkedList<>();
        estaEsperandoAtras = false;
        random = new Random();
        currentMusic = Assets.ListMusicGame.get(Math.abs(random.nextInt()) % Assets.ListMusicGame.size());
        currentMusic.setLooping();

        gameState = new GameState( juegaRaton );
        tablero = gameState.getTablero();

        Assets.GATOVIOLETA.setMoving();
        Assets.GATOAZUL.setMoving();
        Assets.GATONARANJA.setMoving();
        Assets.GATOROJO.setMoving();
        Assets.RATON.setMoving();

        TRANSITION = Transition.Moviendo;
    }


    private int touchX, touchY, cellX, cellY;
    //touchX, touchY son las coordenadas de un evento Touch y cellX, cellY son las coordenadas en el tablero de la casilla q le
    //corresponde a este toque, se calcula en la funcion detectCell()
    private void detectCell(){

        if(touchY < Game.defaultMarginTop || touchY > Game.defaultMarginTop + Game.defaultCellSize*8)return;

        for(int i = 0; i<=8; i++){
            if(touchX < i * Game.defaultCellSize){
                cellX = i-1;
                break;
            }
        }

        for(int i = 0; i<=8; i++){
            if(touchY < i * Game.defaultCellSize + Game.defaultMarginTop){
                cellY = i-1;
                break;
            }
        }


        if(tablero[cellY][cellX] == 1){
            playSelectionSoundRaton();
        }

        if(tablero[cellY][cellX] > 1){
            playSelectionSoundGato();
        }

        posibleMoves = gameState.posibleMoves(cellY,cellX);

    }

    private void playSelectionSoundGato(){
        Assets.SELECTIONSOUNDGATO.play();
    }

    private void playSelectionSoundRaton(){
        Assets.SELECTIONSOUNDRATON.play();
    }

    private int nextMov[];
    private int tablero[][];

    @Override
    public synchronized void update(long deltaTime) {

        touches = game.onTouchHandler.consumeEvents();

        Game.checkIfMutedStateChangedMusic(touches,currentMusic);
        Game.checkIfMutedStateChangedSound(touches);

        if(TRANSITION == Transition.GameOver){
            game.graphics.drawRect(new Rect(190,676,290,776));
            game.graphics.drawRect(new Rect(300,676,400,776));
            game.graphics.drawRect(new Rect(410,676,510,776));

            for(int i = 0 ; i<touches.size(); i++){
                //Ir a Main Menu
                if(touches.get(i).x>190 && touches.get(i).x<290 && touches.get(i).y>676 &&  touches.get(i).y < 776){
                    game.setCurrentScreen(new MainMenuScreen(game ));
                    return;
                }
                //Reiniciar
                else if(touches.get(i).x>300 && touches.get(i).x<400 && touches.get(i).y>676 &&  touches.get(i).y < 776){
                    game.setCurrentScreen(new GameScreen(game, GAME_MODE , juegaRaton ));
                    return;
                }
                //Detalles
                else if(touches.get(i).x>410 && touches.get(i).x<510 && touches.get(i).y>676 &&  touches.get(i).y < 776){
                    game.setCurrentScreen(new GameDetailsScreen(game,jugadas));
                    return;
                }

            }

        }

        boolean isBack = game.onKeyHandler.getIsBack();
        if( isBack || estaEsperandoAtras) {
            if( isBack && estaEsperandoAtras){
                estaEsperandoAtras = false;
            }
            else if(isBack && !estaEsperandoAtras){
                estaEsperandoAtras = true;
            }else if(!isBack && estaEsperandoAtras){
                processTouchesOnBackDialog();
            }

            return;
        }

        if(!gameState.isReady()) {
            return;
        }

        if(TRANSITION == Transition.Esperando && gameState.pierdeJugadorActual()){
            TRANSITION = Transition.GameOver;
            return;
        }

        if(TRANSITION == Transition.Moviendo){
            if(!Assets.GATOAZUL.isMoving() && !Assets.GATONARANJA.isMoving() && !Assets.GATOROJO.isMoving() && !Assets.GATOVIOLETA.isMoving() && ! Assets.RATON.isMoving()){
                TRANSITION = Transition.Esperando;
                guardarNuevoEstado();
            }
            else if(nextMov == null){
                //DO NOTHING
            }
            else if(Assets.GATONARANJA.isInApear() && gameState.getTablero()[nextMov[3]][nextMov[2]] != 2){
                gameState.setMove(nextMov[0],nextMov[1],nextMov[2],nextMov[3]);
            }

            else if(Assets.GATOAZUL.isInApear() && gameState.getTablero()[nextMov[3]][nextMov[2]] != 3){
                gameState.setMove(nextMov[0],nextMov[1],nextMov[2],nextMov[3]);
            }

            else if(Assets.GATOROJO.isInApear() && gameState.getTablero()[nextMov[3]][nextMov[2]] != 4){
                gameState.setMove(nextMov[0],nextMov[1],nextMov[2],nextMov[3]);
            }

            else if(Assets.GATOVIOLETA.isInApear() && gameState.getTablero()[nextMov[3]][nextMov[2]] != 5){
                gameState.setMove(nextMov[0],nextMov[1],nextMov[2],nextMov[3]);
            }

            else if(Assets.RATON.isInApear() && gameState.getTablero()[nextMov[3]][nextMov[2]] != 1){
                gameState.setMove(nextMov[0],nextMov[1],nextMov[2],nextMov[3]);
            }

        }

        //Juega Humano
        else if(TRANSITION == Transition.Esperando && ( (GAME_MODE == MainMenuScreen.GameMode.Multiplayer) || (GAME_MODE == MainMenuScreen.GameMode.SinglePlayer && GameState.playerInTurn == 1))){
            for(int i=0; i<touches.size(); i++){
                touchX=touches.get(i).x;
                touchY=touches.get(i).y;

                detectCell();

                int lastX = currentX;
                int lastY = currentY;

                currentX = cellX;
                currentY = cellY;

                if(gameState.isValidMove(lastX, lastY, currentX, currentY)){
                    TRANSITION = Transition.Moviendo;
                    getAnimatedCharacterById(gameState.getTablero()[lastY][lastX]).setMoving();
                    nextMov = new int[]{lastX, lastY, currentX, currentY};
                    currentX = currentY = -1;
                }
            }
        }
        //Juega PC
        else if(TRANSITION == Transition.Esperando &&  (GAME_MODE == MainMenuScreen.GameMode.SinglePlayer && GameState.playerInTurn == 2)){
            nextMov = gameState.jugadaPerfecta();
            Log.d("IA", Arrays.toString(nextMov));
            getAnimatedCharacterById(gameState.getTablero()[nextMov[1]][nextMov[0]]).setMoving();
            TRANSITION = Transition.Moviendo;
        }


    }

    private void processTouchesOnBackDialog() {
        for(int i=0; i<touches.size(); i++){
            if(pressedBack(i)){
                estaEsperandoAtras = false;
                break;
            }
            if(pressedOver(i)){
                game.setCurrentScreen(new MainMenuScreen(game));
                break;
            }
        }
    }
    /*
        todo
        Dice si el i-esimo touch presiona back;
     */
    private boolean pressedOver(int i) {
        return touches.get(i).x > 215 && touches.get(i).x < 325 && touches.get(i).y > 375 + Game.defaultMarginTop && touches.get(i).y < 390 + Game.defaultMarginTop + 90;
    }

    /*
        Dice si el i-esimo touch presiona over;
     */
    private boolean pressedBack(int i) {
        return touches.get(i).x > 390 && touches.get(i).x < 500 && touches.get(i).y > 375 + Game.defaultMarginTop && touches.get(i).y < 390 + Game.defaultMarginTop + 90;
    }

    private void guardarNuevoEstado() {
        int [][]t = new int[8][8];
        for(int i=0; i<8; i++) {
            t[i] = (Arrays.copyOf(tablero[i], tablero[i].length));
        }
        jugadas.add(t);
    }


    public static AnimatedCharacter getAnimatedCharacterById(int id){
        if(id == 1)
            return Assets.RATON;

        if(id == 2)
            return Assets.GATONARANJA;

        if(id == 3)
            return Assets.GATOAZUL;

        if(id == 4)
            return Assets.GATOROJO;

        if(id == 5)
            return Assets.GATOVIOLETA;

        return null;

    }

    private Rect temp;

    @Override
    public synchronized void present(long deltaTime) {
        game.graphics.drawPixmap(Assets.BACKGROUNDGAMETEST1, 0 , 0);

        game.graphics.drawPixmap(Assets.TABLERO2,0,280);

        game.graphics.drawPixmap(Assets.SELECTED_CELL_MARK , currentX*Game.defaultCellSize, currentY*Game.defaultCellSize+Game.defaultMarginTop);

        for(int i= 0; i<tablero.length; i++){
             for(int x = 0 ; x<tablero.length ; x++){
                 if(tablero[i][x]!=0) {
                     temp = getAnimatedCharacterById(tablero[i][x]).getCurrentAnimSpriteRect(deltaTime);
                     game.graphics.drawPixmap(getAnimatedCharacterById(tablero[i][x]).getCurrentAnimPixmap(), temp, x * Game.defaultCellSize, Game.defaultMarginTop + (i) * Game.defaultCellSize);
                 }
             }
         }

        for(int i=0; i<4; i++){
            if(posibleMoves[i*2]!=-1){
                game.graphics.drawPixmap(Assets.SELECTED_CELL_MOVE_MARK , posibleMoves[i*2+1]*Game.defaultCellSize , posibleMoves[i*2]*Game.defaultCellSize + Game.defaultMarginTop);
            }
        }

        game.graphics.drawPixmap(Assets.ANIMATIONCLOUDTEST1.getSprite() , Assets.ANIMATIONCLOUDTEST1.getRectSpriteInLine(deltaTime, Assets.ANIMATIONCLOUDTEST1.getSprite().getHeight()), 0, 280);

        if(estaEsperandoAtras){
            game.graphics.drawPixmap(Assets.CARTEL_ALERTA1 , 110,470);
        }

        if(TRANSITION == Transition.GameOver){

             if((juegaRaton && GameState.playerInTurn == 1) || (!juegaRaton && GameState.playerInTurn == 2))
                 game.graphics.drawPixmap(Assets.GAME_OVER_GANA_GATO,0,0);
             else
                 game.graphics.drawPixmap(Assets.GAME_OVER_GANA_RATON,0,0);

         }
        Game.paintMutedStateMusic(game);
        Game.paintMutedStateSound(game);
    }

    @Override
    public void onPause() {
        currentMusic.pause();
    }

    @Override
    public void onResume() {
        currentMusic.play();
    }

    @Override
    public void dispose() {
        currentMusic.stop();
        currentMusic = null;

    }
}
