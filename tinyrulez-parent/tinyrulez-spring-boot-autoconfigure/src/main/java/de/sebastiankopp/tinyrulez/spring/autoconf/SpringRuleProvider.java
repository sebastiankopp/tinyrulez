package de.sebastiankopp.tinyrulez.spring.autoconf;

import org.kohsuke.MetaInfServices;
import org.springframework.context.ApplicationContext;
import de.sebastiankopp.tinyrulez.bv.RuleProvider;
import de.sebastiankopp.tinyrulez.core.Rule;

@MetaInfServices
public class SpringRuleProvider implements RuleProvider {

	@Override
	public Rule<?> getRuleByName(String ruleName) {
		ApplicationContext applicationContext = ApplicationContextProvider.getCtx();
		return applicationContext.getBean(ruleName, Rule.class);
	}

}
