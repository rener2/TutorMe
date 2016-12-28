package com.personalweb.website.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseHelper {

    public void closeStatement(PreparedStatement stmt) throws SQLException{
        try {
            stmt.close();
        } catch (SQLException e) {
            //logger.log("Failed to close preparedstatement: ", e);
        }
        }
//
//    public void closeStatement(Statement stmt) throws SQLException{
//        try {
//            stmt.close();
//        } catch (SQLException e) {
//            //logger.log("Failed to close statement: ", e);
//        }
//    }
//    public void closeConnection(Connection cntc) throws SQLException{
//        try {
//            cntc.close();
//        } catch (SQLException e) {
//            //logger.log("Failed to close Connection: ", e);
//        }
//    }
}
