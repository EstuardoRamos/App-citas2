package com.project.Citas.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.Citas.JsonResponse;
import com.project.Citas.DTO.LoginDTO;
import com.project.Citas.DTO.verificarCorreoDTO;
import com.project.Citas.models.userModel;
import com.project.Citas.services.userService;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class userController {
    @Autowired
    userService userService;

    @GetMapping("/{id}")
    public Optional<userModel> getUser(@PathVariable("id") Long id){
        return userService.getUserById(id);
    }

    @PutMapping
    public JsonResponse updateUser(@RequestBody userModel userModel){
        return userService.updateUser(userModel);
    }

    @PutMapping("/password")
    public JsonResponse updatePassword(@RequestBody com.project.Citas.DTO.updatePassword tmp){
        return userService.updatePassword(tmp.getActual(), tmp.getNueva(), tmp.getId());
    }

    @PostMapping
    public JsonResponse saveUser(@RequestBody userModel newUser){
        return userService.saveUser(newUser);
    }

    @PostMapping("/validarCorreo")
    public userModel validarCorreo(@RequestBody verificarCorreoDTO nuevoUsuario){
        return userService.verificarCorreoElectronico(nuevoUsuario.getUsuarioNuevo(), nuevoUsuario.getCodigo());
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO login){
        return userService.login(login.getCorreoElectronico(), login.getContrasenia());
    }

    @PostMapping("/login/a2f")
    public ResponseEntity<?> verificarA2f(@RequestBody LoginDTO credenciales){
        return userService.verificarCodigo2Fa(credenciales.getCorreoElectronico(), credenciales.getContrasenia());
    }




}
