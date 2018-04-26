package de.sebastiankopp.tinyrulez.cdi;

import static de.sebastiankopp.tinyrulez.core.DefaultValidationResult.ok;
import static de.sebastiankopp.tinyrulez.core.DefaultValidationResult.withInsufficiency;

import javax.enterprise.inject.Produces;
import javax.inject.Named;

import de.sebastiankopp.tinyrulez.core.Rule;

public class RulesExposer {

	@Produces
	@Named("uuid")
	public Rule<String> uuidRule() {
		return s -> s.matches("[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}") ?
				ok() : withInsufficiency("The given string " + s + " is not a valid UUID!");
	}
}
