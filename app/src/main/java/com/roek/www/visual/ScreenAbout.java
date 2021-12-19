package com.roek.www.visual;

import android.text.style.UpdateLayout;

import com.roek.www.framework.Assets;
import com.roek.www.framework.OnTouchHandler;
import com.roek.www.framework.Screen;
import com.roek.www.framework.simple.AndroidMusic;
import java.util.ArrayList;
import java.util.Random;

class ScreenAbout extends Screen {

    private ArrayList<OnTouchHandler.Touch> touches;
    private Random random;
    private AndroidMusic currentMusic;

    public ScreenAbout(Game game) {
        super(game);
        random = new Random();

        if (Assets.ListMusicMainMenu != null) {
            currentMusic = Assets.ListMusicMainMenu.get(Math.abs(random.nextInt()) % Assets.ListMusicMainMenu.size());
            currentMusic.setLooping();
        }
    }

    @Override
    public void update(long deltaTime) {
        touches = game.onTouchHandler.consumeEvents();

        Game.checkIfMutedStateChangedMusic(touches,currentMusic);
        Game.checkIfMutedStateChangedSound(touches);

        if(game.onKeyHandler.getIsBack()){
            game.setCurrentScreen(new MainMenuScreen(game));
        }

      }

    @Override
    public void present(long deltaTime) {
        game.graphics.drawPixmap(Assets.ABOUT_SCREEN, 0, 0);

        Game.paintMutedStateMusic(game);
        Game.paintMutedStateSound(game);
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

    }
}
