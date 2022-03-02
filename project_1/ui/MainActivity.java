package univ.major.project.assign.project_1.ui;

import android.app.Activity;
import android.graphics.*;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.Random;

import univ.major.project.assign.project_1.R;
import univ.major.project.assign.project_1.logic.GameMode;

public class MainActivity extends Activity {

    public GameView gv;
    private GameMode gm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gm = (GameMode)getIntent().getSerializableExtra("playerMode");
        gv = new GameView(this);
        gv.setPlayerMode(gm);
        setContentView(gv);
    }

    @Override
    public void onPause(){
        super.onPause();
        gv.gotBG();
    }

    @Override
    public void onResume(){
        super.onResume();
        gv.gotFG();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        gv.cleanupBeforeShutdown();
    }
}