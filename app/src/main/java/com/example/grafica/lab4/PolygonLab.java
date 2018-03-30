package com.example.grafica.lab4;

/**
 * Created by Sex_predator on 08.03.2016.
 */
public class PolygonLab {

    private static Polygon sPolygon;

    private PolygonLab() {
        //no constructor
    }

    public static Polygon getPolygon() {
        if (sPolygon == null) {
            Float[] points = new Float[] {100f, 400f, 100f, 100f, 200f, 600f, 300f, 400f, 200f, 300f, 500f,
                                          300f, 300f, 500f, 600f, 500f, 600f, 0f, 400f, 100f, 0f, 0f};
            sPolygon = new Polygon(points);
        }

        return sPolygon;
    }

    public static void setPolygon(Float[] points) throws IllegalArgumentException {
        if (points.length < 6 || points.length % 2 == 1)
            throw new IllegalArgumentException();
        sPolygon = new Polygon(points);
    }

}
