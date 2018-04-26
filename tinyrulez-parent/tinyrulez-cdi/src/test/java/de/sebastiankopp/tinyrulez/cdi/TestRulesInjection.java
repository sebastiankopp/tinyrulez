package de.sebastiankopp.tinyrulez.cdi;

import java.util.UUID;

import javax.inject.Inject;
import javax.validation.ValidationException;

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
	
	@Test(expectedExceptions=ValidationException.class)
	public void testInvalid() {
		clientBean.gimmeSomething("hans");
	}
	
	
}
