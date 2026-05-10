package engine.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author MishaFre96
 *
 * Esta clase captura las excepciones que se lancen en cualquier controlador
 * y las convierte en respuestas HTTP con el codigo de estado adecuado.
 * Asi evitamos que Spring redirija al /error y nos meta en problemas con la seguridad.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Atrapa cualquier ResponseStatusException y devuelve una respuesta vacia
     * con el mismo codigo de estado que definimos al lanzarla.
     * Por ejemplo, si lanzamos un BAD_REQUEST, la respuesta sera un 400 sin cuerpo.
     *
     * @param ex excepcion que se ha lanzado.
     * @return ResponseEntity vacia con el codigo de estado correspondiente.
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Void> handleResponseStatusException(ResponseStatusException ex) {
        /**
         * Obtenemos el código de estado que pusimos en el throw (ej: HttpStatus.BAD_REQUEST)
         * y lo devolvemos tal cual, sin contenido extra.
         */
        return ResponseEntity.status(ex.getStatusCode()).build();
    }
}