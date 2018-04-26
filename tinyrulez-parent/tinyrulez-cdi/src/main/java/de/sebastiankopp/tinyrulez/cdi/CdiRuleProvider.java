package de.sebastiankopp.tinyrulez.cdi;

import java.lang.annotation.Annotation;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.AnnotationLiteral;
import javax.enterprise.util.TypeLiteral;
import javax.inject.Named;

import org.kohsuke.MetaInfServices;

import de.sebastiankopp.tinyrulez.bv.RuleProvider;
import de.sebastiankopp.tinyrulez.core.Rule;

@MetaInfServices(RuleProvider.class)
public class CdiRuleProvider implements RuleProvider{

	@Override
	public Rule<?> getRuleByName(String name) {
		Instance<Rule<?>> preselect = CDI.current().select(new TypeLiteral<Rule<?>>() {
			private static final long serialVersionUID = 1L;
		});
		Instance<Rule<?>> subselect = preselect.select(new Named() {
			@Override
			public Class<? extends Annotation> annotationType() {
				return Named.class;
			}
			
			@Override
			public String value() {
				return name;
			}
		});
		if (!subselect.isUnsatisfied()) {
			return subselect.get();
		}
		return preselect.select(new AnnotationLiteral<Named>() {
			private static final long serialVersionUID = 1L;
		}).get();
	}

}
