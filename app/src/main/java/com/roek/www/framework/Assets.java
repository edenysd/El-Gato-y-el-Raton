package com.roek.www.framework;

import com.roek.www.framework.simple.AndroidMusic;
import com.roek.www.framework.simple.AndroidSound;
import com.roek.www.framework.simple.PixMap;
import com.roek.www.render.Anim;
import com.roek.www.render.AnimatedCharacter;
import com.roek.www.visual.Game;

import java.io.File;
import java.util.ArrayList;

//Aqui se ponen estaticos todos los recursos que cargaremos en nuestra aplicacion, proximamente cambiar a singlentons
public class Assets {

    public static PixMap BTN_MP_PRESS;
    public static Anim IMAGE_MAIN_MENU;
    public static PixMap IMAGE_SPLASH_SCREEN;
    public static PixMap BTN_ACRD;
    public static PixMap BTN_SP;
    public static PixMap BTN_MP;
    public static PixMap TABLERO1;
    public static PixMap BTN_ACRD_PRESS;
    public static PixMap BTN_SP_PRESS;
    public static PixMap TABLERO2;

    public static AnimatedCharacter GATONARANJA;
    public static AnimatedCharacter GATOAZUL;
    public static AnimatedCharacter GATOROJO;
    public static AnimatedCharacter GATOVIOLETA;
    public static AnimatedCharacter RATON;


    public static AndroidSound SELECTIONSOUND1;

    public static PixMap BACKGROUNDGAMETEST1;
    public static PixMap CLOUDTEST1;
    public static Anim ANIMATIONCLOUDTEST1;
    public static PixMap BTN_SELECT_RATON;
    public static PixMap BTN_SELECT_GATO;
    public static PixMap BTN_SELECT_RATON_PRESS;
    public static PixMap BTN_SELECT_GATO_PRESS;
    public static PixMap ABOUT_SCREEN;

    public static ArrayList<Anim> ANIMATION_IN_PLACE_GATO_NARANJA;
    public static ArrayList<Anim> ANIMATION_IN_PLACE_GATO_ROJO;
    public static ArrayList<Anim> ANIMATION_IN_PLACE_GATO_VIOLETA;
    public static ArrayList<Anim> ANIMATION_IN_PLACE_GATO_AZUL;

    public static AndroidMusic Music1;
    public static AndroidMusic Music2;
    public static AndroidMusic Music3;

    public static ArrayList<AndroidMusic> ListMusicMainMenu;
    public static ArrayList<AndroidMusic> ListMusicGame;
    public static ArrayList<AndroidMusic> ListMusicGameOver;

    public static ArrayList<Anim> ANIMATION_OUT_GATO_NARANJA;
    public static ArrayList<Anim> ANIMATION_IN_GATO_NARANJA;
    public static ArrayList<Anim> ANIMATION_OUT_GATO_ROJO;
    public static ArrayList<Anim> ANIMATION_IN_GATO_ROJO;
    public static ArrayList<Anim> ANIMATION_OUT_GATO_VIOLETA;
    public static ArrayList<Anim> ANIMATION_IN_GATO_VIOLETA;
    public static ArrayList<Anim> ANIMATION_OUT_GATO_AZUL;
    public static ArrayList<Anim> ANIMATION_IN_GATO_AZUL;

    public static PixMap SELECTED_CELL_MOVE_MARK;
    public static PixMap SELECTED_CELL_MARK;

    public static PixMap CARTEL_ALERTA1;

    public static ArrayList<Anim> ANIMATION_IN_PLACE_RATON;
    public static ArrayList<Anim> ANIMATION_OUT_RATON;
    public static ArrayList<Anim> ANIMATION_IN_RATON;
    public static AndroidSound SELECTIONSOUNDRATON;
    public static AndroidSound SELECTIONSOUNDGATO;
    public static PixMap GAME_OVER_GANA_GATO;
    public static PixMap GAME_OVER_GANA_RATON;
    public static PixMap SELECT_CHARACTER;
    public static PixMap MAIN_LOGO;
    public static PixMap MUSIC_ON;
    public static PixMap MUSIC_OFF;
    public static PixMap SFX_ON;
    public static PixMap SFX_OFF;

    public static void LOAD_MAIN_MENU_RECOURCES(Game game) {
        Assets.IMAGE_MAIN_MENU = new Anim(game.graphics.loadPixmap("images" + File.separator + "main_background.png", (int) Game.defaultWidth*2, (int)Game.defaultHeight), 40000000000L);
        Assets.ABOUT_SCREEN = game.graphics.loadPixmap("images" + File.separator + "screen_about.png", 720, 1280);

        Assets.MAIN_LOGO = game.graphics.loadPixmap("images" + File.separator + "main_logo.png", 400 , 600);
        Assets.SELECT_CHARACTER = game.graphics.loadPixmap("images" + File.separator + "select_character.png", 500 , 590);


        Assets.BTN_ACRD = game.graphics.loadPixmap("images" + File.separator + "btn_acrd.png", 500 , 100);
        Assets.BTN_MP = game.graphics.loadPixmap("images" + File.separator + "btn_mp.png", 500 , 100);
        Assets.BTN_SP = game.graphics.loadPixmap("images" + File.separator + "btn_sp.png", 500 , 100);
        Assets.BTN_ACRD_PRESS = game.graphics.loadPixmap("images" + File.separator + "btn_acrd_press.png", 500 , 100);
        Assets.BTN_MP_PRESS = game.graphics.loadPixmap("images" + File.separator + "btn_mp_press.png", 500 , 100);
        Assets.BTN_SP_PRESS = game.graphics.loadPixmap("images" + File.separator + "btn_sp_press.png", 500 , 100);

        Assets.BTN_SELECT_GATO = game.graphics.loadPixmap("images" + File.separator + "selectgato.png", 250, 250);
        Assets.BTN_SELECT_RATON = game.graphics.loadPixmap("images" + File.separator + "selectraton.png", 250, 250);
        Assets.BTN_SELECT_GATO_PRESS = game.graphics.loadPixmap("images" + File.separator + "selectgato_press.png", 250, 250);
        Assets.BTN_SELECT_RATON_PRESS = game.graphics.loadPixmap("images" + File.separator + "selectraton_press.png", 250, 250);

        Assets.MUSIC_ON = game.graphics.loadPixmap("images" + File.separator + "music_on.png", 100, 100);
        Assets.MUSIC_OFF = game.graphics.loadPixmap("images" + File.separator + "music_off.png", 100, 100);
        Assets.SFX_ON = game.graphics.loadPixmap("images" + File.separator + "sfx_on.png", 100, 100);
        Assets.SFX_OFF = game.graphics.loadPixmap("images" + File.separator + "sfx_off.png", 100, 100);

    }
    public static void LOAD_GAME_RECOURCES(Game game){
        Assets.GAME_OVER_GANA_GATO = game.graphics.loadPixmap("images" + File.separator + "cartel_gameover_gana_gato.png", (int) Game.defaultWidth, (int)Game.defaultHeight);

        Assets.GAME_OVER_GANA_RATON = game.graphics.loadPixmap("images" + File.separator + "cartel_gameover_gana_raton.png", (int) Game.defaultWidth, (int)Game.defaultHeight);

        Assets.CARTEL_ALERTA1 = game.graphics.loadPixmap("images" + File.separator + "cartel_alerta1.png", 500 , 300);

        Assets.SELECTED_CELL_MARK = game.graphics.loadPixmap( "images" + File.separator + "marcado.png", 90 , 90);
        Assets.SELECTED_CELL_MOVE_MARK = game.graphics.loadPixmap( "images" + File.separator + "pata.png", 90 , 90);

        Assets.TABLERO2 = game.graphics.loadPixmap("images" + File.separator + "tablero2.png", 720 , 720);

        Assets.CLOUDTEST1 = game.graphics.loadPixmap("images" + File.separator + "cloudanimtest2.png", 720*8 , 720);
        Assets.ANIMATIONCLOUDTEST1 = new Anim(Assets.CLOUDTEST1, 120000000000L);

        //ANIMACIONES GATOS

        //AZUL
        Assets.ANIMATION_IN_PLACE_GATO_AZUL = new ArrayList<Anim>();
        Assets.ANIMATION_IN_PLACE_GATO_AZUL.add(new Anim(game.graphics.loadPixmap("images" + File.separator + "animation1_azul_5400x100.png",4860,90),3000000000L));
        Assets.ANIMATION_IN_PLACE_GATO_AZUL.add(new Anim(game.graphics.loadPixmap("images" + File.separator + "bano_azul_5400x100.png",4860,90),3000000000L));
        Assets.ANIMATION_IN_PLACE_GATO_AZUL.add(new Anim(game.graphics.loadPixmap("images" + File.separator + "dormido_azul_5400x100.png",4860,90),6000000000L));

        Assets.ANIMATION_OUT_GATO_AZUL = new ArrayList<>();
        Assets.ANIMATION_OUT_GATO_AZUL.add(new Anim(game.graphics.loadPixmap("images" + File.separator + "trans1_out_azul_1800x100.png",1620,90),500000000L));

        Assets.ANIMATION_IN_GATO_AZUL = new ArrayList<>();
        Assets.ANIMATION_IN_GATO_AZUL.add(new Anim(game.graphics.loadPixmap("images" + File.separator + "trans1_in_azul_1800x100.png",1620,90),500000000L));


        //NARANJA
        Assets.ANIMATION_IN_PLACE_GATO_NARANJA = new ArrayList<Anim>();
        Assets.ANIMATION_IN_PLACE_GATO_NARANJA.add(new Anim(game.graphics.loadPixmap("images" + File.separator + "animation1_naranja_5400x100.png",4860,90),3000000000L));
        Assets.ANIMATION_IN_PLACE_GATO_NARANJA.add(new Anim(game.graphics.loadPixmap("images" + File.separator + "bano_naranja_5400x100.png",4860,90),3000000000L));
        Assets.ANIMATION_IN_PLACE_GATO_NARANJA.add(new Anim(game.graphics.loadPixmap("images" + File.separator + "dormido_naranja_5400x100.png",4860,90),6000000000L));

        Assets.ANIMATION_OUT_GATO_NARANJA = new ArrayList<>();
        Assets.ANIMATION_OUT_GATO_NARANJA.add(new Anim(game.graphics.loadPixmap("images" + File.separator + "trans1_out_naranja_1800x100.png",1620,90),500000000L));

        Assets.ANIMATION_IN_GATO_NARANJA = new ArrayList<>();
        Assets.ANIMATION_IN_GATO_NARANJA.add(new Anim(game.graphics.loadPixmap("images" + File.separator + "trans1_in_naranja_1800x100.png",1620,90),500000000L));


        //ROJO
        Assets.ANIMATION_IN_PLACE_GATO_ROJO = new ArrayList<Anim>();
        Assets.ANIMATION_IN_PLACE_GATO_ROJO.add(new Anim(game.graphics.loadPixmap("images" + File.separator + "animation1_rojo_5400x100.png",4860,90),3000000000L));
        Assets.ANIMATION_IN_PLACE_GATO_ROJO.add(new Anim(game.graphics.loadPixmap("images" + File.separator + "bano_rojo_5400x100.png",4860,90),3000000000L));
        Assets.ANIMATION_IN_PLACE_GATO_ROJO.add(new Anim(game.graphics.loadPixmap("images" + File.separator + "dormido_rojo_5400x100.png",4860,90),6000000000L));

        Assets.ANIMATION_OUT_GATO_ROJO = new ArrayList<>();
        Assets.ANIMATION_OUT_GATO_ROJO.add(new Anim(game.graphics.loadPixmap("images" + File.separator + "trans1_out_rojo_1800x100.png",1620,90),500000000L));

        Assets.ANIMATION_IN_GATO_ROJO = new ArrayList<>();
        Assets.ANIMATION_IN_GATO_ROJO.add(new Anim(game.graphics.loadPixmap("images" + File.separator + "trans1_in_rojo_1800x100.png",1620,90),500000000L));

        //VIOLETA
        Assets.ANIMATION_IN_PLACE_GATO_VIOLETA = new ArrayList<Anim>();
        Assets.ANIMATION_IN_PLACE_GATO_VIOLETA.add(new Anim(game.graphics.loadPixmap("images" + File.separator + "animation1_violeta_5400x100.png",4860,90),3000000000L));
        Assets.ANIMATION_IN_PLACE_GATO_VIOLETA.add(new Anim(game.graphics.loadPixmap("images" + File.separator + "bano_violeta_5400x100.png",4860,90),3000000000L));
        Assets.ANIMATION_IN_PLACE_GATO_VIOLETA.add(new Anim(game.graphics.loadPixmap("images" + File.separator + "dormido_violeta_5400x100.png",4860,90),6000000000L));

        Assets.ANIMATION_OUT_GATO_VIOLETA = new ArrayList<>();
        Assets.ANIMATION_OUT_GATO_VIOLETA.add(new Anim(game.graphics.loadPixmap("images" + File.separator + "trans1_out_violeta_1800x100.png",1620,90),500000000L));

        Assets.ANIMATION_IN_GATO_VIOLETA = new ArrayList<>();
        Assets.ANIMATION_IN_GATO_VIOLETA.add(new Anim(game.graphics.loadPixmap("images" + File.separator + "trans1_in_violeta_1800x100.png",1620,90),500000000L));


        //ANIMACIONES RATON

        Assets.ANIMATION_IN_PLACE_RATON = new ArrayList<Anim>();
        Assets.ANIMATION_IN_PLACE_RATON.add(new Anim(game.graphics.loadPixmap("images" + File.separator + "animation_estatica_raton_5400x100.png",4860,90),3000000000L));

        Assets.ANIMATION_OUT_RATON = new ArrayList<>();
        Assets.ANIMATION_OUT_RATON.add(new Anim(game.graphics.loadPixmap("images" + File.separator + "trans1_out_raton_1800x100.png",1620,90),500000000L));

        Assets.ANIMATION_IN_RATON = new ArrayList<>();
        Assets.ANIMATION_IN_RATON.add(new Anim(game.graphics.loadPixmap("images" + File.separator + "trans1_in_raton_1900x100.png",1710,90),500000000L));

        //Crear los Animated Character para todos los caracteres
        Assets.GATONARANJA = new AnimatedCharacter(Assets.ANIMATION_IN_PLACE_GATO_NARANJA, Assets.ANIMATION_OUT_GATO_NARANJA ,Assets.ANIMATION_IN_GATO_NARANJA);
        Assets.GATOAZUL = new AnimatedCharacter(Assets.ANIMATION_IN_PLACE_GATO_AZUL, Assets.ANIMATION_OUT_GATO_AZUL ,Assets.ANIMATION_IN_GATO_AZUL);
        Assets.GATOVIOLETA = new AnimatedCharacter(Assets.ANIMATION_IN_PLACE_GATO_VIOLETA, Assets.ANIMATION_OUT_GATO_VIOLETA ,Assets.ANIMATION_IN_GATO_VIOLETA);
        Assets.GATOROJO = new AnimatedCharacter(Assets.ANIMATION_IN_PLACE_GATO_ROJO, Assets.ANIMATION_OUT_GATO_ROJO,Assets.ANIMATION_IN_GATO_ROJO);
        Assets.RATON = new AnimatedCharacter(Assets.ANIMATION_IN_PLACE_RATON, Assets.ANIMATION_OUT_RATON ,Assets.ANIMATION_IN_RATON);

        Assets.BACKGROUNDGAMETEST1 = game.graphics.loadPixmap("images" + File.separator + "backgroundgametest1.png", (int) Game.defaultWidth, (int)Game.defaultHeight);

        //MUSICA
        //Musica para el MainMenu
        Assets.Music1 = game.sound.loadStreamMusic("sounds" + File.separator + "music1.mp3",0.2f);

        Assets.ListMusicMainMenu = new ArrayList<>();
        Assets.ListMusicMainMenu.add(Assets.Music1);

        //Musica para el GameScreen

        Assets.Music2 = game.sound.loadStreamMusic("sounds" + File.separator + "music2.mp3", 0.2f);

        Assets.ListMusicGame = new ArrayList<>();
        Assets.ListMusicGame.add(Assets.Music2);

        //Musica para el GameOverScreen

        Assets.Music3 = game.sound.loadStreamMusic("sounds" + File.separator + "music3.mp3", 0.2f);

        Assets.ListMusicGameOver = new ArrayList<>();
        Assets.ListMusicGameOver.add(Assets.Music3);
    }

}
