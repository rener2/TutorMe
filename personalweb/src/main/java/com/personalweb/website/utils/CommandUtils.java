package com.personalweb.website.utils;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;

public class CommandUtils {


    public static void setNumericValueNullAware(PreparedStatement stmt, int index, Number value) throws SQLException {
        if (value == null) {
            stmt.setNull(index, Types.NUMERIC);
        } else {
            if (value instanceof Integer) {
                stmt.setInt(index, value.intValue());
            } else if (value instanceof Long) {
                stmt.setLong(index, value.longValue());
            } else if (value instanceof BigDecimal) {
                stmt.setBigDecimal(index, (BigDecimal) value);
            } else {
                stmt.setString(index, value.toString()); // maybe db can make use of it?
            }
        }
    }

    public static void setStringValueNullAware(PreparedStatement stmt, int index, String value) throws SQLException {
        if (value == null || value.isEmpty()) {
            stmt.setNull(index, Types.VARCHAR);
        } else {
            stmt.setString(index, value);
        }
    }

    public static void setDateValueNullAware(PreparedStatement stmt, int index, Date value) throws SQLException {
        if (value == null) {
            stmt.setNull(index, Types.DATE);
        } else {
                stmt.setDate(index, value);
        }
    }



    public static void setTimestampValueNullAware(PreparedStatement stmt, int index, Timestamp value) throws SQLException {
        if (value == null) {
            stmt.setNull(index, Types.TIMESTAMP);
        } else {
                stmt.setTimestamp(index, value);
        }
    }
}

