package engine.service;

import engine.model.User;
import engine.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author MishaFre96
 *
 * Servicio que gestiona la busqueda y registro de usuarios.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registra nuevo User
     * @param email email del usuario con formato valido.
     * @param password contraseña valida de minimo 5 caracteres.
     * @throws ResponseStatusException 400 si el mail existe o
     * los datos no son validos.
     */
    public void registerUser(String email, String password) {
        // Validaciones simples
        if(email == null || email.isBlank() || !email.matches(".+@.+\\..+")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if(password == null || password.length() < 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmailIgnoreCase(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // Codificar password y guardar
        User user = new User(email.toLowerCase(), passwordEncoder.encode(password));
        userRepository.save(user);
    }

    /**
     * Buscar usuario por email.
     * @param email email del usuario a buscar.
     * @return user ya encontrado.
     * @throws ResponseStatusException 404 si no existe.
     */
    public User findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}