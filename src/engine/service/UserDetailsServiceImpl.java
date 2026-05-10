package engine.service;

import engine.model.User;
import engine.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author MishaFre96
 *
 * Servicio que carga los datos de un usuario desde la base de datos
 * para que Spring Security pueda autenticarlo mediante Basic Auth.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Busca un user por su email.
     * @param email del usuario.
     * @return objeto UserDetails con email, password y rol.
     * @throws UsernameNotFoundException si el usuario no existe.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        // Convertimos nuestra entidad User al UserDetails que Spring Security necesita
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }
}