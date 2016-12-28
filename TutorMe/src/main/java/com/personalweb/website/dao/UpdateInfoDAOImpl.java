package com.personalweb.website.dao;

import com.personalweb.website.form.PageUser;
import com.personalweb.website.utils.CommandUtils;
import com.personalweb.website.utils.DatabaseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;


@Repository
public class UpdateInfoDAOImpl implements UpdateInfoDAO{

    @Autowired
    JdbcTemplate jdbcTemplate;

    DatabaseHelper databaseHelper = new DatabaseHelper();


    public String updateInfo(PageUser user) {
        String query = "UPDATE TABLENAME " +
                        "SET (first_name, last_name, email) = " +
                        "(?, ?, ?) WHERE USER_ID=?";

        return jdbcTemplate.execute(query,
                new PreparedStatementCallback<String>(){
                    @Override
                    public String doInPreparedStatement(PreparedStatement statement) throws SQLException, DataAccessException {
                        String errorText = null;
                        CommandUtils.setStringValueNullAware(statement, 1, user.getFirstName());
                        CommandUtils.setStringValueNullAware(statement, 2, user.getLastName());
                        CommandUtils.setStringValueNullAware(statement, 3, user.getEmail());
                        CommandUtils.setNumericValueNullAware(statement, 4, user.getUserID());
                        try {
                            statement.executeUpdate();
                        } finally {
                            databaseHelper.closeStatement(statement);
                        }
                        return errorText;
                    }
                });
    }


}

