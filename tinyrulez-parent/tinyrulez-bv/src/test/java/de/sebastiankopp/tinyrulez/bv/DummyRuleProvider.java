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

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import de.sebastiankopp.tinyrulez.core.Rule;

/**
 * @author sebi
 *
 */
//@MetaInfServices // does not work for test sources
public class DummyRuleProvider implements RuleProvider {

	private static final Map<String,Supplier<? extends Rule<?>>> rules = new HashMap<>();
	
	public static void addRule(String name, Supplier<? extends Rule<?>> rule) {
		rules.put(name, rule);
	}
	
	@Override
	public Rule<?> getRuleByName(String ruleName) {
		final Supplier<? extends Rule<?>> supplier = rules.get(ruleName);
		return supplier != null ? supplier.get() : null;
	}
	

}
