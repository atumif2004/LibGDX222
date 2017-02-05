package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by bezvi on 29.01.2017.
 */

public class Resource {


    private static  TextureAtlas atl;
    public  static Button.ButtonStyle button, p_button, m_button;


    public static Sprite player, bomb, start, pause, sound, drop;

    public static   void load(){
        atl = new TextureAtlas(Gdx.files.internal("textures/text.atlas"));

        player = new Sprite(atl.findRegion("hero"));
        bomb = new Sprite(atl.findRegion("bomb"));
        start = new Sprite(atl.findRegion("Start"));
       // pause = new Sprite(atl.findRegion("black-pause-64"));
        sound = new Sprite(atl.findRegion("sound53"));
        drop = new Sprite(atl.findRegion("555"));


        Skin skin = new Skin();
        skin.addRegions(new TextureAtlas(Gdx.files.internal("textures/GuiAT.atlas")));

        p_button = new Button.ButtonStyle();
        p_button.up = skin.getDrawable("black-pause-64");
        p_button.disabled = skin.getDrawable("pause2");

       // m_button = new Button.ButtonStyle();
        //m_button.up = skin.getDrawable("sound53");
       // m_button.disabled = skin.getDrawable("sound53");

    }

}
