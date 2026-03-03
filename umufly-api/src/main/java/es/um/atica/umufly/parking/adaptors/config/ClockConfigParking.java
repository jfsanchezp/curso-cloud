package es.um.atica.umufly.parking.adaptors.config;

import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClockConfigParking {

	@Bean
	public Clock clockParking() {
		return Clock.systemDefaultZone();
	}

}
