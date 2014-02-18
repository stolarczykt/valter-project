package pl.oakfusion.valter;

public class ViolationCase<T> {

	private final CaseBuilder<T> caseBuilder;
	private final String fieldName;
	private Object expected;

	public ViolationCase(CaseBuilder<T> caseBuilder, String fieldName) {
		this.caseBuilder = caseBuilder;
		this.fieldName = fieldName;
	}

	public FieldModifier shouldFailOnce() {
		this.expected = 1;
		return new FieldModifier<>(this, caseBuilder);
	}

	public FieldModifier shouldFailTimes(Integer expected) {
		this.expected = expected;
		return new FieldModifier<>(this, caseBuilder);
	}

	public FieldModifier shouldFailWith(Class expected) {
		this.expected = expected;
		return new FieldModifier<>(this, caseBuilder);
	}

	String getFieldName() {
		return fieldName;
	}

	Object getExpected() {
		return expected;
	}
}
