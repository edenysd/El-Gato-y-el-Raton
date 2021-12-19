package com.roek.www.visual;

import android.util.Log;

import com.roek.www.framework.Assets;
import com.roek.www.framework.Screen;
import com.roek.www.framework.simple.AndroidMusic;
import com.roek.www.framework.simple.PixMap;
import com.roek.www.gamelogic.GameTree;
import com.roek.www.render.Anim;
import com.roek.www.render.AnimatedCharacter;

import java.io.File;
import java.util.ArrayList;

public class SplashScreen extends Screen {

    public SplashScreen(Game game) {
        super(game);
    }


    //Aqui cargaremos los assets
    @Override
    public void update(long deltaTime) {

        GameTree.cargar(game);
        Assets.SELECTIONSOUND1 = game.sound.loadSimpleSound("sounds" + File.separator + "selectionsound1.mp3", 0.1f);
        Assets.SELECTIONSOUNDGATO = game.sound.loadSimpleSound("sounds" + File.separator + "sound_select_gato.mp3",0.5f);
        Assets.SELECTIONSOUNDRATON = game.sound.loadSimpleSound("sounds" + File.separator + "sound_select_raton.mp3", 0.1f);


        Assets.LOAD_GAME_RECOURCES(game);
        Assets.LOAD_MAIN_MENU_RECOURCES(game);

        game.onTouchHandler.consumeEvents();


        game.setCurrentScreen(new MainMenuScreen(game));

    }

    @Override
    public void present(long deltaTime) {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void dispose() {
        Assets.IMAGE_SPLASH_SCREEN=null;
    }
}
