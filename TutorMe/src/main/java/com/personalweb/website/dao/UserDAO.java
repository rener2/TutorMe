package com.personalweb.website.dao;

import com.personalweb.website.form.InboxMessage;
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

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository
public class UserDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    LobHandler lobHandler;

    DatabaseHelper databaseHelper = new DatabaseHelper();


    public PageUser getUser(Long userId) {
        String query = "select * from web.user where user_id = ?";

        return jdbcTemplate.execute(query,
                new PreparedStatementCallback<PageUser>(){
                    @Override
                    public PageUser doInPreparedStatement(PreparedStatement statement) throws SQLException, DataAccessException {
                        ResultSet rs = null;
                        PageUser user = null;
                        try {
                            CommandUtils.setNumericValueNullAware(statement, 1, userId);
                            rs = statement.executeQuery();
                            rs.next();
                            user = resultSetToUser(rs);
                        }  catch (SQLException e) {
                            //todo
                        }
                        return user;                    }
                });
    }

    public PageUser getUser(String email) {
        String query = "select * from web.user where email = ?";

        return jdbcTemplate.execute(query,
                new PreparedStatementCallback<PageUser>(){
                    @Override
                    public PageUser doInPreparedStatement(PreparedStatement statement) throws SQLException, DataAccessException {
                        ResultSet rs = null;
                        PageUser user = null;
                        try {
                            CommandUtils.setStringValueNullAware(statement, 1, email);
                            rs = statement.executeQuery();
                            rs.next();
                            user = resultSetToUser(rs);
                        }  catch (SQLException e) {
                            //todo
                        }
                        return user;
                    }
                });
    }

    public Integer uploadProfilePic(byte[] pic, Long user_id) {
        String query = "update web.user set profile_pic = ? where user_id = ?";
        return jdbcTemplate.execute(query,
                new PreparedStatementCallback<Integer>(){
                    @Override
                    public Integer doInPreparedStatement(PreparedStatement statement) throws SQLException, DataAccessException {
                        ResultSet rs = null;
                        CommandUtils.setBlobValueNullAware(statement, 1, pic, lobHandler);
                        CommandUtils.setNumericValueNullAware(statement, 2, user_id);
                        return statement.executeUpdate();
                    }
                });
    }

     PageUser resultSetToUser(ResultSet rs) throws SQLException {
         PageUser user = new PageUser();
        try {
            user.setUserID(rs.getLong("user_id"));
            user.setEmail(rs.getString("email"));
            user.setFirstName(rs.getString("first_name"));
            user.setPassword(rs.getString("password"));
            user.setLastName(rs.getString("last_name"));
            user.setUsername(rs.getString("username"));
            user.setBirthdate(rs.getDate("birthdate"));
            user.setEducation(rs.getString("education"));
            user.setPhone(rs.getString("phone"));
            user.setSpeciality(rs.getString("speciality"));
            Blob blob = rs.getBlob("profile_pic");
            if(blob != null) {
                int blobLength = (int) blob.length();
                byte[] blobAsBytes = blob.getBytes(1, blobLength);
                user.setProfilePic(blobAsBytes);
                blob.free();
            }
        } catch (SQLException e) {
            throw new SQLException("Unable to parse resultset to user object", e.getMessage());
        }
        return user;
    }

    InboxMessage resultSetToMessage(ResultSet rs) throws SQLException {
        InboxMessage message = new InboxMessage();
        try {
            message.setId(rs.getLong("MESSAGE_ID"));
            message.setFromEmail(rs.getString("EMAIL"));
            message.setSubject(rs.getString("SUBJECT"));
            message.setText(rs.getString("TEXT"));
            message.setRead(rs.getBoolean("ISREAD"));

        } catch (SQLException e) {
            throw new SQLException("Unable to parse resultset to user object", e.getMessage());
        }
        return message;
    }


    public Integer setUserInfo(PageUser user, Long userId) {
        String query = "update web.user set first_name = ?, last_name = ?, education = ?, speciality = ?, email = ?, phone = ?, birthdate = ? " +
                "where user_id = ?";

        return jdbcTemplate.execute(query,
                new PreparedStatementCallback<Integer>(){
                    @Override
                    public Integer doInPreparedStatement(PreparedStatement statement) throws SQLException, DataAccessException {
                        ResultSet rs = null;
                            CommandUtils.setStringValueNullAware(statement, 1, user.getFirstName());
                            CommandUtils.setStringValueNullAware(statement, 2, user.getLastName());
                            CommandUtils.setStringValueNullAware(statement, 3, user.getEducation());
                            CommandUtils.setStringValueNullAware(statement, 4, user.getSpeciality());
                            CommandUtils.setStringValueNullAware(statement, 5, user.getEmail());
                            CommandUtils.setStringValueNullAware(statement, 6, user.getPhone());
                            CommandUtils.setDateValueNullAware(statement, 7, Parser.parseDate(user.getBirthdate()));
                            statement.setLong(8, userId);
                            return statement.executeUpdate();

                    }
                });
    }

    public List<PageUser> findUsersByName(String keyword) {
        String query = "select * from web.user pUser " +
                "left outer join web.advertisement ad " +
                "on pUser.user_id = ad.user_id " +
                "where lower(first_name || ' ' ||  last_name) like ?;"; //%keyword%

        return jdbcTemplate.execute(query,
                new PreparedStatementCallback<List<PageUser>>(){
                    @Override
                    public List<PageUser> doInPreparedStatement(PreparedStatement statement) throws SQLException, DataAccessException {
                        ResultSet rs;
                        List<PageUser> searchUsers = new ArrayList<>();
                        try {
                            CommandUtils.setStringValueNullAware(statement, 1, "%"+keyword.toLowerCase()+"%");
                            rs = statement.executeQuery();
                            while (rs.next()) {
                                searchUsers.add(resultSetToUser(rs));
                            }

                        } catch (SQLException e) {
                            //todo
                        }
                        return searchUsers;
                    }
                });
    }


    public Boolean userExists(String email) {
        String query = "SELECT COUNT(*) AS total FROM web.user WHERE email = ?;";
        return jdbcTemplate.execute(query,
                new PreparedStatementCallback<Boolean>(){
                    @Override
                    public Boolean doInPreparedStatement(PreparedStatement statement) throws SQLException, DataAccessException {
                        ResultSet rs;
                        Boolean result = false;
                        try {
                            CommandUtils.setStringValueNullAware(statement, 1, email);
                            rs = statement.executeQuery();
                            rs.next();
                            if (rs.getInt("total") > 0) result = true;
                        }  catch (SQLException e) {
                            //todo
                        }
                        return result;
                    }
                });

    }

    public List<InboxMessage> getUserMessages(Long userId) {
        String query = "select messages.message_id, messages.text, messages.subject, messages.isread, " +
                "users.email, users.first_name, users.last_name, users.user_id " +
                "from web.message messages "+
                "inner join web.user users "+
                "on messages.from_id = users.user_id "+
                "where messages.to_id = ? " +
                "group by messages.message_id " +
                "order by MAX(MESSAGES.MESSAGE_ID) DESC;";

        return jdbcTemplate.execute(query,
                new PreparedStatementCallback<List<InboxMessage>>(){
                    @Override
                    public List<InboxMessage> doInPreparedStatement(PreparedStatement statement) throws SQLException, DataAccessException {
                        ResultSet rs;
                        List<InboxMessage> messages = new ArrayList<>();
                        try {
                            CommandUtils.setNumericValueNullAware(statement, 1, userId);
                            rs = statement.executeQuery();
                            while (rs.next()) {
                                messages.add(resultSetToMessage(rs));
                            }

                        } catch (SQLException e) {
                            //todo
                        }
                        return messages;
                    }
                });
    }

    public Integer sendMessage(InboxMessage message) {
        String query = "insert into web.message (subject, text, to_id, from_id, isread) " +
                "values(?,?,?,?, false);";
        return jdbcTemplate.execute(query,
                new PreparedStatementCallback<Integer>(){
                    @Override
                    public Integer doInPreparedStatement(PreparedStatement statement) throws SQLException, DataAccessException {
                        Integer result = null;
                        List<InboxMessage> messages = new ArrayList<>();
                        try {
                            CommandUtils.setStringValueNullAware(statement, 1, message.getSubject());
                            CommandUtils.setStringValueNullAware(statement, 2, message.getText());
                            CommandUtils.setNumericValueNullAware(statement, 3, message.getToId());
                            CommandUtils.setNumericValueNullAware(statement,4, message.getFromId());
                            result = statement.executeUpdate();
                        } catch (SQLException e) {
                            //todo
                        }
                        return result;
                    }
                });

    }

    public InboxMessage getMessage(Long messageId) {
        String query = "select messages.message_id, messages.text, messages.subject, messages.isread, " +
                "users.email, users.first_name, users.last_name, users.user_id " +
                "from web.message messages "+
                "inner join web.user users "+
                "on messages.from_id = users.user_id "+
                "where messages.message_id = ?;";

        return jdbcTemplate.execute(query,
                new PreparedStatementCallback<InboxMessage>(){
                    @Override
                    public InboxMessage doInPreparedStatement(PreparedStatement statement) throws SQLException, DataAccessException {
                        ResultSet rs = null;
                        InboxMessage message = null;
                        try {
                            CommandUtils.setNumericValueNullAware(statement, 1, messageId);
                            rs = statement.executeQuery();
                            rs.next();
                            message = resultSetToMessage(rs);
                        }  catch (SQLException e) {
                            //todo
                        }
                        return message;
                    }
                });
    }
}
