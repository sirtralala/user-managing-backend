package at.technikumwien;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class DBInitializer {
	@Autowired
	private UserRepository userRepository;
		
	@EventListener(ApplicationReadyEvent.class)
	public void handleApplicationReady() {
		userRepository.saveAll(Arrays.asList(
			new User("Maria", "Testfrau", Date.from(Instant.now()), true),
			new User("Hans", "Testmann", Date.from(Instant.now()), true),
			new User("Paulus", "Manker", Date.from(Instant.now()), true),
			new User("Richard", "Dorfmeister", Date.from(Instant.now()), true)
		));
	}
}