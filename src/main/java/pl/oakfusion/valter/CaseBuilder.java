package pl.oakfusion.valter;

public class CaseBuilder {

    private final String description;
    private final int expectedViolationsCount;

    public CaseBuilder(String description, int expectedViolationsCount) {
        this.description = description;
        this.expectedViolationsCount = expectedViolationsCount;
    }

    public <T> Object[] forBean(T bean) {
        return new Object[]{description, bean, expectedViolationsCount};
    }
}
