package de.sebastiankopp.tinyrulez.core;

@FunctionalInterface
public interface Rule<T> {

	ValidationResult test(T obj);
	@SuppressWarnings("unchecked")
	default ValidationResult testTypeUnsafe(Object obj) {
		try {
			return test((T) obj);
		} catch (ClassCastException e) {
			return DefaultValidationResult.withInsufficiency("The object " + obj + " is not mappable to the desired type "
					+ getClass().getGenericInterfaces());
		}
	}
}
