package com.example.grafica.lab4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class PolygonDrawView extends View {

    private Paint mPaint;
    private Path  mPolygonPath;

    private ArrayList<Float> mPoints;

    public PolygonDrawView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(2f);
        mPaint.setStyle(Paint.Style.STROKE);

        mPolygonPath = new Path();

        mPoints = new ArrayList<>();

        setOnTouchListener(new TouchListener());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mPoints == null || mPoints.size() == 0)
            return;

        if (mPoints.size() == 2) {
            canvas.drawPoint(mPoints.get(0), mPoints.get(1), mPaint);
            return;
        }

        mPolygonPath.reset();
        mPolygonPath.moveTo(mPoints.get(0), mPoints.get(1));
        for (int i = 3; i < mPoints.size(); i += 2)
            mPolygonPath.lineTo(mPoints.get(i - 1), mPoints.get(i));

        canvas.drawPath(mPolygonPath, mPaint);
    }

    public boolean finish() {
        if (mPoints == null || mPoints.size() < 6)
            return false;

        Float[] points = new Float[mPoints.size()];
        mPoints.toArray(points);

        PolygonLab.setPolygon(points);

        return true;
    }

    public void clear() {
        mPoints.clear();
        invalidate();
    }

    public void remove() {
        if (mPoints.size() >= 2) {
            mPoints.remove(mPoints.size() - 1);
            mPoints.remove(mPoints.size() - 1);
            invalidate();
        }
    }

    class TouchListener implements OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mPoints.add(x);
                    mPoints.add(y);

                    invalidate();
                    return true;

                case MotionEvent.ACTION_MOVE:
                    mPoints.set(mPoints.size() - 2, x);
                    mPoints.set(mPoints.size() - 1, y);

                    invalidate();
                    return true;
            }

            return true;
        }
    }
}
