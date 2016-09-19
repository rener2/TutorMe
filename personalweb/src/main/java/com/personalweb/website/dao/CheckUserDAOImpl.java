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
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class CheckUserDAOImpl implements CheckUserDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    DatabaseHelper databaseHelper = new DatabaseHelper();



    public PageUser getUser(PageUser user) {
        String query = "SELECT * FROM WEB.USER WHERE EMAIL = ? AND PASSWORD = ?";

        return jdbcTemplate.execute(query,
                new PreparedStatementCallback<PageUser>(){
                    @Override
                    public PageUser doInPreparedStatement(PreparedStatement statement) throws SQLException, DataAccessException {
                        String errorText = null;
                        PageUser pageUser = null;
                        ResultSet resultSet = null;
                        CommandUtils.setStringValueNullAware(statement, 1, user.getEmail());
                        CommandUtils.setStringValueNullAware(statement, 2, user.getPassword());
                        try {
                            resultSet = statement.executeQuery();
                            pageUser = userToObject(resultSet);
                        } finally {
                            databaseHelper.closeStatement(statement);
                        }
                        return pageUser;
                    }
                });
    }

    private PageUser userToObject(ResultSet rs) throws SQLException{
        PageUser user = new PageUser();
        if (!rs.next()) return null;
        user.setEmail(rs.getString("email"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setPassword(rs.getString("password"));
        return user;
    }




}
