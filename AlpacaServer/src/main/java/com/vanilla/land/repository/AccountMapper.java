package com.vanilla.land.repository;

import com.vanilla.land.entity.Account;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountMapper implements RowMapper<Account> {

    @Override
    public Account mapRow(ResultSet resultSet, int i) throws SQLException {
        Account account = new Account();
        account.id = resultSet.getInt("id");
        account.accountKey = resultSet.getString("key");
        account.name = resultSet.getString("name");
        account.photoUrl = resultSet.getString("photo_url");
        return account;
    }

}
