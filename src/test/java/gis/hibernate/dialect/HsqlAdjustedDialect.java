package gis.hibernate.dialect;

import org.hibernate.HibernateException;
import org.hibernate.dialect.HSQLDialect;
import org.hibernate.spatial.GeometrySqlTypeDescriptor;
import org.hibernate.spatial.SpatialDialect;
import org.hibernate.spatial.SpatialFunction;
import org.hibernate.spatial.dialect.mysql.MySQLGeometryTypeDescriptor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;

/**
 * Adjusted dialect for hsql in memory database in order to use spatial elements in entities.
 */
public class HsqlAdjustedDialect extends HSQLDialect implements SpatialDialect {

    @Override
    public final String getTypeName(int code, long length, int precision, int scale) throws HibernateException {
        if (code == 3000) {
            return "GEOMETRY";
        }
        return super.getTypeName(code, length, precision, scale);
    }

    @Override
    public final SqlTypeDescriptor remapSqlTypeDescriptor(SqlTypeDescriptor sqlTypeDescriptor) {
        if (sqlTypeDescriptor instanceof GeometrySqlTypeDescriptor) {
            return MySQLGeometryTypeDescriptor.INSTANCE;
        }
        return super.remapSqlTypeDescriptor(sqlTypeDescriptor);
    }

    @Override
    public final String getSpatialRelateSQL(String columnName, int spatialRelation) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final String getSpatialFilterExpression(String columnName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final String getSpatialAggregateSQL(String columnName, int aggregation) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final String getDWithinSQL(String columnName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final String getHavingSridSQL(String columnName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final String getIsEmptySQL(String columnName, boolean isEmpty) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean supportsFiltering() {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean supports(SpatialFunction function) {
        throw new UnsupportedOperationException();
    }

}
