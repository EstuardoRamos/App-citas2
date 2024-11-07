package com.project.Citas.Services;

import com.project.Citas.Errors.ModelAlreadyExistException;
import com.project.Citas.Errors.ModelNotFoundException;
import com.project.Citas.Utils.verificacionA2fCache;
import com.project.Citas.JsonResponse;
import com.project.Citas.models.tipoUsuarioModel;
import com.project.Citas.models.userModel;
import com.project.Citas.repositories.tipoUsuarioRepository;
import com.project.Citas.repositories.userRepository;
import com.project.Citas.services.JwtService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class userServiceTest {

    @InjectMocks
    private com.project.Citas.services.userService userService;

    @Mock
    private userRepository userRepositorie;

    @Mock
    private tipoUsuarioRepository tipoUsuarioRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private verificacionA2fCache cache;

    private userModel user;

    @BeforeEach
    void setUp() {
        user = new userModel();
        user.setId(1L);
        user.setCorreoElectronico("test@example.com");
        user.setContrasenia("password123"); // Recuerda encriptar al probar
        user.setTipoUsuario(new tipoUsuarioModel()); // Simula un tipo de usuario
    }

    @Test
    void updateUser_UserExists_ReturnsSuccessResponse() {
        when(userRepositorie.findById(user.getId())).thenReturn(Optional.of(user));

        JsonResponse response = userService.updateUser(user);

        assertEquals(500, response.getStatusCode());
        assertEquals("Usuario actualizado correctamente", response.getMensaje());
    }

    @Test
    void updateUser_UserDoesNotExist_ReturnsNotFoundResponse() {
        when(userRepositorie.findById(user.getId())).thenReturn(Optional.empty());

        JsonResponse response = userService.updateUser(user);

        assertEquals(404, response.getStatusCode());
        assertEquals("El usuario no existe", response.getMensaje());
    }

    @Test
    void updatePassword_PasswordMatches_ReturnsSuccessResponse() {
        when(userRepositorie.findById(user.getId())).thenReturn(Optional.of(user));
        user.setContrasenia("hashedPassword"); // Simula la contraseña hasheada
        when(userRepositorie.save(any(userModel.class))).thenReturn(user);

        JsonResponse response = userService.updatePassword("hashedPassword", "newPassword", user.getId());

        assertEquals(500, response.getStatusCode());
        assertEquals("Contraseña actualizada exitosamente", response.getMensaje());
    }

    @Test
    void updatePassword_ActualPasswordIncorrect_ReturnsErrorResponse() {
        when(userRepositorie.findById(user.getId())).thenReturn(Optional.of(user));
        user.setContrasenia("hashedPassword"); // Simula la contraseña hasheada

        JsonResponse response = userService.updatePassword("wrongPassword", "newPassword", user.getId());

        assertEquals(404, response.getStatusCode());
        assertEquals("La contraseña actual es incorrecta", response.getMensaje());
    }

    @Test
    void getUserById_UserExists_ReturnsUser() {
        when(userRepositorie.findById(user.getId())).thenReturn(Optional.of(user));

        Optional<userModel> result = userService.getUserById(user.getId());

        assertTrue(result.isPresent());
        assertEquals(user.getId(), result.get().getId());
    }

    @Test
    void getUserById_UserDoesNotExist_ThrowsException() {
        when(userRepositorie.findById(user.getId())).thenReturn(Optional.empty());

        assertThrows(ModelNotFoundException.class, () -> userService.getUserById(user.getId()));
    }

    @Test
    void saveUser_UserAlreadyExists_ThrowsException() {
        when(userRepositorie.findByCorreoElectronico(user.getCorreoElectronico())).thenReturn(Optional.of(user));

        assertThrows(ModelAlreadyExistException.class, () -> userService.saveUser(user));
    }

    @Test
    void saveUser_Success_ReturnsResponse() {
        when(tipoUsuarioRepository.findById(user.getTipoUsuario().getId())).thenReturn(Optional.of(new tipoUsuarioModel()));
        when(userRepositorie.findByCorreoElectronico(user.getCorreoElectronico())).thenReturn(Optional.empty());

        JsonResponse response = userService.saveUser(user);

        assertEquals(500, response.getStatusCode());
        assertEquals("Se ha enviado un codigo de verificacion al correo :" + user.getCorreoElectronico(), response.getMensaje());
    }

    // Agrega más pruebas según sea necesario para otros métodos de userService
}