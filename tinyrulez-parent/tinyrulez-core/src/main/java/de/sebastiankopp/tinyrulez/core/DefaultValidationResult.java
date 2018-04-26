package de.sebastiankopp.tinyrulez.core;

import static java.util.Objects.requireNonNull;

public class DefaultValidationResult implements ValidationResult {

	private final String msg;
	
	private DefaultValidationResult(String msg) {
		this.msg = msg;
	}
	
	public static DefaultValidationResult ok() {
		return new DefaultValidationResult(null);
	}
	
	public static DefaultValidationResult withInsufficiency(String msg) {
		return new DefaultValidationResult(requireNonNull(msg));
	}

	@Override
	public boolean isResultPositive() {
		return msg == null;
	}

	@Override
	public String getMessage() {
		return msg;
	}
}
