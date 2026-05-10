package engine.repository;

import engine.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author MishaFre96
 *
 * Repositorio para la entidad User.
 * Contiene metodos para buscar User por email ignorando mayus y mins.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca un usuario por su email.
     * @param email email del User.
     * @return Optional con el User si existe, vacio si no.
     * Se usa Optional para evitar NullPointerException.
     */
    Optional<User> findByEmailIgnoreCase(String email);

    /**
     * Comprueba que exista un User con ese email.
     * @param email email a comproba.
     * @return true si existe, falso de lo contrario.
     */
    boolean existsByEmailIgnoreCase(String email);
}