package engine.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * @author MishaFre96
 *
 * Representa un cuestionario completado correctamente por un usuario.
 * Guarda la fecha en la que se completo el cuestionario.
 */
@Entity
@Table(name = "quiz_completions")
public class QuizCompletion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime completedAt;

    // Constructor vacio para JPA
    public QuizCompletion() {}

    public QuizCompletion(Quiz quiz, User user, LocalDateTime completedAt) {
        this.quiz = quiz;
        this.user = user;
        this.completedAt = completedAt;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public Quiz getQuiz() {return quiz;}
    public void setQuiz(Quiz quiz) {this.quiz = quiz;}

    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}

    public LocalDateTime getCompletedAt() {return completedAt;}
    public void setCompletedAt(LocalDateTime completedAt) {this.completedAt = completedAt;}
}