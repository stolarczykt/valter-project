package pl.oakfusion.valter;

public class ViolationsCountCase {

    private String description;

    public ViolationsCountCase(String description) {
        this.description = description;
    }

    public CaseBuilder expectsViolations(int expectedViolationsCount) {
        return new CaseBuilder(description, expectedViolationsCount);
    }

    public CaseBuilder expectsOneViolation() {
        return new CaseBuilder(description, 1);
    }
}
