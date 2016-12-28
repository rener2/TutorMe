package com.personalweb.website.dao;

import com.personalweb.website.form.Advertisement;
import com.personalweb.website.form.PageUser;
import com.personalweb.website.utils.CommandUtils;
import com.personalweb.website.utils.DatabaseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class AdDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserDAO userDAO;

    DatabaseHelper databaseHelper = new DatabaseHelper();

    public Integer addAd(Advertisement ad, Long userId) {
        String query = "INSERT INTO web.advertisement (language, subject, cost, contact, user_id) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.execute(query,
                new PreparedStatementCallback<Integer>(){
                    @Override
                    public Integer doInPreparedStatement(PreparedStatement statement) throws SQLException, DataAccessException {
                        CommandUtils.setStringValueNullAware(statement, 1, ad.getStudyLanguage());
                        CommandUtils.setStringValueNullAware(statement, 2, ad.getSubject());
                        CommandUtils.setStringValueNullAware(statement, 3, ad.getHourlyFee());
                        CommandUtils.setStringValueNullAware(statement, 4, ad.getContact());
                        statement.setLong(5, userId);
                        try {
                            return statement.executeUpdate();
                        } finally {
                            databaseHelper.closeStatement(statement);
                        }
                    }
                });
    }

    public List<Advertisement> getPopularAds() {
        String query = "select * " +
                "from web.advertisement ad " +
                "left outer join web.user user " +
                "on ad.user_id = user.user_id " +
                "where popularity > ? ;"; //todo 4 populaarsemat
        return jdbcTemplate.execute(query,
                new PreparedStatementCallback<List<Advertisement> >(){
                    @Override
                    public List<Advertisement>  doInPreparedStatement(PreparedStatement statement) throws SQLException, DataAccessException {
                        statement.setLong(1, new Long(0));
                        ResultSet rs = statement.executeQuery();
                        try {
                            List<Advertisement> popularAds = new ArrayList<>();
                            while (rs.next()) {
                                popularAds.add(resultSetToAd(rs));
                            }
                            return popularAds;
                        } finally {
                            databaseHelper.closeStatement(statement);
                        }
                    }
                });
    }

    public List<Advertisement> getUserAds(Long userId) {
        String query = "select * " +
                "from web.advertisement ad " +
                "left outer join web.user user " +
                "on ad.user_id = user.user_id " +
                "where ad.user_id = ?;";
        return jdbcTemplate.execute(query,
                new PreparedStatementCallback<List<Advertisement> >(){
                    @Override
                    public List<Advertisement>  doInPreparedStatement(PreparedStatement statement) throws SQLException{
                        statement.setLong(1, userId);
                        ResultSet rs = statement.executeQuery();
                        try {
                            List<Advertisement> userAds = new ArrayList<>();
                            while (rs.next()) {
                                userAds.add(resultSetToAd(rs));
                            }
                            return userAds;
                        } finally {
                            databaseHelper.closeStatement(statement);
                        }
                    }
                });
    }


    private Advertisement resultSetToAd(ResultSet rs) throws SQLException{
        Advertisement ad = new Advertisement();
        ad.setContact(rs.getString("contact"));
        ad.setHourlyFee(rs.getString("cost"));
        ad.setStudyLanguage(rs.getString("language"));
        ad.setPopularity(rs.getLong("popularity"));
        ad.setSubject(rs.getString("subject"));
        PageUser adPoster =  userDAO.resultSetToUser(rs);
        ad.setPoster(adPoster);
        return ad;
    }


    public List<Advertisement> findAdsBySubject(String keyword) {
        String query = "select * from web.advertisement ad " +
                "left outer join web.user user " +
                "on ad.user_id = user.user_id " +
                "where lower(ad.subject) like ? ;"; //%keyword%

        return jdbcTemplate.execute(query,
                new PreparedStatementCallback<List<Advertisement> >(){
                    @Override
                    public List<Advertisement>  doInPreparedStatement(PreparedStatement statement) throws SQLException{
                        statement.setString(1, "%"+keyword.toLowerCase()+"%");
                        ResultSet rs = statement.executeQuery();
                        try {
                            List<Advertisement> subjectAds = new ArrayList<>();
                            while (rs.next()) {
                                subjectAds.add(resultSetToAd(rs));
                            }
                            return subjectAds;
                        } finally {
                            databaseHelper.closeStatement(statement);
                        }
                    }
                });
    }



}
