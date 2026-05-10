package engine.repository;

import engine.model.Quiz;
import engine.model.QuizCompletion;
import engine.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author MishaFre96
 *
 * Repositorio para guardar y consultar cuestionarios completados.
 */
@Repository
public interface QuizCompletionRepository extends JpaRepository<QuizCompletion, Long> {

    /**
     * Busca los completos de un usuario concreto ordenados de
     * mas reciente a mas antiguo.
     *
     * @param user usuario del que queremos obtener el historial.
     * @param pageable objeto con la configuracion de pagina.
     * @return pagina con los registros de dicho usuario.
     */
    Page<QuizCompletion> findByUserOrderByCompletedAtDesc(User user, Pageable pageable);

    /**
     * Borra todas las finalizaciones de un cuestionario.
     * Se usa antes de eliminar el cuestionario para no tener problemas
     * con las claves foraneas.
     *
     * @param quizId id del cuestionario que queremos borrar.
     */
    void deleteByQuizId(Long quizId);
}