package kr.co.momdeal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:env.properties")
public class MomdealApplication {

	public static void main(String[] args) {
		SpringApplication.run(MomdealApplication.class, args);
	}

}
