package engine.controller;

import engine.dto.CompletedQuizDTO;
import engine.dto.QuizCreateDTO;
import engine.dto.SolveRequestDTO;
import engine.model.Quiz;
import engine.model.QuizCompletion;
import engine.model.User;
import engine.service.QuizService;
import engine.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

/**
 * @author MishaFre96
 *
 * Controlador REST para gestionar los cuestionarios.
 * Endpoints:
 * - POST /api/quizzes -> crear nuevo cuestionario
 * - GET /api/quizzes/{id} -> obtener cuestionario por id
 * - GET /api/quizzes?page=... -> listar todos los cuestionarios (paginado)
 * - POST /api/quizzes/{id}/solve -> resolver cuestionario
 * - GET /api/quizzes/completed?page=... -> historial de completados
 * - DELETE /api/quizzes/{id} -> borrar cuestionario propio
 */
@RestController
@RequestMapping("/api")
public class QuizController {

    private final QuizService quizService;
    private final UserService userService;

    public QuizController(QuizService quizService, UserService userService) {
        this.quizService = quizService;
        this.userService = userService;
    }

    /**
     * POST /api/quizzes
     * Crea un nuevo cuestionario a partir de los datos del DTO.
     * El campo answer es opcional; si no se envia, se guarda una lista vacia.
     * @param quizDto datos del cuestionario (sin id)
     * @return el cuestionario guardado (sin el campo answer en la respuesta)
     */
    @PostMapping("/quizzes")
    public Map<String, Object> createQuiz(@Valid @RequestBody QuizCreateDTO quizDto) {
        // Validaciones manuales
        if (quizDto.getTitle() == null || quizDto.getTitle().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title is required");
        }
        if (quizDto.getText() == null || quizDto.getText().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Text is required");
        }
        if (quizDto.getOptions() == null || quizDto.getOptions().length < 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Options must have at least 2 items");
        }

        // Obtener el User autenticado
        String authorEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        // Convertir DTO a entidad Quiz
        Quiz quiz = new Quiz();
        quiz.setTitle(quizDto.getTitle());
        quiz.setText(quizDto.getText());
        quiz.setOptions(new ArrayList<>(Arrays.asList(quizDto.getOptions())));

        List<Integer> answer = quizDto.getAnswer();
        if (answer == null) {
            answer = new ArrayList<>();
        }
        quiz.setAnswer(answer);
        quiz.setAuthor(authorEmail.toLowerCase());

        Quiz saved = quizService.createQuiz(quiz);

        Map<String, Object> response = new HashMap<>();
        response.put("id", saved.getId());
        response.put("title", saved.getTitle());
        response.put("text", saved.getText());
        response.put("options", saved.getOptions());
        return response;
    }

    /**
     * GET/api/quizzes/{id}
     * Obtiene un cuestionario por su id.
     * @param id identificador
     * @return el cuestionario encontrado (sin el campo answer)
     * @throws ResponseStatusException 404 si no existe
     */
    @GetMapping("/quizzes/{id}")
    public Map<String, Object> getQuiz(@PathVariable Long id) {
        Quiz quiz = quizService.getQuizById(id);
        Map<String, Object> response = new HashMap<>();
        response.put("id", quiz.getId());
        response.put("title", quiz.getTitle());
        response.put("text", quiz.getText());
        response.put("options", quiz.getOptions());
        return response;
    }

    /**
     * GET/api/quizzes?page=numero
     * Devuelve una pagina con 10 cuestionarios con el formato que pide el test.
     * Si no se envia el parametro page, se asume el primero (0).
     * @param page numero de pagina, empieza en 0.
     * @return pagina de cuestionarios.
     */
    @GetMapping("/quizzes")
    public Page<Quiz> getAllQuizzes(@RequestParam(defaultValue = "0") int page){
        return quizService.getAllQuizzes(PageRequest.of(page, 10));
    }

    /**
     * POST /api/quizzes/{id}/solve
     * Resuelve un cuestionario enviando un array de índices de respuestas.
     * Si se acierta, lo registra en el historial del usuario.
     * @param id identificador del cuestionario.
     * @param solveRequest objeto con el campo answer (lista de enteros).
     * @return mapa con success y feedback.
     * @throws ResponseStatusException 404 si el cuestionario no existe.
     */
    @PostMapping("/quizzes/{id}/solve")
    public Map<String, Object> solveQuiz(@PathVariable Long id,
                                         @RequestBody SolveRequestDTO solveRequest) {
        List<Integer> userAnswers = solveRequest.getAnswer();
        List<Integer> finalAnswers;
        if (userAnswers == null) {
            finalAnswers = new ArrayList<>();
        } else {
            finalAnswers = userAnswers;
        }
        boolean correct = quizService.checkAnswer(id, finalAnswers);

        if (correct) {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findByEmail(email);
            quizService.recordCompletion(user, id);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", correct);
        response.put("feedback", correct ? "Congratulations, you're right!" : "Wrong answer! Please, try again.");
        return response;
    }

    /**
     * GET/api/quizzes/completed?page=numero
     * Devuelve la pagina con los cuestionarios completados del usuario autenticado.
     *
     * @param page numero de pagina, empieza en 0.
     * @return pagina de DTOs con id y completedAt.
     */
    @GetMapping("/quizzes/completed")
    public Page<CompletedQuizDTO> getCompletedQuizzes(@RequestParam(defaultValue = "0") int page){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(email);
        Page<QuizCompletion> completionsPage = quizService.getCompletedQuizzes(user, PageRequest.of(page, 10));
        return completionsPage.map(completion ->
                new CompletedQuizDTO(completion.getQuiz().getId(), completion.getCompletedAt())
        );
    }

    /**
     * DELETE/api/quizzes/{id}
     * Elimina el cuestionario si el usuario autenticado es el autor.
     * @param id id del cuestionario.
     * @return 204 No Content si se elimina correctamente.
     * @throws ResponseStatusException 404 si no existe.
     * @throws ResponseStatusException 403 si no es el autor.
     */
    @DeleteMapping("/quizzes/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
        Quiz quiz = quizService.getQuizById(id);
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

        // Verificamos que el user autenticado es el autor
        if(!quiz.getAuthor().equals(currentUser)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        quizService.deleteQuizById(id);
        return ResponseEntity.noContent().build();
    }
}