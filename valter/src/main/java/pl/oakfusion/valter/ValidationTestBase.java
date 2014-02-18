package pl.oakfusion.valter;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.annotation.Annotation;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public abstract class ValidationTestBase<T> {

	protected static final String DEFAULT_TESTS_NAME_PATTERN = "{index}: {0}";

	private static final Logger LOG = LoggerFactory.getLogger(ValidationTestBase.class);

	protected static Validator validator;

	private T bean;
	private String description;
	private Object expectedViolation;

	protected static <T> CaseBuilder forBean(T bean) {
		return new CaseBuilder<>(bean);
	}

	public ValidationTestBase(String description, T bean, Object violationsCount) {
		this.description = description;
		this.bean = bean;
		this.expectedViolation = violationsCount;
	}

	private void logViolations(Set<ConstraintViolation<T>> violations) {
		for (ConstraintViolation<T> v : violations) {
			LOG.debug("    {}", v);
		}
	}

	public Set<ConstraintViolation<T>> validate() {
		Set<ConstraintViolation<T>> violations = validator.validate(bean);
		LOG.debug("{}; violations: {}; expected violations: {}", description, violations.size(), expectedViolation);
		logViolations(violations);
		return violations;
	}

	public void assertViolations(Set<ConstraintViolation<T>> violations) {
		if (expectedViolation instanceof Integer) {
			assertEquals(expectedViolation, violations.size());
		} else {
			assertTrue(violationsContainsAnnotation(violations, expectedViolation));
		}
	}

	private boolean violationsContainsAnnotation(Set<ConstraintViolation<T>> violations, Object expectedViolation) {
		for (ConstraintViolation<T> violation : violations) {
			Class<? extends Annotation> annotationType = violation.getConstraintDescriptor().getAnnotation().annotationType();
			if (annotationType.equals(expectedViolation)) {
				return true;
			}
		}
		return false;
	}

	@Test
	public void shouldValidate() {
		assertViolations(validate());
	}

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
}

