package pl.oakfusion.valter;

import com.rits.cloning.Cloner;
import org.fest.reflect.reference.TypeRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.fest.reflect.core.Reflection.field;

public class FieldModifier<T> {
    private static final Cloner CLONER = new Cloner();
    ViolationCase<T> violationsCase;

    private static final Logger LOG = LoggerFactory.getLogger(FieldModifier.class);

    public FieldModifier(ViolationCase<T> violationsCase) {
        this.violationsCase = violationsCase;
    }

    public CaseAppender when(Object value) {

        T bean = CLONER.deepClone(violationsCase.caseBuilder.bean);

        field(violationsCase.fieldName).ofType(new TypeRef<Object>() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        }).in(bean).set(value);

        return new CaseAppender<T>(this, bean);
    }

}
