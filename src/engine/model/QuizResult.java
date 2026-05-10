package engine.model;

/**
 * @author MishaFre96
 *
 * Clase para la respuesta al resolver un cuestionario.
 * Contiene si la respuesta fue correcta y un mensaje de feedback.
 */
public class QuizResult {

    private boolean success;
    private String feedback;

    /**
     * Constructor del resultado.
     * @param success true si es correcto, false en caso contrario.
     * @param feedback mensaje informativo para el usuario.
     */
    public QuizResult(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    public boolean isSuccess() {return success;}

    public String getFeedback() {return feedback;}
}