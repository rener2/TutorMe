package com.personalweb.website.dao;

import com.personalweb.website.utils.CommandUtils;
import com.personalweb.website.utils.DatabaseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@Repository
public class InfoDAOImpl implements InfoDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    DatabaseHelper databaseHelper = new DatabaseHelper();


    public Integer getPeopleCount() {
        String query = "select count(*) as total from web.user";

        return jdbcTemplate.execute(query,
                new PreparedStatementCallback<Integer>(){
                    @Override
                    public Integer doInPreparedStatement(PreparedStatement statement) throws SQLException, DataAccessException {
                        String errorText = null;
                        ResultSet rs = null;
                        Integer result = 0;
                        try {
                            rs = statement.executeQuery();
                            rs.next();
                            result = rs.getInt("total");
                        } finally {
                            databaseHelper.closeStatement(statement);
                        }
                        return result;
                    }
                });
    }

}
