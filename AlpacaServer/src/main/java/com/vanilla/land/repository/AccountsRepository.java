package com.vanilla.land.repository;

import com.google.gson.JsonObject;
import com.vanilla.land.entity.Account;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class AccountsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private AccountMapper rowMapper = new AccountMapper();

    public String createAccount(String name) {

        String key = Secure.getKey(10, name);
        if(key == null) {
            return "{\"error\":\"Cannot generate secure key for account\"}";
        }

        jdbcTemplate.update("INSERT INTO accounts(\"key\", name) VALUES (?, ?);", key, name);
        return "{\"account_key\":\"" + getKey(name) + "\"}";

    }

    public String getKey(String name) {
        return jdbcTemplate.queryForObject("SELECT \"key\" FROM accounts WHERE accounts.name = ?", new Object[] { name }, String.class);
    }

    public Account getAccount(String name) {
        return jdbcTemplate.queryForObject("SELECT * FROM accounts WHERE accounts.name = ?", rowMapper, name);
    }

}
