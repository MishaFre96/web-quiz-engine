package engine.repository;

import engine.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author MishaFre96
 *
 * Repositorio para la entidad cuestionario.
 * Genera IDs de forma automatica.
 * Permite guardar, buscar por ID y listar.
 */
@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
}