package com.goebl.simplify;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Test class for {@link Simplify} in context of typical Gps-Tracks.
 * <br>
 * This is more of a demonstration than a test.
 * See http://stackoverflow.com/q/34010298/2176962
 *
 * @author goebl
 * @since 01.12.15
 */
public class GpsLocationTest {

    private static class LatLng {
        private final double lat;
        private final double lng;

        public LatLng(double lat, double lng) {
            this.lat = lat;
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public double getLng() {
            return lng;
        }
    }

    private static LatLng[] coords;

    private static PointExtractor<LatLng> latLngPointExtractor = new PointExtractor<LatLng>() {
        @Override
        public double getX(LatLng point) {
            return point.getLat() * 1000000;
        }

        @Override
        public double getY(LatLng point) {
            return point.getLng() * 1000000;
        }
    };

    @BeforeClass
    public static void readPoints() throws Exception {
        Point[] allPoints = SimplifyTest.readPoints("gps-track.txt");
        List<LatLng> coordsList = new ArrayList<LatLng>(allPoints.length);

        for (Point point:allPoints) {
            coordsList.add(new LatLng(point.getX(), point.getY()));
        }
        coords = coordsList.toArray(new LatLng[coordsList.size()]);
    }

    @Test
    public void testSimplifyGpsTrack() throws Exception {
        Simplify<LatLng> simplify = new Simplify<LatLng>(new LatLng[0], latLngPointExtractor);

        LatLng[] simplified = simplify.simplify(coords, 20f, false);
        System.out.println("coords:" + coords.length + " simplified:" + simplified.length);
        Assert.assertTrue("should be simplified to less than 33%", simplified.length < (coords.length / 3));

        simplified = simplify.simplify(coords, 50f, true);
        System.out.println("coords:" + coords.length + " simplified:" + simplified.length);
        Assert.assertTrue("should be simplified to less than 20%", simplified.length < (coords.length * 0.2));
    }

}
