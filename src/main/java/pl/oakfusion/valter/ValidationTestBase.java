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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public abstract class ValidationTestBase<T> {

    protected static final String TEST_PARAMETERS_LOG_FORMAT = "{index}: {0}";

    private static final String DEFAULT_C_TOR_INITIALIZED_DESCRIPTION = "default c-tor";
    private static final String VALID_OBJECT_DESCRIPTION = "valid object";
    private static final int VALID_OBJECT_VIOLATIONS_COUNT = 0;
    private static final int DEFAULT_VIOLATIONS_COUNT = 1;

    private static final Logger LOG = LoggerFactory.getLogger(ValidationTestBase.class);

    protected static Validator validator;

    private T bean;
    private String description;
    private Object expectedViolation;

    protected static List<Object[]> testCases(Object[]... beans) {
        ArrayList<Object[]> list = new ArrayList<Object[]>();
        list.addAll(asList(beans));
        return list;
    }

    protected static <T> Object[] testCase(String description, T bean, int violationsCount) {
        return new Object[]{description, bean, violationsCount};
    }

    protected static <T> Object[] testCase(String description, T bean) {
        return testCase(description, bean, DEFAULT_VIOLATIONS_COUNT);
    }

    protected static <T> Object[] validObjectTestCase(T bean) {
        return testCase(VALID_OBJECT_DESCRIPTION, bean, VALID_OBJECT_VIOLATIONS_COUNT);
    }

    protected static <T> Object[] defaultCtorTestCase(T bean) {
        return defaultConstructorTestCase(bean, DEFAULT_VIOLATIONS_COUNT);
    }

    protected static <T> Object[] defaultConstructorTestCase(T bean, int violationsCount) {
        return testCase(DEFAULT_C_TOR_INITIALIZED_DESCRIPTION, bean, violationsCount);
    }

    protected static <T> CaseBuilder forBean(T bean) {
        return new CaseBuilder<T>(bean);
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
        if(expectedViolation instanceof Integer) {
            assertEquals(expectedViolation, violations.size());
        } else {
            assertTrue(violationsContainsAnnotation(violations, expectedViolation));
        }
    }

    private boolean violationsContainsAnnotation(Set<ConstraintViolation<T>> violations, Object expectedViolation) {
        for (ConstraintViolation<T> violation : violations) {
            Class<? extends Annotation> annotatoinType = violation.getConstraintDescriptor().getAnnotation().annotationType();
            if(annotatoinType.equals(expectedViolation)) {
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

