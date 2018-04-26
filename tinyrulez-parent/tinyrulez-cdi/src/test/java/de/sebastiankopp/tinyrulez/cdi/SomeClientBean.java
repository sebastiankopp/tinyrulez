package de.sebastiankopp.tinyrulez.cdi;

import java.util.UUID;

import de.sebastiankopp.tinyrulez.bv.Conformous;

public class SomeClientBean {
	
	public String gimmeSomething(@Conformous(name="uuid") String input) {
		return UUID.randomUUID().toString();
	}

}
