package pl.oakfusion.valter;

public class ViolationCase<T> {

    CaseBuilder<T> caseBuilder;
    String fieldName;
    Object expected;

    public ViolationCase(CaseBuilder<T> caseBuilder, String fieldName) {
        this.caseBuilder = caseBuilder;
        this.fieldName = fieldName;
    }

    public FieldModifier shouldFailOnce() {
        this.expected = new Integer(1);
        return new FieldModifier<T>(this);
    }

    public FieldModifier shouldFailTimes(Integer expected) {
       this.expected = expected;
        return new FieldModifier<T>(this);
    }

    public FieldModifier shouldFailWith(Class expected) {
       this.expected = expected;
        return new FieldModifier<T>(this);
    }
}
