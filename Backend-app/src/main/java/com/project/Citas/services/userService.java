package com.project.Citas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.Citas.JsonResponse;
import com.project.Citas.TokenResponse;
import com.project.Citas.Errors.ModelAlreadyExistException;
import com.project.Citas.Errors.ModelNotFoundException;
import com.project.Citas.Utils.verificacionA2fCache;
import com.project.Citas.models.tipoUsuarioModel;
import com.project.Citas.models.userModel;
import com.project.Citas.repositories.tipoUsuarioRepository;
import com.project.Citas.repositories.userRepository;
import java.util.Optional;
import java.util.Random;

@Service
public class userService {
    @Autowired
    userRepository userRepositorie;

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private verificacionA2fCache cache = new verificacionA2fCache();

    @Autowired
    tipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public JsonResponse updateUser(userModel nUserModel){
        Optional<userModel> existe = userRepositorie.findById(nUserModel.getId());
        if(existe.isPresent()){
            userRepositorie.save(nUserModel);
            return new JsonResponse(500, "Usuario actualizado correctamente");
        }else
            return new JsonResponse(404, "El usuario no existe");
    }

    public JsonResponse updatePassword(String actual,String nueva,Long id){
        Optional<userModel> existe = userRepositorie.findById(id);
        if (existe.isPresent()) {
            userModel user = existe.get();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            // Verificar si la contraseña actual coincide con la encriptada
            if (passwordEncoder.matches(actual, user.getContrasenia())) {
                // Si coincide, encriptar la nueva contraseña y actualizarla
                String nuevaContraseniaEncriptada = passwordEncoder.encode(nueva);
                user.setContrasenia(nuevaContraseniaEncriptada);
                userRepositorie.save(user);
                return new JsonResponse(500,"Contraseña actualizada exitosamente");
            } else {
                return new JsonResponse( 404,"La contraseña actual es incorrecta");
            }
        } else {
            return new JsonResponse(404,"Usuario no econtrado");
        }
    }

    public Optional<userModel> getUserById(Long id) {
        Optional<userModel> usuario = userRepositorie.findById(id);
        if (!usuario.isPresent()) {
            throw new ModelNotFoundException("El usuario no con id:" + id + " no existe");
        }
        return usuario;
    }

    public ResponseEntity<?> login(String correoElectronico, String contrasenia) {
        Optional<userModel> user = findByCorreoElectronico(correoElectronico);
        if (user.isPresent()) {
            if (verifyPassword(contrasenia, user.get().getContrasenia())) {
                if (!user.get().isA2f()) {
                    return generarToken(user.get());
                } else {
                    enviarCodigo2FA(correoElectronico, generarCodigo2FA(),"Codigo de inicio de sesion a2f: ");
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Se ha enviado un código 2FA al correo");
                }
            } else {
                throw new ModelNotFoundException("Credenciales incorrectas");
            }
        } else {
            throw new ModelNotFoundException("El usuario no existe ");
        }
    }

    public ResponseEntity<TokenResponse> generarToken(userModel user ) {
        String token = jwtService.generateToken(user.getNombre(),
                user.getTipoUsuario().getId(),user.getId());
        TokenResponse Token = new TokenResponse(token);
        return ResponseEntity.ok(Token);
    }

    private String generarCodigo2FA() {
        // Genera un código de 6 dígitos aleatorio
        return String.format("%06d", new Random().nextInt(999999));
    }

    public void enviarCodigo2FA(String correo, String codigo,String mensake) {
        cache.guardarCodigo(correo, codigo);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(correo);
        message.setSubject("Tu código de autenticación");
        message.setText(mensake + codigo);
        System.out.println("CODIGO: " + codigo);
        mailSender.send(message);
    }

    public ResponseEntity<?> verificarCodigo2Fa(String correo, String codigoIngresado) {
        Optional<userModel> user = userRepositorie.findByCorreoElectronico(correo);
        if (!user.isPresent()) {
            throw new RuntimeException("Usuario no encontrado");
        }
        String codigoGuardado = cache.obtenerCodigo(correo);
        if (codigoGuardado != null && codigoGuardado.equals(codigoIngresado)) {
            System.out.println("Autenticación 2FA exitosa");
            cache.eliminarCodigo(correo); // Limpiar el código después de uso
            return generarToken(user.get());
        } else 
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Se ha enviado un código 2FA al correo");
    }

    public userModel verificarCorreoElectronico(userModel usuarioNuevo,String codigoIngresado){
        String codigoGuardado = cache.obtenerCodigo(usuarioNuevo.getCorreoElectronico());
        if (codigoGuardado != null && codigoGuardado.equals(codigoIngresado)) {
            cache.eliminarCodigo(usuarioNuevo.getCorreoElectronico()); // Limpiar el código después de uso
            String passwordEnscriptada = passwordEncoder.encode(usuarioNuevo.getContrasenia());
            usuarioNuevo.setContrasenia(passwordEnscriptada);
            return userRepositorie.save(usuarioNuevo);
        } else 
            throw new ModelNotFoundException("Codigo no valido o expirado");
    }
    
    public JsonResponse saveUser(userModel userModel) {
        Optional<tipoUsuarioModel> tipoUsuario = tipoUsuarioRepository.findById(userModel.getTipoUsuario().getId());
        if (!tipoUsuario.isPresent()) {
            throw new ModelNotFoundException("El tipo de usuario no existe");
        }
        Optional<userModel> user = userRepositorie.findByCorreoElectronico(userModel.getCorreoElectronico());
        if (user.isPresent()) {
            throw new ModelAlreadyExistException(
                    "El correo electronico: " + userModel.getCorreoElectronico() + " ya existe");
        }
        user = userRepositorie.findByCui(userModel.getCui());
        if(user.isPresent()){
            throw new ModelAlreadyExistException(
                    "El Cui: " + userModel.getCui() + " ya esta asociado a una cuenta");
        }
        user =  userRepositorie.findByNit(userModel.getNit());
        if(user.isPresent()){
            throw new ModelAlreadyExistException(
                    "El NIT: " + userModel.getNit() + " ya esta asociado a una cuenta");
        }
        user = userRepositorie.findByTelefono(userModel.getTelefono());
        if(user.isPresent())
        throw new ModelAlreadyExistException(
            "El Telefono: " + userModel.getTelefono() + " ya esta asociado a una cuenta");
        String passwordEnscriptada = passwordEncoder.encode(userModel.getContrasenia());
        userModel.setContrasenia(passwordEnscriptada);
        enviarCodigo2FA(userModel.getCorreoElectronico(), generarCodigo2FA(),"Codigo de autentificacion de correo: ");
        return new JsonResponse(500, "Se ha enviado un codigo de verificacion al correo :" + userModel.getCorreoElectronico());
        
    }

    public Optional<userModel> findByCorreoElectronico(String correoElectoString) {
        return userRepositorie.findByCorreoElectronico(correoElectoString);

    }

    public boolean verifyPassword(String rawPassword, String encryptedPassword) {
        return BCrypt.checkpw(rawPassword, encryptedPassword); 
    }

}
