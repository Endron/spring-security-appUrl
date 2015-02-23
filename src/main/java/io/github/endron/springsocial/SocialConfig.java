package io.github.endron.springsocial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.github.connect.GitHubConnectionFactory;

@Configuration
@EnableSocial
public class SocialConfig {
	
	@Autowired
	Environment environment;
	
	@Autowired
	ConnectionFactoryLocator connectionFactoryLocator;
	
	@Autowired
	ConnectionRepository connectionRepository;

	@Bean
    public ConnectController connectController() {
        ConnectController controller = new ConnectController(connectionFactoryLocator, connectionRepository);
        controller.setApplicationUrl(environment.getProperty("application.url"));
        return controller;
    }
	
	@Bean
	SocialConfigurer socialConfigurer() {
		return new SocialConfigurer() {
			
			@Override
			public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
				return new InMemoryUsersConnectionRepository(connectionFactoryLocator);
			}
			
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
			public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
				final GitHubConnectionFactory githubConnectionFactory = new GitHubConnectionFactory("", "");
				connectionFactoryConfigurer.addConnectionFactory(githubConnectionFactory);
			}
		};
	}
}
