package engine.dto;

import java.util.List;

/**
 * @author MishaFre96
 *
 * DTO para la creacion de un nuevo cuestionario.
 */
public class QuizCreateDTO {

    private String title;
    private String text;
    private String[] options;
    // El campo answer es opcional, puede ser null o lista vacía.
    private List<Integer> answer;

    // Getters y setters
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getText() {return text;}
    public void setText(String text) {this.text = text;}

    public String[] getOptions() {return options;}
    public void setOptions(String[] options) {this.options = options;}

    public List<Integer> getAnswer() {return answer;}
    public void setAnswer(List<Integer> answer) {this.answer = answer;}
}
