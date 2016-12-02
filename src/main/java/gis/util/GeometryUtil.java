/*
 (C) COPYRIGHT 2016 TECHNOLUTION BV, GOUDA NL
| =======          I                   ==          I    =
|    I             I                    I          I
|    I   ===   === I ===  I ===   ===   I  I    I ====  I   ===  I ===
|    I  /   \ I    I/   I I/   I I   I  I  I    I  I    I  I   I I/   I
|    I  ===== I    I    I I    I I   I  I  I    I  I    I  I   I I    I
|    I  \     I    I    I I    I I   I  I  I   /I  \    I  I   I I    I
|    I   ===   === I    I I    I  ===  ===  === I   ==  I   ===  I    I
|                 +---------------------------------------------------+
+----+            |  +++++++++++++++++++++++++++++++++++++++++++++++++|
     |            |             ++++++++++++++++++++++++++++++++++++++|
     +------------+                          +++++++++++++++++++++++++|
                                                        ++++++++++++++|
                                                                 +++++|
 */
package nl.technolution.wvp.common;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;

/**
 * Geometry class for creating different kinds of geometry.
 */
public class GeometryUtil {
    
    public static final int AMERSFOORT_RD_NEW = 28992;

    private int srid;
    
    public GeometryUtil(int srid) {
       this.srid = srid;
    }

    /**
     * Create a linestring from coordinates.
     * @param coordinates
     * @return A linestring
     */
    public final LineString createLineString(Coordinate[] coordinates) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), srid);
        return geometryFactory.createLineString(coordinates);
    }
    
    /**
     * Creates a point from a coordinate.
     * @param coordinate
     * @return
     */
    public final Point createPoint(Coordinate coordinate) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), srid);
        return geometryFactory.createPoint(coordinate);
    }
    
    
    /**
     * Creates a multiline string from a LineString list.
     * @param lineStringList A list of linestrings.
     * @return A MultilineString
     */
    public final MultiLineString createMultiLineString(List<LineString> lineStringList) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), srid);
        
        LineString[] lineStrings = new LineString[lineStringList.size()];
         
        return geometryFactory.createMultiLineString(lineStringList.toArray(lineStrings));
    }
    
    /**
     * Creates a multiline string from a linestring.
     * @param lineString The linestring
     * @return A multiline string
     */
    public final MultiLineString createMultiLineString(LineString lineString) {
        List<LineString> lineStringList = new ArrayList<>();
        lineStringList.add(lineString);
        return createMultiLineString(lineStringList);
    }
    
    /**
     * 
     * @param point
     * @return
     */
    public final Point addRdSrIdToPoint(Point point) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), srid);
        return geometryFactory.createPoint(point.getCoordinate());
    }

    /**
     * 
     * @param multiLineString
     * @return
     */
    public final MultiLineString addRdSrIdToMultilineString(MultiLineString multiLineString) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), srid);

        Coordinate[] coordinates = multiLineString.getCoordinates();
        LineString lineString = geometryFactory.createLineString(coordinates);
        return createMultiLineString(lineString);
    }

}
