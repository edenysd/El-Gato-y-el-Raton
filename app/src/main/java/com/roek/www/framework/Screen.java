package com.roek.www.framework;

import com.roek.www.framework.simple.AndroidMusic;
import com.roek.www.visual.Game;

import java.util.Random;

public abstract class Screen{
    protected Game game;

    public Screen(Game game){
        this.game = game;

    }

    public abstract void update(long deltaTime);

    public abstract void present(long deltaTime);

    public abstract void onPause();


    public abstract void onResume();

    public abstract void dispose();

}
