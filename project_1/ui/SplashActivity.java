package univ.major.project.assign.project_1.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import univ.major.project.assign.project_1.R;
import univ.major.project.assign.project_1.logic.GameMode;

public class SplashActivity extends Activity {
    private ImageView iv;
    private GameMode gm;


    @Override
    public void onCreate(Bundle b){
        super.onCreate(b);
        iv = new ImageView(this);
        iv.setImageResource(R.drawable.splash);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        setContentView(iv);

    }

    @Override
    public boolean onTouchEvent(MotionEvent m){
        if(m.getAction() == MotionEvent.ACTION_DOWN){
            float x = m.getX();
            float y = m.getY();
            float w = iv.getWidth();
            float h = iv.getHeight();

            if(x<0.5*w && y>0.66*h && y<0.8*h){
                //select the one player mode
                gm = GameMode.ONE_PLAYER;

                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("playerMode", gm);
                startActivity(i);
                finish();
            }else if(x>0.5*w && y>0.66*h && y<0.8*h){
                //select the two player mode
                gm = GameMode.TWO_PLAYER;
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("playerMode", gm);
                startActivity(i);
                finish();
            }else if( x<0.5*w && y>0.83*h){
                AlertDialog.Builder ab = new AlertDialog.Builder(this);
                ab.setMessage(R.string.AboutGame)
                        .setTitle(R.string.AG_title)
                        .setCancelable(false);

                ab.setPositiveButton(R.string.AG_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) { }
                });
                AlertDialog box = ab.create();
                box.show();
            }else if(x>0.5*w && y>0.83*h){
                Intent i = new Intent(this, preference.class);
                startActivity(i);
            }
        }
        return true;
    }
}
