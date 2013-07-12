package com.goebl.simplify;

/**
 * Helper to get X, Y and Z coordinates from a foreign class T.
 *
 * @author hgoebl
 * @since 06.07.13
 */
public interface Point3DExtractor<T> extends PointExtractor<T> {
    double getZ(T point);
}
