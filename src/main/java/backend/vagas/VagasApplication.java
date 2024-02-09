package backend.vagas;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Vagas", description = "API RocketSeat", version = "1"))

public class VagasApplication {

    public static void main(String[] args) {
        SpringApplication.run(VagasApplication.class, args);
    }
}
