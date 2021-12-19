package com.roek.www.visual;

import android.graphics.Rect;
import android.util.Log;

import com.roek.www.framework.Assets;
import com.roek.www.framework.OnTouchHandler;
import com.roek.www.framework.Screen;
import com.roek.www.framework.simple.AndroidMusic;
import com.roek.www.framework.simple.PixMap;
import com.roek.www.gamelogic.GameState;
import com.roek.www.gamelogic.GameTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

class GameDetailsScreen extends Screen {

    LinkedList<int[][]> jugadas;
    private static ArrayList<OnTouchHandler.Touch> touches;
    private int I;
    private AndroidMusic currentMusic;
    private Random random;

    public GameDetailsScreen(Game game, LinkedList<int[][]> jugadas) {
        super(game);
        this.jugadas = jugadas;
        this.random = new Random();
        I=0;

        currentMusic = Assets.ListMusicGameOver.get(Math.abs(random.nextInt()) % Assets.ListMusicGameOver.size());
        currentMusic.setLooping();
    }
    int tablero[][];
    @Override
    public void update(long deltaTime) {

        touches = game.onTouchHandler.consumeEvents();
        Game.checkIfMutedStateChangedMusic(touches,currentMusic);
        Game.checkIfMutedStateChangedSound(touches);
        if(game.onKeyHandler.getIsBack()){
            game.setCurrentScreen(new MainMenuScreen(game));
            return;
        }

        for(int i=0; i<touches.size(); i++){
            if(isRigth(i)){
                if(I<jugadas.size()-1)
                    I++;
            }
            if(isLeft(i)){
                if(I>0)
                    I--;
            }

        }

        tablero = jugadas.get(jugadas.size()-I-1);
    }

    private boolean isLeft(int i) {
        return touches.get(i).x <= 360 && touches.get(i).y > 100;
    }

    private boolean isRigth(int i) {
        return touches.get(i).x > 360 && touches.get(i).y > 100;
    }

    private Rect temp;

    @Override
    public void present(long deltaTime) {
        game.graphics.drawPixmap(Assets.BACKGROUNDGAMETEST1, 0 , 0);

        game.graphics.drawPixmap(Assets.TABLERO2,0,280);

        for(int i= 0; i<tablero.length; i++){
            for(int x = 0 ; x<tablero.length ; x++){
                if(tablero[i][x]!=0) {
                    temp = GameScreen.getAnimatedCharacterById(tablero[i][x]).getCurrentAnimSpriteRect(deltaTime);
                    game.graphics.drawPixmap(GameScreen.getAnimatedCharacterById(tablero[i][x]).getCurrentAnimPixmap(), temp, x * Game.defaultCellSize, Game.defaultMarginTop + (i) * Game.defaultCellSize);
                }
            }
        }


        game.graphics.drawPixmap(Assets.ANIMATIONCLOUDTEST1.getSprite() , Assets.ANIMATIONCLOUDTEST1.getRectSpriteInLine(deltaTime,Assets.ANIMATIONCLOUDTEST1.getSprite().getHeight()), 0, 280);

        game.graphics.drawText("Jugada #" + (jugadas.size() - I) , 360 , 160);
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
        jugadas = null;
        currentMusic.stop();
        currentMusic = null;
    }
}
