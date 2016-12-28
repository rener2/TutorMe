package com.personalweb.website.dao;

import com.personalweb.website.form.PageUser;
import com.personalweb.website.utils.CommandUtils;
import com.personalweb.website.utils.DatabaseHelper;
import com.personalweb.website.utils.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;


@Repository
public class NewUserDAOImpl implements NewUserDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    LobHandler lobHandler;

    DatabaseHelper databaseHelper = new DatabaseHelper();


    public String addUser(PageUser user) {
        String query = "INSERT INTO WEB.USER " +
                "(PASSWORD, EMAIL, PROFILE_PIC)" +
                "VALUES" +
                "(?, ?, ?)";

        return jdbcTemplate.execute(query,
                new PreparedStatementCallback<String>() {
                    @Override
                    public String doInPreparedStatement(PreparedStatement statement) throws SQLException, DataAccessException {
                        String errorText = null;
                        CommandUtils.setStringValueNullAware(statement, 1, user.getPassword());
                        CommandUtils.setStringValueNullAware(statement, 2, user.getEmail());
                        CommandUtils.setBlobValueNullAware(statement, 3, user.getProfilePic(), lobHandler);
                        try {
                            statement.executeUpdate();
                        } finally {
                            databaseHelper.closeStatement(statement);
                        }
                        return errorText;
                    }
                });
    }

    public String addFbUser(PageUser user) {
        String query = "INSERT INTO WEB.USER " +
                "(EMAIL, PROFILE_PIC, FIRST_NAME, LAST_NAME, BIRTHDATE) " +
                "VALUES" +
                "(?, ?, ?, ?, ?)";

        return jdbcTemplate.execute(query,
                new PreparedStatementCallback<String>() {
                    @Override
                    public String doInPreparedStatement(PreparedStatement statement) throws SQLException, DataAccessException {
                        String errorText = null;
                        CommandUtils.setStringValueNullAware(statement, 1, user.getEmail());
                        CommandUtils.setBlobValueNullAware(statement, 2, user.getProfilePic(), lobHandler);
                        CommandUtils.setStringValueNullAware(statement, 3, user.getFirstName());
                        CommandUtils.setStringValueNullAware(statement, 4, user.getLastName());

                        if (user.getBirthdate() != null) {
                            CommandUtils.setDateValueNullAware(statement, 5, Parser.parseDate(user.getBirthdate()));
                        } else {
                            statement.setNull(5, Types.DATE); //todo, hetkel kiire fix
                        }
                        // todo statement.setDate(5, user.getBirthdate());
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

