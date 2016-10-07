package nl.technolution.wvp.common;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.PrecisionModel;

/**
 * Geometry class for creating differents kind of geometry.
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
}
