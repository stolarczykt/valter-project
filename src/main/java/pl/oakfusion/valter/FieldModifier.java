package pl.oakfusion.valter;

import com.rits.cloning.Cloner;
import java.lang.reflect.Field;

public class FieldModifier<T> {
    private static final Cloner CLONER = new Cloner();
    private final CaseBuilder<T> caseBuilder;
    private final ViolationCase<T> violationsCase;

    public FieldModifier(ViolationCase<T> violationsCase, CaseBuilder<T> caseBuilder) {
        this.violationsCase = violationsCase;
        this.caseBuilder = caseBuilder;
    }

    public CaseAppender<T> when(Object value) {

        T bean = CLONER.deepClone(caseBuilder.getBean());

        modifyFieldValue(value, bean);

        return new CaseAppender<>(violationsCase, caseBuilder, bean);
    }

    private void modifyFieldValue(Object value, T bean) {
        try {
            Class<?> c = bean.getClass();
            Field field = c.getDeclaredField(violationsCase.getFieldName());
            field.setAccessible(true);
            field.set(bean, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}
