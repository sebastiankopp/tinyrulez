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

import java.util.ServiceLoader;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import de.sebastiankopp.tinyrulez.core.Rule;
import de.sebastiankopp.tinyrulez.core.ValidationResult;

public class RuleValidator implements ConstraintValidator<Conformous,Object>{

	private String msgTemplate = null;
	private Rule<?> rule = null;
	@Override
	public void initialize(Conformous constraintAnnotation) {
		final String msgFromAnnotation = constraintAnnotation.message();
		if (msgFromAnnotation != null && !msgFromAnnotation.isEmpty()) {
			msgTemplate = msgFromAnnotation;
		}
		final String ruleName = constraintAnnotation.name();
		rule = obtainRule(ruleName);
		if (rule == null) {
			throw new IllegalStateException("The rule " + ruleName + " is not properly defined!");
		}
	}

	private Rule<?> obtainRule(String name) {
		ServiceLoader<RuleProvider> serviceLoader = ServiceLoader.load(RuleProvider.class);
		return serviceLoader.iterator().next().getRuleByName(name);
	}
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (rule == null) {
			throw new IllegalStateException("No rule is given!");
		}
		ValidationResult validationResult = rule.testTypeUnsafe(value);
		if (validationResult.isResultPositive()) {
			return true;
		}
		context.buildConstraintViolationWithTemplate(msgTemplate != null ? msgTemplate : validationResult.getMessage());
		return false;
	}

}
