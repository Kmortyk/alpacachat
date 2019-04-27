package com.vanilla.land;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {

    @GetMapping(path = "/")
    public String index() { return "index"; }

    @GetMapping(path = "/api")
    public String api() { return "api"; }

}
