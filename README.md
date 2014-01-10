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

For more examples see src/test/java/**/*Test.

# Maven Coordinates

```xml
<dependency>
    <groupId>com.goebl</groupId>
    <artifactId>simplify</artifactId>
    <version>1.0.0</version>
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
