package transaction.propagation;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
@RequiredArgsConstructor
@ConfigurationProperties
public class PropagationApplication {
    @Bean
    public BCrypt bCrypt() {
        return new BCrypt();
    }

    public static void main(String[] args) {
        SpringApplication.run(PropagationApplication.class, args);
    }

}
