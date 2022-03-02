package univ.major.project.assign.project_1.util;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import univ.major.project.assign.project_1.ui.preference;

/**
 * SUBJECT CLASS.
 */

public class Timer extends Handler{

    private List<TickListener> observers;
    private boolean paused;
    private static Timer stObject;
    private static int delay;



    public Timer() {
        observers = new ArrayList<>();
        handleMessage(obtainMessage());

    }

    public void subscribe(TickListener t) {
        observers.add(t);
    }

    public void unsubscribe(TickListener t) {
        observers.remove(t);
    }

    public void deregisterAll() {
        observers.clear();
    }

    public void pause() {
        paused = true;
    }

    public void restart() {
        paused = false;
    }

    public static Timer factory(int d){
        if(stObject == null){
            stObject = new Timer();
        }
        delay = d;

        stObject.restart();
        stObject.handleMessage(null);
        return stObject;
    }


    public void ticNotification(){
        if (!paused) {
            for (TickListener t : observers) {
                t.onTick();
            }
        }
    }

    @Override
    public void handleMessage(Message m) {
        ticNotification();
        sendMessageDelayed(obtainMessage(), delay);
    }
}
