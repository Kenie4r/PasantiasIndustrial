package com.industrial.pasantias.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;

@Controller

public class MainController {
    @RequestMapping(method = RequestMethod.GET, path = { "/", "", "/inicio", "/index" })
    public String obtenerPaginaPrincipal(Model model) {        
        return "index.html";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String requestMethodName() {
        return "login.html";
    }

    /*
     * @RequestMapping(path = "/access", method=RequestMethod.POST)
     * public String requestMethodName(@RequestParam String param) {
     * 
     * return new String();
     * }
     */

}
