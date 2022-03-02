package univ.major.project.assign.project_1.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.graphics.*;
import android.widget.ImageView;
import android.widget.Toast;
import android.app.*;


import java.util.*;

import univ.major.project.assign.project_1.R;
import univ.major.project.assign.project_1.logic.GameBoard;
import univ.major.project.assign.project_1.logic.GameMode;
import univ.major.project.assign.project_1.logic.Player;
import univ.major.project.assign.project_1.util.TickListener;
import univ.major.project.assign.project_1.util.Timer;

/**
 * This is the Main view classes. It inherits the view class and implecments the TickListenr which is subject class.
 * In this class we draw all the grid,buttons and tokens and refers the all tapping motions.
 * Also, we modify all the alert Dialog to choose the mode(one player, two player mode) and restarting the game.
 */

@SuppressLint("AppCompatCustomView")
public class GameView extends ImageView implements TickListener {
    private Grid grid;
    private boolean firstRun;
    private GuiButton currentButton;
    private GuiButton counterButton;

    private GuiButton[] buttons;
    private List<GuiToken> tokens;
    private Timer timer;
    private int score_X, score_O;

    private GameBoard engine;
    private GameMode playerMode;

    private MediaPlayer backGroundMusic;
    private MediaPlayer drop;
    private MediaPlayer press;
    private MediaPlayer slide;




    public GameView(Context context){
        super(context);
        firstRun = true;
        buttons = new GuiButton[10];
        tokens = new ArrayList<GuiToken>();
        engine = new GameBoard();
        timer = timer.factory(preference.getSpeed(context));
        score_X = score_O = 0;

        playerMode = GameMode.ONE_PLAYER;

        if(preference.soundOn(context)){
            if(preference.theme(context)==100){
                backGroundMusic = MediaPlayer.create(context, R.raw.bgm_ko);
            }else if(preference.theme(context)==200){
                backGroundMusic = MediaPlayer.create(context, R.raw.bgm_us);
            }else if(preference.theme(context)==300){
                backGroundMusic = MediaPlayer.create(context, R.raw.bgm_ja);
            }
            backGroundMusic.setLooping(true);
            backGroundMusic.start();
        }

        if(preference.effectOn(context)){
            drop = MediaPlayer.create(context,R.raw.dropsound);
            press = MediaPlayer.create(context,R.raw.press);
            slide = MediaPlayer.create(context,R.raw.slidesound);
        }

        if(preference.theme(context)==100){
            setImageResource(R.drawable.bg_ko);
        }else if(preference.theme(context)==200){
            setImageResource(R.drawable.bg_us);
        }else if(preference.theme(context)==300){
            setImageResource(R.drawable.bg_ja);
        }
        setScaleType(ImageView.ScaleType.FIT_XY);

    }

    private void makeDropSound(){
        if(drop != null){
            drop.start();
        }
    }

    private void makePressSound(){
        if(press != null){
            press.start();
        }
    }

    private void makeSlideSound(){
        if(slide != null){
            slide.start();
        }
    }


    private void pauseBGM(){
        if(backGroundMusic != null){
            backGroundMusic.pause();
        }
    }

    private void restartBGM(){
        if(backGroundMusic != null){
            backGroundMusic.start();
        }
    }

    public void gotBG(){
        pauseBGM();
        timer.pause();
    }

    public void gotFG(){
        restartBGM();
        timer.restart();
    }

    public void cleanupBeforeShutdown(){
        if(backGroundMusic != null){
            backGroundMusic.release();
        }
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);

        String score = "X : "+score_X+" VS O : "+score_O;

        if (firstRun) {
            init();
            //calling the  mode choose alert dialog.
//            modeAlertDialog();

            firstRun = false;
        }

        grid.draw(c, score);

        for (GuiToken t : tokens) {
            t.draw(c);
        }
        for (GuiButton b : buttons) {
            b.draw(c);
        }

    }

    /**
     * Method for calling all buttons and grid.
     */
    private void init() {
        float w = getWidth();
        float h = getHeight();
        float unit = w/16f;
        float gridX = unit * 2.5f;
        float cellSize = unit * 2.3f;
        float gridY = unit * 9;
        float buttonTop = gridY - cellSize;
        float buttonLeft = gridX - cellSize;
        grid = new Grid(gridX, gridY, cellSize);
        buttons[0] = new GuiButton('1', this, buttonLeft + cellSize*1, buttonTop, cellSize);
        buttons[1] = new GuiButton('2', this, buttonLeft + cellSize*2, buttonTop, cellSize);
        buttons[2] = new GuiButton('3', this, buttonLeft + cellSize*3, buttonTop, cellSize);
        buttons[3] = new GuiButton('4', this, buttonLeft + cellSize*4, buttonTop, cellSize);
        buttons[4] = new GuiButton('5', this, buttonLeft + cellSize*5, buttonTop, cellSize);
        buttons[5] = new GuiButton('A', this, buttonLeft, buttonTop + cellSize*1, cellSize);
        buttons[6] = new GuiButton('B', this, buttonLeft, buttonTop + cellSize*2, cellSize);
        buttons[7] = new GuiButton('C', this, buttonLeft, buttonTop + cellSize*3, cellSize);
        buttons[8] = new GuiButton('D', this, buttonLeft, buttonTop + cellSize*4, cellSize);
        buttons[9] = new GuiButton('E', this, buttonLeft, buttonTop + cellSize*5, cellSize);

        timer.subscribe(this);
    }

    /**
     * Override onTouchEvent for touching event.
     * @param m motion event!
     * @return Allways true;
     */

    @Override
    public boolean onTouchEvent(MotionEvent m) {

        //ignore touch events if the View is not fully initialized
        if (grid == null || firstRun) return true;

        //ignore touch events if there are any "moving" tokens on-screen.
        if (GuiToken.anyMoving()) return true;

        float x = m.getX();
        float y = m.getY();
        if (m.getAction() == MotionEvent.ACTION_DOWN) {
            //Main.say("finger down!");
            currentButton = null;
            for (GuiButton b : buttons) {
                if (b.contains(x, y)) {
                    currentButton = b;
                    b.press();
                    break;
                }
            }

            //show a helpful hint if the user taps inside the grid
            if (currentButton == null) {
                Toast t = Toast.makeText(getContext(),
                        R.string.tost_txt,
                        Toast.LENGTH_LONG);
                t.show();
            }

        }else if (m.getAction() == MotionEvent.ACTION_UP) {
            for (GuiButton b : buttons) {
                if (b.contains(x, y)) {
                    if (b == currentButton) {
                        handleButtPress(b);
                    }
                }
            }
            currentButton = null;
        }
        return true;
    }

    /**
     * Methode for finding tokens location.
     * @param row tokens raw position.
     * @param col tokens column position.
     * @return
     */

    private GuiToken getTokenAt(char row, char col) {
        for (GuiToken gt : tokens) {
            if (gt.matches(row, col)) {
                return gt;
            }
        }
        return null;
    }

    /**
     * complete the onTick abstract method in the TickListener.
     */

    @Override
    public void onTick() {
//        Random buttOption = new Random();
        this.engine.checkForWin();
//        this.engine.aiObserber();

        if (!GuiToken.anyMoving()) {

            Player winner = this.engine.checkForWin();
            if (winner != Player.BLANK) {
                endGameAlertDialog();
                timer.pause();
                if (winner == Player.X) {
                    score_X++;
                } else if (winner == Player.O) {
                    score_O++;
                } else if (winner == Player.TIE) {
                    score_O++;
                    score_X++;
                }
            } else {
                if (playerMode == GameMode.ONE_PLAYER && engine.getCurrentPlayer()== Player.O) {
                    computerMove();

                }
            }
        }
        invalidate();
    }

    /**
     * worker thread for the computer moveing.
     */
    private void computerMove() {
        post(() -> { handleButtPress(counterButton); }
        );
    }

    /**
     * This Method is for determining the winner.
     * It brings the winner and show in the AlertDialog.
     */

    public void endGameAlertDialog(){
        AlertDialog.Builder ab = new AlertDialog.Builder(getContext());
        if(engine.checkForWin().equals(Player.O)){
            ab.setTitle(R.string.winDia_o);
        }else if(engine.checkForWin().equals(Player.X)){
            ab.setTitle(R.string.winDia_x);
        }else if(engine.checkForWin().equals(Player.TIE)){
            ab.setTitle(R.string.winDia_tie);
        }
        ab.setMessage(R.string.endGame_title)
                .setPositiveButton(R.string.yes, (dialog, which) -> reset())
                .setNegativeButton(R.string.no, (dialog, which) ->
                {
                    ((Activity) getContext()).finish();
                })
                .show();
    }
    private void reset() {
        int image = (int)(Math.random()*4);

        engine.clear();
        tokens.clear();
        timer.deregisterAll();
        timer.subscribe(this);
        timer.restart();
    }

    private void cleanupFallenTokens(){
        List<GuiToken> doomed = new ArrayList<GuiToken>();
        for (GuiToken t : tokens) {
            if (t.isInvisible(getHeight())) {
                makeDropSound();
                doomed.add(t);
                break;
            }
        }
        for (GuiToken d : doomed) {
            tokens.remove(d);
            timer.unsubscribe(d);
        }
    }
    private void handleButtPress(GuiButton gb){
        makePressSound();

        cleanupFallenTokens();

        currentButton = gb;
        currentButton.release();
        char buttLabal = currentButton.getLabel();
        engine.submitMove(buttLabal);
        GuiToken aiTok = new GuiToken(engine.getCurrentPlayer(), currentButton, getResources());
        tokens.add(aiTok);
        timer.subscribe(aiTok);

        ArrayList<GuiToken> neighbors = new ArrayList<>();
        neighbors.add(aiTok);
        if(currentButton.isTopButton()){
            for(char row = 'A'; row <= 'E'; row++) {
                GuiToken FindTokPos = getTokenAt(row, buttLabal);
                if (FindTokPos != null) {
                    neighbors.add(FindTokPos);
                } else {
                    break;
                }
            }
            for (GuiToken t:neighbors){
                t.moveDown();
            }
        }else{
            for(char col = '1'; col <= '5'; col++){
                GuiToken FindTokPos = getTokenAt(buttLabal, col);
                if(FindTokPos != null){
                    neighbors.add(FindTokPos);
                }else{
                    break;
                }
            }
            for(GuiToken t:neighbors){
                t.moveRight();
            }
        }
        makeSlideSound();
        invalidate();
        counterButton = currentButton;
    }

    public void setPlayerMode(GameMode gm){
        this.playerMode = gm;
    }

}
