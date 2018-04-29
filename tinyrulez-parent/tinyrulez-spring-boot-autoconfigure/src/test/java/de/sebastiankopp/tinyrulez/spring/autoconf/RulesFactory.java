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
