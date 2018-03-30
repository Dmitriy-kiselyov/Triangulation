package com.example.grafica.lab4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.grafica.lab4.Polygon.Triangle;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Sex_predator on 07.03.2016.
 */
public class PolygonView extends View {

    private Paint mPolygonPaint;
    private Paint mTrianglePaint;
    private Path  mPolygonPath;
    private Path  mTrianglePath;

    private Polygon mPolygon;
    private int[]   mTriangleColors;
    private Random  mRandom;

    private int mTriangleCount;

    private int   mWidth;
    private int   mHeight;
    private float mTranslateX;
    private float mTranslateY;
    private float mScale;

    public PolygonView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPolygonPaint = new Paint();
        mPolygonPaint.setAntiAlias(true);
        mPolygonPaint.setStyle(Paint.Style.STROKE);
        mPolygonPaint.setStrokeWidth(2f);

        mTrianglePaint = new Paint();
        mTrianglePaint.setAntiAlias(true);
        mTrianglePaint.setStyle(Paint.Style.FILL);

        mPolygonPath = new Path();
        mTrianglePath = new Path();

        mPolygon = PolygonLab.getPolygon();
        if (mPolygon.getTriangles() != null)
            mTriangleColors = new int[mPolygon.getTriangles().length];
        mRandom = new Random();

        mTriangleCount = 0;
    }

    public void reset() {
        mPolygon = PolygonLab.getPolygon();
        if (mPolygon.getTriangles() != null)
            mTriangleColors = new int[mPolygon.getTriangles().length];
        mTriangleCount = 0;

        PointF p[] = mPolygon.getPoints();
        for (PointF pp : p)
            Log.d("Coord", pp.x + "f, " + pp.y + "f, ");

        onSizeChanged(mWidth, mHeight, mWidth, mHeight);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("superState", super.onSaveInstanceState());
        bundle.putInt("triangleCount", mTriangleCount);
        bundle.putIntArray("triangleColors", mTriangleColors);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle bundle = (Bundle) state;

        mTriangleCount = bundle.getInt("triangleCount");
        mTriangleColors = bundle.getIntArray("triangleColors");
        state = bundle.getParcelable("superState");

        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;

        //scale
        PointF[] points = mPolygon.getPoints();

        float minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        float maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
        for (PointF p : points) {
            minX = Math.min(minX, p.x);
            minY = Math.min(minY, p.y);
            maxX = Math.max(maxX, p.x);
            maxY = Math.max(maxY, p.y);
        }

        float width = maxX - minX;
        float height = maxY - minY;

        float scaleX = mWidth / width;
        float scaleY = mHeight / height;
        mScale = Math.min(scaleX, scaleY) * 0.95f;

        //center
        mTranslateX = (mWidth - width * mScale) / 2 - minX * mScale;
        mTranslateY = (mHeight - height * mScale) / 2 - minY * mScale;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(mTranslateX, mTranslateY);

        //draw border
        PointF[] points = mPolygon.getPoints();

        mPolygonPath.reset();
        mPolygonPath.moveTo(points[0].x * mScale, points[0].y * mScale);
        for (int i = 1; i < points.length; i++)
            mPolygonPath.lineTo(points[i].x * mScale, points[i].y * mScale);
        mPolygonPath.close();

        canvas.drawPath(mPolygonPath, mPolygonPaint);

        //fill with triangles
        Triangle[] triangles = mPolygon.getTriangles();
        if (triangles == null) {
            showMessage("Build error");
            return;
        }
        mTriangleCount = Math.min(mTriangleCount, triangles.length);

        for (int i = 0; i < mTriangleCount; i++) {
            Triangle t = triangles[i];

            mTrianglePath.reset();
            mTrianglePath.moveTo(t.getA().x * mScale, t.getA().y * mScale);
            mTrianglePath.lineTo(t.getB().x * mScale, t.getB().y * mScale);
            mTrianglePath.lineTo(t.getC().x * mScale, t.getC().y * mScale);
            mTrianglePath.close();

            int color = mTriangleColors[i];
            if (color == 0) {
                color = Color.rgb(mRandom.nextInt(200) + 25, mRandom.nextInt(200) + 25,
                                  mRandom.nextInt(200) + 25);
                mTriangleColors[i] = color;
            }
            mTrianglePaint.setColor(color);

            canvas.drawPath(mTrianglePath, mTrianglePaint);
        }
    }

    private void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void resetTriangles() {
        mTriangleCount = 0;
        Arrays.fill(mTriangleColors, 0);
        invalidate();
    }

    public void incTriangles() {
        mTriangleCount++;
        invalidate();
    }

}
