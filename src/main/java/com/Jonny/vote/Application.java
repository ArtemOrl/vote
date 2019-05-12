package com.Jonny.vote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.Jonny.vote.entity.dbutil.LocalDateConverter;
@SpringBootApplication
@EnableJpaRepositories("com.Jonny.vote.repository")

/**
 * Explicitly configure @EntityScan to enable the JSR-310 JPA 2.1 converters
 * @EntityScan(basePackages = "ru.gkislin.voting.model", basePackageClasses = {Jsr310JpaConverters.class})
 * Commented as LocalDate mapped to Timestamp. Map via {@link LocalDateConverter}
 */
public class Application {

	public static void main(String[] args) { SpringApplication.run(Application.class, args); }

}
