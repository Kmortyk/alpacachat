package com.vanilla.land.secretchat;

import com.google.gson.Gson;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
public class SecretChatController {

    private final static String REG_LOGIN = "[a-zA-Z0-9_$]*";
    private final static String REG_ROOT = "\\s*r[Oo]{2}t\\s*";
    private ArrayList<SecretChatMessage> secretChatMessages = new ArrayList<>();

    @ResponseBody
    @RequestMapping(path = "/secret_chat", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String secretChatPost(HttpServletRequest req) {

        String nickname = req.getParameter("nickname");
        String message  = req.getParameter("message");

        if(nickname == null || nickname.isEmpty()) nickname = "No nick";
        if(message  == null || message.isEmpty())  message = "No message";

        // nickname check
        if(!nickname.matches(REG_LOGIN) ||
            nickname.matches(REG_ROOT) && !req.getRemoteAddr().equals("192.168.0.62")) {
            return "";
        }

        if(nickname.length() > 10)
            nickname = nickname.substring(0, 9);

        // System.out.println(nickname  + " " + message);

        secretChatMessages.add(new SecretChatMessage(nickname, message));
        return new Gson().toJson(secretChatMessages);

    }

    @ResponseBody
    @RequestMapping(path = "/secret_chat", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String secretChatGet() {
        return new Gson().toJson(secretChatMessages);
    }

}
