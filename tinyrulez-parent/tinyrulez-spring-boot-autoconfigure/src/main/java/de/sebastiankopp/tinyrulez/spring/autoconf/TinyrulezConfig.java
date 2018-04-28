package de.sebastiankopp.tinyrulez.spring.autoconf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TinyrulezConfig {

	@Bean(name="appCtxProvider")
	public ApplicationContextProvider appCtxProvider() {
		return new ApplicationContextProvider();
	}
	
	
}
