package com.industrial.pasantias.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller

public class MainController {

    @RequestMapping(method = RequestMethod.GET, path = {"/", "", "/inicio", "/index"})
    public String  obtenerPaginaPrincipal(){
        return "index.html"; 
    }
}
