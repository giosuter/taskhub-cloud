package ch.devprojects.taskhub.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevFlywayConfig {

  @Bean
  public FlywayMigrationStrategy cleanMigrateStrategy() {
    return (Flyway flyway) -> {
      flyway.clean();    // drop everything
      flyway.migrate();  // rebuild from V1
    };
  }
}