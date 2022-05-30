package org.zerock.b2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing   // auding 기능을 활성화하겠다
public class B2Application {

    public static void main(String[] args) {
        SpringApplication.run(B2Application.class, args);
    }

}
