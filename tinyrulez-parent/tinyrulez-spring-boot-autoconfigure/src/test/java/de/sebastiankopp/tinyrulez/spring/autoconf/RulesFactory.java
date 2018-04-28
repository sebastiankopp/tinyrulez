package de.sebastiankopp.tinyrulez.spring.autoconf;

import static de.sebastiankopp.tinyrulez.core.DefaultValidationResult.ok;
import static de.sebastiankopp.tinyrulez.core.DefaultValidationResult.withInsufficiency;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import de.sebastiankopp.tinyrulez.core.Rule;
import de.sebastiankopp.tinyrulez.core.RulezBuilder;

@Configuration
public class RulesFactory {

	@Scope(scopeName=SCOPE_PROTOTYPE)
	@Bean(name="strRule")
	public Rule<String> numericMaxLengthRule() {
		return RulezBuilder.<String>and()
				.rule(s -> s.matches("\\d+") ? ok() : withInsufficiency("String not dec"))
				.rule(s -> s.length() < 10 ? ok() : withInsufficiency("too long"))
			.build();
	}
	
}
