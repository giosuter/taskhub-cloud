package ch.devprojects.taskhub.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevFlywayConfig {

	@Bean
	FlywayMigrationStrategy cleanMigrateWhenAsked(@Value("${app.db.recreate:false}") boolean recreate) {
		return flyway -> {
			if (!recreate) {
				flyway.migrate();
				return;
			}
			// recreate requested -> temporarily allow clean even if disabled in properties
			Flyway tmp = Flyway.configure().configuration(flyway.getConfiguration()).cleanDisabled(false).load();
			tmp.clean();
			tmp.migrate();
		};
	}
}