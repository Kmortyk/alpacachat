package com.vanilla.land.controller;

import com.vanilla.land.repository.AccountsRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("account")
public class AccountController {

    private final AccountsRepository rep;

    @Autowired
    public AccountController(AccountsRepository rep) { this.rep = rep; }

    @RequestMapping(value = "/enter", method = RequestMethod.POST)
    public String enterInAccount(@RequestBody String body) {

        JSONObject o = new JSONObject(body);
        String name = o.getString("name");

        String key;

        if(rep.keyExists(name)) {
            key = rep.getKey(name);
        } else {
            key = rep.createAccount(name);
        }

        return "{\"account_key\":\"" + key + "\"}";

    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createAccount(@RequestBody String body) {

        JSONObject o = new JSONObject(body);
        String name = o.getString("name");

        return "{\"account_key\":\"" + rep.createAccount(name) + "\"}";

    }

    @RequestMapping(value = "/get_key", method = RequestMethod.POST)
    public String getAccountKey(@RequestBody String body) {
        JSONObject o = new JSONObject(body);
        return"{\"account_key\":\"" + rep.getKey(o.getString("name")) + "\"}";
    }

}
