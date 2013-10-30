package pl.oakfusion.valter;

public class CaseBuilder<T> extends BaseCaseBuilder {

    private final T bean;

    public CaseBuilder(T bean) {
        this.bean = bean;
    }

    public ViolationCase field(String fieldName) {
        return new ViolationCase<>(this, fieldName);
    }

    T getBean() {
        return bean;
    }
}
