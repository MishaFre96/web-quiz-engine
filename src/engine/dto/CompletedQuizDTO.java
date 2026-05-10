package engine.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * @author MishaFre96
 *
 * DTO para devolver el id del quiz y la fecha en la que se completo.
 */
public class CompletedQuizDTO {

    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime completedAt;

    public CompletedQuizDTO(Long id, LocalDateTime completedAt) {
        this.id = id;
        this.completedAt = completedAt;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public LocalDateTime getCompletedAt() {return completedAt;}
    public void setCompletedAt(LocalDateTime completedAt) {this.completedAt = completedAt;}
}