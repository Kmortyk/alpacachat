package com.vanilla.land.repository;

import com.vanilla.land.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AccountsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private AccountMapper rowMapper = new AccountMapper();

    public String createAccount(String name) {

        String key = Secure.getKey(10, name);
        if(key == null) return "";

        jdbcTemplate.update("INSERT INTO accounts(\"key\", name) VALUES (?, ?);", key, name);
        return getKey(name);

    }

    public boolean keyExists(String name) {

        String query = "SELECT COUNT(*) FROM accounts WHERE accounts.name = ?";
        Integer count = jdbcTemplate.queryForObject(query, new Object[] { name }, Integer.class);

        return count != null && count > 0;

    }

    public String getKey(String name) {
        return jdbcTemplate.queryForObject("SELECT \"key\" FROM accounts WHERE accounts.name = ?", new Object[] { name }, String.class);
    }

    public Account getAccount(String name) {
        return jdbcTemplate.queryForObject("SELECT * FROM accounts WHERE accounts.name = ?", rowMapper, name);
    }

}
