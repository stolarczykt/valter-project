package pl.oakfusion.valter;

public class CaseAppender<T> {
    FieldModifier fieldModifier;
    T bean;

    public CaseAppender(FieldModifier fieldModifier, T bean) {
        this.fieldModifier = fieldModifier;
        this.bean = bean;
    }

    public CaseBuilder withDescription(String description) {
        fieldModifier.violationsCase.caseBuilder.addToList(new Object[]{description, bean, fieldModifier.violationsCase.expected});
        return fieldModifier.violationsCase.caseBuilder;
    }
}
