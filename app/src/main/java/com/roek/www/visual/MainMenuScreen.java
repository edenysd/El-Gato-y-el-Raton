package com.roek.www.visual;


import android.graphics.Rect;

import com.roek.www.framework.Assets;
import com.roek.www.framework.OnTouchHandler;
import com.roek.www.framework.Screen;
import com.roek.www.framework.simple.AndroidMusic;
import com.roek.www.framework.simple.PixMap;

import java.util.ArrayList;
import java.util.Random;

public class MainMenuScreen extends Screen {

    public enum GameMode {
        Esperando, About, SinglePlayer, Multiplayer
    }

    private GameMode GAME_MODE = GameMode.Esperando;
    private long TIME_TRANSITION = 300000000L;
    private static ArrayList<OnTouchHandler.Touch> touches;

    private PixMap selectedItem;
    private boolean TRANSITION=false;
    private long currentTimeTransition = 300000000L;
    private GameMode gameModeTransition;
    private Random random;
    private AndroidMusic currentMusic;
    public MainMenuScreen(Game game) {
        super(game);
        random = new Random();

        if(Assets.ListMusicMainMenu != null) {
            currentMusic = Assets.ListMusicMainMenu.get(Math.abs(random.nextInt()) % Assets.ListMusicMainMenu.size());
            currentMusic.setLooping();
        }
    }

    @Override
    public void update(long deltaTime) {


            if(game.onKeyHandler.getIsBack()) {
                if (GAME_MODE == GameMode.Esperando)
                    game.finish();
                else {
                    GAME_MODE = GameMode.Esperando;
                }
            }

            touches = game.onTouchHandler.consumeEvents();

            Game.checkIfMutedStateChangedMusic(touches,currentMusic);
            Game.checkIfMutedStateChangedSound(touches);

            if(checkTransition(deltaTime))return;

            if(GAME_MODE == GameMode.Esperando)
                for (int i=0; i<touches.size(); i++){
                    if (touches.get(i).x > 111 && touches.get(i).x <610 && touches.get(i).y > 750 && touches.get(i).y < 850){
                        //Eligio UN JUGADOR
                        gameModeTransition = GameMode.SinglePlayer;
                        TRANSITION = true;
                        selectedItem = Assets.BTN_SP_PRESS;
                        playSelectionSound();
                    }
                    else if (touches.get(i).x > 111 && touches.get(i).x <610 && touches.get(i).y > 900 && touches.get(i).y < 1000){
                        //Eligio MULTIJUGADOR
                        gameModeTransition = GameMode.Multiplayer;
                        TRANSITION = true;
                        selectedItem = Assets.BTN_MP_PRESS;
                        playSelectionSound();
                    }
                    else if (touches.get(i).x > 111 && touches.get(i).x <610 && touches.get(i).y > 1050 && touches.get(i).y < 1150){
                        //Eligio MOSTRAR ACERCA...
                        gameModeTransition = GameMode.About;
                        TRANSITION = true;
                        selectedItem = Assets.BTN_ACRD_PRESS;
                        playSelectionSound();
                        //POR PROGRAMAR
                    }
                }
            else if(GAME_MODE == GameMode.About){
                game.setCurrentScreen(new ScreenAbout(game));
            }
            //GAME_MODE es 1 o 2, es decir que el usuario quiere jugar
            else {
                for (int i=0; i<touches.size(); i++){
                    boolean JUEGA_RATON = false;
                    if (touches.get(i).x > 390 && touches.get(i).x <640 && touches.get(i).y > 830 && touches.get(i).y < 1080){
                        //Eligio jugar Raton
                        JUEGA_RATON = true;
                        playSelectionSoundRaton();
                    }
                    else if (touches.get(i).x > 90 && touches.get(i).x <340 && touches.get(i).y > 830 && touches.get(i).y < 1080) {
                        //Eligio Jugar Gato
                        JUEGA_RATON = false;
                        playSelectionSoundGato();
                    } else { continue; }

                    game.setCurrentScreen(new GameScreen(game, GAME_MODE, JUEGA_RATON));
                    break;
                }
            }
    }

    private boolean checkTransition(long deltaTime) {
        if(TRANSITION){
            currentTimeTransition -= deltaTime;
            if(currentTimeTransition<=0){
                TRANSITION = false;
                currentTimeTransition = TIME_TRANSITION;
                GAME_MODE = gameModeTransition;
                selectedItem = null;
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public void present(long deltaTime) {
            if(GAME_MODE==GameMode.Esperando) {
                game.graphics.drawPixmap(Assets.IMAGE_MAIN_MENU.getSprite(),Assets.IMAGE_MAIN_MENU.getRectSpriteInLine(deltaTime, (int) Game.defaultWidth), 0, 0);
                game.graphics.drawPixmap(Assets.MAIN_LOGO ,160,100);

                game.graphics.drawPixmap(Assets.BTN_SP_PRESS == selectedItem ? Assets.BTN_SP_PRESS : Assets.BTN_SP, 110, 750);
                game.graphics.drawPixmap(Assets.BTN_MP_PRESS == selectedItem ? Assets.BTN_MP_PRESS : Assets.BTN_MP, 110, 900);
                game.graphics.drawPixmap(Assets.BTN_ACRD_PRESS == selectedItem ? Assets.BTN_ACRD_PRESS : Assets.BTN_ACRD, 110, 1050);
            }
            else {
                game.graphics.drawPixmap(Assets.IMAGE_MAIN_MENU.getSprite(),Assets.IMAGE_MAIN_MENU.getRectSpriteInLine(deltaTime, (int) Game.defaultWidth), 0, 0);
                game.graphics.drawPixmap(Assets.SELECT_CHARACTER , 110, 180);
                game.graphics.drawPixmap(Assets.BTN_SELECT_GATO, 100, 830);
                game.graphics.drawPixmap(Assets.BTN_SELECT_RATON, 400, 830);
            }

        Game.paintMutedStateMusic(game);
        Game.paintMutedStateSound(game);
    }

    private void playSelectionSound(){
        if(Assets.SELECTIONSOUND1 != null)
            Assets.SELECTIONSOUND1.play();
    }

    private void playSelectionSoundGato(){
        if(Assets.SELECTIONSOUNDGATO != null)
           Assets.SELECTIONSOUNDGATO.play();
    }

    private void playSelectionSoundRaton(){
        if(Assets.SELECTIONSOUNDRATON != null)
            Assets.SELECTIONSOUNDRATON.play();
    }

    @Override
    public void onPause() {
        if(currentMusic != null)
            currentMusic.pause();
    }

    @Override
    public void onResume() {
        if(currentMusic != null)
            currentMusic.play();
    }

    @Override
    public void dispose() {

        touches = null;
        currentMusic.stop();
        currentMusic = null;
    }
}
