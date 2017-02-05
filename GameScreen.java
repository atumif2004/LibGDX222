package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.particles.values.RectangleSpawnShapeValue;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.Iterator;

public class GameScreen  implements Screen {

    final Drop game;

     OrthographicCamera camera;


    Texture background;
    Texture bombImage;

      Stage stage;
      GameGui gui;


    Sprite drop;
    Sprite hero;
    Sprite bomb;
    Sprite sound;





    Sound dropSound;
    Sound fallwatermelonSound;

    Music rainMusic;

    Rectangle bucket;

    Vector3 touchPos;

    Array<Rectangle> raindrops;
    Array<Rectangle> bombdrops;

    long lastDropTime;
    long lastDropBombTime;

    int dropsGatchered;

    public  static  final  int width = 800;
    public  static final int height = 480;







    public GameScreen (final Drop gam) {
        Resource.load();

        this.game = gam;

        stage = new Stage(new ScreenViewport());


        if(GameData.state == State.PLAY) {


            camera = new OrthographicCamera();
            camera.setToOrtho(false, width, height);

            drop = Resource.drop;
            hero = Resource.player;
            bomb = Resource.start;
            sound = Resource.sound;


            gui = new GameGui();


            touchPos = new Vector3();


            background = new Texture("backplay.png");
            bombImage = new Texture("bomb.png");


            dropSound = Gdx.audio.newSound(Gdx.files.internal("waterdrop.wav"));
            rainMusic = Gdx.audio.newMusic(Gdx.files.internal("MainSound.mp3"));
            fallwatermelonSound = Gdx.audio.newSound(Gdx.files.internal("smack.wav"));

            rainMusic.setLooping(true);
            rainMusic.setVolume(0.2f);
            rainMusic.play();

            bucket = new Rectangle();
            bucket.x = 800 / 2 - 64 / 2;
            bucket.y = 20;
            bucket.width = 64;
            bucket.height = 64;

            raindrops = new Array<Rectangle>();
            spawnRaindrop();
            bombdrops = new Array<Rectangle>();
            spawnBomb();
        }



    }



















    private void spawnRaindrop(){
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800-64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }
    public  void  spawnBomb() {
        Rectangle bombdrop = new Rectangle();
        bombdrop.x = MathUtils.random(0,800-64);
        bombdrop.y = 480;
        bombdrop.width = 64;
        bombdrop.height = 64;
        bombdrops.add(bombdrop);
        lastDropBombTime= TimeUtils.nanoTime();

    }


    @Override
    public void render (float delta) {

        camera.update();
        game.batch.begin();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.draw(background,0,0,GameScreen.width, GameScreen.height);
        game.font.draw(game.batch, "Drops Collected: " + dropsGatchered, 0, 480);
        //game.batch.draw(pause,800-32,480-32,32,32);
        game.batch.draw(sound ,800-64,480-32,32,32);





        game.batch.draw(hero, bucket.x, bucket.y);
        for (Rectangle raindrop: raindrops){
            game.batch.draw(drop, raindrop.x, raindrop.y);

        }
        for (Rectangle bombdrop: bombdrops){
            game.batch.draw(bombImage, bombdrop.x, bombdrop.y);
        }
        game.batch.end();
        gui.act(delta);
        gui.draw();

        if(Gdx.input.isTouched()){
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = (int) (touchPos.x -64 / 2);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();

        if (bucket.x < 0) bucket.x = 0;
        if (bucket.x > 800 - 64) bucket.x = 800 - 64;

        if(GameData.state == State.PLAY){

        }


        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();
        if(TimeUtils.nanoTime() - lastDropBombTime > 1000000000) spawnBomb();







             Iterator<Rectangle> itere = bombdrops.iterator();
             Iterator<Rectangle> iter = raindrops.iterator();
              while (iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= MathUtils.random(200,850) * Gdx.graphics.getDeltaTime();
            if (raindrop.y + 64 < 0){
                fallwatermelonSound.play(0.1f);
                iter.remove();
            }

            if (raindrop.overlaps(bucket)) {
                dropsGatchered++;
                dropSound.play(0.1f);
                iter.remove();
            }
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
        //dropImage.dispose();
        //bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
        bombImage.dispose();
    }

    @Override
    public void show() {
       gui = new GameGui();


        InputMultiplexer in = new InputMultiplexer();
        in.addProcessor(stage);
        in.addProcessor(gui);
        Gdx.input.setInputProcessor(in);

        rainMusic.play();
    }







}
