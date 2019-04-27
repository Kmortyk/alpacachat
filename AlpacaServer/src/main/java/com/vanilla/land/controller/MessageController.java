package com.vanilla.land.controller;

import com.vanilla.land.repository.AccountsRepository;
import com.vanilla.land.repository.MessagesRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("message")
public class MessageController {

    private final MessagesRepository rep;

    @Autowired
    public MessageController(MessagesRepository rep) { this.rep = rep; }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity sendMessage(@RequestBody String body) {

        JSONObject obj = new JSONObject(body);

        String accountKey = obj.getString("account_key");
        String message = obj.getString("message");

        rep.addMessage(accountKey, message);

        return new ResponseEntity(HttpStatus.OK);

    }

    @RequestMapping(value = "/get_all", method = RequestMethod.GET)
    public String getAll() {
        return rep.getAll().toString();
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public String get(@RequestBody String body) {

        JSONObject obj = new JSONObject(body);
        String accountKey = obj.getString("key");

        return rep.get(accountKey).toString();

    }

}
