package pl.oakfusion.valter;

import com.rits.cloning.Cloner;
import java.lang.reflect.Field;

import static java.lang.String.format;
import static org.junit.Assert.fail;

public class FieldModifier<T> {
    private static final Cloner CLONER = new Cloner();
    private static final String NO_SUCH_FIELD_FAIL_MESSAGE_FORMAT = "Maybe, probably field: '%s' was not found.";
    private static final String CAN_NOT_ACCESS_FIELD_MESSAGE_FORMAT = "Can not access field: '%s'";
    private static final String CAN_NOT_SET_FIELD_MESSAGE_FORMAT = "Can not set field '%s', type mismatch.";
    private final CaseBuilder<T> caseBuilder;
    private final ViolationCase<T> violationsCase;

    public FieldModifier(ViolationCase<T> violationsCase, CaseBuilder<T> caseBuilder) {
        this.violationsCase = violationsCase;
        this.caseBuilder = caseBuilder;
    }

    public CaseAppender<T> when(Object value) {

        T bean = CLONER.deepClone(caseBuilder.getBean());

        modifyFieldValue(value, bean, violationsCase.getFieldName());

        return new CaseAppender<>(violationsCase, caseBuilder, bean);
    }

    private void modifyFieldValue(Object value, T bean, String fieldName) {
        try {
            Class<?> c = bean.getClass();
            Field field = c.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(bean, value);
        } catch (NoSuchFieldException e) {
            fail(format(NO_SUCH_FIELD_FAIL_MESSAGE_FORMAT, fieldName));
        } catch (IllegalAccessException e) {
            fail(format(CAN_NOT_ACCESS_FIELD_MESSAGE_FORMAT, fieldName));
        } catch (IllegalArgumentException e) {
            fail(format(CAN_NOT_SET_FIELD_MESSAGE_FORMAT, fieldName));
        }
    }
}
