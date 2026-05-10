package engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "engine")
public class WebQuizEngine {

    public static void main(String[] args) {
        SpringApplication.run(WebQuizEngine.class, args);
    }

}
