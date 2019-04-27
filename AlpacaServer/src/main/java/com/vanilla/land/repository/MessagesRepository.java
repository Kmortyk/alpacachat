package com.vanilla.land.repository;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class MessagesRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int addMessage(String accountKey, String message) {
        return jdbcTemplate.update("INSERT INTO messages(account_key, message) VALUES (?, ?);", accountKey, message);
    }

    public JSONArray getAll() {

        String sql = "SELECT messages.message AS message, " +
                     "accounts.name AS name FROM messages " +
                     "LEFT JOIN accounts ON messages.account_key = accounts.key;";

        return get(sql, null);

    }

    public JSONArray get(String key) {

        String sql = "SELECT messages.message AS message, " +
                "accounts.name AS name FROM messages " +
                "LEFT JOIN accounts ON messages.account_key = accounts.key;";

        return get(sql, key);

    }

    public JSONArray get(String key, int from, int to) {

        String sql = "SELECT messages.account_key AS account_key " +
                     "messages.message AS message, " +
                     "accounts.name AS name FROM messages " +
                     "WHERE messages.id >" + from + " AND messages.id < " + to +
                     "LEFT JOIN accounts ON messages.account_key = accounts.key;";

        return get(sql, key);

    }

    private JSONArray get(String sql, String requestAccountKey) {

        JSONArray jsonArr = new JSONArray();

        try(
                Connection connection = jdbcTemplate.getDataSource().getConnection();
                Statement statement = connection.createStatement();
                ResultSet set = statement.executeQuery(sql);
        )
        {
            while(set.next()) {
                String message = set.getString("message");
                String name = set.getString("name");

                JSONObject obj = new JSONObject()
                        .put("name", name)
                        .put("message", message);

                if(requestAccountKey != null) {
                    String accountKey = set.getString("account_key");
                    if(accountKey.equals(requestAccountKey))
                        obj.put("cur_user", "true");
                }

                jsonArr.put(obj);
            }
        } catch (SQLException e) { e.printStackTrace(); }

        return jsonArr;

    }

}
