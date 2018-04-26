package de.sebastiankopp.tinyrulez.core;

import static org.testng.Assert.assertFalse;

import org.testng.annotations.Test;

public class TestRulezBuilder {
	@Test
	public void test1() {
		Rule<String> r_isStrLongEnough = s -> (s != null && s.length() >= 8) ?
				DefaultValidationResult.ok() : DefaultValidationResult.withInsufficiency("String too short");
		
		assertFalse(r_isStrLongEnough.test("quut").isResultPositive());
		
	}

}
