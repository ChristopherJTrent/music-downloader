package net.arkevorkhat.music_downloader.configuration;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class DataSourceBean {
	@ConfigurationProperties(prefix = "spring.datasource")
	@Bean
	@Primary
	DataSource getDataSource() {
		var env = System.getenv();
		return DataSourceBuilder.create()
			.url(env.getOrDefault("DATABASE_URL", "jdbc:h2:file:/data/db"))
			.username(env.getOrDefault("DATABASE_USERNAME", "music_downloader"))
			.password(env.getOrDefault("DATABASE_PASSWORD", "adminPassword"))
		.build();
	}
}
