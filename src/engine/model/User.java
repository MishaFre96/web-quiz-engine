package engine.model;

import jakarta.persistence.*;

/**
 * @author MishaFre96
 *
 * Entidad que representa un usuario registrado.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // Constructor vacio
    public User() {}

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
}