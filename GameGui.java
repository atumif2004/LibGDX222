package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by bezvi on 29.01.2017.
 */

public class GameGui extends Stage {

    public GameGui() {

        Button p = new Button(Resource.p_button);
        p.setBounds(800-32,480-32,32,32);
        p.addListener(new ClickListener() {
                          @Override
                          public void clicked(InputEvent event, float x, float y) {
                              if (GameData.state == State.PLAY) {
                                  GameData.state = State.PAUSE;
                              } else GameData.state = State.PLAY;
                          }
                      });





        addActor(p);

    }


}
