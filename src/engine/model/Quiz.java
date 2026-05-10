package engine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

/**
 * @author MishaFre96
 *
 * Clase que representa un cuestionario.
 * Contiene titulo, texto de la pregunta y opciones de respuesta.
 */

@Entity
@Table(name = "quizzes")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String text;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "quiz_options", joinColumns = @JoinColumn(name = "quiz_id"))
    @Column(name = "option_text")
    @Fetch(FetchMode.SUBSELECT)
    private List<String> options;

    // Los campos answer y author no se envian al cliente gracias a @JsonIgnore.
    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "quiz_answers", joinColumns = @JoinColumn(name = "quiz_id"))
    @Column(name = "answer_index")
    @Fetch(FetchMode.SUBSELECT)
    private List<Integer> answer;

    @JsonIgnore
    @Column(name = "author_email", nullable = false)
    private String author;

    // Constructor vacio
    public Quiz(){}

    /**
     * Constructor del cuestionario.
     *
     * @param id id del cuestionario.
     * @param title titulo del cuestionario.
     * @param text pregunta.
     * @param options lista de respuestas (array de strings).
     * @param answer respuesta correcta.
     */
    public Quiz(Long id, String title, String text, List<String> options, List<Integer> answer) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    // Constructor sobrecargado con el campo autor
    public Quiz(Long id, String title, String text, List<String> options, List<Integer> answer, String author) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
        this.author = author;
    }

    // Getters y setters
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getText() {return text;}
    public void setText(String text) {this.text = text;}

    public List<String> getOptions() {return options;}
    public void setOptions(List<String> options) {this.options = options;}

    public List<Integer> getAnswer() {return answer;}
    public void setAnswer(List<Integer> answer) {this.answer = answer;}

    public String getAuthor() {return author;}
    public void setAuthor(String author) {this.author = author;}
}