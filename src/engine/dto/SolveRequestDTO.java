package engine.dto;

import java.util.List;

/**
 * @author MishaFre96
 *
 * DTO para recibir el array de respuestas al resolver un cuestionario.
 */
public class SolveRequestDTO {

    private List<Integer> answer;

    public List<Integer> getAnswer() {return answer;}
    public void setAnswer(List<Integer> answer) {this.answer = answer;}
}