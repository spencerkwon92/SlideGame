package univ.major.project.assign.project_1.ui;

import android.graphics.*;

/**
 * This is the class for drawing creating GRID.
 */

public class Grid {
    private final int dim = 5;
    private float lineWidth;
    private Paint paint;
    private RectF bounds;
    private float cellWidth;

    public Grid(float x, float y, float cellWidth) {
        this.cellWidth = cellWidth;
        lineWidth = cellWidth/20;
        bounds = new RectF(x, y, x+cellWidth*dim, y+cellWidth*dim);
        paint = new Paint();
        paint.setStrokeWidth(lineWidth);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize((cellWidth*2)/4);

    }

    public float getTop() {
        return bounds.top;
    }

    public void draw(Canvas c, String stringSet) {
        for (int i=0; i<=dim; ++i) {
            c.drawLine(bounds.left, bounds.top + cellWidth*i, bounds.right, bounds.top + cellWidth*i, paint);
        }
        for (int i=0; i<=dim; ++i) {
            c.drawLine(bounds.left + cellWidth*i, bounds.top, bounds.left + cellWidth*i, bounds.bottom, paint);
        }

        c.drawText(stringSet, cellWidth/0.5f,cellWidth*2, paint);
    }



}
