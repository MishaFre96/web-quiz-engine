package engine.controller;

import engine.dto.RegistrationRequestDTO;
import engine.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author MishaFre96
 *
 * Controlador para registro de users.
 */
@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * POST/api/register
     * Registra nuevo user.
     * @param request JSON con email y password.
     * @return 200 OK si sale bien.
     */
    @PostMapping("/api/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequestDTO request){
        userService.registerUser(request.getEmail(), request.getPassword());
        return ResponseEntity.ok().build();
    }
}