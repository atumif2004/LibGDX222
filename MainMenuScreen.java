package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by bezvi on 19.01.2017.
 */

public class MainMenuScreen implements Screen{
    Texture background;
    final Drop game;
    OrthographicCamera camera;
    Sprite sprite;


    public  MainMenuScreen(final Drop gam) {
        Resource.load();
     this.game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800,480);
        background = new Texture("backmain.png");
        sprite = Resource.start;
        //sprite.setBounds(0,0,180,180);







    }

    @Override
    public void show() {




    }



    @Override
    public void render(float delta) {


        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(background,0,0,GameScreen.width, GameScreen.height);
        game.batch.draw(sprite,800/2-180/2,200,180,180);
        game.font.draw(game.batch, "Catch the fruit!", 100,150);
        game.font.draw(game.batch, "Touch the screen to start!", 100,100);
        game.batch.end();


        if (Gdx.input.isTouched()){
            game.setScreen(new GameScreen(game));
            dispose();
        }


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        background.dispose();
    }









}
