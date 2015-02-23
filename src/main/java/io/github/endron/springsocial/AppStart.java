package io.github.endron.springsocial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.github.connect.GitHubConnectionFactory;

@SpringBootApplication
@EnableSocial
public class AppStart extends SocialConfigurerAdapter {

	public static void main(final String... args) {
		SpringApplication.run(AppStart.class, args);
	}

	@Bean
	public ConnectController connectController(final ConnectionFactoryLocator connectionFactoryLocator,
			final ConnectionRepository connectionRepository) {
		ConnectController controller = new ConnectController(connectionFactoryLocator, connectionRepository);
		controller.setApplicationUrl("http://localhost:8080/");
		return controller;
	}

	@Bean
	public SocialConfigurer socialConfiguterAdapter() {
		return new SocialConfigurerAdapter() {
			@Override
			public UserIdSource getUserIdSource() {
				return new UserIdSource() {
					@Override
					public String getUserId() {
						return "testuser";
					}
				};
			}

			@Override
			public void addConnectionFactories(final ConnectionFactoryConfigurer connectionFactoryConfigurer,
					final Environment environment) {
				final GitHubConnectionFactory githubConnectionFactory = new GitHubConnectionFactory("", "");
				connectionFactoryConfigurer.addConnectionFactory(githubConnectionFactory);
			}
		};
	}
}
