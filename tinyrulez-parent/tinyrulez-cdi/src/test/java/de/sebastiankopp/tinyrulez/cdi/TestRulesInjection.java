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

import java.util.UUID;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.beans11.BeansDescriptor;
import org.testng.annotations.Test;

public class TestRulesInjection extends Arquillian {

	@Inject
	SomeClientBean clientBean;
	
	@Deployment
	public static JavaArchive deploy() {
		String beansXml = Descriptors.create(BeansDescriptor.class)
				.beanDiscoveryMode("all")
				.exportAsString();
		return ShrinkWrap.create(JavaArchive.class)
				.addAsManifestResource(new StringAsset(beansXml), "beans.xml")
				.addClasses(CdiRuleProvider.class, RulesExposer.class, SomeClientBean.class);
	}
	
	@Test
	public void testOk() {
		clientBean.gimmeSomething(UUID.randomUUID().toString());
	}
	
	@Test(expectedExceptions=ConstraintViolationException.class)
	public void testInvalid() {
		clientBean.gimmeSomething("hans");
	}
}
