package com.sideprojectstarter.dao;

import com.sideproject.starter.dao.JdbcSavingTipzDao;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;

public class JdbcSavingTipzDaoTests extends BaseDaoTests{

    private JdbcSavingTipzDao dao;

    @Before
    public void setup(){
        dao = new JdbcSavingTipzDao(dataSource);
    }

    @Test
    public void get_balance_returns_correct_balance(){

    }









}
