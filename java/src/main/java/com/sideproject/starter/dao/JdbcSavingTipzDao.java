package com.sideproject.starter.dao;

import com.sideproject.starter.exception.DaoException;
import com.sideproject.starter.model.SavingTipz;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;


import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcSavingTipzDao implements SavingTipzDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcSavingTipzDao(@NonNull DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public SavingTipz getSavingTipz() {
        SavingTipz savingTipz = new SavingTipz();
        String sql = "SELECT message FROM greetings LIMIT 1";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            if (results.next()) {
                savingTipz.setMessage(results.getString("message"));
            } else {
                throw new DaoException("No message found");
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database");
        }
        return savingTipz;
    }


    @Override
    public SavingTipz addMessage(SavingTipz savingTipz) {
        String sql = "INSERT INTO greetings (message) VALUES (?);";
        try {
            int results = jdbcTemplate.update(sql, savingTipz.getMessage());
        } catch (NullPointerException e) {
            throw new DaoException("Returns null.", e);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database.", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation.", e);

        }
        return savingTipz;
    }

    @Override
    public List<SavingTipz> getAllSavingTipzs() {
        List<SavingTipz> savingTipzList = new ArrayList<>();
        String sql = "SELECT message FROM greetings";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                savingTipzList.add(mapRowsToSaving(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database");
        }
        return savingTipzList;
    }

    @Override
    public void deleteMessage(String messages) {
        String sql = "SELECT * FROM greetings";
        try {
            int rowsAffected = jdbcTemplate.update(sql, messages);
            if (rowsAffected == 0) {
                throw new DaoException("No message found with ID: " + messages.substring(0, 10));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database", e);
        }
    }

    public SavingTipz mapRowsToSaving(SqlRowSet rowSet){
        SavingTipz savingTipz = new SavingTipz();
        savingTipz.setMessage(rowSet.getString("message"));
        return savingTipz;
    }

}
