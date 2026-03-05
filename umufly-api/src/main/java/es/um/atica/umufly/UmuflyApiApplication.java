package es.um.atica.umufly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication( scanBasePackages = {
		"es.um.atica.umufly", "es.um.atica.fundewebjs"
} )
public class UmuflyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UmuflyApiApplication.class, args);
	}

}
