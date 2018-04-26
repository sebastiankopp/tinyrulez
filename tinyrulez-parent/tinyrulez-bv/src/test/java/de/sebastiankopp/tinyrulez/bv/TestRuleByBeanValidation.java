/*
 * Copyright (C) Sebastian Kopp, 2018
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package de.sebastiankopp.tinyrulez.bv;

import static de.sebastiankopp.tinyrulez.core.DefaultValidationResult.ok;
import static de.sebastiankopp.tinyrulez.core.DefaultValidationResult.withInsufficiency;
import static javax.validation.Validation.buildDefaultValidatorFactory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.not;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import de.sebastiankopp.tinyrulez.core.Rule;

/**
 * @author sebi
 *
 */
public class TestRuleByBeanValidation {
	
	private Validator validator = null;

	@BeforeMethod(alwaysRun=true)
	void initRules() {
		DummyRuleProvider.addRule("decimal", this::createRuleDecimalString);
		validator = buildDefaultValidatorFactory().getValidator();
	}
	
	@Test
	public void testInvalidInput() {
		Set<ConstraintViolation<SthToBeValidated>> violations = validator.validate(new SthToBeValidated("bla"));
		assertThat(violations, not(emptyIterable()));
//		assertEquals(violations.iterator().next().getMessage(), "The given string is not pure decimal");
	}
	
	@Test
	public void testValidInput() {
		Set<ConstraintViolation<SthToBeValidated>> violations = validator.validate(new SthToBeValidated("1230"));
		assertThat(violations, emptyIterable());
	}

	private Rule<String> createRuleDecimalString() {
		return s -> s.matches("\\d+") ? ok() : withInsufficiency("The given string is not pure decimal");
	}
}
