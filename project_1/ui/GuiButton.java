package univ.major.project.assign.project_1.ui;

import android.graphics.*;
import android.view.View;

import univ.major.project.assign.project_1.R;

/**
 * This is the class for drawing creating BUTTONS
 */

public class GuiButton {
    private RectF bounds;
    private boolean pressed;
    private static Bitmap pressedButton, unpressedButton;
    private static boolean firstLoad = true;
    private char label;

    public GuiButton(char name, View parent, float x, float y, float width) {
        label = name;
        bounds = new RectF(x, y, x+width, y+width);
        pressed = false;

        if (firstLoad) {
            firstLoad = false;
            pressedButton = BitmapFactory.decodeResource(parent.getResources(), R.drawable.button1);
            unpressedButton = BitmapFactory.decodeResource(parent.getResources(), R.drawable.button2);
            pressedButton = Bitmap.createScaledBitmap(pressedButton, (int)width, (int)width, true);
            unpressedButton = Bitmap.createScaledBitmap(unpressedButton, (int)width, (int)width, true);
        }
    }

    public boolean contains(float x, float y) {
        return bounds.contains(x, y);
    }

    public void press() {
        pressed = true;
    }

    public void release() {
        pressed = false;
    }

    public void draw(Canvas c) {
        if (pressed) {
            c.drawBitmap(pressedButton, bounds.left, bounds.top, null);
        } else {
            c.drawBitmap(unpressedButton, bounds.left, bounds.top, null);
        }
    }

    public char getLabel() {
        return label;
    }

    public boolean isTopButton() {
        return (label >= '1' && label <= '5');
    }

    public boolean isLeftButton() {
        return (label >= 'A' && label <= 'E');
    }

    public RectF getBounds() {
        return bounds;
    }
}
