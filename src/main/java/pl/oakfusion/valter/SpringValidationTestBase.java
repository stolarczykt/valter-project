package pl.oakfusion.valter;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;

import javax.validation.Validator;

@ContextConfiguration(locations = "classpath:validationContext.xml")
public class SpringValidationTestBase<T> extends ValidationTestBase<T>{

    @Autowired
    protected Validator validator;
    private TestContextManager testContextManager;

    public SpringValidationTestBase(String description, T bean, int violationsCount) {
        super(description, bean, violationsCount);
    }

    @Override
    public Validator getValidator() {
        return validator;
    }

    @Before
    public void setupContext() throws Exception {
        this.testContextManager = new TestContextManager(getClass());
        this.testContextManager.prepareTestInstance(this);
    }

}
