package engine.service;

import engine.model.Quiz;
import engine.model.QuizCompletion;
import engine.model.User;
import engine.repository.QuizCompletionRepository;
import engine.repository.QuizRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author MishaFre96
 *
 * Servicio que gestiona la lógica de negocio de los cuestionarios.
 */
@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizCompletionRepository completionRepository;

    public QuizService(QuizRepository quizRepository,
                       QuizCompletionRepository completionRepository) {
        this.quizRepository = quizRepository;
        this.completionRepository = completionRepository;
    }

    /**
     * Guarda un nuevo cuestionario.
     * @param quiz objeto quiz (sin id) recibido del cliente
     * @return el quiz guardado con el id asignado
     */
    public Quiz createQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    /**
     * Busca un cuestionario por su id.
     * @param id identificador
     * @return el cuestionario
     * @throws ResponseStatusException 404 si no existe
     */
    public Quiz getQuizById(Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /**
     * Devuelve una pagina con todos los cuestionario.
     * Pageable trae nummero de pagina y tamaño por defecto.
     * @param pageable objeto con la configuracion de la pagina.
     * @return pagina con los cuestionarios y los datos que pide el test.
     */
    public Page<Quiz> getAllQuizzes(Pageable pageable) {
        return quizRepository.findAll(pageable);
    }

    /**
     * Comprueba si las respuestas del usuario son correctas para un cuestionario.
     * @param id identificador del cuestionario.
     * @param userAnswers lista de índices elegidos por el usuario.
     * @return true si son correctas (mismo tamaño y todos los indices coinciden), false en caso contrario
     * @throws ResponseStatusException 404 si el cuestionario no existe.
     */
    public boolean checkAnswer(Long id, List<Integer> userAnswers) {
        Quiz quiz = getQuizById(id);
        List<Integer> correctAnswers = quiz.getAnswer();

        // Comparamos el tamaño de ambas listas
        if (userAnswers.size() != correctAnswers.size()) {
            return false;
        }
        // Comprobamos que cada respuesta del usuario esta en la lista de correctas
        for (int answer : userAnswers) {
            if (!correctAnswers.contains(answer)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Guarda un cuestionario realizado.
     * Se llama cuando el usuario acierta la respuesta.
     * @param user usuario autenticado.
     * @param quizId id del cuestionario resuelto.
     * @throws ResponseStatusException 404 si el cuestionario no existe.
     */
    public void recordCompletion(User user, Long quizId) {
        Quiz quiz = getQuizById(quizId);
        QuizCompletion completion = new QuizCompletion(quiz, user, LocalDateTime.now());
        completionRepository.save(completion);
    }

    /**
     * Devuelve una pagina con los cuestionarios completados de un usuario,
     * ordenados de reciente a antiguo.
     * @param user usuario del que queremos el historial.
     * @param pageable objeto con la configuracion de la pagina.
     * @return pagina con los cuestionario completados.
     */
    @Transactional(readOnly = true)
    public Page<QuizCompletion> getCompletedQuizzes(User user, Pageable pageable){
        return completionRepository.findByUserOrderByCompletedAtDesc(user, pageable);
    }

    /**
     * Elimina un cuestionario por su id.
     * @param id id del cuestionario.
     * @throws ResponseStatusException 404 si no existe
     */
    @Transactional
    public void deleteQuizById(Long id) {
        if (!quizRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        completionRepository.deleteByQuizId(id);
        quizRepository.deleteById(id);
    }
}