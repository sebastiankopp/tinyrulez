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
