package pl.oakfusion.valter;

public class CaseAppender<T> {
	private final ViolationCase<T> violationCase;
	private final CaseBuilder<T> caseBuilder;
	private final T bean;

	public CaseAppender(ViolationCase<T> violationCase, CaseBuilder<T> caseBuilder, T bean) {
		this.violationCase = violationCase;
		this.caseBuilder = caseBuilder;
		this.bean = bean;
	}

	public CaseBuilder withDescription(String description) {
		caseBuilder.addToList(new Object[]{description, bean, violationCase.getExpected()});
		return caseBuilder;
	}
}
