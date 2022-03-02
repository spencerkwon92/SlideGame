package univ.major.project.assign.project_1.ui;

import android.content.res.Resources;
import android.graphics.*;
import android.util.Log;

import univ.major.project.assign.project_1.R;
import univ.major.project.assign.project_1.logic.Player;
import univ.major.project.assign.project_1.util.TickListener;

/**
 * This is the class for drawing creating TOKENS.
 * In this class, there is inner class that defining the position of toekens.
 */

public class GuiToken implements TickListener {

    private Player player;
    private RectF bounds;
    private PointF velocity;
    private PointF destination;
    private float tolerance;
    private Bitmap image;
    private GridPosition pos;
    private static int movers = 0;
    private boolean falling;


    private static class GridPosition {
        char row;
        char column;

        GridPosition(char r, char c) {
            row = r;
            column = c;
        }
    }

    public GuiToken(Player p, GuiButton parent, Resources res) {
        this.bounds = new RectF(parent.getBounds());
        falling = false;
        velocity = new PointF();
        destination = new PointF();
        tolerance = bounds.height()/10f;
        player = p;

        if (player == Player.X) {
            image = BitmapFactory.decodeResource(res, R.drawable.tokx_us);
        } else {
            image = BitmapFactory.decodeResource(res, R.drawable.toko_us);
        }
        image = Bitmap.createScaledBitmap(image, (int)bounds.width(), (int)bounds.height(), true);
        if (parent.isTopButton()) {
            pos = new GridPosition((char)('A'-1), parent.getLabel());
        } else {
            pos = new GridPosition(parent.getLabel(), (char)('1'-1));
        }
    }

    public void draw(Canvas c) {
        c.drawBitmap(image, bounds.left, bounds.top, null);
    }

    public boolean matches(char row, char col) {
        return (pos.row == row && pos.column == col);
    }

    private void move() {
        if (falling) {
            velocity.y *= 2;
        } else {
            if (isMoving()) {
                float dx = destination.x - bounds.left;
                float dy = destination.y - bounds.top;
                if (PointF.length(dx, dy) < tolerance) {
                    bounds.offsetTo(destination.x, destination.y);
                    velocity.set(0,0);
                    movers--;
                    if (fellOff()) {
                        velocity.set(0, 1);
                        falling = true;
                    }
                }
            }
        }
        bounds.offset(velocity.x, velocity.y);
    }

    private boolean fellOff() {
        return (pos.column > '5' || pos.row > 'E');
    }

    public boolean isInvisible(int h) {
        return (bounds.top > h);
    }

    public void moveDown() {
        setGoal(bounds.left, bounds.top+bounds.height());
        pos.row++;
    }

    public void moveRight() {
        setGoal(bounds.left+bounds.width(), bounds.top);
        pos.column++;
    }

    public boolean isMoving() {
        return (velocity.x > 0 || velocity.y > 0);
    }

    private void setGoal(float x, float y) {
        movers++;
        destination.set(x,y);
        float dx = destination.x - bounds.left;
        float dy = destination.y - bounds.top;
        velocity.x = dx/11f;
        velocity.y = dy/11f;
    }

    public void onTick() {
        Log.d("CS203", "movers="+movers);
        move();
    }

    public static boolean anyMoving() {
        return (movers > 0);
    }


}
