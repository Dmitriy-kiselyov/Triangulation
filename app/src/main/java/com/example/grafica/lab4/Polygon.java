package com.example.grafica.lab4;

import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Sex_predator on 08.03.2016.
 */
public class Polygon {

    private PointF[]   mPoints;
    private Triangle[] mTriangles;
    private boolean[]  taken;

    public Polygon(Float[] points) throws IllegalArgumentException {
        if (points.length % 2 == 1 || points.length < 6)
            throw new IllegalArgumentException();

        mPoints = new PointF[points.length / 2];
        for (int i = 0; i < points.length; i += 2)
            mPoints[i / 2] = new PointF(points[i], points[i + 1]);

        mTriangles = new Triangle[mPoints.length - 2];

        taken = new boolean[mPoints.length];

        triangulate();

        Log.d("POINTS", "POINTS");

    }

    private void triangulate() {
        int trainPos = 0, leftPoints = mPoints.length;

        int ai = findNextNotTaken(0);
        int bi = findNextNotTaken(ai + 1);
        int ci = findNextNotTaken(bi + 1);

        Timer t = new Timer();
        t.start();

        while (leftPoints > 3) {
            Log.d("WHILE",
                  ai + " " + bi + " " + ci + " " + isLeft(mPoints[ai], mPoints[bi], mPoints[ci]));
            if (isLeft(mPoints[ai], mPoints[bi], mPoints[ci]) && canBuildTriangle(ai, bi, ci)) {
                mTriangles[trainPos++] = new Triangle(mPoints[ai], mPoints[bi], mPoints[ci]);
                taken[bi] = true;
                leftPoints--;
                bi = ci;
                ci = findNextNotTaken(ci + 1);
            } else {
                ai = findNextNotTaken(ai + 1);
                bi = findNextNotTaken(ai + 1);
                ci = findNextNotTaken(bi + 1);
            }

            if (t.time() > 1_000) {
                t.stop();
                Log.d("Polygon", "TOO LONG");
                mTriangles = null;
                break;
            }
        }

        if (mTriangles != null)
            mTriangles[trainPos] = new Triangle(mPoints[ai], mPoints[bi], mPoints[ci]);
    }

    private int findNextNotTaken(int startPos) {
        startPos %= mPoints.length;
        if (!taken[startPos])
            return startPos;

        int i = (startPos + 1) % mPoints.length;
        while (i != startPos) {
            if (!taken[i])
                return i;
            i = (i + 1) % mPoints.length;
        }

        return -1;
    }

    private boolean isLeft(PointF a, PointF b, PointF c) {
        float abX = b.x - a.x;
        float abY = b.y - a.y;
        float acX = c.x - a.x;
        float acY = c.y - a.y;

        return abX * acY - acX * abY < 0;
    }

    public PointF[] getPoints() {
        return mPoints;
    }

    @Nullable
    public Triangle[] getTriangles() {
        return mTriangles;
    }

    private boolean isPointInside(PointF a, PointF b, PointF c, PointF p) {
        float ab = (a.x - p.x) * (b.y - a.y) - (b.x - a.x) * (a.y - p.y);
        float bc = (b.x - p.x) * (c.y - b.y) - (c.x - b.x) * (b.y - p.y);
        float ca = (c.x - p.x) * (a.y - c.y) - (a.x - c.x) * (c.y - p.y);

        return (ab >= 0 && bc >= 0 && ca >= 0) || (ab <= 0 && bc <= 0 && ca <= 0);
    }

    private boolean canBuildTriangle(int ai, int bi, int ci) {
        for (int i = 0; i < mPoints.length; i++)
            if (i != ai && i != bi && i != ci)
                if (isPointInside(mPoints[ai], mPoints[bi], mPoints[ci], mPoints[i]))
                    return false;
        return true;
    }

    public class Triangle {

        private PointF a, b, c;

        public Triangle(PointF a, PointF b, PointF c) {
            this.a = a;
            this.b = b;
            this.c = c;

            Log.d("TRIANGLE", a + " " + b + " " + c);
        }

        public PointF getA() {
            return a;
        }

        public PointF getB() {
            return b;
        }

        public PointF getC() {
            return c;
        }
    }

}
