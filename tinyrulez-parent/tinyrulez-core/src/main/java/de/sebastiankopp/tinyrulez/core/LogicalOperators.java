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

package de.sebastiankopp.tinyrulez.core;

import static de.sebastiankopp.tinyrulez.core.DefaultValidationResult.ok;
import static de.sebastiankopp.tinyrulez.core.DefaultValidationResult.withInsufficiency;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.stream.Collectors.partitioningBy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public enum LogicalOperators {

	AND, OR, NOT;
	
	public static class And<T> implements Rule<T> {
		private final List<Rule<? super T>> composedParts = new ArrayList<>();
		private final String msg;
		public And(Collection<? extends Rule<? super T>> rules, String msg) {
			composedParts.addAll(rules);
			this.msg = msg;
		}
		public And(Collection<? extends Rule<? super T>> rules) {
			this(rules, null);
		}
		
		public ValidationResult test(T obj) {
			Optional<ValidationResult> firstOneMismatching = composedParts.stream()
					.map(e -> e.test(obj))
					.filter(e -> !e.isResultPositive())
					.findFirst();
			if (firstOneMismatching.isPresent()) {
				return msg == null ? firstOneMismatching.get() : withInsufficiency(msg);
			} else {
				return ok();
			}
		}
	}
	
	public static class Or<T> implements Rule<T> {

		private final List<Rule<? super T>> composedParts = new ArrayList<>();
		private final String msg;
		public Or(Collection<? extends Rule<? super T>> rules, String msg) {
			composedParts.addAll(rules);
			this.msg = msg;
		}
		public Or(Collection<? extends Rule<? super T>> rules) {
			this(rules, null);
		}
		
		@Override
		public ValidationResult test(T obj) {
			Map<Boolean, List<ValidationResult>> resultsBySuccess = composedParts.stream()
					.map(e -> e.test(obj))
					.collect(partitioningBy(ValidationResult::isResultPositive));
			List<ValidationResult> violations = resultsBySuccess.get(FALSE);
			List<ValidationResult> successList = resultsBySuccess.get(TRUE);
			if (violations != null && !violations.isEmpty() && (successList == null || successList.isEmpty())) {
				return msg == null ? violations.stream().findFirst().get() : withInsufficiency(msg);
			}
			return ok();
		}
	}
	
	public static class Not<T> implements Rule<T> {
		private final Rule<? super T> negatedRule;
		private final String msg;
		public Not(Rule<? super T> rule) {
			this (rule, "The given rule " + rule  + " is fulfilled hence its negation is not");
		}
		public Not(Rule<? super T> rule, String msg) {
			negatedRule = rule;
			this.msg = msg;
		}
		
		
		@Override
		public ValidationResult test(T obj) {
			if (!negatedRule.test(obj).isResultPositive()) {
				return ok();
			}
			return DefaultValidationResult.withInsufficiency(msg);
		}
		
	}
}
