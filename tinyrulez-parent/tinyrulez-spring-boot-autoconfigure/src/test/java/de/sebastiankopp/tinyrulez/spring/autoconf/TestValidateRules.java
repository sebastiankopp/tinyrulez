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

import static org.testng.Assert.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import de.sebastiankopp.tinyrulez.core.Rule;
import de.sebastiankopp.tinyrulez.core.ValidationResult;

@SpringBootTest(classes= {TinyrulezConfig.class, RulesFactory.class})
public class TestValidateRules extends AbstractTestNGSpringContextTests {

	@Autowired
	@Qualifier("strRule")
	Rule<String> validationRule;
	
	@Test(dataProvider="dp1", dataProviderClass=TestValidateRules.class)
	public void test(String val, boolean expResult) {
		ValidationResult result1 = validationRule.test(val);
		assertEquals(result1.isResultPositive(), expResult);
	}
	
	@DataProvider(name="dp1")
	public static Object[][] provideData() {
		return new Object [][] {
			new Object[] {"abababsafdgaf", false},
			new Object[] {"123", true},
			new Object[] {"abab", false},
			new Object[] {"12345678912", false},
			new Object[] {"a23", false}
		};
	}
	
}
