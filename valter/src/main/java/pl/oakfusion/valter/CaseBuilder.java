package pl.oakfusion.valter;

import java.util.ArrayList;
import java.util.List;

public class CaseBuilder<T> {

    private final T bean;
    private ArrayList<Object[]> list = new ArrayList<Object[]>();


    public CaseBuilder(T bean) {
        this.bean = bean;
    }

    public List<Object[]> toList() {
        return list;
    }

    public ViolationCase field(String fieldName) {
        return new ViolationCase<>(this, fieldName);
    }

    public void addToList(Object[] objects) {
        list.add(objects);
    }

    T getBean() {
        return bean;
    }
}
