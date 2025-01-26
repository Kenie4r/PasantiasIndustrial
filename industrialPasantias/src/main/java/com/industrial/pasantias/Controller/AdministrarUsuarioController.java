package com.industrial.pasantias.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.industrial.pasantias.Model.Usuario;
import com.industrial.pasantias.Servicio.UsuarioService;
import com.industrial.pasantias.ViewModel.GenericResponse;


@RestController
public class AdministrarUsuarioController {

    @Autowired
    private UsuarioService servicioUsuario; 

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(path = "/validarPass", method=RequestMethod.POST)
    public GenericResponse requestMethodName(@RequestParam String actualPass, Authentication authentication) throws Exception{
        GenericResponse result = new GenericResponse();         
        //String hasherdPass = passwordEncoder.encode(actualPass); 
        Usuario user =   servicioUsuario.obtenerPorUsername(authentication.getName()); 
        result.setMessage("Nombre de usuario logeado:  " + authentication.getName());
        if(passwordEncoder.matches(actualPass, user.getPassword())){
            result.setResult("00");
            result.setActive(true);
            
          result.setMessage("Contraseña coinciden");
        }else {
            result.setResult("99");
            result.setActive(false);
           result.setMessage("Por favor ingresar su contraseña ");
        }
        return result;
    }
    

    @RequestMapping(path = "/ChangePass", method=RequestMethod.POST)
    public GenericResponse changePassword(@RequestParam(name = "newPass") String pass, Authentication authentication) throws Exception {
        GenericResponse response = new GenericResponse(); 
        if(pass == null || pass.isEmpty() ){
            response.setResult("91");
            response.setMessage( "Por favor ingrese su nueva contraseña");
            response.setActive(false);
        }else{ 
            Usuario user  = servicioUsuario.obtenerPorUsername(authentication.getName()); 
            String hashPassword = passwordEncoder.encode(pass); 
            user.setPassword(hashPassword);
            servicioUsuario.guardar(user); 
            response.setResult("00");
            response.setMessage( "Contraseña actualizada de manera correcta");
            response.setActive(true);
        }
        

        return response;
    }
    
    

}
