package com.goebl.simplify;

/**
 * Simplification of a 3D-polyline.
 *
 * @author hgoebl
 * @since 06.07.13
 */
public class Simplify3D<T> extends AbstractSimplify<T> {

    private final Point3DExtractor<T> pointExtractor;

    /**
     * Simple constructor for 3D-Simplifier.
     * <br>
     * With this simple constructor your array elements must implement {@link Point3D}.<br>
     * If you have coordinate classes which cannot be changed to implement <tt>Point3D</tt>, use
     * {@link #Simplify3D(Object[], Point3DExtractor)} constructor!
     *
     * @param sampleArray pass just an empty array (<tt>new MyPoint[0]</tt>) - necessary for type consistency.
     */
    public Simplify3D(T[] sampleArray) {
        super(sampleArray);
        this.pointExtractor = new Point3DExtractor<T>() {
            @Override
            public double getX(T point) {
                return ((Point) point).getX();
            }

            @Override
            public double getY(T point) {
                return ((Point) point).getY();
            }

            @Override
            public double getZ(T point) {
                return ((Point3D) point).getZ();
            }
        };
    }

    /**
     * Alternative constructor for 3D-Simplifier.
     * <br>
     * With this constructor your array elements do not have to implement a special interface like {@link Point3D}.<br>
     * Implement a {@link Point3DExtractor} to give <tt>Simplify3D</tt> access to your coordinates.
     *
     * @param sampleArray pass just an empty array (<tt>new MyPoint[0]</tt>) - necessary for type consistency.
     * @param pointExtractor your implementation to extract X, Y and Z coordinates from you array elements.
     */
    public Simplify3D(T[] sampleArray, Point3DExtractor<T> pointExtractor) {
        super(sampleArray);
        this.pointExtractor = pointExtractor;
    }

    @Override
    public double getSquareDistance(T p1, T p2) {

        double dx = pointExtractor.getX(p1) - pointExtractor.getX(p2);
        double dy = pointExtractor.getY(p1) - pointExtractor.getY(p2);
        double dz = pointExtractor.getZ(p1) - pointExtractor.getZ(p2);

        return dx * dx + dy * dy + dz * dz;
    }

    @Override
    public double getSquareSegmentDistance(T p0, T p1, T p2) {
        double x0, y0, z0, x1, y1, z1, x2, y2, z2, dx, dy, dz, t;

        x1 = pointExtractor.getX(p1);
        y1 = pointExtractor.getY(p1);
        z1 = pointExtractor.getZ(p1);
        x2 = pointExtractor.getX(p2);
        y2 = pointExtractor.getY(p2);
        z2 = pointExtractor.getZ(p2);
        x0 = pointExtractor.getX(p0);
        y0 = pointExtractor.getY(p0);
        z0 = pointExtractor.getZ(p0);

        dx = x2 - x1;
        dy = y2 - y1;
        dz = z2 - z1;

        if (dx != 0.0d || dy != 0.0d || dz != 0.0d) {
            t = ((x0 - x1) * dx + (y0 - y1) * dy + (z0 - z1) * dz)
                    / (dx * dx + dy * dy + dz * dz);

            if (t > 1.0d) {
                x1 = x2;
                y1 = y2;
                z1 = z2;
            } else if (t > 0.0d) {
                x1 += dx * t;
                y1 += dy * t;
                z1 += dz * t;
            }
        }

        dx = x0 - x1;
        dy = y0 - y1;
        dz = z0 - z1;

        return dx * dx + dy * dy + dz * dz;
    }
}
