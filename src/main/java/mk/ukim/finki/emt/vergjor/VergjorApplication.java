package mk.ukim.finki.emt.vergjor;

import mk.ukim.finki.emt.vergjor.repository.AccountActivationsRepository;
import mk.ukim.finki.emt.vergjor.repository.DepartmentRepository;
import mk.ukim.finki.emt.vergjor.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class VergjorApplication {

    public static void main(String[] args) {
        SpringApplication.run(VergjorApplication.class, args);
    }

}
