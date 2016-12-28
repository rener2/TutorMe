package com.personalweb.website.utils;

import org.springframework.jdbc.support.lob.LobHandler;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;

public class CommandUtils {
//
//    public static Calendar addMonths(Calendar calendar, int months) {
//        calendar.add(Calendar.MONTH, months);
//        return calendar;
//    }
//
//    public static Calendar getCurrentDate() {
//        Calendar currentDate = Calendar.getInstance();
//        return CommandUtils.clearTime(currentDate);
//    }

    public static void setStringValueNullAware(PreparedStatement stmt, int index, String value) throws SQLException {
        if (value == null) {
            stmt.setNull(index, Types.VARCHAR);
        } else {
            stmt.setString(index, value);
        }
    }

//    public static java.sql.Date toSqlDate(java.util.Date date) {
//        if (date == null) {
//            return null;
//        }
//        return new Date(date.getTime());
//    }

    public static Calendar clearTime(Calendar calendar) {
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        return calendar;
    }

//
//    public static String splitAndReturnLastName(String name) {
//        String[] arr = name.split("\\s+");
//        if (arr.length > 0)
//            return arr[arr.length - 1];
//        else
//            return null;
//    }
//
//    public static boolean allAreNullOrEmpty(String... strings) {
//        if (strings != null) {
//            for (String string : strings) {
//                if (!string.isEmpty())
//                    return false;
//            }
//        }
//        return true;
//    }

    public static void setBlobValueNullAware(PreparedStatement stmt, int index, byte[] value, LobHandler lobHandler) throws SQLException {
        if (value == null) {
            stmt.setNull(index, Types.BLOB);
        } else {
            lobHandler.getLobCreator().setBlobAsBytes(stmt, index, value);
        }
    }

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
    public static void setDateValueNullAware(PreparedStatement stmt, int index, Date value) throws SQLException{
        if(value == null) stmt.setNull(index, Types.DATE);
        else stmt.setDate(index, value);
        }

//
//    public static Long getLongNullAware(CallableStatement cs, int parameterIndex) throws SQLException {
//        Long l = cs.getLong(parameterIndex);
//        return cs.wasNull() ? null : l;
//    }
//
//    public static String truncateBytes(String input, Charset charset, int maxBytes) {
//        if (input.isEmpty()) {
//            return input;
//        }
//
//        ByteBuffer outBuffer = ByteBuffer.allocate(maxBytes);
//        CharBuffer inBuffer = CharBuffer.wrap(input.toCharArray());
//        CharsetEncoder encoder = charset.newEncoder();
//        encoder.onMalformedInput(CodingErrorAction.REPLACE);
//        encoder.onUnmappableCharacter(CodingErrorAction.REPLACE);
//        CoderResult result = encoder.encode(inBuffer, outBuffer, true);
//        if (result.isUnderflow()) {
//            return input;
//        } else {
//            return new String(outBuffer.array(), 0, outBuffer.position(), charset);
//        }
//    }


}
