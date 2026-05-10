package engine.dto;

/**
 * @author MishaFre96
 *
 * DTO para recibir los datos de registro de un nuevo user.
 */
public class RegistrationRequestDTO {

    private String email;
    private String password;

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
}