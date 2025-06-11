package nashtech.training.ordersystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OrderSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderSystemApplication.class, args);
	}

}
