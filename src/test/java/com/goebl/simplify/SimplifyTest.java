package com.goebl.simplify;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Test class for {@link Simplify}.
 *
 * @author goebl
 * @since 06.07.13
 */
public class SimplifyTest {

    private static Point[] allPoints;
    private static final float[] TOLERANCES = new float[]{
            1.0f, 1.5f, 2.0f, 4.0f, 5.0f
    };

    private static final float[][] POINTS_2D = new float[][]{
            {3.14f, 5.2f}, {5.7f, 8.1f}, {4.6f, -1.3f}
    };

    @BeforeClass
    public static void readPoints() throws Exception {
        allPoints = readPoints("points-all.txt");
    }

    @Test
    public void testSimpleQuality() throws Exception {
        for (float tolerance : TOLERANCES) {
            assertPointsEqual(tolerance, false);
        }
    }

    @Test
    public void testHighQuality() throws Exception {
        for (float tolerance : TOLERANCES) {
            assertPointsEqual(tolerance, true);
        }
    }

    @Test
    public void testCustomPointExtractor() {
        PointExtractor<float[]> pointExtractor = new PointExtractor<float[]>() {
            @Override
            public double getX(float[] point) {
                return point[0];
            }

            @Override
            public double getY(float[] point) {
                return point[1];
            }
        };

        Simplify<float[]> simplify = new Simplify<float[]>(new float[0][0], pointExtractor);

        float[][] simplified = simplify.simplify(POINTS_2D, 5.0f, false);
        Assert.assertEquals("array should be simplified", 2, simplified.length);

        simplified = simplify.simplify(POINTS_2D, 5.0f, true);
        Assert.assertEquals("array should be simplified", 2, simplified.length);
    }

    @Test
    public void testInvalidPointsParam() {
        Simplify<Point> aut = new Simplify<Point>(new MyPoint[0]);
        Assert.assertNull("return null when point-array is null", aut.simplify(null, 1f, false));

        Point[] only2 = new Point[2];
        only2[0] = new MyPoint(1, 2);
        only2[1] = new MyPoint(2, 3);

        Assert.assertTrue("return identical array when less than 3 points",
                only2 == aut.simplify(only2, 1f, false));
    }

    private void assertPointsEqual(float tolerance, boolean highQuality) throws Exception {
        Point[] pointsExpected = readPoints(tolerance, highQuality);
        long start = System.nanoTime();

        Simplify<Point> aut = new Simplify<Point>(new MyPoint[0]);
        Point[] pointsActual = aut.simplify(allPoints, tolerance, highQuality);
        long end = System.nanoTime();
        System.out.println("tolerance=" + tolerance + " hq=" + highQuality
                + " nanos=" + (end - start));

        Assert.assertNotNull("wrong test setup", pointsExpected);
        Assert.assertNotNull("simplify must return Point[]", pointsActual);

        Assert.assertArrayEquals("tolerance=" + tolerance + " hq=" + highQuality,
                pointsExpected, pointsActual);
    }

    private static Point[] readPoints(float tolerance, boolean highQuality) throws Exception {
        return readPoints(String.format(Locale.US, "points-%01.1f-%s.txt",
                tolerance, highQuality ? "hq" : "sq"));
    }

    static Point[] readPoints(String fileName) throws Exception {
        List<MyPoint> pointList = new ArrayList<MyPoint>();
        File file = new File("src/test/resources", fileName);
        InputStream is = null;
        BufferedReader reader = null;
        try {
            is = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() == 0) {
                    continue;
                }
                String[] xy = line.split(",");
                double x = Double.parseDouble(xy[0]);
                double y = Double.parseDouble(xy[1]);
                pointList.add(new MyPoint(x, y));
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (is != null) {
                is.close();
            }
        }
        return pointList.toArray(new MyPoint[pointList.size()]);
    }

    private static class MyPoint implements Point {
        double x;
        double y;

        private MyPoint(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public double getX() {
            return x;
        }

        @Override
        public double getY() {
            return y;
        }

        @Override
        public String toString() {
            return "{" + "x=" + x + ", y=" + y + '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MyPoint myPoint = (MyPoint) o;

            if (Double.compare(myPoint.x, x) != 0) return false;
            if (Double.compare(myPoint.y, y) != 0) return false;

            return true;
        }

    }

}
