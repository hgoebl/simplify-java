# simplify-java #

Simplification of a 2D-polyline or a 3D-polyline.

  * Uses Radial-Distance algorithm (fast) or Douglas-Peucker (high quality) algorithm
  * Port of [mourner / simplify-js](https://github.com/mourner/simplify-js), a High-performance JavaScript 2D/3D
    polyline simplification library by Vladimir Agafonkin
  * Can handle arbitrary objects carrying coordinates (2D, 3D)
    * either by implementing an interface
    * or by providing a helper extracting the coordinates
  * Leaves the objects untouched, just creates a new array referencing the simplified points
  * requires Java 5
  * Maven Build
  * JUnit-tested
    * 94% lines covered
    * reference data is created by "original" JavaScript implementation (Version 1.1.0)

# Example #

```java
    // create an instance of the simplifier (empty array needed by List.toArray)
    Simplify<Point> simplify = new Simplify<Point>(new MyPoint[0]);

    // here we have an array with hundreds of points
    Point[] allPoints = ...
    double tolerance = ...
    boolean highQuality = true; // Douglas-Peucker, false for Radial-Distance

    // run simplification process
    Point[] lessPoints = simplify.simplify(allPoints, tolerance, highQuality);
```

**Please note**
The algorithm squares differences of x, y and z coordinates. If this difference is less than 1,
the square of it will get even less. In such cases, the tolerance has negative effect.

Solution: multiply your coordinates by a factor so the values are shifted in a way so that taking
the square of the differences creates greater values.

If your Points don't have the `com.goebl.simplify.Point` interface, you can implement it on your
Point-Class, or (better w.r.t. separation of concerns) provide an implementation of the
`PointExtractor` interface.
 
Here is an example (taken from the test cases):

Example for your own point-class, let's assume it's not possible/desirable to
let it implement com.goebl.simplify.Point interface: 

```java
public class LatLng {
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
```

In the class where you simplify the points, you need a **PointExtractor**. As mentioned above,
the resulting x and y values are shifted in a space where delta-x and delta-y are no longer very
small numbers below 1:

```java
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
```

Simplification now works like this. Using a `PointExtractor` has the positive effect that you get
an array of your original points, not copies:

```java
LatLng[] coords = ... // the array of your "original" points

Simplify<LatLng> simplify = new Simplify<LatLng>(new LatLng[0], latLngPointExtractor);

LatLng[] simplified = simplify.simplify(coords, 20f, false);
```

For more examples see src/test/java/**/*Test.

# Maven Coordinates

```xml
<dependency>
    <groupId>com.goebl</groupId>
    <artifactId>simplify</artifactId>
    <version>1.0.0</version>
    
    <!-- or -->

    <groupId>com.goebl</groupId>
    <artifactId>simplify</artifactId>
    <version>1.0.1-SNAPSHOT</version>
</dependency>
```

Gradle

    'com.goebl:simplify:1.0.0'

Not using Maven/Gradle? - Then you can download the plain JAR from following links directly:

 * [SNAPSHOT Versions](https://oss.sonatype.org/content/groups/staging/com/goebl/simplify/)
 * [RELEASE Versions](http://repo.maven.apache.org/maven2/com/goebl/simplify/)

# Licence #

  * [The MIT License](http://opensource.org/licenses/MIT)

# TODO #

  * publish gh_pages (JavaDoc)

# Alternatives / Infos #

  * <http://www.codeproject.com/Articles/114797/Polyline-Simplification>
